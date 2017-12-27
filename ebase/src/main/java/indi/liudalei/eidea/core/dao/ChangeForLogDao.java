package indi.liudalei.eidea.core.dao;

import java.io.Serializable;

/**
 * Created by 刘大磊 on 2016/12/22 13:50.
 */
public interface ChangeForLogDao {
    void update(Object model,Serializable pk);
    void delete(Object model,Serializable pk);
    void insert(Object model,Serializable pk);
}
