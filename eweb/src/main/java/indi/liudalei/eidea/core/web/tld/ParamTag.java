package indi.liudalei.eidea.core.web.tld;

import indi.liudalei.eidea.base.web.vo.UserResource;
import indi.liudalei.eidea.core.web.def.WebConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

/**
 * Created by 刘大磊 on 2017/4/18 16:33.
 */
public class ParamTag extends BodyTagSupport {
    public static final String NORMAL_PARAM = "normal";
    public static final String LABEL_PARAM = "label";
    public static final String MESSAGE_PARAM = "message";
    protected Object value;
    /**
     * message,label,normal
     */
    private String type = NORMAL_PARAM;

    public void setValue(Object value) throws JspTagException {
        this.value = value;

    }

    @Override
    public int doEndTag() throws JspException {
        Tag t = findAncestorWithClass(this, BaseMessageSupport.class);
        if (t == null) {
            throw new JspTagException("参数标签不在 label标签或message标签里面");
        }
        BaseMessageSupport parent = (BaseMessageSupport) t;

        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        UserResource resource = (UserResource) request.getSession().getAttribute(WebConst.SESSION_RESOURCE);
        switch (type) {
            case LABEL_PARAM:
                this.value = resource.getLabel((String) value);
                break;
            case MESSAGE_PARAM:
                this.value = resource.getMessage((String) value);
                break;
        }
        parent.params.add(value);
        return super.doEndTag();
    }

    public void setType(String type) {
        this.type = type;
    }
}
