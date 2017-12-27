package indi.liudalei.eidea.core.web.tld;

import indi.liudalei.eidea.base.service.SpringContextHolder;
import indi.liudalei.eidea.base.web.vo.UserResource;
import indi.liudalei.eidea.core.i18n.DbResourceBundle;
import indi.liudalei.eidea.core.service.MessageService;
import indi.liudalei.eidea.core.web.def.WebConst;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import java.text.MessageFormat;

/**
 * Created by 刘大磊 on 2016/12/29 9:50.
 */
public class MessageTag extends BaseMessageSupport {
    private static final Logger logger = Logger.getLogger(MessageTag.class);
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
            String keyValue = resource.getMessage(key);

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
