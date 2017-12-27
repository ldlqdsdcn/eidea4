package indi.liudalei.eidea.core.web.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Angularjs 分页前端设置类
 * Created by 刘大磊 on 2016/10/20 17:41.
 */
@Getter
@Setter
public class PagingResult implements java.io.Serializable{
    /**
     * 当前页
     */
    private int pageNumber;
    /**
     * 记录数
     */
    private int totalSize;
    /**
     * record
     */
    private List list;
}
