package com.dsdl.eidea.core.web.def;

/**
 * Created by 刘大磊 on 2016/12/20 17:03.
 */
public class WebConst {
    public static final String SESSION_LOGINUSER = "loginUser";
    public static final String PAGING_SETTINGS = "pagingSettingResult";
    public static final String SESSION_USERCONTENT = "userContent";
    public static final String SESSION_SEARCH_PARAM = "_searchParam";
    public static final String PAGE_URI = "uri";
    public static final String SESSION_ACTUALUSER = "actualUser";
    public static final String SESSION_LEFTMENU = "menuString";
    public static final String PAGE_PRIVILEGES = "pagePrivileges";
    public static final String PAGE_MODULE_ID="pageModuleId";
    public static final String PRIVILEGES = "privileges";
    public static final String SESSION_RESOURCE = "userResource";
    public static final String SESSION_LANGUAGE="language";
    public static final String ORG_IDS = "orgIdList";
    /**
     * token header 权限验证
     */
    public static final String HEADER_TOKEN = "token";
    /*****************************************************
     * 常用占位符
     */
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
}
