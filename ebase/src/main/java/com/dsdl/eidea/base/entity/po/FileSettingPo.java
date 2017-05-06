
/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.base.entity.po;

import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
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
    @Column(name = "[name]",length =255 )
    private String name;
    /**
    * 上传根目录
    **/
    @Column(name = "[root_directory]",length =255 )
    private String rootDirectory;
    /**
    * 文件大小
    **/
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
    @Column(name = "[created]",length =19 )
    private Date created;
    /**
    * 模块id
    **/
    @Column(name = "[module_id]" )
    private Integer moduleId;
}