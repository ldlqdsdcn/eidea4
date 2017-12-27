package indi.liudalei.eidea.soap.ws;

import indi.liudalei.eidea.soap.model.Client;
import org.apache.cxf.annotations.WSDLDocumentation;
import org.apache.cxf.annotations.WSDLDocumentationCollection;

import javax.jws.WebService;
import java.util.List;

/**
 * Created by 刘大磊 on 2017/4/22 11:27.
 */
@WebService
@WSDLDocumentationCollection(
        {
                @WSDLDocumentation("实体信息访问接口"),
                @WSDLDocumentation(value = "实体信息",
                        placement = WSDLDocumentation.Placement.TOP),
                @WSDLDocumentation(value = "My binding description",
                        placement = WSDLDocumentation.Placement.BINDING)
        }
)
public interface ClientWs {
    @WSDLDocumentation("获取所有用户信息")
    List<Client> getUserList();
    @WSDLDocumentation("你好啊测试")
    public String getHello(String name);
}
