package com.dsdl.eidea.core.entity.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by 车东明 on 2017/4/17.
 */
@Entity
@Setter
@Getter
@Table(name = "core_report_settings", catalog = "e_idea")
public class ReportPo implements Serializable {
    @Id
    @Column(name = "_key", unique = true, nullable = false, length = 100)
    private String key;
    @Column(name = "value", nullable = false, length = 50)
    private String value;
}
