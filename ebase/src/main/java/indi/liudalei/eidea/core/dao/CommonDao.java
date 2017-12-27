package indi.liudalei.eidea.core.dao;

import java.io.Serializable;

/**
 * Created by 刘大磊 on 2017/2/15.
 * 该接口为通用泛型接口，只能用DataAccess 注解注入
 */
public interface CommonDao<T, ID extends Serializable> extends BaseDao<T, ID> {

}
