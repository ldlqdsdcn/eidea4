package com.dsdl.eidea.core.def;

/**
 * Created by 刘大磊 on 2017/5/26 9:43.
 */
public class EideaConst {
    /*****************************************************
     *                                                   *
     *  常用SQL占位符                                    *
     ****************************************************
    /**
     * 当前用户的OrgId
     */
    public static final String EXPRESS_ORG_ID = "@UserOrgId@";
    /**
     * 当前用户的clientId
     */
    public static final String EXPRESS_CLIENT_ID = "@UserClientId@";
    /**
     * 当前用户的 userId
     */
    public static final String EXPRESS_USER_ID = "@UserId@";
    /**
     * 当前时间
     */
    public static final String EXPRESS_CURRENT_TIME = "@CurrentTime@";
    /**
     * 当前日期
     */
    public static final String EXPRESS_CURRENT_DATE = "@CurrentDate@";
    /**
     * 当前记录的column值
     */
    public static final String EXPRESS_RECORD="@Record.";
    /**
     * 页面输入值占位符
     * 常用来替换查询界面输入的值
     */
    public static final String EXPRESS_VALUE="@value@";
    /**
     * 工作流变量，通用关键字
     * 审批通过 值为 true
     * 审批拒绝 值为 false
     */
    public static final String BPM_AGREE="approved";


}
