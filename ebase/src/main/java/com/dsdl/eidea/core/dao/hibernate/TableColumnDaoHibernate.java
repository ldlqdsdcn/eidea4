package com.dsdl.eidea.core.dao.hibernate;

import com.dsdl.eidea.core.dao.SearchDao;
import com.dsdl.eidea.core.dao.TableColumnDao;
import com.dsdl.eidea.core.entity.po.SearchPo;
import com.dsdl.eidea.core.entity.po.TableColumnPo;
import org.springframework.stereotype.Repository;

/**
 * Created by 刘大磊 on 2016/12/23 9:02.
 */
@Repository
public class TableColumnDaoHibernate extends BaseDaoHibernate<TableColumnPo, Integer>  implements TableColumnDao {
}
