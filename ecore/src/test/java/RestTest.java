import indi.liudalei.eidea.util.RestHelper;

/**
 * @author liudalei
 * @version 1.0.0
 * @date 2017/11/16
 * @description
 */
public class RestTest {
    public static void main(String[] args)
    {
        RestHelper restHelper=new RestHelper();

        restHelper.addHeader("x-api-key","K4pNHoNC5DLwkKIy3m84Vx23pjR8wZicRLcfpGNiovCNLCdMGlBpvXsl5RG794Nu");
        RestHelper.ResponseEntity responseEntity=restHelper.postBody("http://123.235.48.50:9000/bankapi/qlRefactoring/replyFinanceResult",
                "[{\"receivableNo\":\"PN2017111400009\",\"remark\":\"test\",\"reqNo\":\"FA2017111400004\",\"status\":\"N\"}]");
        System.out.println("status:"+responseEntity.getStatus());
        System.out.println(responseEntity.getBody());
    }
}
