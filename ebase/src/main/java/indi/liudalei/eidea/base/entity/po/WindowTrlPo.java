
/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package indi.liudalei.eidea.base.entity.po;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
* table name core_window_trl
*            窗体信息
* Date:2017-05-02 15:42:27
**/
@Getter
@Setter
@Entity(name = "core_window_trl")
@org.hibernate.annotations.Cache(usage= CacheConcurrencyStrategy.READ_ONLY)
public class WindowTrlPo implements java.io.Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "[id]",nullable = false,unique = true,length = 11)
    @Id
    private Integer id;
    /**
    * 
    **/
    @Column(name = "[window_id]", nullable = false, length = 11)
    @NotNull(message = "error.window.id.not_null")
    private Integer windowId;
    /**
    * 
    **/
    @Column(name = "[lang]", length = 10,nullable = false)
    @NotBlank(message = "error.lang.not.null")
    @Length(min = 1,max = 10,message = "tab.error.lang.length")
    private String lang;
    /**
    * 
    **/
    @Column(name = "[name]",length =100 )
    @Length(max = 100,message = "windowtrl.error.name.length")
    private String name;
    /**
    * 
    **/
    @Column(name = "[description]", length = 500)
    @Length(max = 500, message = "tab.error.description.length")
    private String description;
    /**
    * 
    **/
    @Column(name = "[help]", length = 500)
    @Length(max = 500,message="tabtrl.error.help.length")
    private String help;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "[window_id]",insertable = false,updatable = false)
    private WindowPo windowPo;
}