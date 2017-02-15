package com.dsdl.eidea.core.web.vo;

import com.dsdl.eidea.core.web.def.PagingDef;
import com.dsdl.eidea.core.web.def.PagingType;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by 刘大磊 on 2016/10/20 17:45.
 */
@Getter
@Setter
public class PagingSettingResult implements java.io.Serializable {
    private static final PagingSettingResult pagingSettingResult = new PagingSettingResult(PagingType.MEMORY.getKey(), PagingDef.PER_PAGE_RECORD_SIZE, PagingDef.MAX_ITEMS);
    private static final PagingSettingResult dbpagingSettingResult = new PagingSettingResult(PagingType.DATABASE.getKey(), PagingDef.PER_PAGE_RECORD_SIZE, PagingDef.MAX_ITEMS);

    public PagingSettingResult() {

    }

    public PagingSettingResult(PagingType pagingType) {
        this(pagingType.getKey(), PagingDef.PER_PAGE_RECORD_SIZE, PagingDef.MAX_ITEMS);
    }

    private PagingSettingResult(int pageType, int perPageSize, int pagingButtonSize) {
        this.pageType = pageType;
        this.perPageSize = perPageSize;
        this.pagingButtonSize = pagingButtonSize;
    }

    /**
     * 分页类型
     */
    private int pageType;
    /**
     * 每页大小
     */
    private int perPageSize;
    /**
     * paing 按钮显示数量
     */
    private int pagingButtonSize;

    /**
     * 默认分页类型为内存分页
     *
     * @return
     */
    public static PagingSettingResult getDefault() {
        return pagingSettingResult;
    }

    public static PagingSettingResult getDbPaging() {
        return dbpagingSettingResult;
    }
}
