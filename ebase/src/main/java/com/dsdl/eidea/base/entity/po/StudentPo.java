package com.dsdl.eidea.base.entity.po;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by 车东明 on 2017/5/25.
 */
@Setter
@Getter
public class StudentPo {
    @Id
    @Column(name = "id", nullable = false, unique = true, length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * tab名
     **/
    @Column(name = "[name]", length = 200, nullable = false)
    @NotNull(message = "datadicttype.value.not.empty")
    @Length(min = 2, max = 200, message = "error.datadicttype.name.length")
    private String name;
    @Column(name = "[org_id]")
    private Integer orgId;

    @Column(name = "[client_id]")
    private Integer clientId;
    /**
     * 是否有效
     **/
    @Column(name = "[isactive]", length = 1)
    @Length(min = 1, max = 1, message = "isactive.length")
    private String isactive;
    /**
     * 创建时间
     **/
    @Column(name = "[created]", length = 19, nullable = false)
    private Date created;
    /**
     * 创建人
     **/
    @Column(name = "[createdby]", length = 11, nullable = false)
    @NotNull(message = "error.createdby.not_null")
    private Integer createdby;
    /**
     * 修改时间
     **/
    @Column(name = "[updated]", length = 19, nullable = false)
    private Date updated;
    /**
     * 修改人
     **/
    @Column(name = "[updatedby]", length = 11, nullable = false)
    @NotNull(message = "error.updatby.not_null")
    private Integer updatedby;
}
