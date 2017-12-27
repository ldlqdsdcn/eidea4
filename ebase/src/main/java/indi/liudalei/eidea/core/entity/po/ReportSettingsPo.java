package indi.liudalei.eidea.core.entity.po;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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
    @Length(min = 1,max = 100,message = "error.reportSetting.key.length")
    private String key;
    @Column(name = "value", nullable = false, length = 500)
    @Length(min = 1,max = 500,message = "error.reportSetting.value.length")
    private String value;
    /**
     * 是否系统初始化设置
     */
    @Column(name = "init", nullable = false, length = 1)
    private String init;
    @Transient
    private boolean created = false;
}
