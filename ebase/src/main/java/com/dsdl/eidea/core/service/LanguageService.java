package com.dsdl.eidea.core.service;

import com.dsdl.eidea.core.entity.bo.LanguageBo;
import com.googlecode.genericdao.search.Search;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/8 16:14.
 */
public interface LanguageService {
    List<LanguageBo> findLanguage(Search search);

    LanguageBo getLanguageBo(String code);

    boolean save(LanguageBo languageBo);

    void deletes(String[] codes);

    boolean findExistLanguageByName(String languageName);

    /**
     * 获取语言有效的语言列表
     *
     * @param notInCodes 不包含参数中的语言
     * @return
     */
    List<LanguageBo> findLanguageListForActivated(String[] notInCodes);

    /**
     * 获取有效的语言列表
     *
     * @return
     */
    List<LanguageBo> findLanguageListForActivated();

    /**
     * 判断语言是否存在
     *
     * @param code
     * @return
     */
    boolean findExistLanguage(String code);

    /**
     * getLanguageForActivated:登录页面语种
     *
     * @return
     */
    List<LanguageBo> getLanguageForActivated();

}
