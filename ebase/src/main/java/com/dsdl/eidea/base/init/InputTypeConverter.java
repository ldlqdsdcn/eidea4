package com.dsdl.eidea.base.init;

import com.dsdl.eidea.core.def.FieldInputType;

import javax.persistence.AttributeConverter;

/**
 * Created by 车东明 on 2017/5/31.
 */
public class InputTypeConverter implements AttributeConverter<FieldInputType,Integer> {
    /**
     * 将实体属性x转化为y存入数据库中
     * @param fieldInputType
     * @return
     */
    @Override
    public Integer convertToDatabaseColumn(FieldInputType fieldInputType) {
        return fieldInputType.getKey();
    }

    /**
     * 将数据库中的字段转换为实体属性x显示
     * @param key
     * @return
     */
    @Override
    public FieldInputType  convertToEntityAttribute(Integer key) {
        for (FieldInputType type:FieldInputType.values()){
            if (key==type.getKey()){
                return type;
            }
        }
       throw new RuntimeException("UnkownDataBase Key:"+key);
    }
}
