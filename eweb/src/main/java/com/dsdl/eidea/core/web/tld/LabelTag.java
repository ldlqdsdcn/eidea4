package com.dsdl.eidea.core.web.tld;

import com.dsdl.eidea.base.service.SpringContextHolder;
import com.dsdl.eidea.base.web.vo.UserResource;
import com.dsdl.eidea.core.i18n.DbResourceBundle;
import com.dsdl.eidea.core.service.MessageService;
import com.dsdl.eidea.core.web.def.WebConst;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import java.text.MessageFormat;

/**
 * Created by 刘大磊 on 2016/12/29 9:50.
 * 输出对应的 label消息
 */
public class LabelTag extends BaseMessageSupport {
    private static final Logger logger = Logger.getLogger(LabelTag.class);
    private String key;
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
            String keyValue = resource.getLabel(key);
            if (!params.isEmpty()) {
                Object[] paramArray = params.toArray();
                keyValue = MessageFormat.format(keyValue,paramArray);
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

}
