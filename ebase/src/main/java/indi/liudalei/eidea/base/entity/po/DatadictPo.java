
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
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
* table name base_datadict
*            数据字典
* Date:2017-04-26 15:34:59
**/
@Getter
@Setter
@Entity(name = "base_datadict")
public class DatadictPo implements java.io.Serializable {
    @Id
    @Column(name = "id",nullable = false,unique = true,length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "code",length =20,nullable = false)
    @NotBlank(message = "datadict.code.not.empty")
    @Length(min = 1,max = 20,message="error.datadict.code.length")
    private String code;
    @Column(name = "msgtext",length =200,nullable = false)
    @NotBlank(message = "datadict.msgtext.not.empty")
    @Length(min=1,max = 20,message = "error.datadict.msgtext.length")
    private String msgtext;
    @Column(name = "isactive",length =1)
    @Length(min=1,max=1,message = "isactive.length")
    private String isactive;
    @Column(name = "data_type",length =20,nullable = false)
    @NotBlank(message = "datadicttype.datatype.not.empty")
    @Length(min=1,max = 20,message = "error.datadict.datatype.length")
    private String dataType;
    @Column(name = "remark",length =200)
    @Length(max = 200,message="client.error.remark.length_error")
    private String remark;
}