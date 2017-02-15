<#include "../inc/class_header.ftl"/>

package ${packagename};
import ${modelpackage}.${modelname}Po;
import com.googlecode.genericdao.search.ISearch;
import java.util.List;

/**
 * @author 刘大磊 ${datetime}
 */
public interface ${modelname}Service {
	List<${modelname}Po> get${modelname}List(ISearch search);
	${modelname}Po get${modelname}(${pkClass} id);
	void save${modelname}(${modelname}Po ${modelname?uncap_first});
	void deletes(${pkClass}[] ids);
}