
<#include "../inc/class_header.ftl"/>

package ${basePackage}.${module}.entity.po;

<#if hasDate>
import java.util.Date;
</#if>
<#if hasDecimal>
import java.math.BigDecimal;
</#if>
import javax.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
/**
* table name ${tableName}
*            ${tableRemark}
* Date:${datetime}
**/
@Getter
@Setter
@Entity(name = "${tableInfo.tableName}")
public class ${tableInfo.poName}Po implements java.io.Serializable {
 <#if tableInfo.pkColumn.propertyType=='Integer'||tableInfo.pkColumn.propertyType=='Long'>
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "${tableInfo.pkColumn.columnName}")
 <#else>
    @Column(name = "${tableInfo.pkColumn.columnName}",length =${tableInfo.pkColumn.columnSize?c},unique = true, nullable = false )
 </#if>
    @Id
    private ${tableInfo.pkColumn.propertyType} ${tableInfo.pkColumn.propertyName?uncap_first};
<#list tableInfo.columnInfoList as prop>
    /**
    * ${prop.remarks}
    **/
    <#if prop.propertyType=='Integer'||prop.propertyType=='Long'>
    @Column(name = "${prop.columnName}" )
    <#else>
    @Column(name = "${prop.columnName}",length =${prop.columnSize?c} )
    </#if>
    private ${prop.propertyType} ${prop.propertyName?uncap_first};
</#list>
}