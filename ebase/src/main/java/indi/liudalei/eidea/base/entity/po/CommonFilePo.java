
/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package indi.liudalei.eidea.base.entity.po;

import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
/**
* table name common_file
*            文件表
* Date:2017-05-02 13:09:39
**/
@Getter
@Setter
@Entity(name = "common_file")
public class CommonFilePo implements java.io.Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "[id]")
    @Id
    private Integer id;
    /**
    * 文件名称
    **/
    @Column(name = "[filename]",length =255 )
    private String filename;
    /**
    * 文件类型
    **/
    @Column(name = "[file_type]",length =200 )
    private String fileType;
    /**
    * 文件路径
    **/
    @Column(name = "[path]",length =255 )
    private String path;
    /**
    * 扩展名
    **/
    @Column(name = "[extension]",length =50 )
    private String extension;
    /**
    * 文件大小
    **/
    @Column(name = "[file_size]" )
    private Integer fileSize;
    /**
    * 文件创建时间
    **/
    @Column(name = "[file_created]",length =19 )
    private Date fileCreated;
    /**
    * 文件修改时间
    **/
    @Column(name = "[file_updated]",length =19 )
    private Date fileUpdated;
    /**
    * 是否只读
    **/
    @Column(name = "[file_isreadonly]" )
    private Integer fileIsreadonly;
    /**
    * 是否隐藏
    **/
    @Column(name = "[file_ishidden]" )
    private Integer fileIshidden;
    /**
    * 创建时间
    **/
    @Column(name = "[created]",length =19 )
    private Date created;
    /**
    * 文件设置id
    **/
    @Column(name = "[common_file_setting_id]" )
    private Integer commonFileSettingId;
    /**
    * 文件简要
    **/
    @Column(name = "[file_abstract]",length =255 )
    private String fileAbstract;
    /**
    * 文件关键字
    **/
    @Column(name = "[file_keyword]",length =255 )
    private String fileKeyword;
    /**
    * 文件存储方式
    **/
    @Column(name = "[file_mode]" )
    private Integer fileMode;
    /**
    * 文件存放字段
    **/
    @Column(name = "[file_blob]",length =2147483647 )
    private String fileBlob;
}