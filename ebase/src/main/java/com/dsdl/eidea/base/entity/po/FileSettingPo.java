
/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.base.entity.po;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.dsdl.eidea.base.entity.bo.ModuleBo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
* table name common_file_setting
*            文件设置
* Date:2017-05-02 13:07:49
**/
@Getter
@Setter
@Entity(name = "common_file_setting")
public class FileSettingPo implements java.io.Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "[id]")
    @Id
    private Integer id;
    /**
    * 名称
    **/
    @NotBlank(message = "名称不能为空")
    @Length(max = 255,min = 1,message = "最大长度为255位")
    @Column(name = "[name]",length =255 )
    private String name;
    /**
    * 上传根目录
    **/
    @NotBlank(message = "长传根目录不能为空")
    @Length(max = 255,message = "最大长度为255位")
    @Column(name = "[root_directory]",length =255 )
    private String rootDirectory;
    /**
    * 文件大小
    **/
    @NotNull(message = "文件大小不能为空")
    @Column(name = "[file_size]" )
    private Integer fileSize;
    /**
    * 文件类型
    **/
    @Column(name = "[file_types]",length =255 )
    private String fileTypes;
    /**
    * 存储类型
    **/
    @Column(name = "[storage_mode]" )
    private Integer storageMode;
    /**
    * ftp 连接id
    **/
    @Column(name = "[ftpcommection_id]" )
    private Integer ftpcommectionId;
    /**
    * 创建时间
    **/
    @NotNull(message = "创建时间不能为空")
    @Column(name = "[created]",length =19 )
    private Date created;
    /**
    * 模块id
    **/
    @NotNull(message = "模块ID不能为空")
    @Column(name = "[module_id]" )
    private Integer moduleId;
    @Transient
    private List<ModuleBo> moduleBos;
}