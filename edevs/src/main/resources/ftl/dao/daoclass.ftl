<#include "../inc/class_header.ftl"/>

package ${packagename};

import org.springframework.stereotype.Repository;

import ${interfacefullname};
import ${modelpackage}.${modelname}Po;
<#if needImportBaseDao>
import BaseDaoHibernate;
</#if>
/**
 * @author 刘大磊 ${datetime}
 */
@Repository("${repositoryname}") 
public class ${modelname}DaoHibernate extends BaseDaoHibernate<${modelname}Po,${pkClass}> implements ${modelname}Dao {

}
