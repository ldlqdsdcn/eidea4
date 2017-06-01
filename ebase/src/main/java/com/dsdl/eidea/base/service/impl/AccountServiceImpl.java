package com.dsdl.eidea.base.service.impl;

import com.dsdl.eidea.base.def.ActivateDef;
import com.dsdl.eidea.base.entity.po.RolePo;
import com.dsdl.eidea.base.entity.po.UserPo;
import com.dsdl.eidea.base.entity.po.UserRolePo;
import com.dsdl.eidea.base.exception.ServiceException;
import com.dsdl.eidea.base.service.AccountService;
import com.dsdl.eidea.core.dao.CommonDao;
import com.dsdl.eidea.core.spring.annotation.DataAccess;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 刘大磊 on 2017/5/19 14:20.
 */
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private IdentityService identityService;
    @DataAccess(entity = RolePo.class)
    private CommonDao<RolePo, Integer> roleDao;
    @DataAccess(entity = UserPo.class)
    private CommonDao<UserPo, Integer> userDao;

    /**
     * 保存用户信息，并且同步用户信息到activiti的identity.User和identify.Group
     *
     */
    public void saveUser(Integer userId) {
        String userIdStr=String.valueOf(userId);
        // 同步数据到Activiti Identify模块
            UserPo userPo=userDao.find(userId);
            UserQuery userQuery = identityService.createUserQuery();
            List<org.activiti.engine.identity.User> activitiUsers = userQuery.userId(userPo.getUsername()).list();

            List<UserRolePo> userRolePoList= userPo.getSysUserRoles();
            List<Group> groupList = identityService.createGroupQuery().list();
            for(Group group:groupList)
            {
                identityService.deleteMembership(userPo.getUsername(),group.getId());
            }
            User activitiUser=null;
            if(activitiUsers.size()==0)
            {
                activitiUser=identityService.newUser(userPo.getUsername());
            }
            else
            {
                activitiUser=activitiUsers.get(0);
            }
        activitiUser.setFirstName(userPo.getName());
        activitiUser.setLastName(StringUtils.EMPTY);
        activitiUser.setPassword(StringUtils.EMPTY);
        activitiUser.setEmail(userPo.getEmail());

        identityService.saveUser(activitiUser);
        for(UserRolePo userRolePo:userRolePoList)
        {
            log.warn("userId="+userIdStr+" | roleId="+userRolePo.getSysRole().getId());
            identityService.createMembership(userPo.getUsername(),userRolePo.getSysRole().getNo());
        }

    }
    public void saveRole(Integer roleId)
    {
        String roleIdStr=String.valueOf(roleId);
        RolePo rolePo=roleDao.find(roleId);
        List<Group> groupList=identityService.createGroupQuery().groupId(roleIdStr).list();
        Group group=null;
        if(groupList.size()==0)
        {
            group=identityService.newGroup(rolePo.getId().toString());
        }
        else
        {
            group=groupList.get(0);
        }
        group.setName(rolePo.getName());
        group.setType("");
        identityService.saveGroup(group);


    }

    /**
     *
     * @param roleId
     */
    public void deleteRole(Integer roleId)
    {
        RolePo rolePo=roleDao.find(roleId);
        identityService.deleteGroup(rolePo.getNo());
    }


    @Override
    public void deleteUser(Integer userId) throws ServiceException {
        UserPo userPo = userDao.find(userId);
        /**
         * 同步删除Activiti User Group
         */
            // 同步删除Activiti User
            List<UserRolePo> roleList = userPo.getSysUserRoles();
            for (UserRolePo role : roleList) {
                identityService.deleteMembership(userPo.getUsername(), role.getSysRole().getNo());
            }

            // 同步删除Activiti User
            identityService.deleteUser(userPo.getUsername());



    }

    @Override
    public void saveSynAllUserAndRoleToActiviti() {

        // 清空工作流用户、角色以及关系
        deleteAllActivitiIdentifyData();

        // 复制角色数据
        synRoleToActiviti();

        // 复制用户以及关系数据
        synUserWithRoleToActiviti();
    }

    /**
     * 同步所有角色数据到{@link Group}
     */
    private void synRoleToActiviti() {
        List<RolePo> allRole = roleDao.findAll();
        for (RolePo role : allRole) {
            if (ActivateDef.ACTIVATED.getKey().equals(role.getIsactive())) {
                Group group = identityService.newGroup(role.getNo());
                group.setName(role.getName());
                group.setType("");
                identityService.saveGroup(group);
            }

        }
    }

    /**
     * 复制用户以及关系数据
     */
    private void synUserWithRoleToActiviti() {
        List<UserPo> allUser = userDao.findAll();
        for (UserPo user : allUser) {
            if (ActivateDef.ACTIVATED.getKey().equals(user.getIsactive())) {
                // 添加一个用户到Activiti
                saveUser(user.getId());
            }
        }
    }

    @Override
    public void deleteAllActivitiIdentifyData() {

        List<Group> groupList = identityService.createGroupQuery().list();
        List<User> userList = identityService.createUserQuery().list();
        for (Group group : groupList) {
            for (User user : userList) {
                identityService.deleteMembership(user.getId(), group.getId());
            }
        }
        for (User user : userList) {
            identityService.deleteUser(user.getId());
        }
        for (Group group : groupList) {
            identityService.deleteGroup(group.getId());
        }
    }
}
