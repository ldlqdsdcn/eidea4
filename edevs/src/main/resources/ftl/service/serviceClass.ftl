<#include "../inc/class_header.ftl"/>

package ${packagename};

import com.dsdl.eidea.core.spring.annotation.DataAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ${modelpackage}.${modelname}Po;
import ${interfacefullservicename};
<#if paingByDb>
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.SearchResult;
import com.googlecode.genericdao.search.Search;
<#else>
import com.googlecode.genericdao.search.ISearch;
</#if>
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
<#if paingByDb>
	public PaginationResult<${modelname}Po> get${modelname}ListByPaging(Search search,QueryParams queryParams)
    {
		search.setFirstResult(queryParams.getFirstResult());
		search.setMaxResults(queryParams.getPageSize());
		PaginationResult<${modelname}Po> paginationResult = null;
		if (queryParams.isInit()) {
		SearchResult<${modelname}Po> searchResult = ${modelname?uncap_first}Dao.searchAndCount(search);
		paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
		}
		else
		{
		List<${modelname}Po> ${modelname?uncap_first}PoList = ${modelname?uncap_first}Dao.search(search);
		paginationResult = PaginationResult.pagination(${modelname?uncap_first}PoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
		}
    	return paginationResult;
    }
<#else>
	public List<${modelname}Po> get${modelname}List(ISearch search)
    {
    	return ${repositoryname}.search(search);
    }
</#if>

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
