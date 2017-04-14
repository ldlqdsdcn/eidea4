import com.dsdl.eidea.util.ReadPropertiesUtil;

import java.net.URLEncoder;

/**
 * Created by 刘大磊 on 2017/1/9 11:10.
 */
public class EncodeTest {
    public static void main(String[] args)throws Exception
    {
        System.out.println(URLEncoder.encode(" (x86)", "GBK"));

       String aeskey= ReadPropertiesUtil.readValue("aeskey");
        System.out.println(aeskey);
        //System.out.println;
    }
}
