package indi.liudalei.eidea.core.entity.union;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by 刘大磊 on 2017/1/7 8:49.
 * 国际化联合查询结果集
 */
@Getter
@Setter
public class MsgUnion {
    private String key;
    private String defaultValue;
    private String value;
}
