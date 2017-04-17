<#include "../inc/class_header.ftl"/>

package ${packagename};

import com.dsdl.eidea.core.spring.annotation.DataAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.googlecode.genericdao.search.ISearch;
import ${modelpackage}.${modelname}Po;
import ${interfacefullservicename};
import com.dsdl.eidea.core.dao.CommonDao;
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
	@DataAccess(entity =${modelname}Po.class)
	private CommonDao<${modelname}Po,${pkClass}> ${repositoryname};

    public List<${modelname}Po> get${modelname}List(ISearch search)
	{
		return ${repositoryname}.search(search);
	}
    public ${modelname}Po get${modelname}(${pkClass} ${pkName})
	{
		return ${repositoryname}.find(${pkName});
	}
    public void save${modelname}(${modelname}Po ${modelname?uncap_first})
	{
		${repositoryname}.save(${modelname?uncap_first});
	}
    public void deletes(${pkClass}[] ${pkName}s)
	{
		${repositoryname}.removeByIds(${pkName}s);
	}
}
