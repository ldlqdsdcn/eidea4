package indi.liudalei.eidea.core.web.tld;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘大磊 on 2017/4/18 16:47.
 */
public class BaseMessageSupport extends BodyTagSupport {
    protected List params;
    public BaseMessageSupport() {
        super();
        params = new ArrayList();

    }

    @Override
    public int doStartTag() throws JspException {
        params.clear();
        return super.doStartTag();
    }
}
