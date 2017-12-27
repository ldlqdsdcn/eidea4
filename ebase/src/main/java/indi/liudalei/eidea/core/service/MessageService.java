package indi.liudalei.eidea.core.service;

import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.entity.bo.MessageBo;
import indi.liudalei.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;
import indi.liudalei.eidea.core.i18n.DbResourceBundle;

import java.util.List;

/**
 * Created by admin on 2016/12/16.
 */
public interface MessageService {
    PaginationResult<MessageBo> findMessage(Search search, QueryParams queryParams);

    MessageBo getMessageBo(String key);

    boolean save(MessageBo messageBo);

    void deletes(String[] keys);

    List<MessageBo> findMessageListForActivated(String[] notInCodes);

    boolean findExistMessage(String key);

    /**
     * 获取国际化信息
     *
     * @param lang
     * @return
     */
    DbResourceBundle getResourceBundle(String lang);
}
