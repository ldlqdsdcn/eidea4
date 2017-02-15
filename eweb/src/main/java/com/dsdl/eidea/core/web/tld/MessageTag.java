package com.dsdl.eidea.core.web.tld;

import com.dsdl.eidea.base.service.SpringContextHolder;
import com.dsdl.eidea.base.web.vo.UserResource;
import com.dsdl.eidea.core.i18n.DbResourceBundle;
import com.dsdl.eidea.core.service.MessageService;
import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.util.StringUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Created by 刘大磊 on 2016/12/29 9:50.
 */
public class MessageTag extends BodyTagSupport {
    private static final Logger logger = Logger.getLogger(MessageTag.class);
    private String key;
    private String paramValue;
    private MessageService messageService= SpringContextHolder.getBean(MessageService.class);

    /* (non-Javadoc)
     * @see javax.servlet.jsp.tagext.BodyTagSupport#doEndTag()
     */
    @Override
    public int doEndTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        UserResource resource = (UserResource) request.getSession().getAttribute(WebConst.SESSION_RESOURCE);
        if(resource==null)
        {
           DbResourceBundle dbResourceBundle= messageService.getResourceBundle(request.getLocale().toString());
            resource=new UserResource(request.getLocale(),dbResourceBundle);
            request.getSession().setAttribute(WebConst.SESSION_RESOURCE,resource);
        }
        try {
            String keyValue = resource.getMessage(key);

            if (StringUtil.isNotEmpty(paramValue)) {
                Object[] paramArray = paramValue.split("#");
                keyValue = String.format(keyValue, paramArray);
            }

            pageContext.getOut().print(keyValue);
        } catch (Exception e) {
            logger.error("国际化输出错误" + key, e);
        }
        return super.doEndTag();
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    public void setParamValue(String paramValue) {

        this.paramValue = paramValue;
    }
}
