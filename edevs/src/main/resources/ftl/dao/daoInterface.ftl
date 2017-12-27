<#include "../inc/class_header.ftl"/>

package ${packagename};

import ${modelpackage}.${modelname}Po;
<#if needImportBaseDao>
import BaseDao;
</#if>
/**
* @author 刘大磊 ${datetime}
*/
public interface ${modelname}Dao extends BaseDao<${modelname}Po,${pkClass}> {

}
