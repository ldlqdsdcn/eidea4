package com.dsdl.eidea.base.init;

import com.dsdl.eidea.core.def.FieldInputType;

import javax.persistence.AttributeConverter;

/**
 * Created by 车东明 on 2017/5/31.
 */
public class InputTypeConverter implements AttributeConverter<String,Integer> {
    /**
     * 将实体属性x转化为y存入数据库中
     * @param desc
     * @return
     */
    @Override
    public Integer convertToDatabaseColumn(String desc) {
        try{
            return Integer.parseInt(desc);//是数字就直接返回
        }catch (NumberFormatException e){
            for (FieldInputType fieldInputType: FieldInputType.values()){
                if (desc.equals(fieldInputType.getDesc())){
                    return fieldInputType.getKey();
                }
            }
        }
        throw new RuntimeException("UnkownInputType:"+desc);
    }

    /**
     * 将数据库中的字段转换为实体属性x显示
     * @param key
     * @return
     */
    @Override
    public String  convertToEntityAttribute(Integer key) {
        for (FieldInputType type:FieldInputType.values()){
            if (key==type.getKey()){
                return type.getDesc();
            }
        }
       throw new RuntimeException("UnkownDataBase Key:"+key);
    }
}
