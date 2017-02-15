package com.dsdl.eidea.common.web.controller;

import com.dsdl.eidea.base.web.vo.UserResource;
import com.dsdl.eidea.core.i18n.DbResourceBundle;
import com.dsdl.eidea.core.service.MessageService;
import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.util.LocaleHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by Bobo on 2017/1/11 15:06.
 */
@Controller
public class ChangedLanguageController {
    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/common/changeLanguage", method = RequestMethod.GET)
    public ModelAndView changeLanguage(HttpServletRequest request, String language) {
        ModelAndView modelAndView = new ModelAndView("/login");
        if (language == null) {
            language = request.getLocale().toString();
        }
        DbResourceBundle dbResourceBundle = messageService.getResourceBundle(language);
        Locale locale = LocaleHelper.parseLocale(language);
        request.getSession().setAttribute(WebConst.SESSION_RESOURCE, new UserResource(locale, dbResourceBundle));
        return modelAndView;
    }

}
