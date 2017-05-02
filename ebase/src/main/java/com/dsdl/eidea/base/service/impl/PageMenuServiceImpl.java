package com.dsdl.eidea.base.service.impl;

import com.dsdl.eidea.base.def.ActivateDef;
import com.dsdl.eidea.base.def.MenuTypeDef;
import com.dsdl.eidea.base.def.OperatorDef;
import com.dsdl.eidea.base.entity.bo.PageMenuBo;
import com.dsdl.eidea.base.entity.bo.PageMenuTrlBo;
import com.dsdl.eidea.base.entity.po.*;
import com.dsdl.eidea.base.service.PageMenuService;
import com.dsdl.eidea.core.dao.CommonDao;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.entity.bo.LanguageBo;
import com.dsdl.eidea.core.params.QueryParams;
import com.dsdl.eidea.core.service.LanguageService;
import com.dsdl.eidea.core.spring.annotation.DataAccess;
import com.dsdl.eidea.util.StringUtil;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by admin on 2016/12/13.
 */
@Service
public class PageMenuServiceImpl implements PageMenuService {
    private static final Logger logger = Logger.getLogger(PageMenuServiceImpl.class);
    @DataAccess(entity = PageMenuPo.class)
    private CommonDao<PageMenuPo, Integer> pageMenuDao;
    @DataAccess(entity = UserPo.class)
    private CommonDao<UserPo, Integer> userDao;
    @Autowired
    private LanguageService languageService;
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public PaginationResult<PageMenuBo> findPageMenu(Search search, QueryParams queryParams) {
        search.setFirstResult(queryParams.getFirstResult());
        search.setMaxResults(queryParams.getPageSize());
        PaginationResult<PageMenuBo> paginationResult = null;
        if (queryParams.isInit()) {
            SearchResult<PageMenuPo> searchResult = pageMenuDao.searchAndCount(search);
            List<PageMenuBo> list = modelMapper.map(searchResult.getResult(), new TypeToken<List<PageMenuBo>>() {
            }.getType());
            paginationResult = PaginationResult.pagination(list, searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
        } else {
            List<PageMenuPo> pageMenuPoList = pageMenuDao.search(search);
            List<PageMenuBo> pageMenuBoList = modelMapper.map(pageMenuPoList, new TypeToken<List<PageMenuBo>>() {
            }.getType());
            paginationResult = PaginationResult.pagination(pageMenuBoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
        }
        return paginationResult;
    }

    @Override
    public void deleteMenuById(Integer[] ids) {
        pageMenuDao.removeByIds(ids);
    }

    @Override
    public void save(PageMenuBo pageMenuBo) {
        PageMenuPo pageMenuPo = modelMapper.map(pageMenuBo, PageMenuPo.class);
        List<PageMenuTrlPo> pageMenuTrlPoList = new ArrayList<PageMenuTrlPo>();
        for (PageMenuTrlBo pt : pageMenuBo.getPageMenuTrlBo()) {
            PageMenuTrlPo pageMenuTrl = new PageMenuTrlPo();
            pageMenuTrl.setId(pt.getId());
            pageMenuTrl.setLanguageCode(pt.getLanguageCode());
            pageMenuTrl.setName(pt.getName());
            pageMenuTrl.setRemark(pt.getRemark());
            pageMenuTrl.setPageMenuPo(pageMenuPo);
            pageMenuTrlPoList.add(pageMenuTrl);
        }
        pageMenuPo.setPageMenuTrlPoList(pageMenuTrlPoList);
        pageMenuDao.save(pageMenuPo);
        pageMenuBo.setId(pageMenuPo.getId());

    }

    @Override
    public boolean findExistUrl(Integer id) {

        PageMenuPo po = pageMenuDao.find(id);
        if (po != null) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public PageMenuBo getPageMenuBo(Integer id) {
        PageMenuPo pageMenuPo = pageMenuDao.find(id);
        PageMenuBo pageMenuBo = modelMapper.map(pageMenuPo, PageMenuBo.class);
        List<PageMenuTrlBo> pageMenuTrlBo = modelMapper.map(pageMenuPo.getPageMenuTrlPoList(), new TypeToken<List<PageMenuTrlBo>>() {
        }.getType());

        List<LanguageBo> languageBoList = languageService.findLanguageListForActivated();
        List<PageMenuTrlBo> pagemenuList = new ArrayList<PageMenuTrlBo>();
        for (LanguageBo lan : languageBoList) {
            PageMenuTrlBo pagemenu = new PageMenuTrlBo();
            pagemenu.setLanguageCode(lan.getCode());
            if (pageMenuTrlBo.size() > 0) {
                for (PageMenuTrlBo pm : pageMenuTrlBo) {
                    if (lan.getCode().equals(pm.getLanguageCode())) {
                        pagemenu.setId(pm.getId());
                        pagemenu.setName(pm.getName());
                        pagemenu.setRemark(pm.getRemark());
                    }
                }
            }
            pagemenuList.add(pagemenu);
        }
        pageMenuBo.setPageMenuTrlBo(pagemenuList);

        return pageMenuBo;
    }

    @Override
    public List<PageMenuBo> getModuleMenuList() {
        Search search = new Search();
        search.addFilterIn("menuType", 2);
        List<PageMenuPo> pageMenuPoList = pageMenuDao.search(search);
        List<PageMenuBo> pageMenuBoList = modelMapper.map(pageMenuPoList, new TypeToken<List<PageMenuBo>>() {
        }.getType());
        return pageMenuBoList;
    }

    @Override
    public List<PageMenuBo> getListMenuType() {
        Search search = new Search();
        search.addFilterIn("menuType", MenuTypeDef.FOLDER.getKey());
        List<PageMenuPo> pageMenuPoList = pageMenuDao.search(search);
        List<PageMenuBo> PageMenuBoList = modelMapper.map(pageMenuPoList, new TypeToken<List<PageMenuBo>>() {
        }.getType());
        return PageMenuBoList;
    }

    public String getLeftMenuListByUserId(Integer userId, String contextPath) {
        return getLeftMenuListByUserId(userId, contextPath, "zh_CN");
    }

    @Override
    public String getLeftMenuListByUserId(Integer userId, String contextPath, String languageCode) {
        UserPo userPo = userDao.find(userId);
        List<UserRolePo> userRolePoList = userPo.getSysUserRoles();
        List<PageMenuPo> pageMenuPoList = new ArrayList<>();
        for (UserRolePo userRolePo : userRolePoList) {
            RolePo rolePo = userRolePo.getSysRole();
            logger.debug("rolePo name=" + rolePo.getName());
            if (rolePo.getIsactive().equals(ActivateDef.ACTIVATED.getKey())) {
                List<ModuleRolePo> moduleRolePoList = rolePo.getSysModuleRoles();
                for (ModuleRolePo moduleRolePo : moduleRolePoList) {
                    if (hasViewPrivilege(moduleRolePo)) {
                        List<ModuleMenuPo> moduleMenuPoList = moduleRolePo.getSysModule().getSysModuleMenus();
                        for (ModuleMenuPo moduleMenuPo : moduleMenuPoList) {
                            PageMenuPo pageMenuPo = moduleMenuPo.getSysPageMenu();
                            logger.debug("menu name=" + pageMenuPo.getName());
                            if (ActivateDef.ACTIVATED.getKey().equals(pageMenuPo.getIsactive())) {

                                if (!pageMenuPoList.contains(pageMenuPo)) {
                                    pageMenuPoList.add(pageMenuPo);
                                }
                            }
                        }
                    }
                }
            }
        }
        logger.debug("pageMenuPoList.size=" + pageMenuPoList.size());
        return getLeftMenuList(pageMenuPoList, contextPath, languageCode);
    }

    private boolean hasViewPrivilege(ModuleRolePo moduleRolePo) {
        List<PrivilegesPo> privilegesPoList = moduleRolePo.getSysPrivilegeses();
        for (PrivilegesPo privilegesPo : privilegesPoList) {
            if (privilegesPo.getSysOperator().getNo().equals(OperatorDef.VIEW.getKey())) {
                return true;
            }
        }
        return false;
    }

    private List<PageMenuPo> getForderMenu(PageMenuPo pageMenuPo, List<PageMenuPo> menuForderList) {


        if (pageMenuPo.getParentMenuId() != null) {
            PageMenuPo forderMenu = pageMenuDao.find(pageMenuPo.getParentMenuId());
            if (!menuForderList.contains(forderMenu)) {
                menuForderList.add(forderMenu);
                if (forderMenu.getParentMenuId() != null) {
                    getForderMenu(forderMenu, menuForderList);
                }

            }

        }

        return menuForderList;
    }

    private String getLeftMenuList(List<PageMenuPo> pageMenuPoList, String contextPath, String languageCode) {

        List<PageMenuPo> menuForderList = new ArrayList<>();

        for (PageMenuPo pageMenuPo : pageMenuPoList) {
            getForderMenu(pageMenuPo, menuForderList);
        }
        pageMenuPoList.addAll(menuForderList);
        logger.debug("pageMenuPoList.size=" + pageMenuPoList.size());
        List<PageMenuBo> pageMenuBoList = new ArrayList<>();
        for (PageMenuPo pageMenuPo : pageMenuPoList) {
            List<PageMenuTrlPo> pageMenuTrlPoList = pageMenuPo.getPageMenuTrlPoList();
            PageMenuBo pageMenuBo = modelMapper.map(pageMenuPo, PageMenuBo.class);
            if (pageMenuTrlPoList != null) {
                for (PageMenuTrlPo pageMenuTrlPo : pageMenuTrlPoList) {
                    if (languageCode.equals(pageMenuTrlPo.getLanguageCode())) {
                        if (StringUtil.isNotEmpty(pageMenuTrlPo.getName())) {
                            pageMenuBo.setName(pageMenuTrlPo.getName());
                        }
                        break;
                    }
                }
            }
            pageMenuBoList.add(pageMenuBo);

        }
//        List<PageMenuBo> pageMenuBoList = modelMapper.map(pageMenuPoList, new TypeToken<List<PageMenuBo>>() {
//        }.getType());
        logger.debug("pageMenuBoList.size=" + pageMenuBoList.size());
        return buildMenu(null, pageMenuBoList, contextPath);

    }

    private String buildMenu(Integer menuId, List<PageMenuBo> pageMenuBoList, String contextPath) {

        List<PageMenuBo> currentLevelPageMenuList = new ArrayList<>();
        for (int i = pageMenuBoList.size() - 1; i >= 0; i--) {
            PageMenuBo pageMenuBo = pageMenuBoList.get(i);
            if (menuId == pageMenuBo.getParentMenuId() || (menuId != null && menuId.equals(pageMenuBo.getParentMenuId()))) {
                pageMenuBoList.remove(i);
                currentLevelPageMenuList.add(pageMenuBo);
            }
        }
        currentLevelPageMenuList = currentLevelPageMenuList.stream().sorted((h1, h2) -> h1.getSeqNo().compareTo(h2.getSeqNo())).collect(Collectors.toList());
        StringBuffer buffer = new StringBuffer();
        if (currentLevelPageMenuList.size() > 0) {
            if (menuId == null) {
                buffer.append("<ul class=\"nav side-menu\">");
            } else {
                buffer.append("<ul class=\"nav child_menu\">");
            }
            for (PageMenuBo pageMenuBo : currentLevelPageMenuList) {
                if (pageMenuBo.getParentMenuId() == menuId || (menuId != null && menuId.equals(pageMenuBo.getParentMenuId()))) {
                    buffer.append("<li><a ");
                    if (pageMenuBo.getMenuType() == MenuTypeDef.HREF.getKey()) {
                        buffer.append("data-addtab='").append(pageMenuBo.getName()).append("' data-url='").append(contextPath).append(pageMenuBo.getUrl()).append("'");
                    }
                    buffer.append(">");
                    if (StringUtil.isNotEmpty(pageMenuBo.getIcon()) && pageMenuBo.getParentMenuId() == null) {
                        buffer.append("<i class=\"").append(pageMenuBo.getIcon()).append("\"></i>");
                    }
                    buffer.append(pageMenuBo.getName());
                    if (pageMenuBo.getMenuType() == MenuTypeDef.FOLDER.getKey()) {
                        buffer.append("<span class=\"fa fa-chevron-down\"></span>");
                    }
                    buffer.append("</a>");
                    if (pageMenuBo.getMenuType() == MenuTypeDef.FOLDER.getKey()) {
                        buffer.append(buildMenu(pageMenuBo.getId(), pageMenuBoList, contextPath));
                    }
                    buffer.append("</li>\n");
                }
            }
            buffer.append("</ul>");
        }
        return buffer.toString();
    }

}
