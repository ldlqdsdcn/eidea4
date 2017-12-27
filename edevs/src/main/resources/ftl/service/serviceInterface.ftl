<#include "../inc/class_header.ftl"/>

package ${packagename};
import ${modelpackage}.${modelname}Po;
<#if paingByDb>
import PaginationResult;
import QueryParams;
import com.googlecode.genericdao.search.Search;
<#else>
import com.googlecode.genericdao.search.ISearch;
</#if>
import java.util.List;

/**
 * @author 刘大磊 ${datetime}
 */
public interface ${modelname}Service {
<#if paingByDb>
	PaginationResult<${modelname}Po> get${modelname}ListByPaging(Search search,QueryParams queryParams);
<#else>
    List<${modelname}Po> get${modelname}List(ISearch search);
</#if>
	${modelname}Po get${modelname}(${pkClass} ${pkName});
	void save${modelname}(${modelname}Po ${modelname?uncap_first});
	void deletes(${pkClass}[] ${pkName}s);
}