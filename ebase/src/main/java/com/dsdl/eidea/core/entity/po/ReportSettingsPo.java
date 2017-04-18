package com.dsdl.eidea.core.entity.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by 车东明 on 2017/4/17.
 */
@Entity
@Setter
@Getter
@Table(name = "core_report_settings", catalog = "e_idea")
public class ReportSettingsPo implements Serializable {
    @Id
    @Column(name = "[key]", unique = true, nullable = false, length = 100)
    private String key;
    @Column(name = "value", nullable = false, length = 50)
    private String value;
    @Transient
    private boolean created = false;
}
