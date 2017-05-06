package com.dsdl.eidea.devs.strategy;

import com.dsdl.eidea.base.def.MenuTypeDef;
import com.dsdl.eidea.base.entity.bo.PageMenuBo;
import com.dsdl.eidea.base.entity.bo.PageMenuTrlBo;
import com.dsdl.eidea.base.service.PageMenuService;
import com.dsdl.eidea.core.entity.bo.LanguageBo;
import com.dsdl.eidea.core.service.LanguageService;
import com.dsdl.eidea.devs.i18n.TranslateHelper;
import com.dsdl.eidea.util.LocaleHelper;
import com.googlecode.genericdao.search.Search;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘大磊 on 2017/4/18 14:14.
 */
public class PagemenuGenerateStrategy {
    private String listUrl;
    private String name;
    private LanguageService languageService;
    private PageMenuService pageMenuService;

    public PagemenuGenerateStrategy(String listUrl, String name, LanguageService languageService, PageMenuService pageMenuService) {
        this.listUrl = listUrl;
        this.name = name;
        this.languageService = languageService;
        this.pageMenuService = pageMenuService;
    }

    public void generatePagemenu() {

        Search search=new Search();
        search.addFilterEqual("url",listUrl);
        List pageMenuList=pageMenuService.findPageMenu(search);
        if(pageMenuList.size()>0)
        {
            System.out.println("该菜单已经存在，生成菜单失败");
            return;
        }
        PageMenuBo pageMenuBo = new PageMenuBo();
        pageMenuBo.setCreated(true);
        pageMenuBo.setIsactive("N");
        pageMenuBo.setMenuType(MenuTypeDef.HREF.getKey());
        pageMenuBo.setName(this.name);
        pageMenuBo.setSeqNo(10000);
        pageMenuBo.setUrl(this.listUrl);
        List<LanguageBo> languageBoList = languageService.getLanguageForActivated();
        List<PageMenuTrlBo> pageMenuTrlBoList = new ArrayList<>();
        for (LanguageBo languageBo : languageBoList) {
            PageMenuTrlBo pageMenuTrlBo = new PageMenuTrlBo();
            if ("zh_CN".equals(languageBo.getCode())) {
                pageMenuTrlBo.setName(name);
            } else {
                pageMenuTrlBo.setName(TranslateHelper.translate(name, "zh", LocaleHelper.geLanguageCode(languageBo.getCode())));
            }
            pageMenuTrlBo.setLanguageCode(languageBo.getCode());

            pageMenuTrlBoList.add(pageMenuTrlBo);
        }
        pageMenuBo.setPageMenuTrlBo(pageMenuTrlBoList);
        pageMenuService.save(pageMenuBo);
    }
}
