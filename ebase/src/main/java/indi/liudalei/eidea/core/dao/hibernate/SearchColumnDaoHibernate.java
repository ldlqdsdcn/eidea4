package indi.liudalei.eidea.core.dao.hibernate;

import indi.liudalei.eidea.core.dao.SearchColumnDao;
import indi.liudalei.eidea.core.entity.po.SearchColumnPo;
import com.googlecode.genericdao.search.Search;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/19 16:35.
 */
@Repository
public class SearchColumnDaoHibernate extends BaseDaoHibernate<SearchColumnPo, Integer> implements SearchColumnDao {

    @Override
    public void removeSearchColumnIdNotIn(List<Integer> ids, Integer searchPoId) {
        Search search = new Search();
        search.addFilterNotIn("id", ids);
        search.addFilterEqual("coreSearch.id", searchPoId);
        List<SearchColumnPo> searchColumnPoList = search(search);
        for (SearchColumnPo searchColumnPo : searchColumnPoList) {
            remove(searchColumnPo);
        }
    }
}
