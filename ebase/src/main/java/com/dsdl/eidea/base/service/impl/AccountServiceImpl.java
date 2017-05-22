package com.dsdl.eidea.base.service.impl;

import com.dsdl.eidea.base.dao.IdentityDao;
import com.dsdl.eidea.base.def.ActivateDef;
import com.dsdl.eidea.base.entity.bo.UserBo;
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
import org.apache.commons.lang3.builder.ToStringBuilder;
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
    @Autowired
    private IdentityDao identityDao;

    /**
     * 保存用户信息，并且同步用户信息到activiti的identity.User和identify.Group
     *
     * @param user          用户对象{@link UserBo}
     * @param roleIds       用户拥有的角色ID集合
     * @param synToActiviti 是否同步数据到Activiti
     */
    public void save(UserPo user, Integer orgId, List<Integer> roleIds, boolean synToActiviti) {
        String userId = String.valueOf(user.getId());
        // 同步数据到Activiti Identify模块
        if (synToActiviti) {
            UserQuery userQuery = identityService.createUserQuery();
            List<org.activiti.engine.identity.User> activitiUsers = userQuery.userId(userId).list();

            if (activitiUsers.size() == 1) {
                updateActivitiData(user, roleIds, activitiUsers.get(0));
            } else if (activitiUsers.size() > 1) {
                String errorMsg = "发现重复用户：id=" + userId;
                log.error(errorMsg);
                throw new RuntimeException(errorMsg);
            } else {
                newActivitiUser(user, roleIds);
            }
        }

    }

    /**
     * 添加工作流用户以及角色
     *
     * @param user    用户对象{@link UserPo}
     * @param roleIds 用户拥有的角色ID集合
     */
    private void newActivitiUser(UserPo user, List<Integer> roleIds) {
        String userId = user.getId().toString();

        // 添加用户
        saveActivitiUser(user);

        // 添加membership
        addMembershipToIdentify(roleIds, userId);
    }

    /**
     * 添加一个用户到Activiti {@link org.activiti.engine.identity.User}
     *
     * @param user 用户对象, {@link UserPo}
     */
    private void saveActivitiUser(UserPo user) {
        String userId = user.getId().toString();
        org.activiti.engine.identity.User activitiUser = identityService.newUser(userId);
        cloneAndSaveActivitiUser(user, activitiUser);
        log.debug("add activiti user: {}", ToStringBuilder.reflectionToString(activitiUser));
    }

    /**
     * 添加Activiti Identify的用户于组关系
     *
     * @param roleIds 角色ID集合
     * @param userId  用户ID
     */
    private void addMembershipToIdentify(List<Integer> roleIds, String userId) {
        for (Integer roleId : roleIds) {
            RolePo role = roleDao.find(roleId);
            log.debug("add role to activit: {}", role);
            //TODO
            identityService.createMembership(userId, role.getNo());
        }
    }

    /**
     * 更新工作流用户以及角色
     *
     * @param user         用户对象{@link UserPo}
     * @param roleIds      用户拥有的角色ID集合
     * @param activitiUser Activiti引擎的用户对象，{@link org.activiti.engine.identity.User}
     */
    private void updateActivitiData(UserPo user, List<Integer> roleIds, org.activiti.engine.identity.User activitiUser) {

        String userId = user.getId().toString();

        // 更新用户主体信息
        cloneAndSaveActivitiUser(user, activitiUser);

        // 删除用户的membership
        List<Group> activitiGroups = identityService.createGroupQuery().groupMember(userId).list();
        for (Group group : activitiGroups) {
            log.debug("delete group from activit: {}", ToStringBuilder.reflectionToString(group));
            identityService.deleteMembership(userId, group.getId());
        }

        // 添加membership
        addMembershipToIdentify(roleIds, userId);
    }

    /**
     * 使用系统用户对象属性设置到Activiti User对象中
     *
     * @param user         系统用户对象
     * @param activitiUser Activiti User
     */
    private void cloneAndSaveActivitiUser(UserPo user, org.activiti.engine.identity.User activitiUser) {
        activitiUser.setFirstName(user.getName());
        activitiUser.setLastName(StringUtils.EMPTY);
        activitiUser.setPassword(StringUtils.EMPTY);
        activitiUser.setEmail(user.getEmail());
        identityService.saveUser(activitiUser);
    }

    @Override
    public void delete(Integer userId, boolean synToActiviti) throws ServiceException {
        UserPo userPo = userDao.find(userId);
        /**
         * 同步删除Activiti User Group
         */
        if (synToActiviti) {
            // 同步删除Activiti User
            List<UserRolePo> roleList = userPo.getSysUserRoles();
            for (UserRolePo role : roleList) {
                identityService.deleteMembership(userId.toString(), String.valueOf(role.getSysRole().getId()));
            }

            // 同步删除Activiti User
            identityService.deleteUser(userId.toString());
        }


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
                String groupId = role.getNo();
                Group group = identityService.newGroup(groupId);
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
                String userId = user.getId().toString();
                // 添加一个用户到Activiti
                saveActivitiUser(user);
                // 角色和用户的关系
                List<UserRolePo> roleList = user.getSysUserRoles();
                for (UserRolePo role : roleList) {
                    identityService.createMembership(userId, role.getSysRole().getNo());
                    log.debug("add membership {user: {}, role: {}}", userId, String.valueOf(role.getId()));
                }
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
