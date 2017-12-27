package indi.liudalei.eidea.base.entity.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by 刘大磊 on 2017/5/3 17:23.
 * 下拉选择列表
 */
@Getter
@Setter
public class SelectItemBo {
    /**
     * 主键值
     */
    private Serializable key;
    /**
     * value值
     */
    private String value;
}
