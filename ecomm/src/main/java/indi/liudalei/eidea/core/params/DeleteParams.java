package indi.liudalei.eidea.core.params;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by 刘大磊 on 2017/4/19 17:51.
 */
@Getter
@Setter
public class DeleteParams<PK> implements java.io.Serializable{
    private QueryParams queryParams;
    private PK[] ids;
}
