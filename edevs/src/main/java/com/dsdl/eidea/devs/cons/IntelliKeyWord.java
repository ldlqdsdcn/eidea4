package com.dsdl.eidea.devs.cons;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 2016/8/25.
 * 系统中关键字
 */
public class IntelliKeyWord {
    private static Map<String, String> LABEL_MAP = new HashMap<>();
    private static Set<String> READ_ONLY_SET = new HashSet<>();
    private static Set<String> SKIPPED_FIELD_SET = new HashSet<>();
    private static Set<String> BOOL_SELECTION_SET = new HashSet<>();
    private static Map<String, String> FIELD_WIDTH_MAP = new HashMap<>();
    private static Map<String, String> FOREIGN_MAP = new HashMap<>();
    private static Set<String> SQL_KEY_WORD_MAP = new HashSet<>();
    private static Map<String, String> SQL_TYPE_MAP = new HashMap<>();
    private static Set<String> NOT_NEED_VAL_COLUMN_SET = new HashSet<>();
    private static Map<String, Integer> DISPLAY_DECORATOR_MAP = new HashMap<>();
    private static Set<String> TABLE_PREFIX = new HashSet<>();

    static {
        TABLE_PREFIX.add("core_");
        TABLE_PREFIX.add("sys_");
        TABLE_PREFIX.add("base_");
        LABEL_MAP.put("name", "名称");
        LABEL_MAP.put("created", "创建时间");
        LABEL_MAP.put("updated", "修改时间");
        LABEL_MAP.put("createdby", "创建人");
        LABEL_MAP.put("updatedby", "修改人");
        LABEL_MAP.put("isactive", "是否有效");
        LABEL_MAP.put("level", "等级");
        LABEL_MAP.put("title", "标题");
        LABEL_MAP.put("role", "角色");
        LABEL_MAP.put("desc", "描述");
        LABEL_MAP.put("descr", "描述");
        LABEL_MAP.put("remark", "备注");
        LABEL_MAP.put("help", "帮助");
        LABEL_MAP.put("value", "键值");
        LABEL_MAP.put("msgtext", "信息");
        LABEL_MAP.put("bgnTime", "开始时间");
        LABEL_MAP.put("endTime", "结束时间");
        LABEL_MAP.put("bgnDate", "开始日期");
        LABEL_MAP.put("endDate", "结束日期");
        LABEL_MAP.put("orgId", "组织");
        LABEL_MAP.put("clientId", "实体");
        READ_ONLY_SET.add("created");
        READ_ONLY_SET.add("createdby");
        READ_ONLY_SET.add("updated");
        READ_ONLY_SET.add("updatedby");
        SKIPPED_FIELD_SET.add("id");
        BOOL_SELECTION_SET.add("isactive");
        FIELD_WIDTH_MAP.put("descr", "width:500px;");
        FIELD_WIDTH_MAP.put("help", "width:500px;");
        FIELD_WIDTH_MAP.put("remark", "width:500px;");
        FOREIGN_MAP.put("createdby", "user");
        FOREIGN_MAP.put("updatedby", "user");
        FOREIGN_MAP.put("userId", "user");
        FOREIGN_MAP.put("orgId", "org");
        FOREIGN_MAP.put("clientId", "client");
        SQL_KEY_WORD_MAP.add("name");
        SQL_TYPE_MAP.put("INT", "INTEGER");
        SQL_TYPE_MAP.put("INT UNSIGNED", "INTEGER");
        SQL_TYPE_MAP.put("DATETIME", "TIMESTAMP");

        NOT_NEED_VAL_COLUMN_SET.add("id");
        NOT_NEED_VAL_COLUMN_SET.add("created");
        NOT_NEED_VAL_COLUMN_SET.add("createdby");
        NOT_NEED_VAL_COLUMN_SET.add("updated");
        NOT_NEED_VAL_COLUMN_SET.add("updatedby");
        NOT_NEED_VAL_COLUMN_SET.add("org_id");
        NOT_NEED_VAL_COLUMN_SET.add("client_id");
        NOT_NEED_VAL_COLUMN_SET.add("user_id");
        NOT_NEED_VAL_COLUMN_SET.add("orgId");
        NOT_NEED_VAL_COLUMN_SET.add("clientId");
        NOT_NEED_VAL_COLUMN_SET.add("userId");


    }

    public static Integer getDecorator(String propertyKey) {
        Integer decoType = DISPLAY_DECORATOR_MAP.get(propertyKey);
        if (decoType == null) {
            decoType = 0;
        }
        return decoType;
    }

    public static String getModule(String key) {
        return FOREIGN_MAP.get(key);
    }

    public static String getLabel(String key) {
        return LABEL_MAP.get(key);
    }

    public static boolean isReadOnly(String key) {
        return READ_ONLY_SET.contains(key);
    }

    public static boolean hasSkipped(String key) {
        return SKIPPED_FIELD_SET.contains(key);
    }

    public static boolean isBooleanTag(String key) {
        return BOOL_SELECTION_SET.contains(key);
    }

    public static String getWidth(String key) {
        return FIELD_WIDTH_MAP.get(key);
    }

    public static boolean isKeyWord(String key) {
        return SQL_KEY_WORD_MAP.contains(key);
    }

    public static String getSQlType(String key) {
        return SQL_TYPE_MAP.get(key);
    }

    public static boolean isNotValidate(String key) {
        return NOT_NEED_VAL_COLUMN_SET.contains(key);
    }

    public static String removePrefix(String tableName) {
        for (String prefix : TABLE_PREFIX) {
            if (tableName.startsWith(prefix)) {
                return tableName.replaceFirst(prefix, "");
            }
        }
        return tableName;
    }
}
