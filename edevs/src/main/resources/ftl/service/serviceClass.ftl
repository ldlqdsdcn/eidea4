<#include "../inc/class_header.ftl"/>

package ${packagename};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.googlecode.genericdao.search.ISearch;
import ${interfacefulldaoname};
import ${modelpackage}.${modelname}Po;
import ${interfacefullservicename};
import java.util.List;
<#if lineList??>
<#list lineList as item>
import ${basePackage}.${item.module}.model.${item.model}Po;
</#list>
</#if>
/**
 * @author 刘大磊 ${datetime}
 */
${serviceName}
public class ${modelname}ServiceImpl  implements	${modelname}Service {
	@Autowired
	private ${modelname}Dao ${repositoryname};

    public List<${modelname}Po> get${modelname}List(ISearch search)
	{
		return ${repositoryname}.search(search);
	}
    public ${modelname}Po get${modelname}(${pkClass} id)
	{
		return ${repositoryname}.find(id);
	}
    public void save${modelname}(${modelname}Po ${modelname?uncap_first})
	{
		${repositoryname}.save(${modelname?uncap_first});
	}
    public void deletes(${pkClass}[] ids)
	{
		${repositoryname}.removeByIds(ids);
	}
}
