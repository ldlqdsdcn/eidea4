致力开发 开发效率最高，运行效率最高的java web框架

基于 spring mvc+spring +Hibernate.

该框架适用于ERP CRM SCM 等系统开发。
也可以通过RMI,支持分布式网站系统，电商平台，手机app服务器
Dao数据操作仅仅增删改查，没有特殊需求，不需要写Dao接口和Dao实现类，用下面这种引入方式：
    @DataAccess(entity = RolePo.class)
    private CommonDao<RolePo,Integer> roleDao;
    数据库sql文件<br>
    https://github.com/ldlqdsdcn/eidea4db
    
    配置说明：
    
    1.安装lombok插件
    https://projectlombok.org/ 
    2.spring 框架支持

    2.把ereport 字体库注释掉
3. spring rmi 作为分布式框架
4.测试框架使用 mockit

4.  storm 用于高并发处理
