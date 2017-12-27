
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
* table name common_file_relation
*            文件管理表
* Date:2017-05-02 13:11:50
**/
@Getter
@Setter
@Entity(name = "common_file_relation")
public class FileRelationPo implements java.io.Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "[id]")
    @Id
    private Integer id;
    /**
    * 文件id
    **/
    @Column(name = "[file_id]" )
    private Integer fileId;
    /**
    * 表名称
    **/
    @Column(name = "[table_name]",length =255 )
    private String tableName;
    /**
    * 表字段id
    **/
    @Column(name = "[table_id]" )
    private Integer tableId;
    /**
    * 创建时间
    **/
    @Column(name = "[created]",length =19 )
    private Date created;
}