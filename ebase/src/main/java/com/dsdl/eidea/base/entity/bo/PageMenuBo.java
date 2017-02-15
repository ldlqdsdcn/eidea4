package com.dsdl.eidea.base.entity.bo;

import com.dsdl.eidea.base.entity.po.ModuleMenuPo;
import lombok.Data;

import javax.persistence.Column;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by admin on 2016/12/13.
 * 菜单设置 jdd
 */
@Data
public class PageMenuBo {
    private Integer id;
    @NotBlank(message = "pagemenu.name.check")
    @Length(max=45,min=1,message="pagemenu.name.prompt")
    private String name;
    @Length(max=200,message="pagemenu.url.check")
    private String url;
    private String isactive;
    @Length(max=200,message="pagemenu.remark.check")
    private String remark;
    private Integer parentMenuId;
    private Integer menuType;
    /**
     * 是否新建 默认为false
     */
    private boolean created = false;
    //服务器名称
    private String serverName;
    @Length(max=100,message="pagemenu.icon.check")
    private String icon;
    @NotBlank(message = "pagemenu.seqno.check")
    @Length(max=11,min=1,message="pagemenu.seqno.longcheck")
    private Integer seqNo;
    private List<PageMenuTrlBo> pageMenuTrlBo;
}
