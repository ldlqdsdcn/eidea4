package indi.liudalei.eidea.core.dao.mybatis;

import indi.liudalei.eidea.base.entity.bo.UserBo;
import indi.liudalei.eidea.base.entity.po.ChangelogPo;
import indi.liudalei.eidea.core.dao.ChangeForLogDao;
import indi.liudalei.eidea.core.dao.util.ChangelogHelper;
import indi.liudalei.eidea.core.def.ChangeOperatorType;
import indi.liudalei.eidea.core.entity.bo.TableBo;
import indi.liudalei.eidea.core.entity.bo.TableColumnBo;
import indi.liudalei.eidea.util.StringUtil;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.hibernate.HibernateMetadataUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;

/**
 * Created by 刘大磊 on 2016/12/22 13:52.
 */
@Repository
public class ChangeForLogDaoMybatis implements ChangeForLogDao {
    private static final Logger logger = Logger.getLogger(ChangeForLogDaoMybatis.class);
    private HibernateMetadataUtil metadataUtil;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        metadataUtil = HibernateMetadataUtil.getInstanceForSessionFactory(sessionFactory);
    }

    private SessionFactory sessionFactory;
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    @Autowired(required = false)
    private HttpServletRequest request;
    private final Gson gson = new Gson();

    private UserBo getUser() {
        if (request == null) {
            return null;
        }
        UserBo user = (UserBo) request.getSession().getAttribute("loginUser");
        return user;
    }

    @Override
    public void update(Object model, Serializable pk) {

        this.writeLog((Serializable) model, ChangeOperatorType.UPDATE.getKey(), pk);

    }

    @Override
    public void delete(Object model, Serializable pk) {
        this.writeLog((Serializable) model, ChangeOperatorType.DELETE.getKey(), pk);
    }

    @Override
    public void insert(Object model, Serializable pk) {

        this.writeLog((Serializable) model, ChangeOperatorType.INSERT.getKey(), pk);

    }

    private List<Map<String, Object>> writeChangelogList(Collection<Serializable> collection) {
        List<Map<String, Object>> list = new ArrayList<>(collection.size());
        for (Serializable line : collection) {
            Map<String, Object> oneRow = writeChangelogContent(line);
            list.add(oneRow);
        }
        return list;
    }

    private Map<String, Object> writeChangelogContent(Serializable model) {
        Map<String, Object> obj = new HashMap<>();
        ;
        TableBo table = ChangelogHelper.get(model.getClass().getName());
        if (table != null) {
            try {
                List<TableColumnBo> tableColumnList = table.getTableColumnBoList();
                if (tableColumnList != null) {
                    for (TableColumnBo column : tableColumnList) {
                        Object fieldObject = PropertyUtils.getProperty(model, column.getPropertyName());
                        if (fieldObject instanceof Collection) {
                            List<Map<String, Object>> result = writeChangelogList((Collection) fieldObject);
                            obj.put(column.getPropertyName(), result);
                        } else {
                            obj.put(column.getPropertyName(), fieldObject);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("插入操作日志出错", e);
            }
        }
        return obj;
    }

    public void writeLog(Serializable model, String operateType, Serializable pk) {
        TableBo table = ChangelogHelper.get(model.getClass().getName());
        try {
            if (table != null) {
                Map<String, Object> obj = writeChangelogContent(model);

                ChangelogPo log = new ChangelogPo();
                log.setOperateType(operateType);
                log.setInDate(new Date());
                log.setContext(gson.toJson(obj));
                log.setTableId(table.getId());
                if(getUser()==null)
                {
                    //TODO 如果找不到用户，则为管理员
                    log.setUserId(1);
                }
                else
                {
                    log.setUserId(getUser().getId());
                }

                if (request != null) {
                    UserBo user = (UserBo) request.getSession().getAttribute("loginUser");
                    if (user != null) {
                        log.setUserId(user.getId());
                    }
                }

                if (pk != null)
                    log.setPk(String.valueOf(pk));
                if (StringUtil.isNotEmpty(table.getBuPk())) {
                    Object buPk = PropertyUtils.getProperty(model, table.getBuPk());
                    if (buPk != null)
                        log.setBuPk(buPk.toString());

                }
                log.setTableId(table.getId());
                sqlSessionTemplate.insert("com.dsdl.base.dao.sql.ChangelogMapper.insert", log);
            }

        } catch (Exception e) {
            logger.error("插入操作日志出错", e);
        }
    }
}
