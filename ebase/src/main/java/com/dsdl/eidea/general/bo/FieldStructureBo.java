package com.dsdl.eidea.general.bo;

import com.dsdl.eidea.base.entity.po.FieldPo;
import com.dsdl.eidea.base.entity.po.FieldTrlPo;
import com.dsdl.eidea.base.entity.po.FieldValidatorPo;
import com.dsdl.eidea.core.def.EventType;
import com.dsdl.eidea.core.def.FieldInputType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘大磊 on 2017/5/27 8:58.
 */
@Getter
@Setter
public class FieldStructureBo {
    /**
     * 字段翻译
     */
    private FieldTrlPo fieldTrlPo;
    /**
     * 字段内容
     */
    private FieldPo fieldPo;
    /**
     * 输入类型
     */
    private FieldInputType fieldInputType;
    /**
     * 字段验证
     */
    private List<FieldValidatorPo> fieldValidatorPoList;
    /**
     * 当输入类型为checkbox时该值有用
     */
    private String trueValue;
    /**
     * 当输入类型为checkbox时该值有用
     */
    private String falseValue;
    /**
     * 事件
     */
    private List<EventType> eventList=new ArrayList<>();

    /**
     * 添加event
     * @param event
     */
    public void addEvent(EventType event)
    {
        eventList.add(event);
    }


}
