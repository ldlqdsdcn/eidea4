package com.dsdl.eidea.core.def;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 刘大磊 on 2016/12/17 16:34.
 * 弹出查询也的查询方式
 * 普通查询：每个都是一个文本框
 * 组合查询：可以组合出很多种条件
 */
public enum SearchPageType {
    NORMAL(0, "普通查询"), COMBINATION(1, "组合查询");
    private static Map<Integer, SearchPageType> searchPageTypeMap = new HashMap<>();

    static {
        SearchPageType[] searchPageTypes = SearchPageType.values();
        for (SearchPageType searchPageType : searchPageTypes) {
            searchPageTypeMap.put(searchPageType.getKey(), searchPageType);
        }
    }

    /**
     * 根据主键获取label值
     * @param key
     * @return
     */
    public static String getSearchPageDesc(int key) {
        return searchPageTypeMap.get(key).getDesc();
    }

    SearchPageType(int key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    private final int key;
    private final String desc;

    public int getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }


}
