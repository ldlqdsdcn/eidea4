package com.dsdl.eidea.core.util;

import com.dsdl.eidea.base.exception.ServiceException;
import com.dsdl.eidea.core.entity.bo.QueryExpression;
import com.dsdl.eidea.core.entity.bo.SqlColumn;
import com.dsdl.eidea.core.entity.bo.SqlCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘大磊 on 2017/6/5 10:03.
 */
public class SQLUtil {


    public static void main(String[] args) {
        String sql = "select sys_org.id AS item_id,sys_org.name as item_value from sys_org where isactive='Y' and client_id=@record.client_id@";
        String field = "";
        getCondition(sql);
        System.out.println(getCondition(sql).size());
        List<String> conditions = getConditionKeyWord(sql);
        for (String condition : conditions) {
            System.out.println(condition);
        }
    }

    /**
     *
     * @param sql
     * @param columnList
     * @return
     */
    public static QueryExpression buildQueryExpression(String sql, List<SqlColumn> columnList)
    {
        String sqlBuilder=new String(sql);
        QueryExpression queryExpression=new QueryExpression();
        List<String> conditions=getConditionKeyWord(sql);
        List<SqlCondition> sqlConditionList=new ArrayList<>();
        for(int i=0;i<conditions.size();i++)
        {
            String condition=conditions.get(i);
            SqlCondition sqlCondition=new SqlCondition();
            sqlCondition.setIndex(i+1);
            sqlBuilder=sqlBuilder.replace("@"+condition+"@","?");
            if(condition.startsWith("record."))
            {
                String columnName=condition.substring(condition.indexOf("."));
                sqlCondition.setColumnName(columnName);
            }
            sqlConditionList.add(sqlCondition);
        }
        queryExpression.setSql(sqlBuilder);
        queryExpression.setColumnList(columnList);
        return queryExpression;
    }
    private static List<String> getConditionKeyWord(String sql) {
        List<String> conditions = new ArrayList<>();
        List<Integer> conditionIndexList = getCondition(sql);
        if (conditionIndexList.size() % 2 != 0) {
            throw new ServiceException("表达式@符号不匹配");
        }
        for (int i = 0; i < conditionIndexList.size(); i++) {
            if (i != 0 && i % 2 == 1) {

                int bgnIndex = conditionIndexList.get(i - 1);
                int endIndex = conditionIndexList.get(i);
                String str = sql.substring(bgnIndex + 1, endIndex);
                conditions.add(str);
            }
        }
        return conditions;

    }

    private static List<Integer> getCondition(String sql) {
        List<Integer> indexArray = new ArrayList<>();
        int index = -1;
        while (true) {
            index = sql.indexOf('@', index + 1);
            if (index == -1) {
                return indexArray;
            } else {
                indexArray.add(index);
            }
        }


    }

}
