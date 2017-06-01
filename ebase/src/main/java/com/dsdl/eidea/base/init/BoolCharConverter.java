package com.dsdl.eidea.base.init;

import com.dsdl.eidea.base.def.BoolChar;

import javax.persistence.AttributeConverter;

/**
 * Created by 车东明 on 2017/6/1.
 */
public class BoolCharConverter implements AttributeConverter<BoolChar,String> {
    @Override
    public String convertToDatabaseColumn(BoolChar boolChar) {

        return boolChar.getKey();
    }

    @Override
    public BoolChar convertToEntityAttribute(String s) {
        for (BoolChar boolChar:BoolChar.values()){
         if (boolChar.getKey().equals(s)){
             return  boolChar;
         }
        }
        throw new RuntimeException("aaa"+s);
    }

}
