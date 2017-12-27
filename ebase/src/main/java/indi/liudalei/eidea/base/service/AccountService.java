package indi.liudalei.eidea.base.service;

import indi.liudalei.eidea.base.exception.OrganizationNotFoundException;
import indi.liudalei.eidea.base.exception.ServiceException;

/**
 * Created by 刘大磊 on 2017/5/19 13:59.
 * 维护activiti用户组 角色 权限接口
 */
public interface AccountService {
    /**
     * 保存角色
     * @param roleId
     */
    void saveRole(Integer roleId);
    /**
     * 添加用户并[同步其他数据库]
     * <ul>
     * <li>step 1: 保存系统用户，同时设置和部门的关系</li>
     * <li>step 2: 同步用户信息到activiti的identity.User，同时设置角色</li>
     * </ul>
     * @param userId
     * 用户对象
     * @throws OrganizationNotFoundException    关联用户和部门的时候从数据库查询不到哦啊部门对象
     * @throws  Exception                       其他未知异常
     */
    void saveUser(Integer userId)
            throws OrganizationNotFoundException, ServiceException;

     void deleteRole(Integer roleId);
    /**
     * 删除用户
     * @param userId        用户ID
     * @throws Exception
     */
    public void deleteUser(Integer userId) throws ServiceException;

    /**
     * 同步用户、角色数据到工作流
     * @throws Exception
     */
    public void saveSynAllUserAndRoleToActiviti();

    /**
     * 删除工作流引擎Activiti的用户、角色以及关系
     * @throws Exception
     */
    public void deleteAllActivitiIdentifyData();
}
