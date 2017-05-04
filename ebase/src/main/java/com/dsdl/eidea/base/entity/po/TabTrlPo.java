
/**
 * 版权所有 刘大磊 2013-07-01
 * 作者：刘大磊
 * 电话：13336390671
 * email:ldlqdsd@126.com
 */
package com.dsdl.eidea.base.entity.po;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * table name core_tab_trl
 *            Tab页翻译
 * Date:2017-05-02 15:43:44
 **/
@Getter
@Setter
@Entity(name = "core_tab_trl")
public class TabTrlPo implements java.io.Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "[id]", nullable = false, unique = true, length = 11)
    @NotNull(message = "common.primary_key.isempty")
    @Length(min = 1, max = 11,message = "error.datadict.id.length")
    @Id
    private Integer id;
    /**
     * 语言 code
     **/
    @NotNull(message = "error.tab.id.not.null")
    @Length(min = 1, max = 11,message = "error.datadict.id.length")
    @Column(name = "[tab_id]", nullable = false, length = 11)
    private Integer tabId;
    /**
     *
     **/
    @Column(name = "[lang]", length = 10,nullable = false)
    @NotBlank(message = "error.lang.not.null")
    @Length(min = 1,max = 10,message = "tab.error.lang.length")
    private String lang;
    /**
     * 名称
     **/
    @Column(name = "[name]", length = 200)
    @Length( max = 200, message = "error.datadicttype.name.length")
    private String name;
    /**
     * 描述
     **/
    @Column(name = "[description]", length = 500)
    @Length(max = 500, message = "tab.error.description.length")
    private String description;
    /**
     * 帮助
     **/
    @Column(name = "[help]", length = 500)
    @Length(max = 500,message="tabtrl.error.help.length")
    private String help;
}