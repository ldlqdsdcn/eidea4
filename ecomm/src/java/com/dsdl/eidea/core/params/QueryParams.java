package com.dsdl.eidea.core.params;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by 刘大磊 on 2017/2/6 19:26.
 */
@Getter
@Setter
public class QueryParams implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 当前页 当 pageNo为-1的时候，进行统计页数count操作
     */
    private int pageNo = 1;
    /**
     * 页面显示记录数
     */
    private int pageSize = 15;
    /**
     * 总页数
     */
    private int totalPage = 1;

    /**
     * 获得每页15页分页参数
     * @return
     */
    public static QueryParams get15PerPageParam() {
        QueryParams queryParams = new QueryParams();
        queryParams.pageSize = 15;
        return queryParams;
    }

    /**
     * 获得每页20页分页参数
     * @return
     */
    public static QueryParams get20PerPageParam() {
        QueryParams queryParams = new QueryParams();
        queryParams.pageSize = 20;
        return queryParams;
    }

    /**
     * 获得每页10页分页参数
     * @return
     */
    public static QueryParams get10PerPageParam() {
        QueryParams queryParams = new QueryParams();
        queryParams.pageSize = 10;
        return queryParams;
    }

    /**
     * 获取首条记录
     *
     * @return
     */
    public int getFirstResult() {
        int firstRow = pageNo - 1;
        if (firstRow < 0) {
            firstRow = 0;
        }
        return firstRow * pageSize;
    }

    /**
     * 根据记录数获取页数
     *
     * @param recordCount
     * @return
     */
    public int getTotalPageByRecordCount(int recordCount) {
        if (recordCount % pageSize == 0) {
            totalPage = recordCount / pageSize;
        } else {
            totalPage = recordCount / pageSize + 1;
        }
        return totalPage;
    }
}
