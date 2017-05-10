package com.dsdl.eidea.core.entity.bo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/7 10:00.
 */
@Getter
@Setter
public class TableBo implements java.io.Serializable{
    private Integer id;
    @NotBlank(message="client.error.name.not_null")
    @Length(min = 2,max = 50,message = "table.error.name.length_error")
    private String name;
    @NotBlank(message="table.error.table_name.not_null")
    @Length(min = 2,max = 45,message = "table.error.table_name.length_error")
    private String tableName;
    @NotBlank(message="table.error.po_class.not_null")
    @Length(min = 2,max = 100,message = "table.error.po_class.length_error")
    private String poClass;
    @NotBlank(message="table.error.out_log.not_null")
    @Length(min = 1,max = 1,message="client.error.out_log.length_error")
    private String outLog;
    @Length(max = 40,message="table.error.bu_pk.length_error")
    private String buPk;
    @Length(max = 200,message = "client.error.remark.length_error")
    private String remark;
    @Length(max = 3000,message = "table.error.extern_json.length_error")
    private String externJson;
    @NotBlank(message="client.error.isactive.not_null")
    @Length(min = 1,max = 1,message="client.error.isactive.length_error")
    private String isactive;
    private int entityType;
    @NotNull(message = "entity.information.not.empty")
    private List<TableColumnBo> tableColumnBoList;

}
