-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.7.12-log


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema e_idea
--

CREATE DATABASE IF NOT EXISTS e_idea;
USE e_idea;

--
-- Definition of table `base_datadict`
--

DROP TABLE IF EXISTS `base_datadict`;
CREATE TABLE `base_datadict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL,
  `msgtext` varchar(200) NOT NULL,
  `isactive` char(1) NOT NULL,
  `data_type` varchar(20) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `base_datadict`
--

/*!40000 ALTER TABLE `base_datadict` DISABLE KEYS */;
INSERT INTO `base_datadict` (`id`,`code`,`msgtext`,`isactive`,`data_type`,`remark`) VALUES 
 (1,'Y','有效','Y','isactive','是否有效'),
 (2,'N','无效','N','isactive','是否有效');
/*!40000 ALTER TABLE `base_datadict` ENABLE KEYS */;


--
-- Definition of table `core_label`
--

DROP TABLE IF EXISTS `core_label`;
CREATE TABLE `core_label` (
  `_key` varchar(100) NOT NULL,
  `msgtext` varchar(500) NOT NULL,
  `isactive` char(1) DEFAULT NULL,
  PRIMARY KEY (`_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `core_label`
--

/*!40000 ALTER TABLE `core_label` DISABLE KEYS */;
INSERT INTO `core_label` (`_key`,`msgtext`,`isactive`) VALUES 
 ('base.delete.select.column','删除选择列','Y'),
 ('base.Field.information','字段信息','Y'),
 ('base.identifier','标识符','Y'),
 ('base.newBuild','新建','Y'),
 ('base.remarks','备注','Y'),
 ('base.save.success','保存成功','Y'),
 ('base.saveall','保存','Y'),
 ('base.search','查找','Y'),
 ('base.serialNumber','序号','Y'),
 ('base.whetherEffective','是否有效','Y'),
 ('changelog.actiontypes','动作类型','Y'),
 ('changelog.business.name','业务名','Y'),
 ('changelog.operatetime','操作时间','Y'),
 ('changelog.operation.list','操作日志列表','Y'),
 ('changelog.primarykey','主键','Y'),
 ('changelog.show.all','显示全部','Y'),
 ('changelog.show.thisartical','显示本条','Y'),
 ('client.column.no','No','Y'),
 ('client.title','实体信息','Y'),
 ('common.button.add','添加','Y'),
 ('common.button.back','返回','Y'),
 ('common.button.checkfalse','false','Y'),
 ('common.button.checktrue','true','Y'),
 ('common.button.closed','关闭','Y'),
 ('common.button.create','新建','Y'),
 ('common.button.create_wizard','新建向导','Y'),
 ('common.button.delete','删除','Y'),
 ('common.button.edit','编辑','Y'),
 ('common.button.inquire','查询','Y'),
 ('common.button.no','否','Y'),
 ('common.button.save','保存','Y'),
 ('common.button.search','查找','Y'),
 ('common.button.view','查看','Y'),
 ('common.button.yes','是','Y'),
 ('common.label.firstpage','第一页','Y'),
 ('common.label.lastpage','最后页','Y'),
 ('common.label.nextpage','下一页','Y'),
 ('common.label.previouspage','上一页','Y'),
 ('common.search.character','比较符','Y'),
 ('common.search.field','字段','Y'),
 ('common.search.fields','字段列表','Y'),
 ('common.search.query.condition.empty','查询条件为空','Y'),
 ('common.search.value','value','Y'),
 ('custom.list.index','序号','Y'),
 ('customer.column.email','电子邮箱','Y'),
 ('customer.column.telephone','联系电话','Y'),
 ('customerext.column.proplabel','标签','Y'),
 ('datadict.column.isactive','停用','Y'),
 ('datadict.column.name','名称','Y'),
 ('directory.access.directory','访问目录','Y'),
 ('dymenu.label.chainedaddress','链接地址','Y'),
 ('dymenu.label.menufolde','菜单文件夹','Y'),
 ('dymenu.label.menuname','菜单名称','Y'),
 ('dymenu.label.newmenu','新建菜单','Y'),
 ('dymenuForm.label.icon','图标','Y'),
 ('dymenuForm.label.menutype','菜单类型','Y'),
 ('dymenuForm.label.name','页面名称','Y'),
 ('dymenuForm.label.sequence','显示顺序','Y'),
 ('dymenuForm.label.type.hyperlink','超链接','Y'),
 ('dymenuForm.title2','菜单设置','Y'),
 ('dymenus.label.type','类型','Y'),
 ('dymenus.label.type.hyperlink','超链接','Y'),
 ('dymenus.title','菜单设置','Y'),
 ('index.change_password','修改密码','Y'),
 ('index.email_message','Film festivals used to be do-or-die moments for movie makers. They were where...','Y'),
 ('index.email_name','John Smith','Y'),
 ('index.email_see_all','查看全部邮件','Y'),
 ('index.email_size','email_size','Y'),
 ('index.email_time','3 mins ago','Y'),
 ('index.enterprise_thinking','企业思想','Y'),
 ('index.help','帮助','Y'),
 ('index.log_out','登出','Y'),
 ('index.profile','个人设置','Y'),
 ('index.proportion','50%','Y'),
 ('index.settings','系统设置','Y'),
 ('index.title','鼎商动力企业平台','Y'),
 ('javaclass.column.classname','类名','Y'),
 ('label.keyvalue','键值','Y'),
 ('label.keyvalueone','key值','Y'),
 ('label.message','信息','Y'),
 ('label.messagevalue','消息内容','Y'),
 ('label.translation','标签翻译','Y'),
 ('language.column.code','编码','Y'),
 ('language.language','语言','Y'),
 ('language.title','语言信息','Y'),
 ('language.translation','语言翻译','Y'),
 ('linkman.column.email','电子邮件','Y'),
 ('linkman.location','位置：联系人','Y'),
 ('load.please.wait','加载中,请稍等……','Y'),
 ('login.login','登 录','Y'),
 ('login.name.already.exists','登录名称已存在','Y'),
 ('login.password','密  码','Y'),
 ('login.title','用户登录','Y'),
 ('login.userName','用户编号','Y'),
 ('login.username.login','登录名称','Y'),
 ('loginlog.label.host','登陆主机','Y'),
 ('loginlog.label.ip','IP地址','Y'),
 ('loginlog.label.lastpage','最后页','Y'),
 ('loginlog.label.log','登录日志','Y'),
 ('loginlog.label.logindate','登录时间','Y'),
 ('loginlog.label.loginhost','登录主机','Y'),
 ('loginlog.label.loginname','登录名','Y'),
 ('loginlog.label.logoutdate','登出时间','Y'),
 ('loginlog.label.sessionId','会话主键','Y'),
 ('menu.changelog','操作日志','Y'),
 ('menu.module','模块设置','Y'),
 ('menu.online','在线用户','Y'),
 ('menu.user','用户信息','Y'),
 ('message.copy.success','复制成功','Y'),
 ('message.message.setting','消息设置','Y'),
 ('message.message.translation','消息翻译','Y'),
 ('Message.required','必填信息','Y'),
 ('Messages','消息','Y'),
 ('module.column.moduleaccessdirectory','访问目录设置','Y'),
 ('module.column.modulesetlist','模块设置列表','Y'),
 ('module.column.name','模块名称','Y'),
 ('module.label.parentmenu','父菜单','Y'),
 ('module.name.checkfalse','否','Y'),
 ('module.name.checktrue','是','Y'),
 ('officeTestExam.button.view','查看','Y'),
 ('officeTestExam.select.selectTypeList','查询类型','Y'),
 ('online.label.logindate','登录时间','Y'),
 ('operator.column.init','是否初始化','Y'),
 ('org.column.level','组织级别','Y'),
 ('org.column.name','组织名称','Y'),
 ('org.column.no','No','Y'),
 ('org.title','组织信息','Y'),
 ('page.column.name','页面名称','Y'),
 ('password.confirm.password.inconsistent','密码和确认密码不一致','Y'),
 ('public.error.goback','返回上一页','Y'),
 ('public.error.hidedetail','隐藏详细','Y'),
 ('public.error.title','错误提示:','Y'),
 ('public.error.viewdetail','查看详细','Y'),
 ('realtrans.column.org','组织','Y'),
 ('role.data.access.rights','数据访问权限','Y'),
 ('role.data.modelName','模块名称','Y'),
 ('role.operatingAuthority','操作权限','Y'),
 ('role.operationController','操作控制','Y'),
 ('role.organizationName','组织名称','Y'),
 ('role.roleName','角色名','Y'),
 ('role.setUp','角色设置','Y'),
 ('search.condition','查询条件维护','Y'),
 ('search.connection.symbol','连接符','Y'),
 ('search.page.type','查询页类型','Y'),
 ('searchcolumn.column.datatype','数据类型','Y'),
 ('searchcolumn.column.showtype','显示类型','Y'),
 ('searchcolumn.column.tableName','表名','Y'),
 ('Security','安全设置','Y'),
 ('success.save','保存成功','Y'),
 ('table.column.buskey','业务主键','Y'),
 ('table.column.order','顺序','Y'),
 ('table.column.po','Po类','Y'),
 ('table.column_output_log','输出日志','Y'),
 ('table.label.attributename','属性名','Y'),
 ('table.label.column','列名','Y'),
 ('table.label.columndesc','列注释','Y'),
 ('table.label.data_type','数据类型','Y'),
 ('table.label.decimal_places','小数位','Y'),
 ('table.label.defaults','默认值','Y'),
 ('table.label.external_association_table','外部关联表','Y'),
 ('table.label.field_name','字段名','Y'),
 ('table.label.field_type','字段类型','Y'),
 ('table.label.foreign_key','外键','Y'),
 ('table.label.index_name','索引名','Y'),
 ('table.label.is_empty','是否为空','Y'),
 ('table.label.no','编号','Y'),
 ('table.label.reference_field','引用字段','Y'),
 ('table.label.reference_table','引用表','Y'),
 ('table.label.size','大小','Y'),
 ('table.label.type','类型','Y'),
 ('table.lable.column_size','列数大小','Y'),
 ('table.lable.digits','数字','Y'),
 ('table.lable.information','字段信息','Y'),
 ('table.lable.isview','是否显示','Y'),
 ('table.lable.log','记录日志','Y'),
 ('table.lable.only','唯一','Y'),
 ('table.title','表信息维护','Y'),
 ('table.wizard.index','索引','Y'),
 ('table.wizard.table_notes','表注释','Y'),
 ('table.wizard.title','根据表结构生成生成table和table_column','Y'),
 ('user.column.client','实体','Y'),
 ('user.column.password','密码','Y'),
 ('user.column.role','角色','Y'),
 ('user.column.telephone','电话','Y'),
 ('user.ConfirmPassword','密码确认','Y'),
 ('user.initialization','初始化','Y'),
 ('user.user.name','用户名称','Y'),
 ('user.username','用户姓名','Y');
/*!40000 ALTER TABLE `core_label` ENABLE KEYS */;


--
-- Definition of table `core_label_trl`
--

DROP TABLE IF EXISTS `core_label_trl`;
CREATE TABLE `core_label_trl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `msgtext` varchar(500) NOT NULL,
  `_key` varchar(100) NOT NULL,
  `lang` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_core_label_trl_core_label1_idx` (`_key`),
  KEY `fk_core_label_trl_core_language1_idx` (`lang`),
  CONSTRAINT `fk_core_label_trl_core_label1` FOREIGN KEY (`_key`) REFERENCES `core_label` (`_key`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_core_label_trl_core_language1` FOREIGN KEY (`lang`) REFERENCES `core_language` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2161 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `core_label_trl`
--

/*!40000 ALTER TABLE `core_label_trl` DISABLE KEYS */;
INSERT INTO `core_label_trl` (`id`,`msgtext`,`_key`,`lang`) VALUES 
 (410,'电子邮箱','customer.column.email','zh_CN'),
 (411,'Email','customer.column.email','en_US'),
 (435,'联系电话','customer.column.telephone','zh_CN'),
 (489,'标签','customerext.column.proplabel','zh_CN'),
 (504,'停用','datadict.column.isactive','zh_CN'),
 (505,'名称','datadict.column.name','zh_CN'),
 (506,'Name','datadict.column.name','en_US'),
 (534,'菜单名称','dymenu.label.menuname','zh_CN'),
 (535,'Menu Name','dymenu.label.menuname','en_US'),
 (540,'页面名称','dymenuForm.label.name','zh_CN'),
 (541,'Menu Name','dymenuForm.label.name','en_US'),
 (548,'显示顺序','dymenuForm.label.sequence','zh_CN'),
 (549,'Sequence','dymenuForm.label.sequence','en_US'),
 (552,'超链接','dymenuForm.label.type.hyperlink','zh_CN'),
 (553,'Hyperlink','dymenuForm.label.type.hyperlink','en_US'),
 (570,'菜单设置','dymenuForm.title2','zh_CN'),
 (581,'类型','dymenus.label.type','zh_CN'),
 (582,'Type','dymenus.label.type','en_US'),
 (583,'超链接','dymenus.label.type.hyperlink','zh_CN'),
 (584,'Hyperlink','dymenus.label.type.hyperlink','en_US'),
 (597,'菜单设置','dymenus.title','zh_CN'),
 (598,'Menu Setting','dymenus.title','en_US'),
 (667,'类名','javaclass.column.classname','zh_CN'),
 (668,'Class Name','javaclass.column.classname','en_US'),
 (679,'编码','language.column.code','zh_CN'),
 (680,'Code','language.column.code','en_US'),
 (685,'语言','language.title','zh_CN'),
 (686,'Language','language.title','en_US'),
 (703,'电子邮件','linkman.column.email','zh_CN'),
 (704,'Email','linkman.column.email','en_US'),
 (738,'位置：联系人','linkman.location','zh_CN'),
 (739,'Location:Linkman','linkman.location','en_US'),
 (794,'登 录','login.login','zh_CN'),
 (795,'Login','login.login','en_US'),
 (796,'用户登录','login.title','zh_CN'),
 (797,'User Login','login.title','en_US'),
 (798,'密  码','login.password','zh_CN'),
 (799,'Password','login.password','en_US'),
 (800,'用户编号','login.userName','zh_CN'),
 (801,'User ID','login.userName','en_US'),
 (802,'IP地址','loginlog.label.ip','zh_CN'),
 (803,'IP Address','loginlog.label.ip','en_US'),
 (804,'登录时间','loginlog.label.logindate','zh_CN'),
 (805,'Login Date','loginlog.label.logindate','en_US'),
 (806,'登录主机','loginlog.label.loginhost','zh_CN'),
 (807,'Login Host','loginlog.label.loginhost','en_US'),
 (808,'登录名','loginlog.label.loginname','zh_CN'),
 (809,'User Login Name','loginlog.label.loginname','en_US'),
 (810,'登出时间','loginlog.label.logoutdate','zh_CN'),
 (811,'Logout Date','loginlog.label.logoutdate','en_US'),
 (812,'会话主键','loginlog.label.sessionId','zh_CN'),
 (813,'Session ID','loginlog.label.sessionId','en_US'),
 (840,'操作日志','menu.changelog','zh_CN'),
 (841,'Change Log','menu.changelog','en_US'),
 (871,'模块设置','menu.module','zh_CN'),
 (872,'Module','menu.module','en_US'),
 (874,'在线用户','menu.online','zh_CN'),
 (875,'Online User','menu.online','en_US'),
 (894,'用户信息','menu.user','zh_CN'),
 (895,'User','menu.user','en_US'),
 (898,'复制成功','message.copy.success','zh_CN'),
 (899,'Copy Success','message.copy.success','en_US'),
 (900,'必填信息','Message.required','zh_CN'),
 (901,'必填信息','Message.required','en_US'),
 (902,'消息','Messages','zh_CN'),
 (903,'消息','Messages','en_US'),
 (918,'模块名称','module.column.name','zh_CN'),
 (919,'Module Name','module.column.name','en_US'),
 (944,'父菜单','module.label.parentmenu','zh_CN'),
 (945,'Parent Menu','module.label.parentmenu','en_US'),
 (1182,'是否初始化','operator.column.init','zh_CN'),
 (1183,'Init','operator.column.init','en_US'),
 (1201,'组织级别','org.column.level','zh_CN'),
 (1202,'Org Level','org.column.level','en_US'),
 (1203,'组织名称','org.column.name','zh_CN'),
 (1204,'Org Name','org.column.name','en_US'),
 (1225,'组织信息','org.title','zh_CN'),
 (1226,'Organization','org.title','en_US'),
 (1234,'页面名称','page.column.name','zh_CN'),
 (1235,'Page Name','page.column.name','en_US'),
 (1314,'返回上一页','public.error.goback','zh_CN'),
 (1315,'隐藏详细','public.error.hidedetail','zh_CN'),
 (1316,'错误提示:','public.error.title','zh_CN'),
 (1317,'查看详细','public.error.viewdetail','zh_CN'),
 (1399,'组织','realtrans.column.org','zh_CN'),
 (1400,'Org','realtrans.column.org','en_US'),
 (1497,'数据类型','searchcolumn.column.datatype','zh_CN'),
 (1498,'Data Type','searchcolumn.column.datatype','en_US'),
 (1503,'显示类型','searchcolumn.column.showtype','zh_CN'),
 (1504,'Show Type','searchcolumn.column.showtype','en_US'),
 (1505,'表名','searchcolumn.column.tableName','zh_CN'),
 (1506,'Table','searchcolumn.column.tableName','en_US'),
 (1509,'安全设置','Security','zh_CN'),
 (1510,'Security','Security','en_US'),
 (1524,'保存成功','success.save','zh_CN'),
 (1606,'业务主键','table.column.buskey','zh_CN'),
 (1607,'Business Key','table.column.buskey','en_US'),
 (1620,'列名','table.label.column','zh_CN'),
 (1621,'Column','table.label.column','en_US'),
 (1622,'列注释','table.label.columndesc','zh_CN'),
 (1623,'Column Desc','table.label.columndesc','en_US'),
 (1624,'编号','table.label.no','zh_CN'),
 (1625,'No','table.label.no','en_US'),
 (1626,'类型','table.label.type','zh_CN'),
 (1627,'Type','table.label.type','en_US'),
 (1628,'数字','table.lable.digits','zh_CN'),
 (1629,'digits','table.lable.digits','en_US'),
 (1630,'是否显示','table.lable.isview','zh_CN'),
 (1631,'Is View','table.lable.isview','en_US'),
 (1638,'表信息维护','table.title','zh_CN'),
 (1639,'Table','table.title','en_US'),
 (1642,'实体','user.column.client','zh_CN'),
 (1643,'Client','user.column.client','en_US'),
 (1656,'密码','user.column.password','zh_CN'),
 (1657,'Password','user.column.password','en_US'),
 (1660,'角色','user.column.role','zh_CN'),
 (1661,'Role','user.column.role','en_US'),
 (1662,'电话','user.column.telephone','zh_CN'),
 (1670,'密码确认','user.ConfirmPassword','zh_CN'),
 (1671,'密码确认','user.ConfirmPassword','en_US'),
 (1856,'Role setting','role.setUp','en_US'),
 (1857,'角色设置','role.setUp','zh_CN'),
 (1858,'Role setting','role.setUp','en_US'),
 (1859,'角色设置','role.setUp','zh_CN'),
 (1860,'Newly build','base.newBuild','en_US'),
 (1861,'新建','base.newBuild','zh_CN'),
 (1862,'lookup','base.search','en_US'),
 (1863,'查找','base.search','zh_CN'),
 (1866,'Serial number','base.serialNumber','en_US'),
 (1867,'序号','base.serialNumber','zh_CN'),
 (1868,'Role name','role.roleName','en_US'),
 (1869,'角色名','role.roleName','zh_CN'),
 (1870,'Remarks','base.remarks','en_US'),
 (1871,'备注','base.remarks','zh_CN'),
 (1872,'is active','base.whetherEffective','en_US'),
 (1873,'是否有效','base.whetherEffective','zh_CN'),
 (1876,'Preservation','base.saveall','en_US'),
 (1877,'保存','base.saveall','zh_CN'),
 (1880,'Operating authority','role.operatingAuthority','en_US'),
 (1881,'操作权限','role.operatingAuthority','zh_CN'),
 (1882,'Data access rights','role.data.access.rights','en_US'),
 (1883,'数据访问权限','role.data.access.rights','zh_CN'),
 (1884,'Module name','role.data.modelName','en_US'),
 (1885,'模块名称','role.data.modelName','zh_CN'),
 (1886,'Operation control','role.operationController','en_US'),
 (1887,'操作控制','role.operationController','zh_CN'),
 (1888,'Organization name','role.organizationName','en_US'),
 (1889,'组织名称','role.organizationName','zh_CN'),
 (1890,'host computer','loginlog.label.host','en_US'),
 (1891,'登陆主机','loginlog.label.host','zh_CN'),
 (1892,'Ding business power enterprise platform','index.title','en_US'),
 (1893,'鼎商动力企业平台','index.title','zh_CN'),
 (1894,'log','loginlog.label.log','en_US'),
 (1895,'登录日志','loginlog.label.log','zh_CN'),
 (1896,'Save success','base.save.success','en_US'),
 (1897,'保存成功','base.save.success','zh_CN'),
 (1898,'Enterprise thinking','index.enterprise_thinking','en_US'),
 (1899,'企业思想','index.enterprise_thinking','zh_CN'),
 (1900,'change Password','index.change_password','en_US'),
 (1901,'修改密码','index.change_password','zh_CN'),
 (1902,'profile','index.profile','en_US'),
 (1903,'个人设置','index.profile','zh_CN'),
 (1904,'settings','index.settings','en_US'),
 (1905,'系统设置','index.settings','zh_CN'),
 (1906,'50%','index.proportion','en_US'),
 (1907,'50%','index.proportion','zh_CN'),
 (1908,'Help','index.help','en_US'),
 (1909,'帮助','index.help','zh_CN'),
 (1910,'Log Out','index.log_out','en_US'),
 (1911,'登出','index.log_out','zh_CN'),
 (1914,'Load, please wait for a moment','load.please.wait','en_US'),
 (1915,'加载中,请稍等……','load.please.wait','zh_CN'),
 (1918,'6','index.email_size','en_US'),
 (1919,'6','index.email_size','zh_CN'),
 (1922,'John Smith','index.email_name','en_US'),
 (1923,'John Smith','index.email_name','zh_CN'),
 (1924,'last page','loginlog.label.lastpage','en_US'),
 (1925,'最后页','loginlog.label.lastpage','zh_CN'),
 (1926,'3 mins ago','index.email_time','en_US'),
 (1927,'3 mins ago','index.email_time','zh_CN'),
 (1928,'Film festivals used to be do-or-die moments for movie makers. They were where...','index.email_message','en_US'),
 (1929,'Film festivals used to be do-or-die moments for movie makers. They were where...','index.email_message','zh_CN'),
 (1930,'See All Alerts','index.email_see_all','en_US'),
 (1931,'查看全部邮件','index.email_see_all','zh_CN'),
 (1932,'Query condition maintenance','search.condition','en_US'),
 (1933,'查询条件维护','search.condition','zh_CN'),
 (1934,'dymenuForm.label.menutype','dymenuForm.label.menutype','en_US'),
 (1935,'菜单类型','dymenuForm.label.menutype','zh_CN'),
 (1936,'Query page type','search.page.type','en_US'),
 (1937,'查询页类型','search.page.type','zh_CN'),
 (1940,'identifier','base.identifier','en_US'),
 (1941,'标识符','base.identifier','zh_CN'),
 (1944,'icon','dymenuForm.label.icon','en_US'),
 (1945,'图标','dymenuForm.label.icon','zh_CN'),
 (1946,'chained address','dymenu.label.chainedaddress','en_US'),
 (1947,'链接地址','dymenu.label.chainedaddress','zh_CN'),
 (1948,'Field information','base.Field.information','en_US'),
 (1949,'字段信息','base.Field.information','zh_CN'),
 (1950,'Delete select column','base.delete.select.column','en_US'),
 (1951,'删除选择列','base.delete.select.column','zh_CN'),
 (1952,'Menu folde','dymenu.label.menufolde','en_US'),
 (1953,'菜单文件夹','dymenu.label.menufolde','zh_CN'),
 (1954,'Client','client.title','en_US'),
 (1955,'实体信息','client.title','zh_CN'),
 (1958,'Key','label.keyvalue','en_US'),
 (1959,'键值','label.keyvalue','zh_CN'),
 (1960,'information','label.message','en_US'),
 (1961,'信息','label.message','zh_CN'),
 (1962,'No','client.column.no','en_US'),
 (1963,'No','client.column.no','zh_CN'),
 (1966,'The value of key','label.keyvalueone','en_US'),
 (1967,'key值','label.keyvalueone','zh_CN'),
 (1968,'new menu','dymenu.label.newmenu','en_US'),
 (1969,'新建菜单','dymenu.label.newmenu','zh_CN'),
 (1972,'Message content','label.messagevalue','en_US'),
 (1973,'消息内容','label.messagevalue','zh_CN'),
 (1976,'Label translation','label.translation','en_US'),
 (1977,'标签翻译','label.translation','zh_CN'),
 (1978,'Message setting','message.message.setting','en_US'),
 (1979,'消息设置','message.message.setting','zh_CN'),
 (1980,'Message translation','message.message.translation','en_US'),
 (1981,'消息翻译','message.message.translation','zh_CN'),
 (1982,'No','org.column.no','en_US'),
 (1983,'No','org.column.no','zh_CN'),
 (1986,'User name','user.username','en_US'),
 (1987,'用户姓名','user.username','zh_CN'),
 (1988,'Language information','language.title','en_US'),
 (1989,'语言信息','language.title','zh_CN'),
 (1990,'logon name','login.username.login','en_US'),
 (1991,'登录名称','login.username.login','zh_CN'),
 (1996,'User name','user.user.name','en_US'),
 (1997,'用户名称','user.user.name','zh_CN'),
 (1998,'Language translation','language.translation','en_US'),
 (1999,'语言翻译','language.translation','zh_CN'),
 (2000,'Initialization','user.initialization','en_US'),
 (2001,'初始化','user.initialization','zh_CN'),
 (2002,'language','language.language','en_US'),
 (2003,'语言','language.language','zh_CN'),
 (2004,'Access directory','directory.access.directory','en_US'),
 (2005,'访问目录','directory.access.directory','zh_CN'),
 (2006,'New wizard','common.button.create_wizard','en_US'),
 (2007,'新建向导','common.button.create_wizard','zh_CN'),
 (2012,'Po category','table.column.po','en_US'),
 (2013,'Po类','table.column.po','zh_CN'),
 (2014,'Output log','table.column_output_log','en_US'),
 (2015,'输出日志','table.column_output_log','zh_CN'),
 (2020,'Module set list','module.column.modulesetlist','en_US'),
 (2021,'模块设置列表','module.column.modulesetlist','zh_CN'),
 (2022,'order','table.column.order','en_US'),
 (2023,'顺序','table.column.order','zh_CN'),
 (2024,'The field name','table.label.field_name','en_US'),
 (2025,'字段名','table.label.field_name','zh_CN'),
 (2026,'Access directory settings','module.column.moduleaccessdirectory','en_US'),
 (2027,'访问目录设置','module.column.moduleaccessdirectory','zh_CN'),
 (2028,'Business name','changelog.business.name','en_US'),
 (2029,'业务名','changelog.business.name','zh_CN'),
 (2030,'primary key','changelog.primarykey','en_US'),
 (2031,'主键','changelog.primarykey','zh_CN'),
 (2032,'Field information','table.lable.information','en_US'),
 (2033,'字段信息','table.lable.information','zh_CN'),
 (2034,'Action Types','changelog.actiontypes','en_US'),
 (2035,'动作类型','changelog.actiontypes','zh_CN'),
 (2036,'operate time','changelog.operatetime','en_US'),
 (2037,'操作时间','changelog.operatetime','zh_CN'),
 (2038,'Operation log list','changelog.operation.list','en_US'),
 (2039,'操作日志列表','changelog.operation.list','zh_CN'),
 (2042,'show all','changelog.show.all','en_US'),
 (2043,'显示全部','changelog.show.all','zh_CN'),
 (2044,'Display this article','changelog.show.thisartical','en_US'),
 (2045,'显示本条','changelog.show.thisartical','zh_CN'),
 (2054,'Joiner','search.connection.symbol','en_US'),
 (2055,'连接符','search.connection.symbol','zh_CN'),
 (2056,'true','module.name.checktrue','en_US'),
 (2057,'是','module.name.checktrue','zh_CN'),
 (2058,'false','module.name.checkfalse','en_US'),
 (2059,'否','module.name.checkfalse','zh_CN'),
 (2068,'Inquire','common.button.inquire','en_US'),
 (2069,'查询','common.button.inquire','zh_CN'),
 (2070,'Add to','common.button.add','en_US'),
 (2071,'添加','common.button.add','zh_CN'),
 (2072,'The list of fields','common.search.fields','en_US'),
 (2073,'字段列表','common.search.fields','zh_CN'),
 (2074,'closed','common.button.closed','en_US'),
 (2075,'关闭','common.button.closed','zh_CN'),
 (2076,'value','common.search.value','en_US'),
 (2077,'值','common.search.value','zh_CN'),
 (2078,'Comparison character','common.search.character','en_US'),
 (2079,'比较符','common.search.character','zh_CN'),
 (2080,'fields','common.search.field','en_US'),
 (2081,'字段','common.search.field','zh_CN'),
 (2082,'Login name already exists','login.name.already.exists','en_US'),
 (2083,'登录名称已存在','login.name.already.exists','zh_CN'),
 (2084,'Password and confirm password inconsistent','password.confirm.password.inconsistent','en_US'),
 (2085,'密码和确认密码不一致','password.confirm.password.inconsistent','zh_CN'),
 (2086,'previous page','common.label.previouspage','en_US'),
 (2087,'上一页','common.label.previouspage','zh_CN'),
 (2088,'next page','common.label.nextpage','en_US'),
 (2089,'下一页','common.label.nextpage','zh_CN'),
 (2090,'last page','common.label.lastpage','en_US'),
 (2091,'最后页','common.label.lastpage','zh_CN'),
 (2092,'first page','common.label.firstpage','en_US'),
 (2093,'第一页','common.label.firstpage','zh_CN'),
 (2094,'new built','common.button.create','en_US'),
 (2095,'新建','common.button.create','zh_CN'),
 (2096,'view','common.button.view','en_US'),
 (2097,'查看','common.button.view','zh_CN'),
 (2098,'save','common.button.save','en_US'),
 (2099,'保存','common.button.save','zh_CN'),
 (2100,'delete','common.button.delete','en_US'),
 (2101,'删除','common.button.delete','zh_CN'),
 (2102,'edit','common.button.edit','en_US'),
 (2103,'编辑','common.button.edit','zh_CN'),
 (2104,'search','common.button.search','en_US'),
 (2105,'查找','common.button.search','zh_CN'),
 (2106,'false','common.button.checkfalse','en_US'),
 (2107,'否','common.button.checkfalse','zh_CN'),
 (2108,'true','common.button.checktrue','en_US'),
 (2109,'是','common.button.checktrue','zh_CN'),
 (2110,'back','common.button.back','en_US'),
 (2111,'返回','common.button.back','zh_CN'),
 (2114,'The index name','table.label.index_name','en_US'),
 (2115,'索引名','table.label.index_name','zh_CN'),
 (2116,'index','table.wizard.index','en_US'),
 (2117,'索引','table.wizard.index','zh_CN'),
 (2118,'External association table','table.label.external_association_table','en_US'),
 (2119,'外部关联表','table.label.external_association_table','zh_CN'),
 (2120,'Reference table','table.label.reference_table','en_US'),
 (2121,'引用表','table.label.reference_table','zh_CN'),
 (2122,'Reference field','table.label.reference_field','en_US'),
 (2123,'引用字段','table.label.reference_field','zh_CN'),
 (2124,'Is empty','table.label.is_empty','en_US'),
 (2125,'是否为空','table.label.is_empty','zh_CN'),
 (2126,'The field type','table.label.field_type','en_US'),
 (2127,'字段类型','table.label.field_type','zh_CN'),
 (2128,'size','table.label.size','en_US'),
 (2129,'大小','table.label.size','zh_CN'),
 (2130,'Defaults','table.label.defaults','en_US'),
 (2131,'默认值','table.label.defaults','zh_CN'),
 (2132,'Foreign key','table.label.foreign_key','en_US'),
 (2133,'外键','table.label.foreign_key','zh_CN'),
 (2136,'Query type','officeTestExam.select.selectTypeList','en_US'),
 (2137,'查询类型','officeTestExam.select.selectTypeList','zh_CN'),
 (2138,'Serial number','custom.list.index','en_US'),
 (2139,'序号','custom.list.index','zh_CN'),
 (2140,'See','officeTestExam.button.view','en_US'),
 (2141,'查看','officeTestExam.button.view','zh_CN'),
 (2142,'login time','online.label.logindate','en_US'),
 (2143,'登录时间','online.label.logindate','zh_CN'),
 (2144,'The query condition is empty','common.search.query.condition.empty','en_US'),
 (2145,'查询条件为空','common.search.query.condition.empty','zh_CN'),
 (2146,'Based on table structure generation table and table_column generation','table.wizard.title','en_US'),
 (2147,'根据表结构生成生成table和table_column','table.wizard.title','zh_CN'),
 (2148,'Table Notes','table.wizard.table_notes','en_US'),
 (2149,'表注释','table.wizard.table_notes','zh_CN'),
 (2150,'Logging','table.lable.log','en_US'),
 (2151,'记录日志','table.lable.log','zh_CN'),
 (2152,'only','table.lable.only','en_US'),
 (2153,'唯一','table.lable.only','zh_CN'),
 (2154,'columnSize','table.lable.column_size','en_US'),
 (2155,'列数大小','table.lable.column_size','zh_CN'),
 (2156,'yes','common.button.yes','en_US'),
 (2157,'是','common.button.yes','zh_CN'),
 (2158,'no','common.button.no','en_US'),
 (2159,'否','common.button.no','zh_CN'),
 (2160,'ssss','base.delete.select.column','ja_JP');
/*!40000 ALTER TABLE `core_label_trl` ENABLE KEYS */;


--
-- Definition of table `core_language`
--

DROP TABLE IF EXISTS `core_language`;
CREATE TABLE `core_language` (
  `code` varchar(10) NOT NULL COMMENT '语言编码',
  `name` varchar(200) NOT NULL COMMENT '语言名称',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `isactive` char(1) NOT NULL DEFAULT 'N' COMMENT '是否有效',
  PRIMARY KEY (`code`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='国际化表';

--
-- Dumping data for table `core_language`
--

/*!40000 ALTER TABLE `core_language` DISABLE KEYS */;
INSERT INTO `core_language` (`code`,`name`,`remark`,`isactive`) VALUES 
 ('ar_SA','عربي','阿拉伯语','N'),
 ('DE_DE','Deutsch','德语','N'),
 ('en_GB','English England','英国英语','N'),
 ('en_US','English USA','English USA','Y'),
 ('es_ES','El español','西班牙语','N'),
 ('fr_FR','Français','法语','N'),
 ('ja_JP','日本語','日语','N'),
 ('ko_KR','한국어','韩国语','N'),
 ('ru_RU','русский','俄语','N'),
 ('ta_TA','ภาษาไทย','泰国语','N'),
 ('tr_TR','تركي','土耳其语','N'),
 ('zh_CN','简体中文','简体中文','Y');
/*!40000 ALTER TABLE `core_language` ENABLE KEYS */;


--
-- Definition of table `core_language_trl`
--

DROP TABLE IF EXISTS `core_language_trl`;
CREATE TABLE `core_language_trl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `language_code` varchar(10) NOT NULL COMMENT '语言id',
  `name` varchar(200) NOT NULL COMMENT '语言名称',
  `lang` varchar(10) NOT NULL COMMENT '语言环境',
  PRIMARY KEY (`id`),
  KEY `fk_language_trl_language_code_idx` (`language_code`),
  KEY `fk_language_trl_language_idx` (`lang`),
  CONSTRAINT `fk_language_trl_language` FOREIGN KEY (`lang`) REFERENCES `core_language` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_language_trl_language_code` FOREIGN KEY (`language_code`) REFERENCES `core_language` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='国际化翻译表';

--
-- Dumping data for table `core_language_trl`
--

/*!40000 ALTER TABLE `core_language_trl` DISABLE KEYS */;
INSERT INTO `core_language_trl` (`id`,`language_code`,`name`,`lang`) VALUES 
 (1,'zh_CN','简体中文','zh_CN'),
 (2,'en_US','美国英语','zh_CN'),
 (3,'zh_CN','Chinese','en_US'),
 (4,'en_US','American English','en_US'),
 (7,'ja_JP','Japanese','en_US'),
 (8,'ja_JP','日语','zh_CN'),
 (9,'fr_FR','French','en_US'),
 (10,'fr_FR','法语','zh_CN'),
 (13,'DE_DE','German','en_US'),
 (14,'DE_DE','德语','zh_CN'),
 (15,'en_GB','English England','en_US'),
 (16,'en_GB','英国英语','zh_CN'),
 (17,'es_ES','Spanish','en_US'),
 (18,'es_ES','西班牙语','zh_CN'),
 (19,'tr_TR','Turkish','en_US'),
 (20,'tr_TR','土耳其语','zh_CN'),
 (21,'ko_KR','Korean','en_US'),
 (22,'ko_KR','韩国语','zh_CN'),
 (23,'ru_RU','Russian','en_US'),
 (24,'ru_RU','俄语','zh_CN'),
 (25,'ru_RU','ロシア語','ja_JP'),
 (26,'ja_JP','日本語','ja_JP'),
 (27,'DE_DE','1111','DE_DE'),
 (28,'ar_SA','Arabic','en_US'),
 (29,'ar_SA','阿拉伯语','zh_CN'),
 (30,'ta_TA','taland','en_US'),
 (31,'ta_TA','泰国语','zh_CN');
/*!40000 ALTER TABLE `core_language_trl` ENABLE KEYS */;


--
-- Definition of table `core_message`
--

DROP TABLE IF EXISTS `core_message`;
CREATE TABLE `core_message` (
  `_key` varchar(100) NOT NULL,
  `msgtext` varchar(500) NOT NULL,
  `isactive` char(1) NOT NULL,
  PRIMARY KEY (`_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `core_message`
--

/*!40000 ALTER TABLE `core_message` DISABLE KEYS */;
INSERT INTO `core_message` (`_key`,`msgtext`,`isactive`) VALUES 
 ('222222','333333','Y'),
 ('base.input.remark','请输入remark','Y'),
 ('base.msgtext.length','消息内容长度必须为1到45','N'),
 ('client.error.isactive.length_error','是否有效长度必须为1','Y'),
 ('client.error.isactive.not_null','是否有效不允许为空','Y'),
 ('client.error.name.length_error','名字必须由2~30个字符组成','Y'),
 ('client.error.name.not_null','名称不能为空','Y'),
 ('client.error.no.length_error','编号必须由2~10个字符组成','Y'),
 ('client.error.no.not_null','编号不允许为空','Y'),
 ('client.error.out_log.length_error','输出日志长度必须为1','Y'),
 ('client.error.remark.length_error','备注长度不能大于200','Y'),
 ('client.input.no','请输入no','Y'),
 ('client.msg.client_code_exists','实体code已存在','N'),
 ('client.msg.primary_key_is_empty','主键为空，获取信息失败','N'),
 ('client.msg.select_delete','请选择再删除','Y'),
 ('common.create.success','创建单据成功','Y'),
 ('common.error.delete.failure','删除{0}失败','Y'),
 ('common.errror.get_object','获取{0}失败','Y'),
 ('common.errror.pk.required','主键不能为空','Y'),
 ('common.primary_key.isempty','主键id不能为空','N'),
 ('common.search.error.Integer.column_name','必须为整数','Y'),
 ('common.search.error.number.column_name','必须为数字','Y'),
 ('common.warn.confirm.deletion','确认删除？','Y'),
 ('common.warn.deleted.success','删除成功','Y'),
 ('directory.input.link.address','请输入链接地址','Y'),
 ('directory.input.pagename','请输入页面名称','Y'),
 ('entity.id.length','实体ID长度必须为1到11','Y'),
 ('entity.information.not.empty','实体信息不允许为空','Y'),
 ('identity.not.allowed.empty','唯一标识不允许为空','Y'),
 ('initial.length','初始化用户长度必须为1位','Y'),
 ('initial.user.not.allowed.empty','初始化用户不允许为空','Y'),
 ('isactive.length','是否有效长度必须为1位','Y'),
 ('label.input.keyvalue','请输入key值','Y'),
 ('label.input.message','请输入消息内容','Y'),
 ('language.error.name.length_error','名称必须由1~200个字符组成','Y'),
 ('language.input.code','请输入code','Y'),
 ('language.msg.code_exists','语言code已存在','Y'),
 ('link.address.length','链接地址长度必须为1到50','Y'),
 ('link.address.not.empty','链接地址不允许为空','Y'),
 ('login.input.loginname','请输入登录名称','Y'),
 ('login.input.name','请输入name','Y'),
 ('login.input.order','请输入显示顺序','Y'),
 ('login.input.password','请输入密码','Y'),
 ('login.input.password.enter','请输入密码确认','Y'),
 ('login.input.remark','请输入备注','Y'),
 ('login.input.url','请输入url','Y'),
 ('login.msg.logining','登录中……','Y'),
 ('logon.name.isnot.empty','登录名称不能为空','Y'),
 ('logon.name.not.empty','登录名称不允许为空','Y'),
 ('mailbox.length','邮箱长度必须为1到100','Y'),
 ('modile.deleteselect.check','是否删除选中记录','Y'),
 ('module.column.modulename','请输入模块名称','Y'),
 ('module.deleted.success','删除成功','Y'),
 ('module.name.check','备注不能超过200位','Y'),
 ('module.name.empty.check','模块名称不允许为空','Y'),
 ('module.name.longcheck','模块名称必须为2到45','Y'),
 ('number.length','编号长度必须为1到50','Y'),
 ('org.error.name.length_error','组织名称必须由2~20个字符组成','Y'),
 ('org.error.name.not_null','组织名称不允许为空','Y'),
 ('org.error.no.length_error','编号长度必须为3到10','Y'),
 ('org.error.no.not_null','组织编号不允许为空','Y'),
 ('org.input.name','请输入名称','Y'),
 ('org.input.remark','请输入备注','Y'),
 ('org.msg.no_exists','组织no已存在','Y'),
 ('pagemenu.choose.information','请选择再删除','Y'),
 ('pagemenu.connection.point','该连接已存在','Y'),
 ('pagemenu.icon.check','图标长度不能超过100个字符','Y'),
 ('pagemenu.name.check','name不允许为空','Y'),
 ('pagemenu.name.prompt','name长度必须为1到45个字符','Y'),
 ('pagemenu.primarykey.check.isnull','ID为空，请重新选择编辑','Y'),
 ('pagemenu.primarykey.information','主键为空，信息失败','Y'),
 ('pagemenu.remark.check','remark长度不能超过200个字符','Y'),
 ('pagemenu.seqno.check','显示顺序不允许为空','Y'),
 ('pagemenu.seqno.longcheck','显示顺序长度必须为1到11位','Y'),
 ('pagemenu.url.check','url长度不能超过200个字符','Y'),
 ('password.length','密码长度必须为6到45','Y'),
 ('password.not.allowed.empty','密码不允许为空','Y'),
 ('query.types.empty','查询类型不允许为空','Y'),
 ('search.msg.name_not_null','名称不允许为空','Y'),
 ('search.msg.uniquely_identifies_not_null','唯一标识不允许为空','Y'),
 ('search.table.name','请输入标识符','Y'),
 ('table.error.bu_pk.length_error','业务主键字符最大不超过40','Y'),
 ('table.error.extern_json.length_error','扩展信息字符最大不超过3000','Y'),
 ('table.error.name.length_error','名称必须由2~50个字符组成','Y'),
 ('table.error.out_log.not_null','输出日志不允许为空','Y'),
 ('table.error.po_class.length_error','poClass 必须由2~10个字符组成','Y'),
 ('table.error.po_class.not_null','poClass不允许为空','Y'),
 ('table.error.table_name.length_error','table_name必须由2~45个字符组成','Y'),
 ('table.error.table_name.not_null','tableName不允许为空','Y'),
 ('table.input.po','请输入po 类','Y'),
 ('table.input.table_name','请输入table name','Y'),
 ('table.input.table_notes','请输入表注释','Y'),
 ('telephone.number.length','电话号码长度长度必须为1到45','Y'),
 ('tissue.id.length','组织ID长度必须为1到11','Y'),
 ('user.input.user.email','请输入邮箱','Y'),
 ('user.input.user.name','请输入用户名称','Y'),
 ('user.input.user.phone','请输入电话','Y');
/*!40000 ALTER TABLE `core_message` ENABLE KEYS */;


--
-- Definition of table `core_message_trl`
--

DROP TABLE IF EXISTS `core_message_trl`;
CREATE TABLE `core_message_trl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `_key` varchar(100) NOT NULL,
  `msgtext` varchar(500) NOT NULL,
  `lang` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_core_message_trl_key_idx` (`_key`),
  KEY `fk_core_message_trl_core_language1_idx` (`lang`),
  CONSTRAINT `fk_core_message_trl_core_language1` FOREIGN KEY (`lang`) REFERENCES `core_language` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_core_message_trl_key` FOREIGN KEY (`_key`) REFERENCES `core_message` (`_key`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=241 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `core_message_trl`
--

/*!40000 ALTER TABLE `core_message_trl` DISABLE KEYS */;
INSERT INTO `core_message_trl` (`id`,`_key`,`msgtext`,`lang`) VALUES 
 (1,'common.create.success','create ticket success!','en_US'),
 (2,'common.create.success','创建单据成功','zh_CN'),
 (3,'222222','0101','en_US'),
 (4,'222222','22222','zh_CN'),
 (5,'login.msg.logining','logining now...','en_US'),
 (6,'login.msg.logining','登录中……','zh_CN'),
 (7,'login.input.name','Please input name','en_US'),
 (8,'login.input.name','请输入name','zh_CN'),
 (9,'login.input.url','Please input url','en_US'),
 (10,'login.input.url','请输入url','zh_CN'),
 (11,'login.input.remark','Please input remark','en_US'),
 (12,'login.input.remark','请输入备注','zh_CN'),
 (15,'login.input.order','Please enter the display order','en_US'),
 (16,'login.input.order','请输入显示顺序','zh_CN'),
 (17,'module.column.modulename','Please enter the name of the module','en_US'),
 (18,'module.column.modulename','请输入模块名称','zh_CN'),
 (21,'base.input.remark','Please input remark','en_US'),
 (22,'base.input.remark','请输入remark','zh_CN'),
 (23,'search.table.name','please enter identifier','en_US'),
 (24,'search.table.name','请输入标识符','zh_CN'),
 (25,'client.input.no','Please enter no','en_US'),
 (26,'client.input.no','请输入no','zh_CN'),
 (27,'label.input.keyvalue','Please enter the key value','en_US'),
 (28,'label.input.keyvalue','请输入key值','zh_CN'),
 (29,'label.input.message','Please enter the message content','en_US'),
 (30,'label.input.message','请输入消息内容','zh_CN'),
 (31,'pagemenu.connection.point','The connection already exists','en_US'),
 (32,'pagemenu.connection.point','该连接已存在','zh_CN'),
 (33,'pagemenu.primarykey.information','Primary key is empty, information failed','en_US'),
 (34,'pagemenu.primarykey.information','主键为空，信息失败','zh_CN'),
 (35,'login.input.loginname','Please enter a login name','en_US'),
 (36,'login.input.loginname','请输入登录名称','zh_CN'),
 (37,'pagemenu.choose.information','Please choose to delete','en_US'),
 (38,'pagemenu.choose.information','请选择再删除','zh_CN'),
 (39,'login.input.password','Please input a password','en_US'),
 (40,'login.input.password','请输入密码','zh_CN'),
 (41,'login.input.password.enter','Please enter a password to confirm','en_US'),
 (42,'login.input.password.enter','请输入密码确认','zh_CN'),
 (43,'user.input.user.name','Please enter a user name','en_US'),
 (44,'user.input.user.name','请输入用户名称','zh_CN'),
 (45,'user.input.user.email','Please enter your email address','en_US'),
 (46,'user.input.user.email','请输入邮箱','zh_CN'),
 (47,'user.input.user.phone','Please enter the phone','en_US'),
 (48,'user.input.user.phone','请输入电话','zh_CN'),
 (49,'directory.input.pagename','Please enter the name of the page','en_US'),
 (50,'directory.input.pagename','请输入页面名称','zh_CN'),
 (51,'directory.input.link.address','Please enter a link address','en_US'),
 (52,'directory.input.link.address','请输入链接地址','zh_CN'),
 (53,'pagemenu.name.check','Name is not allowed to empty','en_US'),
 (54,'pagemenu.name.check','name不允许为空','zh_CN'),
 (55,'pagemenu.name.prompt','Name length must be 1 to 45 characters','en_US'),
 (56,'pagemenu.name.prompt','name长度必须为1到45个字符','zh_CN'),
 (57,'pagemenu.url.check','URL length cannot exceed 200 characters','en_US'),
 (58,'pagemenu.url.check','url长度不能超过200个字符','zh_CN'),
 (59,'pagemenu.remark.check','Remark length cannot exceed 200 characters','en_US'),
 (60,'pagemenu.remark.check','remark长度不能超过200个字符','zh_CN'),
 (61,'pagemenu.icon.check','Icon length should not exceed 100 characters','en_US'),
 (62,'pagemenu.icon.check','图标长度不能超过100个字符','zh_CN'),
 (63,'pagemenu.seqno.check','Display order is not allowed to empty','en_US'),
 (64,'pagemenu.seqno.check','显示顺序不允许为空','zh_CN'),
 (65,'pagemenu.seqno.longcheck','Display order length must be 1 to 11 bits','en_US'),
 (66,'pagemenu.seqno.longcheck','显示顺序长度必须为1到11位','zh_CN'),
 (67,'isactive.length','Effective length must be 1','en_US'),
 (68,'isactive.length','是否有效长度必须为1位','zh_CN'),
 (69,'identity.not.allowed.empty','The only identity is not allowed to empty','en_US'),
 (70,'identity.not.allowed.empty','唯一标识不允许为空','zh_CN'),
 (71,'module.name.check','Remark no more than 200','en_US'),
 (72,'module.name.check','备注不能超过200位','zh_CN'),
 (73,'query.types.empty','Query types are not allowed to be empty','en_US'),
 (74,'query.types.empty','查询类型不允许为空','zh_CN'),
 (75,'module.name.empty.check','Module name is not allowed to empty','en_US'),
 (76,'module.name.empty.check','模块名称不允许为空','zh_CN'),
 (77,'module.name.longcheck','Module name must be 2 to 45','en_US'),
 (78,'module.name.longcheck','模块名称必须为2到45','zh_CN'),
 (79,'base.msgtext.length','Message content must be 1 to 45','en_US'),
 (80,'base.msgtext.length','消息内容长度必须为1到45','zh_CN'),
 (81,'logon.name.not.empty','Logon name not allowed','en_US'),
 (82,'logon.name.not.empty','登录名称不允许为空','zh_CN'),
 (83,'number.length','Number length must be 1 to 50','en_US'),
 (84,'number.length','编号长度必须为1到50','zh_CN'),
 (85,'password.not.allowed.empty','Password is not allowed to empty','en_US'),
 (86,'password.not.allowed.empty','密码不允许为空','zh_CN'),
 (87,'password.length','Password length must be between 6 and 45','en_US'),
 (88,'password.length','密码长度必须为6到45','zh_CN'),
 (89,'initial.user.not.allowed.empty','Initial user is not allowed to be empty','en_US'),
 (90,'initial.user.not.allowed.empty','初始化用户不允许为空','zh_CN'),
 (93,'initial.length','Initial user length must be 1 bits','en_US'),
 (94,'initial.length','初始化用户长度必须为1位','zh_CN'),
 (95,'telephone.number.length','The telephone number length must be 1 to 45','en_US'),
 (96,'telephone.number.length','电话号码长度长度必须为1到45','zh_CN'),
 (101,'entity.information.not.empty','Entity information is not allowed to be empty','en_US'),
 (102,'entity.information.not.empty','实体信息不允许为空','zh_CN'),
 (105,'entity.id.length','Entity ID length must be between 1 and 11','en_US'),
 (106,'entity.id.length','实体ID长度必须为1到11','zh_CN'),
 (109,'tissue.id.length','Tissue ID length must be between 1 and 11','en_US'),
 (110,'tissue.id.length','组织ID长度必须为1到11','zh_CN'),
 (113,'link.address.not.empty','Link address is not allowed to empty','en_US'),
 (114,'link.address.not.empty','链接地址不允许为空','zh_CN'),
 (117,'link.address.length','Link address length must be 1 to 50','en_US'),
 (118,'link.address.length','链接地址长度必须为1到50','zh_CN'),
 (125,'mailbox.length','Mailbox length must be between 1 and 100','en_US'),
 (126,'mailbox.length','邮箱长度必须为1到100','zh_CN'),
 (129,'client.msg.select_delete','Please select Delete','en_US'),
 (130,'client.msg.select_delete','请选择再删除','zh_CN'),
 (131,'modile.deleteselect.check','Do you want to delete the selected record','en_US'),
 (132,'modile.deleteselect.check','是否删除选中记录','zh_CN'),
 (145,'module.deleted.success','Delete success','en_US'),
 (146,'module.deleted.success','删除成功','zh_CN'),
 (149,'org.msg.no_exists','The organization no already exists','en_US'),
 (150,'org.msg.no_exists','组织no已存在','zh_CN'),
 (151,'search.msg.name_not_null','Names are not allowed to be empty','en_US'),
 (152,'search.msg.name_not_null','名称不允许为空','zh_CN'),
 (153,'logon.name.isnot.empty','Logon name cannot be empty','en_US'),
 (154,'logon.name.isnot.empty','登录名称不能为空','zh_CN'),
 (155,'search.msg.uniquely_identifies_not_null','Unique identifier is not allowed to be empty','en_US'),
 (156,'search.msg.uniquely_identifies_not_null','唯一标识不允许为空','zh_CN'),
 (157,'org.input.name','Please enter a name','en_US'),
 (158,'org.input.name','请输入名称','zh_CN'),
 (159,'org.input.remark','Please enter a comment','en_US'),
 (160,'org.input.remark','请输入备注','zh_CN'),
 (161,'table.input.table_name','Please enter table name','en_US'),
 (162,'table.input.table_name','请输入table name','zh_CN'),
 (163,'table.input.po','Please enter the po class','en_US'),
 (164,'table.input.po','请输入po 类','zh_CN'),
 (165,'table.input.table_notes','Please enter a table comment','en_US'),
 (166,'table.input.table_notes','请输入表注释','zh_CN'),
 (167,'pagemenu.primarykey.check.isnull','ID is null','en_US'),
 (168,'pagemenu.primarykey.check.isnull','ID为空，请重新选择编辑','zh_CN'),
 (169,'common.primary_key.isempty','Primary key ID cannot be empty','en_US'),
 (170,'common.primary_key.isempty','主键id不能为空','zh_CN'),
 (171,'client.msg.primary_key_is_empty','The primary key is empty and the fetch failed','en_US'),
 (172,'client.msg.primary_key_is_empty','主键为空，获取信息失败','zh_CN'),
 (173,'client.msg.client_code_exists','Entity code already exists','en_US'),
 (174,'client.msg.client_code_exists','实体code已存在','zh_CN'),
 (175,'client.error.no.not_null','Numbers are not allowed to be empty','en_US'),
 (176,'client.error.no.not_null','编号不允许为空','zh_CN'),
 (177,'client.error.no.length_error','The number must consist of 2 to 10 characters','en_US'),
 (178,'client.error.no.length_error','编号必须由2~10个字符组成','zh_CN'),
 (179,'client.error.name.not_null','The name can not be null','en_US'),
 (180,'client.error.name.not_null','名称不能为空','zh_CN'),
 (181,'client.error.name.length_error','The name must consist of 2 to 30 characters','en_US'),
 (182,'client.error.name.length_error','名字必须由2~30个字符组成','zh_CN'),
 (183,'client.error.isactive.not_null','Whether to be valid or not is not allowed','en_US'),
 (184,'client.error.isactive.not_null','是否有效不允许为空','zh_CN'),
 (185,'client.error.isactive.length_error','Whether the effective length must be 1','en_US'),
 (186,'client.error.isactive.length_error','是否有效长度必须为1','zh_CN'),
 (187,'client.error.remark.length_error','Remarks The length can not be greater than 200','en_US'),
 (188,'client.error.remark.length_error','备注长度不能大于200','zh_CN'),
 (189,'org.error.no.not_null','The organization number is not allowed to be empty','en_US'),
 (190,'org.error.no.not_null','组织编号不允许为空','zh_CN'),
 (191,'org.error.no.length_error','The number must be 3 to 10 in length','en_US'),
 (192,'org.error.no.length_error','编号长度必须为3到10','zh_CN'),
 (193,'org.error.name.not_null','Organization name is not allowed to be empty','en_US'),
 (194,'org.error.name.not_null','组织名称不允许为空','zh_CN'),
 (195,'org.error.name.length_error','Organization name must be from 2 to 20 characters','en_US'),
 (196,'org.error.name.length_error','组织名称必须由2~20个字符组成','zh_CN'),
 (197,'table.error.name.length_error','The name must consist of 2 to 50 characters','en_US'),
 (198,'table.error.name.length_error','名称必须由2~50个字符组成','zh_CN'),
 (199,'table.error.table_name.length_error','Table_name must be from 2 to 45 characters','en_US'),
 (200,'table.error.table_name.length_error','table_name必须由2~45个字符组成','zh_CN'),
 (201,'table.error.table_name.not_null','TableName is not allowed to be empty','en_US'),
 (202,'table.error.table_name.not_null','tableName不允许为空','zh_CN'),
 (203,'table.error.po_class.not_null','PoClass is not allowed to be empty','en_US'),
 (204,'table.error.po_class.not_null','poClass不允许为空','zh_CN'),
 (205,'table.error.po_class.length_error','PoClass must consist of 2 to 10 characters','en_US'),
 (206,'table.error.po_class.length_error','poClass 必须由2~10个字符组成','zh_CN'),
 (207,'table.error.out_log.not_null','The output log is not allowed to be empty','en_US'),
 (208,'table.error.out_log.not_null','输出日志不允许为空','zh_CN'),
 (209,'client.error.out_log.length_error','The output log length must be 1','en_US'),
 (210,'client.error.out_log.length_error','输出日志长度必须为1','zh_CN'),
 (211,'table.error.bu_pk.length_error','The maximum number of primary key characters is 40 or less','en_US'),
 (212,'table.error.bu_pk.length_error','业务主键字符最大不超过40','zh_CN'),
 (213,'table.error.extern_json.length_error','The extended information characters are limited to a maximum of 3000 characters','en_US'),
 (214,'table.error.extern_json.length_error','扩展信息字符最大不超过3000','zh_CN'),
 (215,'language.input.code','Please enter code','en_US'),
 (216,'language.input.code','请输入code','zh_CN'),
 (217,'language.msg.code_exists','The language code already exists','en_US'),
 (218,'language.msg.code_exists','语言code已存在','zh_CN'),
 (219,'language.error.name.length_error','The name must be from 1 to 200 characters','en_US'),
 (220,'language.error.name.length_error','名称必须由1~200个字符组成','zh_CN'),
 (223,'common.search.error.Integer.column_name','Must be an integer','en_US'),
 (224,'common.search.error.Integer.column_name','必须为整数','zh_CN'),
 (225,'common.search.error.number.column_name','Must be a number','en_US'),
 (226,'common.search.error.number.column_name','必须为数字','zh_CN'),
 (231,'common.errror.get_object','get{}failure','en_US'),
 (232,'common.errror.get_object','获取{0}失败','zh_CN'),
 (233,'common.error.delete.failure','remove{0}failure','en_US'),
 (234,'common.error.delete.failure','删除{0}失败','zh_CN'),
 (235,'common.errror.pk.required','主键不能为空','en_US'),
 (236,'common.errror.pk.required','primary key is required','zh_CN'),
 (237,'common.warn.deleted.success','deleted success','en_US'),
 (238,'common.warn.deleted.success','删除成功','zh_CN'),
 (239,'common.warn.confirm.deletion','confirm deletion?','en_US'),
 (240,'common.warn.confirm.deletion','确认删除？','zh_CN');
/*!40000 ALTER TABLE `core_message_trl` ENABLE KEYS */;


--
-- Definition of table `core_search`
--

DROP TABLE IF EXISTS `core_search`;
CREATE TABLE `core_search` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `uri` varchar(45) NOT NULL,
  `show_type` int(11) DEFAULT NULL,
  `isactive` char(1) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `core_search`
--

/*!40000 ALTER TABLE `core_search` DISABLE KEYS */;
INSERT INTO `core_search` (`id`,`name`,`uri`,`show_type`,`isactive`,`remark`) VALUES 
 (3,'语言查询','core_language',0,'Y',NULL),
 (4,'标签查询','core_label',0,'Y',NULL),
 (5,'实体查询','sys_client',1,'Y','实体查询'),
 (6,'菜单设置','sys_pagemenu',0,'Y',NULL),
 (7,'消息设置','core_message',0,'Y',NULL);
/*!40000 ALTER TABLE `core_search` ENABLE KEYS */;


--
-- Definition of table `core_search_column`
--

DROP TABLE IF EXISTS `core_search_column`;
CREATE TABLE `core_search_column` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seq_no` int(11) NOT NULL,
  `core_search_id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `property_name` varchar(500) DEFAULT NULL,
  `data_type` int(11) DEFAULT NULL,
  `show_type` int(11) DEFAULT NULL,
  `rel_oper` varchar(200) DEFAULT NULL,
  `fk_table` varchar(50) DEFAULT NULL,
  `fk_key_column` varchar(45) DEFAULT NULL,
  `fk_label_column` varchar(45) DEFAULT NULL,
  `coditions` varchar(200) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `newline` char(1) DEFAULT NULL,
  `core_label_key` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_core_search_column_core_search1_idx` (`core_search_id`),
  KEY `fk_core_search_column_core_label1_idx` (`core_label_key`),
  CONSTRAINT `core_search_column_ibfk_1` FOREIGN KEY (`core_search_id`) REFERENCES `core_search` (`id`),
  CONSTRAINT `fk_core_search_column_core_label1` FOREIGN KEY (`core_label_key`) REFERENCES `core_label` (`_key`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `core_search_column`
--

/*!40000 ALTER TABLE `core_search_column` DISABLE KEYS */;
INSERT INTO `core_search_column` (`id`,`seq_no`,`core_search_id`,`name`,`property_name`,`data_type`,`show_type`,`rel_oper`,`fk_table`,`fk_key_column`,`fk_label_column`,`coditions`,`remark`,`newline`,`core_label_key`) VALUES 
 (31,1,4,'key','key',1,1,'1,,,,,',NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (32,2,4,'msgtext','msgtext',1,1,'1,,,,,',NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (33,1,7,'key','key',1,1,'1,,,,,',NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (34,2,7,'msgtext','msgtext',1,1,'1,,,,,',NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `core_search_column` ENABLE KEYS */;


--
-- Definition of table `core_table`
--

DROP TABLE IF EXISTS `core_table`;
CREATE TABLE `core_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '名称',
  `table_name` varchar(45) NOT NULL COMMENT '表名',
  `po_class` varchar(200) NOT NULL COMMENT '对应po类',
  `out_log` char(1) NOT NULL DEFAULT 'N' COMMENT '是否输出日志',
  `bu_pk` varchar(40) DEFAULT NULL COMMENT '业务主键',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `extern_json` varchar(3000) DEFAULT NULL COMMENT '扩展信息',
  `isactive` char(1) NOT NULL DEFAULT 'N' COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='系统表对应关系';

--
-- Dumping data for table `core_table`
--

/*!40000 ALTER TABLE `core_table` DISABLE KEYS */;
INSERT INTO `core_table` (`id`,`name`,`table_name`,`po_class`,`out_log`,`bu_pk`,`remark`,`extern_json`,`isactive`) VALUES 
 (3,'语言信息表','core_language','com.dsdl.eidea.core.entity.po.LanguagePo','Y','code',NULL,'{\"outLog\":false,\"exportedFK\":[{\"pkTableName\":\"core_language\",\"pkColumnName\":\"code\",\"fkTableName\":\"core_label_trl\",\"fkColumnName\":\"lang\",\"fkName\":\"fk_core_label_trl_core_language1\"},{\"pkTableName\":\"core_language\",\"pkColumnName\":\"code\",\"fkTableName\":\"core_language_trl\",\"fkColumnName\":\"lang\",\"fkName\":\"fk_language_trl_language\"},{\"pkTableName\":\"core_language\",\"pkColumnName\":\"code\",\"fkTableName\":\"core_language_trl\",\"fkColumnName\":\"language_code\",\"fkName\":\"fk_language_trl_language_code\"},{\"pkTableName\":\"core_language\",\"pkColumnName\":\"code\",\"fkTableName\":\"core_message_trl\",\"fkColumnName\":\"lang\",\"fkName\":\"fk_core_message_trl_core_language1\"}],\"importedFK\":[],\"uniqueKeyList\":[{\"indexColumnName\":\"PRIMARY\",\"indexName\":\"code\"},{\"indexColumnName\":\"name_UNIQUE\",\"indexName\":\"name\"}]}','Y'),
 (4,'标签信息','core_label','com.dsdl.eidea.core.entity.po.LabelPo','N','key',NULL,'{\"outLog\":false,\"exportedFK\":[{\"pkTableName\":\"core_label\",\"pkColumnName\":\"key\",\"fkTableName\":\"core_label_trl\",\"fkColumnName\":\"key\",\"fkName\":\"fk_core_label_trl_core_label1\"},{\"pkTableName\":\"core_label\",\"pkColumnName\":\"key\",\"fkTableName\":\"core_search_column\",\"fkColumnName\":\"core_label_key\",\"fkName\":\"fk_core_search_column_core_label1\"}],\"importedFK\":[],\"uniqueKeyList\":[{\"indexColumnName\":\"PRIMARY\",\"indexName\":\"key\"}]}','N');
/*!40000 ALTER TABLE `core_table` ENABLE KEYS */;


--
-- Definition of table `core_table_column`
--

DROP TABLE IF EXISTS `core_table_column`;
CREATE TABLE `core_table_column` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `column_name` varchar(45) DEFAULT NULL,
  `data_type` int(11) DEFAULT NULL,
  `can_show` char(1) DEFAULT NULL,
  `property_name` varchar(45) DEFAULT NULL,
  `out_log` char(1) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `fk_table_id` int(11) DEFAULT NULL,
  `fk_column_id` int(11) DEFAULT NULL,
  `is_unique` char(1) DEFAULT NULL,
  `column_size` int(11) DEFAULT NULL,
  `digits` int(11) DEFAULT NULL,
  `nullable` char(1) DEFAULT NULL,
  `core_table_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_core_table_column_core_table1_idx` (`core_table_id`),
  CONSTRAINT `fk_core_table_column_table_id` FOREIGN KEY (`core_table_id`) REFERENCES `core_table` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `core_table_column`
--

/*!40000 ALTER TABLE `core_table_column` DISABLE KEYS */;
INSERT INTO `core_table_column` (`id`,`name`,`column_name`,`data_type`,`can_show`,`property_name`,`out_log`,`remark`,`fk_table_id`,`fk_column_id`,`is_unique`,`column_size`,`digits`,`nullable`,`core_table_id`) VALUES 
 (14,'语言编码','code',2,'Y','code','Y',NULL,NULL,NULL,'Y',10,0,'N',3),
 (15,'语言名称','name',2,'Y','name','Y',NULL,NULL,NULL,'N',200,0,'N',3),
 (16,'备注','remark',2,'Y','remark','Y',NULL,NULL,NULL,'N',500,0,'Y',3),
 (17,'是否有效','isactive',2,'Y','isactive','Y',NULL,NULL,NULL,'N',1,0,'N',3),
 (18,'键值','key',2,'Y','key','Y',NULL,NULL,NULL,'N',100,0,'N',4),
 (19,'消息','msgtext',2,'Y','msgtext','Y',NULL,NULL,NULL,'N',500,0,'N',4),
 (20,'是否有效','isactive',2,'Y','isactive','Y',NULL,NULL,NULL,'N',1,0,'Y',4);
/*!40000 ALTER TABLE `core_table_column` ENABLE KEYS */;


--
-- Definition of table `core_window`
--

DROP TABLE IF EXISTS `core_window`;
CREATE TABLE `core_window` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `isactive` char(1) NOT NULL COMMENT '是否有效',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `user_id` int(11) NOT NULL COMMENT '用户',
  `org_id` int(11) NOT NULL COMMENT '组织',
  `in_date` datetime NOT NULL COMMENT '输入时间',
  `mt_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='窗体信息';

--
-- Dumping data for table `core_window`
--

/*!40000 ALTER TABLE `core_window` DISABLE KEYS */;
/*!40000 ALTER TABLE `core_window` ENABLE KEYS */;


--
-- Definition of table `core_window_trl`
--

DROP TABLE IF EXISTS `core_window_trl`;
CREATE TABLE `core_window_trl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `window_id` int(11) NOT NULL,
  `lang` varchar(10) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='窗体信息';

--
-- Dumping data for table `core_window_trl`
--

/*!40000 ALTER TABLE `core_window_trl` DISABLE KEYS */;
/*!40000 ALTER TABLE `core_window_trl` ENABLE KEYS */;


--
-- Definition of table `sys_changelog`
--

DROP TABLE IF EXISTS `sys_changelog`;
CREATE TABLE `sys_changelog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operate_type` char(1) NOT NULL,
  `context` text NOT NULL,
  `in_date` datetime NOT NULL,
  `pk` varchar(45) DEFAULT NULL,
  `bu_pk` varchar(45) DEFAULT NULL,
  `core_table_id` int(11) NOT NULL,
  `sys_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_core_changelog_core_table1_idx` (`core_table_id`),
  KEY `fk_core_changelog_sys_user1_idx` (`sys_user_id`),
  CONSTRAINT `fk_sys_changelog_table_id` FOREIGN KEY (`core_table_id`) REFERENCES `core_table` (`id`),
  CONSTRAINT `sys_changelog_ibfk_1` FOREIGN KEY (`sys_user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='操作日志表';

--
-- Dumping data for table `sys_changelog`
--

/*!40000 ALTER TABLE `sys_changelog` DISABLE KEYS */;
INSERT INTO `sys_changelog` (`id`,`operate_type`,`context`,`in_date`,`pk`,`bu_pk`,`core_table_id`,`sys_user_id`) VALUES 
 (2,'U','{\"code\":\"DE_DE\",\"isactive\":\"N\",\"name\":\"Deutsch\",\"remark\":\"德语\"}','2016-12-23 09:43:54','DE_DE','DE_DE',3,1),
 (3,'U','{\"code\":\"DE_DE\",\"isactive\":\"Y\",\"name\":\"Deutsch\",\"remark\":\"德语\"}','2016-12-23 14:30:07','DE_DE','DE_DE',3,1),
 (4,'U','{\"code\":\"DE_DE\",\"isactive\":\"N\",\"name\":\"Deutsch\",\"remark\":\"德语\"}','2016-12-23 14:30:18','DE_DE','DE_DE',3,1),
 (5,'I','{\"code\":\"ar_SA\",\"isactive\":\"N\",\"name\":\"عربي\",\"remark\":\"阿拉伯语\"}','2016-12-27 15:06:06','ar_SA','ar_SA',3,1),
 (6,'I','{\"code\":\"ta_TA\",\"isactive\":\"N\",\"name\":\"ภาษาไทย\",\"remark\":\"泰国语\"}','2016-12-27 15:07:56','ta_TA','ta_TA',3,1),
 (7,'U','{\"code\":\"DE_DE\",\"isactive\":\"N\",\"name\":\"Deutsch\",\"remark\":\"德语\"}','2016-12-28 14:00:55','DE_DE','DE_DE',3,1),
 (8,'U','{\"code\":\"ar_SA\",\"isactive\":\"N\",\"name\":\"عربي\",\"remark\":\"阿拉伯语\"}','2016-12-28 14:00:59','ar_SA','ar_SA',3,1),
 (9,'I','{\"code\":\"wad\",\"isactive\":\"Y\",\"name\":\"wd\",\"remark\":\"wd\"}','2017-01-11 10:44:19','wad','wad',3,1),
 (10,'U','{\"code\":\"wad\",\"isactive\":\"Y\",\"name\":\"wd\",\"remark\":\"wd\"}','2017-01-11 10:45:26','wad','wad',3,1),
 (11,'D','{\"code\":\"wad\",\"isactive\":\"Y\",\"name\":\"wd\",\"remark\":\"wd\"}','2017-01-11 10:45:39','wad','wad',3,1),
 (12,'U','{\"code\":\"ar_SA\",\"isactive\":\"N\",\"name\":\"عربي\",\"remark\":\"阿拉伯语\"}','2017-01-11 10:47:15','ar_SA','ar_SA',3,1),
 (13,'U','{\"code\":\"en_GB\",\"isactive\":\"N\",\"name\":\"English England\",\"remark\":\"英国英语\"}','2017-01-11 10:48:33','en_GB','en_GB',3,1),
 (14,'U','{\"code\":\"ar_SA\",\"isactive\":\"N\",\"name\":\"عربي\",\"remark\":\"阿拉伯语\"}','2017-01-12 16:26:53','ar_SA','ar_SA',3,1),
 (15,'U','{\"code\":\"ar_SA\",\"isactive\":\"N\",\"name\":\"عربي\",\"remark\":\"阿拉伯语\"}','2017-01-12 16:57:01','ar_SA','ar_SA',3,1),
 (16,'U','{\"code\":\"ar_SA\",\"isactive\":\"N\",\"name\":\"عربي\",\"remark\":\"阿拉伯语\"}','2017-01-13 09:16:44','ar_SA','ar_SA',3,1),
 (17,'U','{\"code\":\"ar_SA\",\"isactive\":\"N\",\"name\":\"عربي\",\"remark\":\"阿拉伯语\"}','2017-01-13 09:18:45','ar_SA','ar_SA',3,1),
 (18,'U','{\"code\":\"ja_JP\",\"isactive\":\"Y\",\"name\":\"日本語\",\"remark\":\"日语\"}','2017-01-16 10:52:08','ja_JP','ja_JP',3,1),
 (19,'U','{\"code\":\"ja_JP\",\"isactive\":\"N\",\"name\":\"日本語\",\"remark\":\"日语\"}','2017-01-16 10:52:36','ja_JP','ja_JP',3,1);
/*!40000 ALTER TABLE `sys_changelog` ENABLE KEYS */;


--
-- Definition of table `sys_client`
--

DROP TABLE IF EXISTS `sys_client`;
CREATE TABLE `sys_client` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `isactive` char(1) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `no_UNIQUE` (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sys_client`
--

/*!40000 ALTER TABLE `sys_client` DISABLE KEYS */;
INSERT INTO `sys_client` (`id`,`no`,`name`,`isactive`,`remark`) VALUES 
 (1,'1000001','青岛鼎商动力软件股份有限公司','Y','初始化数据'),
 (6,'0003','日本小田','Y','小田集团'),
 (8,'aeno001','aeno','N','aeno'),
 (9,'delmar','德玛国际物流','N',NULL),
 (11,'1111','11','Y','11');
/*!40000 ALTER TABLE `sys_client` ENABLE KEYS */;


--
-- Definition of table `sys_directory`
--

DROP TABLE IF EXISTS `sys_directory`;
CREATE TABLE `sys_directory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '目录名称',
  `directory` varchar(500) NOT NULL COMMENT '目录',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `isactive` char(1) NOT NULL DEFAULT 'N' COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sys_directory`
--

/*!40000 ALTER TABLE `sys_directory` DISABLE KEYS */;
INSERT INTO `sys_directory` (`id`,`name`,`directory`,`remark`,`isactive`) VALUES 
 (2,'系统设置—核心','/core','系统设置核心模块设置','Y'),
 (3,'系统设置-基础信息','/base',NULL,'Y'),
 (4,'公共模块','/common',NULL,'Y');
/*!40000 ALTER TABLE `sys_directory` ENABLE KEYS */;


--
-- Definition of table `sys_module`
--

DROP TABLE IF EXISTS `sys_module`;
CREATE TABLE `sys_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '模块名',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `isactive` char(1) NOT NULL DEFAULT 'N' COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='模块表';

--
-- Dumping data for table `sys_module`
--

/*!40000 ALTER TABLE `sys_module` DISABLE KEYS */;
INSERT INTO `sys_module` (`id`,`name`,`remark`,`isactive`) VALUES 
 (1,'系统设置','系统设置信息','Y'),
 (2,'基础数据','基础数据设置','Y'),
 (3,'公共模块','公共模块','Y'),
 (4,'测试模块',NULL,'Y'),
 (5,'特然','Terry','Y');
/*!40000 ALTER TABLE `sys_module` ENABLE KEYS */;


--
-- Definition of table `sys_module_directory`
--

DROP TABLE IF EXISTS `sys_module_directory`;
CREATE TABLE `sys_module_directory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_module_id` int(11) NOT NULL,
  `sys_directory_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_my_module_directory_sys_module1_idx` (`sys_module_id`),
  KEY `fk_my_module_directory_sys_directory1_idx` (`sys_directory_id`),
  CONSTRAINT `fk_sys_module_directory_directory_id` FOREIGN KEY (`sys_directory_id`) REFERENCES `sys_directory` (`id`),
  CONSTRAINT `fk_sys_module_directory_sys_module_id` FOREIGN KEY (`sys_module_id`) REFERENCES `sys_module` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='模块目录表';

--
-- Dumping data for table `sys_module_directory`
--

/*!40000 ALTER TABLE `sys_module_directory` DISABLE KEYS */;
INSERT INTO `sys_module_directory` (`id`,`sys_module_id`,`sys_directory_id`) VALUES 
 (2,1,2),
 (3,1,3),
 (4,3,4),
 (5,5,3);
/*!40000 ALTER TABLE `sys_module_directory` ENABLE KEYS */;


--
-- Definition of table `sys_module_menu`
--

DROP TABLE IF EXISTS `sys_module_menu`;
CREATE TABLE `sys_module_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_page_menu_id` int(11) NOT NULL,
  `sys_module_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_sys_module_menu_sys_page_menu1_idx` (`sys_page_menu_id`),
  KEY `fk_sys_module_menu_sys_module1_idx` (`sys_module_id`),
  CONSTRAINT `fk_sys_module_menu_sys_module_id` FOREIGN KEY (`sys_module_id`) REFERENCES `sys_module` (`id`),
  CONSTRAINT `fk_sys_module_menu_sys_page_menu_id` FOREIGN KEY (`sys_page_menu_id`) REFERENCES `sys_page_menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sys_module_menu`
--

/*!40000 ALTER TABLE `sys_module_menu` DISABLE KEYS */;
INSERT INTO `sys_module_menu` (`id`,`sys_page_menu_id`,`sys_module_id`) VALUES 
 (20,2,3),
 (46,4,1),
 (47,5,1),
 (48,6,1),
 (49,7,1),
 (51,9,1),
 (52,10,1),
 (53,11,1),
 (54,12,1),
 (55,13,1),
 (56,14,1),
 (57,15,1),
 (58,16,1),
 (59,32,1),
 (60,34,1),
 (61,35,1),
 (62,4,5);
/*!40000 ALTER TABLE `sys_module_menu` ENABLE KEYS */;


--
-- Definition of table `sys_module_role`
--

DROP TABLE IF EXISTS `sys_module_role`;
CREATE TABLE `sys_module_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_role_id` int(11) NOT NULL,
  `sys_module_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_sys_module_role_sys_role1_idx` (`sys_role_id`),
  KEY `fk_sys_module_role_sys_module1_idx` (`sys_module_id`),
  CONSTRAINT `fk_sys_module_role_ibfk_1` FOREIGN KEY (`sys_role_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `sys_module_role_ibfk_1` FOREIGN KEY (`sys_module_id`) REFERENCES `sys_module` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sys_module_role`
--

/*!40000 ALTER TABLE `sys_module_role` DISABLE KEYS */;
INSERT INTO `sys_module_role` (`id`,`sys_role_id`,`sys_module_id`) VALUES 
 (1,2,1),
 (2,2,2),
 (3,2,3),
 (4,1,1),
 (5,1,2),
 (6,1,3),
 (7,2,4),
 (8,3,1),
 (9,3,2),
 (10,3,3),
 (11,3,4),
 (32,1,4),
 (33,1,5);
/*!40000 ALTER TABLE `sys_module_role` ENABLE KEYS */;


--
-- Definition of table `sys_operator`
--

DROP TABLE IF EXISTS `sys_operator`;
CREATE TABLE `sys_operator` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no` varchar(45) NOT NULL COMMENT '编号',
  `name` varchar(45) NOT NULL,
  `isactive` char(1) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `no_UNIQUE` (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='操作表';

--
-- Dumping data for table `sys_operator`
--

/*!40000 ALTER TABLE `sys_operator` DISABLE KEYS */;
INSERT INTO `sys_operator` (`id`,`no`,`name`,`isactive`,`remark`) VALUES 
 (1,'add','添加','Y','222'),
 (2,'update','修改','Y',NULL),
 (3,'delete','删除','Y',NULL),
 (4,'view','查看','Y',NULL);
/*!40000 ALTER TABLE `sys_operator` ENABLE KEYS */;


--
-- Definition of table `sys_org`
--

DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no` varchar(45) NOT NULL,
  `name` varchar(200) NOT NULL,
  `isactive` char(1) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `sys_client_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `no_UNIQUE` (`no`),
  KEY `fk_sys_org_sys_client1_idx` (`sys_client_id`),
  CONSTRAINT `fk_sys_org_client_id` FOREIGN KEY (`sys_client_id`) REFERENCES `sys_client` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sys_org`
--

/*!40000 ALTER TABLE `sys_org` DISABLE KEYS */;
INSERT INTO `sys_org` (`id`,`no`,`name`,`isactive`,`remark`,`sys_client_id`) VALUES 
 (1,'103','青岛鼎商动力','Y','qingdao dingshangdongli',1),
 (2,'10002','上海鼎商动力','Y','上海鼎商动力',1),
 (11,'10003','上海小田','Y',NULL,6),
 (12,'10004','日本小田','Y','test',6);
/*!40000 ALTER TABLE `sys_org` ENABLE KEYS */;


--
-- Definition of table `sys_page_menu`
--

DROP TABLE IF EXISTS `sys_page_menu`;
CREATE TABLE `sys_page_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seq_no` int(11) DEFAULT NULL,
  `icon` varchar(100) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL COMMENT '菜单名',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单url',
  `isactive` char(1) DEFAULT NULL COMMENT '是否有效',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `parent_menu_id` int(11) DEFAULT NULL COMMENT '父菜单id',
  `menu_type` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `parent_menu_id` (`parent_menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='菜单维护';

--
-- Dumping data for table `sys_page_menu`
--

/*!40000 ALTER TABLE `sys_page_menu` DISABLE KEYS */;
INSERT INTO `sys_page_menu` (`id`,`seq_no`,`icon`,`name`,`url`,`isactive`,`remark`,`parent_menu_id`,`menu_type`) VALUES 
 (1,1,'fa fa-home','首页',NULL,'Y','',NULL,1),
 (2,10001,NULL,'首页','/default.jsp','Y','Dashboard',1,2),
 (3,2,'fa fa-cog','系统设置',NULL,'Y','',NULL,1),
 (4,20001,NULL,'实体信息','/base/client/showList','Y','实体信息',37,2),
 (5,20002,NULL,'组织信息','/base/org/showList','Y','组织信息',37,2),
 (6,20003,NULL,'语言设置','/core/language/showList','Y','语言设置',36,2),
 (7,20004,NULL,'表信息','/core/table/showList','Y','表信息',36,2),
 (9,20006,NULL,'角色信息','/base/role/showList','Y','角色信息',3,2),
 (10,20007,NULL,'查询条件','/core/search/showList','Y','查询条件',3,2),
 (11,20008,NULL,'标签设置','/core/label/showList','Y','标签设置',3,2),
 (12,20009,NULL,'消息设置','/core/message/showList','Y','消息设置',3,2),
 (13,20010,NULL,'用户信息','/base/user/getUserToJsp','Y','用户信息',3,2),
 (14,20011,NULL,'访问目录','/base/directory/showList','Y','访问目录',3,2),
 (15,20012,NULL,'登录日志','/base/userSession/showList','Y','登录日志',3,2),
 (16,20013,NULL,'菜单设置','/base/pagemenu/showList','Y','菜单设置',3,2),
 (28,10002,NULL,'首页2',NULL,'Y','首页2',1,2),
 (32,20014,NULL,'模块设置','/base/module/getModuleToJsp','Y',NULL,36,2),
 (34,20015,NULL,'操作日志','/base/changelog/showList','Y',NULL,3,2),
 (35,20016,NULL,'在线用户','/base/userOnline/showList','Y',NULL,3,2),
 (36,2001,NULL,'系统管理',NULL,'Y',NULL,3,1),
 (37,2002,NULL,'组织管理',NULL,'Y','组织管理',3,1);
/*!40000 ALTER TABLE `sys_page_menu` ENABLE KEYS */;


--
-- Definition of table `sys_page_menu_trl`
--

DROP TABLE IF EXISTS `sys_page_menu_trl`;
CREATE TABLE `sys_page_menu_trl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `language_code` varchar(10) DEFAULT NULL,
  `page_menu_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `page_menu_id` (`page_menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sys_page_menu_trl`
--

/*!40000 ALTER TABLE `sys_page_menu_trl` DISABLE KEYS */;
INSERT INTO `sys_page_menu_trl` (`id`,`language_code`,`page_menu_id`,`name`,`remark`) VALUES 
 (1,'zh_CN',3,'测试','测试'),
 (2,'en_US',3,'test','test'),
 (3,'en_US',36,'System Admin','System Admin'),
 (4,'zh_CN',36,'系统管理','系统管理'),
 (5,'en_US',32,'Module Setting',NULL),
 (6,'zh_CN',32,'模块设置',NULL),
 (7,'en_US',7,'Table Setting',NULL),
 (8,'zh_CN',7,'表信息',NULL),
 (9,'en_US',37,'Organization',''),
 (10,'zh_CN',37,'组织管理',NULL),
 (11,'en_US',4,'Client',NULL),
 (12,'zh_CN',4,'实体信息',NULL),
 (13,'en_US',5,'Org Setting',NULL),
 (14,'zh_CN',5,'组织信息',NULL),
 (15,'en_US',37,'Organization',''),
 (16,'zh_CN',37,'组织管理',NULL),
 (17,'en_US',6,'Language Setting',NULL),
 (18,'zh_CN',6,'语言设置',NULL);
/*!40000 ALTER TABLE `sys_page_menu_trl` ENABLE KEYS */;


--
-- Definition of table `sys_privileges`
--

DROP TABLE IF EXISTS `sys_privileges`;
CREATE TABLE `sys_privileges` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_module_role_id` int(11) NOT NULL,
  `sys_operator_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_sys_opera_privileges_sys_module_role1_idx` (`sys_module_role_id`),
  KEY `fk_sys_opera_privileges_sys_operator1_idx` (`sys_operator_id`),
  CONSTRAINT `fk_sys_privileges_sys_module_role` FOREIGN KEY (`sys_module_role_id`) REFERENCES `sys_module_role` (`id`),
  CONSTRAINT `fk_sys_privileges_sys_operator_id` FOREIGN KEY (`sys_operator_id`) REFERENCES `sys_operator` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sys_privileges`
--

/*!40000 ALTER TABLE `sys_privileges` DISABLE KEYS */;
INSERT INTO `sys_privileges` (`id`,`sys_module_role_id`,`sys_operator_id`) VALUES 
 (1,1,1),
 (3,1,3),
 (4,1,4),
 (5,2,1),
 (6,2,2),
 (7,2,3),
 (8,2,4),
 (9,3,1),
 (10,3,2),
 (11,3,3),
 (12,3,4),
 (14,7,1),
 (15,7,2),
 (16,7,3),
 (17,7,4),
 (18,1,2),
 (19,8,2),
 (20,9,2),
 (21,10,2),
 (22,10,4),
 (23,11,2),
 (24,11,4);
/*!40000 ALTER TABLE `sys_privileges` ENABLE KEYS */;


--
-- Definition of table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '角色名',
  `remark` varchar(200) NOT NULL COMMENT '备注',
  `isactive` char(1) DEFAULT 'N' COMMENT '是否有效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色信息表';

--
-- Dumping data for table `sys_role`
--

/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` (`id`,`name`,`remark`,`isactive`) VALUES 
 (1,'普通用户','普通用户','Y'),
 (2,'超级管理员','超级管理员','Y'),
 (3,'普通管理员','普通管理员','Y');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;


--
-- Definition of table `sys_role_orgaccess`
--

DROP TABLE IF EXISTS `sys_role_orgaccess`;
CREATE TABLE `sys_role_orgaccess` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_role_id` int(11) NOT NULL,
  `sys_org_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_sys_role_orgaccess_sys_role1_idx` (`sys_role_id`),
  KEY `fk_sys_role_orgaccess_sys_org1_idx` (`sys_org_id`),
  CONSTRAINT `fk_sys_role_orgaccess_org_id` FOREIGN KEY (`sys_org_id`) REFERENCES `sys_org` (`id`),
  CONSTRAINT `sys_role_orgaccess_ibfk_1` FOREIGN KEY (`sys_role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sys_role_orgaccess`
--

/*!40000 ALTER TABLE `sys_role_orgaccess` DISABLE KEYS */;
INSERT INTO `sys_role_orgaccess` (`id`,`sys_role_id`,`sys_org_id`) VALUES 
 (1,2,1),
 (2,2,2),
 (3,2,11),
 (4,2,12),
 (5,3,1),
 (6,3,2),
 (7,3,11),
 (8,3,12);
/*!40000 ALTER TABLE `sys_role_orgaccess` ENABLE KEYS */;


--
-- Definition of table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键pk，自增',
  `username` varchar(50) NOT NULL COMMENT '登陆名',
  `password` varchar(45) NOT NULL COMMENT '密码',
  `name` varchar(45) NOT NULL COMMENT '用户姓名',
  `init` char(1) NOT NULL DEFAULT 'N' COMMENT '是否初始化用户 Y是，N不是',
  `email` varchar(100) DEFAULT NULL COMMENT '邮件',
  `telephone` varchar(45) DEFAULT NULL COMMENT '电话',
  `isactive` char(1) DEFAULT 'N' COMMENT '是否有效',
  `user_pic` blob,
  `client_id` int(11) NOT NULL,
  `org_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

--
-- Dumping data for table `sys_user`
--

/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`id`,`username`,`password`,`name`,`init`,`email`,`telephone`,`isactive`,`user_pic`,`client_id`,`org_id`) VALUES 
 (1,'administrator','8ddcff3a80f4189ca1c9d4d902c3c909','管理员','Y','ldlqdsd@126.com','13336390671','Y',NULL,1,1);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;


--
-- Definition of table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_user_id` int(11) NOT NULL,
  `sys_role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_role_user_id_idx` (`sys_user_id`),
  KEY `fk_user_role_role_id_idx` (`sys_role_id`),
  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`sys_user_id`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`sys_role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sys_user_role`
--

/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` (`id`,`sys_user_id`,`sys_role_id`) VALUES 
 (3,1,1),
 (4,1,2);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;


--
-- Definition of table `sys_user_session`
--

DROP TABLE IF EXISTS `sys_user_session`;
CREATE TABLE `sys_user_session` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `session_id` varchar(100) NOT NULL,
  `login_date` datetime DEFAULT NULL,
  `logout_date` datetime DEFAULT NULL,
  `remote_addr` varchar(50) DEFAULT NULL,
  `remote_host` varchar(50) DEFAULT NULL,
  `sys_user_id` int(11) NOT NULL,
  `token` varchar(800) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_core_user_session_sys_user1_idx` (`sys_user_id`),
  CONSTRAINT `sys_user_session_ibfk_1` FOREIGN KEY (`sys_user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=404 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sys_user_session`
--

/*!40000 ALTER TABLE `sys_user_session` DISABLE KEYS */;
INSERT INTO `sys_user_session` (`id`,`session_id`,`login_date`,`logout_date`,`remote_addr`,`remote_host`,`sys_user_id`,`token`) VALUES 
 (90,'2497260E63938715BF9332AD2B70D7D7','2017-01-03 14:18:18','2017-01-03 14:58:02','192.168.56.1','solomon_liu',1,NULL),
 (91,'1D2A9FEA9EC34E0014DDA60FC93509D4','2017-01-03 15:15:55','2017-01-03 15:17:38','192.168.56.1','solomon_liu',1,NULL),
 (92,'9D5B9449420E95C7D14C970258034E31','2017-01-03 15:17:39',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (93,'513AB4D87B6CEA35EB536E7CFDEF6FBE','2017-01-03 15:18:20',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (94,'805B453AA25015ABBB871CB26F7929AD','2017-01-03 15:22:11',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (95,'19D34AE5EB90E44C1E9F5E2F34411D49','2017-01-03 15:23:37',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (96,'83C3E3264508C40A83EAC1C4A534D5E9','2017-01-03 15:23:38',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (97,'90FA643A44EADAFDD33747E7B0C839EB','2017-01-03 15:26:45',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (98,'D40D1DFE27F0CC360D929424C80843AA','2017-01-03 15:29:21','2017-01-03 15:29:45','192.168.56.1','solomon_liu',1,NULL),
 (99,'FBBE0B8165DEB03610AA3EE6B4480186','2017-01-03 16:42:15','2017-01-03 17:17:02','192.168.56.1','solomon_liu',1,NULL),
 (100,'E87B8C94C744629EC64DE650513D7238','2017-01-04 10:51:17','2017-01-04 11:27:02','192.168.56.1','solomon_liu',1,NULL),
 (101,'0C5D07762A78AEB491C6D090117DCCD9','2017-01-04 14:34:52',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (102,'0C5D07762A78AEB491C6D090117DCCD9','2017-01-04 14:42:29',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (103,'DF01795DB29465021E5BD57C1E20E6A9','2017-01-04 15:17:58',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (104,'A730674C09E07D704487B0F9F0D479A9','2017-01-05 10:20:24','2017-01-05 10:59:12','192.168.56.1','solomon_liu',1,NULL),
 (105,'AD3FD7858D4C46821AC11B553EB8C1AF','2017-01-05 11:15:17','2017-01-05 12:02:05','172.16.21.82','solomon_liu',1,NULL),
 (106,'FBE295C1590DA4C07989D49B7B7826A5','2017-01-05 11:27:39','2017-01-05 11:58:05','192.168.56.1','solomon_liu',1,NULL),
 (107,'0DD07A1A619284C1FE11C39C398B2FB4','2017-01-05 12:19:27',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (108,'B2F6E32A014299A6051459A8AB4CB246','2017-01-05 12:25:31','2017-01-05 12:56:18','192.168.56.1','solomon_liu',1,NULL),
 (109,'A9354CADE035F756E819A3D56FF81CE5','2017-01-05 12:27:27',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (110,'05C71C3B8FA51C99D6A7B946C665D4EF','2017-01-05 12:57:36',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (111,'4BD5D77EB0C4DD798EEF87FE93EB4687','2017-01-05 13:04:06',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (112,'A84E21DE74BCCCE120F679292A6AF045','2017-01-05 13:06:57','2017-01-05 13:43:43','192.168.56.1','solomon_liu',1,NULL),
 (113,'B2C9C95E72F9272B3DF5139789BFDE05','2017-01-05 13:19:56','2017-01-05 13:24:45','192.168.56.1','solomon_liu',1,NULL),
 (114,'8B1B098E4CB1A723EC6AA316DD4870B3','2017-01-05 13:24:46',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (115,'412B0C7F38524CB170A53F592526A0D9','2017-01-05 13:55:33',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (116,'276DBD73D0C7A87A5F5A83E757DDF1B4','2017-01-05 13:56:48','2017-01-05 14:27:28','192.168.56.1','solomon_liu',1,NULL),
 (117,'83C686966BD3AA3BFC9A7B4D834DC308','2017-01-05 14:39:10','2017-01-05 15:09:28','192.168.56.1','solomon_liu',1,NULL),
 (118,'F8EFC160BEC07B8AB982AE8198319542','2017-01-06 09:16:44','2017-01-06 09:49:11','192.168.56.1','solomon_liu',1,NULL),
 (119,'C38EE804F62BF81FA19BBDEDBA465D58','2017-01-06 10:16:46','2017-01-06 10:48:11','192.168.56.1','solomon_liu',1,NULL),
 (120,'6A91CAFBA44B6FFCB5AA74B446CB650A','2017-01-07 14:54:26',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (121,'9CBB9EF6D9689DBBE2FE74EDA5C0D084','2017-01-07 14:55:37',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (122,'AE5BC995311DCAF4EAC6199BEC364A90','2017-01-07 14:56:22',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (123,'EBDB6582A8C6D18749C71BBF101B9AF0','2017-01-07 14:57:37',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (124,'439E3F0E351C56FAA668C65A012B7C1D','2017-01-07 15:04:06',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (125,'80A4C6F7C67BA544CE33524C4A7357FB','2017-01-07 15:10:31',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (126,'98B239623BEFA733776E392ED5AED284','2017-01-07 15:20:17','2017-01-07 15:20:25','192.168.56.1','solomon_liu',1,NULL),
 (127,'4F5E2CC45C2BABF35B859965B7A4A8DA','2017-01-07 15:20:43',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (128,'30458D8CED4C49DE06BA3DE8A7F3F92D','2017-01-07 15:38:13',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (129,'FD7129A48C5DD622AA60E52E1734F328','2017-01-07 15:48:52','2017-01-07 15:57:18','192.168.0.16','admin-PC',1,NULL),
 (130,'94CFA394ADE32C78B3C37F8D0E04CD3D','2017-01-07 15:57:28',NULL,'192.168.0.16','admin-PC',1,NULL),
 (131,'F392B1444C3A3495C07E8629921487A8','2017-01-07 15:58:20',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (132,'7D4A0C56884390DCA04D589FF1C7C63E','2017-01-07 16:01:39',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (133,'D3CE78389555815A1B607104E17BE4F9','2017-01-07 16:07:29',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (134,'8D612CCBED5CD1051C41C98AD895BA93','2017-01-07 16:07:23','2017-01-07 16:13:21','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (135,'5024DCADD4E215533996BAEEB50CC153','2017-01-07 16:13:45','2017-01-07 16:48:00','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (136,'630E7AB33A2EF27C18F4DDDC0FDCC28F','2017-01-07 16:15:02',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (137,'739F1D18FBD6C35B19D048B6685FAC93','2017-01-07 16:22:16',NULL,'192.168.0.16','admin-PC',1,NULL),
 (138,'2D8D442FACD31AEF50E9B2D23D62C1BD','2017-01-07 16:35:23',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (139,'630E7AB33A2EF27C18F4DDDC0FDCC28F','2017-01-07 16:39:42',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (140,'8A2A48AD900C1BCEEE91405411874DCC','2017-01-07 16:48:11','2017-01-07 17:06:43','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (141,'E3345CCFE1AF866B1859CCEACDE2DD18','2017-01-07 16:53:25',NULL,'192.168.0.16','admin-PC',1,NULL),
 (142,'F17C8901E6AEB524D53D2FA15541D900','2017-01-07 17:06:56','2017-01-07 17:08:55','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (143,'C2DB8AC748AF1745ED472DAFF444EC32','2017-01-07 17:07:48',NULL,'192.168.0.16','admin-PC',1,NULL),
 (144,'34729FB24AB69854BF24710FF90F0A60','2017-01-07 17:09:02','2017-01-07 17:17:57','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (145,'7DBE8263838BDEF3E7D65AEF07C1B77A','2017-01-07 17:14:02',NULL,'192.168.0.16','admin-PC',1,NULL),
 (146,'4B23DF25CBED456820225BA53B9C3CAD','2017-01-07 17:15:35','2017-01-07 17:48:22','192.168.56.1','solomon_liu',1,NULL),
 (147,'6D5860EEFF90BD1B274C6E4C00333F84','2017-01-07 17:17:51',NULL,'192.168.0.16','admin-PC',1,NULL),
 (148,'7874485E3C31AA5E773887D0A851B62F','2017-01-07 17:18:02','2017-01-07 17:20:09','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (149,'7301A9EDB053012F6A6BBE6258EA5D19','2017-01-07 17:20:13','2017-01-07 17:51:05','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (150,'94953D8B7A911ADE3AAB54B8AF3CF15B','2017-01-07 17:21:32',NULL,'192.168.0.16','admin-PC',1,NULL),
 (151,'A55AA1D7A891E3718EAAAC290DF63970','2017-01-07 17:34:33',NULL,'192.168.0.16','admin-PC',1,NULL),
 (152,'FE08301AE55FF8AA08775CEBE52BD63A','2017-01-09 08:45:28',NULL,'192.168.0.16','admin-PC',1,NULL),
 (153,'12FA9322EFD316EFF882D5B28715730D','2017-01-09 08:46:57','2017-01-09 11:23:30','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (154,'F5BFE8F18DD59B21C3E34246E1220E8A','2017-01-09 08:49:29',NULL,'192.168.0.94','admin-PC',1,NULL),
 (155,'0FFC45B093B953C4BBC3C284A0CFE109','2017-01-09 08:49:43',NULL,'192.168.0.94','admin-PC',1,NULL),
 (156,'E0E63FD3846EA979AE73122994C018A9','2017-01-09 08:58:29',NULL,'192.168.0.94','admin-PC',1,NULL),
 (157,'24744E5B0622C43628C0395A990CF6A8','2017-01-09 08:59:11','2017-01-09 09:04:38','192.168.0.94','admin-PC',1,NULL),
 (158,'F59A7FF134BEA3334B874FA58F9FCBFD','2017-01-09 09:04:50',NULL,'192.168.0.16','admin-PC',1,NULL),
 (159,'B8CCD9D4D20F6DEE27B4E55FAC76D980','2017-01-09 09:06:25',NULL,'192.168.0.94','admin-PC',1,NULL),
 (160,'440ABF8A6CCCDB33F27773F8419900A5','2017-01-09 09:07:08','2017-01-09 09:28:52','192.168.0.94','admin-PC',1,NULL),
 (161,'2B44DA4326B11908025C38FAF873D0B6','2017-01-09 09:07:05',NULL,'192.168.0.16','admin-PC',1,NULL),
 (162,'BB21BB653699F0C2FDCDD5F2875AE745','2017-01-09 09:09:49',NULL,'192.168.0.16','admin-PC',1,NULL),
 (163,'B7BC89C9A0E37821AA4DDC9042222895','2017-01-09 09:11:52',NULL,'192.168.0.16','admin-PC',1,NULL),
 (164,'D13846EE0953E93B5E381902A07A7C17','2017-01-09 09:29:01',NULL,'192.168.0.94','admin-PC',1,NULL),
 (165,'2425640368794FB73FD7667B87150387','2017-01-09 09:29:51',NULL,'192.168.0.94','admin-PC',1,NULL),
 (166,'B0FB5FBF2948B1A38167E1B70DC53EAA','2017-01-09 09:40:52',NULL,'192.168.0.94','admin-PC',1,NULL),
 (167,'43EDF96C48889273E3F1C8803AF8BD35','2017-01-09 09:48:12','2017-01-09 10:36:07','192.168.0.94','admin-PC',1,NULL),
 (168,'6EB6DCC9402050943708A2A7595E179F','2017-01-09 10:36:14','2017-01-09 11:59:48','192.168.0.94','admin-PC',1,NULL),
 (169,'6E2C0808EC8851401C273297E8E830E1','2017-01-09 11:21:36',NULL,'192.168.0.16','admin-PC',1,NULL),
 (170,'C3857CDB5BC6E53601FE911146CA9E43','2017-01-09 11:23:35','2017-01-09 11:57:00','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (171,'D39509FF8B74AF283747756BE7604DD9','2017-01-09 11:28:28',NULL,'192.168.0.16','admin-PC',1,NULL),
 (172,'B5DFE32B477C9C77EA4C3B2BA557A09C','2017-01-09 13:17:40','2017-01-09 15:19:18','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (173,'490C1887583D55D92C5F469FF40A894B','2017-01-09 13:32:23',NULL,'192.168.0.94','admin-PC',1,NULL),
 (174,'7E2A37F875F6BE141D74E8AC7A16827B','2017-01-09 13:34:52','2017-01-09 14:01:33','192.168.0.16','admin-PC',1,NULL),
 (175,'A706A99F3F62D6B4CFD7EE40F4EFBA2A','2017-01-09 14:01:41',NULL,'192.168.0.16','admin-PC',1,NULL),
 (176,'E297AF6A187B39E189B8B2682CCF88C2','2017-01-09 14:09:40',NULL,'192.168.0.16','admin-PC',1,NULL),
 (177,'89E19A891C7A7EE90324FF619B606660','2017-01-09 14:14:40',NULL,'192.168.0.16','admin-PC',1,NULL),
 (178,'E7265CF6C2E8F742F775E72CFFE14A08','2017-01-09 14:23:34',NULL,'192.168.0.16','admin-PC',1,NULL),
 (179,'E7D3E2B1AC3AA660A7172BE63AF60E8D','2017-01-09 14:30:21',NULL,'192.168.0.16','admin-PC',1,NULL),
 (180,'D7227755814F1F75B394826D060EC227','2017-01-09 14:37:28','2017-01-09 15:41:02','192.168.0.94','admin-PC',1,NULL),
 (181,'1DB7F2C89D78C0CAC2A2CC10410F2866','2017-01-09 14:38:40',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (182,'56C3758E87EC09FFFB38480103F19B78','2017-01-09 14:39:39','2017-01-09 15:29:13','192.168.56.1','solomon_liu',1,NULL),
 (183,'FD9D88A157CFBAEACBC891CA8C6CD8D3','2017-01-09 14:45:31',NULL,'192.168.0.16','admin-PC',1,NULL),
 (184,'3D12822F58C0460AE36C6DD5F93EDE35','2017-01-09 14:48:23',NULL,'192.168.0.16','admin-PC',1,NULL),
 (185,'ECF9B8CEC29BE346E0A16B887FB47798','2017-01-09 14:57:30',NULL,'192.168.0.16','admin-PC',1,NULL),
 (186,'C73816DF879C0A7447044BEF5C8D70B1','2017-01-09 15:19:24','2017-01-09 15:46:07','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (187,'767F21E919B451183538A80FE1663D48','2017-01-09 15:34:31',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (188,'EFE45AE0B131364F767E77655EC159F1','2017-01-09 15:37:22',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (189,'29BA9D82D590AC1B489CA9B2C2727F52','2017-01-09 15:44:37',NULL,'192.168.0.16','admin-PC',1,NULL),
 (190,'EA9B83469F8FE010E253725462AEE6B7','2017-01-09 15:46:18','2017-01-09 15:51:08','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (191,'9A99C55A2ED56A9889D451B83E71254C','2017-01-09 15:51:13','2017-01-09 15:57:44','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (192,'DE8022661A6CDBA7EF92B5C40CE38F34','2017-01-09 15:57:50','2017-01-09 16:03:37','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (193,'2C1807180C2F2CFCB2DC30239E62CC62','2017-01-09 15:58:49',NULL,'192.168.0.94','admin-PC',1,NULL),
 (194,'E6A4863CD1DCABDDBD720B03A8CB543C','2017-01-09 16:03:41','2017-01-09 17:20:51','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (195,'5D88DAE825BFB07E72C4DB617E6B2852','2017-01-09 16:04:09',NULL,'192.168.0.94','admin-PC',1,NULL),
 (196,'E54071F149DE1009EA14486A653E1DE2','2017-01-09 16:09:32',NULL,'192.168.0.94','admin-PC',1,NULL),
 (197,'6EDE37B5746C0288FBB0101AACF9EDA7','2017-01-09 16:12:31',NULL,'192.168.0.94','admin-PC',1,NULL),
 (198,'2C76437C22DA546C4D8BA654B43F343F','2017-01-09 16:13:12',NULL,'192.168.0.94','admin-PC',1,NULL),
 (199,'ADAD5A9392F9CB75852D124676D0545D','2017-01-09 16:14:42',NULL,'192.168.0.94','admin-PC',1,NULL),
 (200,'78484F16499E27F8D8C4A95DD2B4A37C','2017-01-09 16:15:47',NULL,'192.168.0.94','admin-PC',1,NULL),
 (201,'918ACEB3ADF65BC48797C7BAA53AAE21','2017-01-09 16:19:25','2017-01-09 17:06:22','192.168.0.94','admin-PC',1,NULL),
 (202,'FD30AF92E869082F999E74D077E8FA53','2017-01-09 17:17:52',NULL,'192.168.0.94','admin-PC',1,NULL),
 (203,'AD6EC32278AA0D5293D6BB83A0A350B7','2017-01-09 17:20:56','2017-01-09 18:22:18','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (204,'C213D902EA60039341557551B4995CAF','2017-01-09 17:32:47',NULL,'192.168.0.16','admin-PC',1,NULL),
 (205,'2684B86C3E9FB5BD84774A42CD071F6F','2017-01-09 17:35:45',NULL,'192.168.0.94','admin-PC',1,NULL),
 (206,'043EBD1964562E644F458B58F604E39D','2017-01-09 17:36:09','2017-01-09 17:37:17','192.168.0.94','admin-PC',1,NULL),
 (207,'A7EF8796A7D06D12D237346401522A9F','2017-01-09 17:37:22',NULL,'192.168.0.94','admin-PC',1,NULL),
 (208,'1EF4A063FAE986F1C716E75D40D8F04A','2017-01-09 17:37:59',NULL,'192.168.0.16','admin-PC',1,NULL),
 (209,'FF88234FEE0943C035AD44C6BEB126C5','2017-01-09 17:42:43',NULL,'192.168.0.16','admin-PC',1,NULL),
 (210,'E578B7F451F8EC204649FC84E4F18856','2017-01-09 17:49:49',NULL,'192.168.0.94','admin-PC',1,NULL),
 (211,'864BCCCD8FD6527FF2E801DBAE84FE78','2017-01-09 17:50:56',NULL,'192.168.0.16','admin-PC',1,NULL),
 (212,'7B6E355FDF3897D44A87F1FB748317FA','2017-01-09 17:53:52',NULL,'172.16.20.81','solomon_liu',1,NULL),
 (213,'DF8BA3AF9B2EA1034FE8F74EE094FBA5','2017-01-09 17:54:06',NULL,'192.168.0.94','admin-PC',1,NULL),
 (214,'0AB18A89B67D6F9AD96508B0A5A2BF89','2017-01-09 17:54:47',NULL,'192.168.0.16','admin-PC',1,NULL),
 (215,'F96D46CA0E0C04112585792C031EFA08','2017-01-09 17:59:12',NULL,'192.168.0.16','admin-PC',1,NULL),
 (216,'D2C308965AF9C2F362DB9CF057B95149','2017-01-10 08:52:25',NULL,'192.168.0.94','admin-PC',1,NULL),
 (217,'40CF991AB15B290AF17AD27BDCDFBFBE','2017-01-10 09:07:57','2017-01-10 09:49:48','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (218,'AC0CFBDF6607614E7C8B8541B7859505','2017-01-10 09:25:21',NULL,'192.168.0.16','admin-PC',1,NULL),
 (219,'0DDB569BBDD968085B22C8DBCD4641E7','2017-01-10 09:27:16',NULL,'192.168.0.94','admin-PC',1,NULL),
 (220,'1F36FF476872D74DD32F2B7459731639','2017-01-10 09:36:42',NULL,'192.168.0.94','admin-PC',1,NULL),
 (221,'EFC7EB0B03BC1B4AEBD2C2BF60D8F878','2017-01-10 09:43:17',NULL,'192.168.0.16','admin-PC',1,NULL),
 (222,'424635A5BB276438DDC9243D1D8582AE','2017-01-10 09:50:55','2017-01-10 09:58:39','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (223,'FE0205D79A2C68948398083C20C31479','2017-01-10 09:58:43','2017-01-10 10:19:10','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (224,'824063F8DA147369B75852761973BD76','2017-01-10 10:00:52','2017-01-10 10:33:37','192.168.0.16','admin-PC',1,NULL),
 (225,'EBF049A3293C95EBC503A32B3656F366','2017-01-10 10:14:27',NULL,'192.168.0.94','admin-PC',1,NULL),
 (226,'371004FAB952C0EEE792FEE2CC30D253','2017-01-10 10:19:14','2017-01-10 10:25:19','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (227,'D08D189E203F958FEC9441C531517734','2017-01-10 10:23:48','2017-01-10 11:05:29','192.168.0.94','admin-PC',1,NULL),
 (228,'35F11415ABB63FDF1D5F1EFAAD12D5C2','2017-01-10 10:25:24','2017-01-10 10:30:36','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (229,'6ACCB71879E24A6F23BF025CCC0C3C7D','2017-01-10 10:30:40','2017-01-10 10:51:25','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (230,'751C4EA0E834A178434AE0EBB0686319','2017-01-10 11:02:06','2017-01-10 11:09:53','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (231,'E227E8EA805F3676B37BD40F58AA8EFD','2017-01-10 11:09:57','2017-01-10 11:19:04','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (232,'CDBEFA2DFB81BEC11B5BF090956E5BB3','2017-01-10 11:14:47',NULL,'192.168.0.16','admin-PC',1,NULL),
 (233,'69BCDB35446AF74FC80532B6C7AECC81','2017-01-10 11:19:08','2017-01-10 11:21:03','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (234,'8C413B9D30F075B314E8887DDD62D12B','2017-01-10 11:21:07','2017-01-10 11:22:30','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (235,'449B787D1C7C28B841C0CDB716DD7B23','2017-01-10 11:22:34','2017-01-10 11:53:29','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (236,'9893CE1815A203B12BA6321309163B2D','2017-01-10 11:30:51','2017-01-10 12:02:30','192.168.0.94','admin-PC',1,NULL),
 (237,'6BC96CDBCCF8E6F7BF23C57772C6209A','2017-01-10 13:35:17',NULL,'192.168.0.94','admin-PC',1,NULL),
 (238,'B284D04CF965586ECF21A4CFC488F234','2017-01-10 13:41:50',NULL,'172.16.20.14','solomon_liu',1,NULL),
 (239,'6D50F8B8F6CC16A85C25014D759A0E6D','2017-01-10 14:08:16',NULL,'192.168.0.94','admin-PC',1,NULL),
 (240,'9A2885A0F7D81183168EE262FB486CCB','2017-01-10 14:49:20',NULL,'192.168.0.94','admin-PC',1,NULL),
 (241,'04A849309678C5075B43977A1D5BB1C2','2017-01-10 15:01:34',NULL,'192.168.0.94','admin-PC',1,NULL),
 (242,'039989D608E8F1F5CAFF8474703B4209','2017-01-10 16:03:24',NULL,'192.168.0.94','admin-PC',1,NULL),
 (243,'409F1BB5088DDE3DC0EA4E1F22B99EE9','2017-01-10 16:40:08',NULL,'192.168.0.94','admin-PC',1,NULL),
 (244,'6FF619CA9185D700BF75DF2D8D018DBD','2017-01-10 16:42:37',NULL,'192.168.0.94','admin-PC',1,NULL),
 (245,'A4A06AF39DC9FAA295D9434FA055193E','2017-01-10 16:56:16',NULL,'192.168.0.94','admin-PC',1,NULL),
 (246,'5A0A70931131E0649B487065FFAA4368','2017-01-10 16:58:04','2017-01-10 17:00:32','192.168.0.94','admin-PC',1,NULL),
 (247,'DEA8E03BC4382571F71E046455BB47D6','2017-01-10 17:00:49',NULL,'192.168.0.94','admin-PC',1,NULL),
 (248,'8C82A1A4E00669CA88588D0FB5EE3EB7','2017-01-10 17:08:20',NULL,'192.168.0.94','admin-PC',1,NULL),
 (249,'110855C354C390C12352D0B94EAAF2A9','2017-01-10 17:09:39','2017-01-10 17:42:16','192.168.0.94','admin-PC',1,NULL),
 (250,'9189962C3FF368772030E47BFFCEA207','2017-01-10 17:24:27',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (251,'B74215C437369DECE69C7583394C9800','2017-01-10 17:25:26','2017-01-10 17:30:02','192.168.56.1','solomon_liu',1,NULL),
 (252,'440A652E81969B47B05001119E67CC28','2017-01-10 17:26:59',NULL,'192.168.0.16','admin-PC',1,NULL),
 (253,'6F076E3F1F32DBDCD5E17D4BB92C2517','2017-01-10 17:30:14','2017-01-10 17:30:39','192.168.56.1','solomon_liu',1,NULL),
 (254,'A29ACD7E6202996C8A507D17C8456F71','2017-01-10 17:30:40',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (255,'97457B1582758707D7EBE6FEAC3D8F5E','2017-01-10 17:48:25',NULL,'192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (256,'384AE289C33361C1A1792940817BE273','2017-01-10 17:51:32',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (257,'4FCB5D6B2F4B270281988A34A6F5E8D3','2017-01-10 17:59:56',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (258,'3E94BB62504849F5A5153E6EEBD9F8E2','2017-01-10 18:02:17',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (259,'4FE031CBE560746A1CD20B70C3318069','2017-01-11 08:52:51','2017-01-11 09:23:38','192.168.56.1','solomon_liu',1,NULL),
 (260,'449623A47BC7B832E3A5DCBAE69256B0','2017-01-11 09:15:24',NULL,'192.168.0.16','admin-PC',1,NULL),
 (261,'120C26E56AFD35370FE29D0D5031C1C1','2017-01-11 09:21:49',NULL,'192.168.0.16','admin-PC',1,NULL),
 (262,'B80E20FE5CE320A39D483015F67A50AF','2017-01-11 09:26:10',NULL,'192.168.0.94','admin-PC',1,NULL),
 (263,'6B5E27A8A4FD10EADD8B42D16036581E','2017-01-11 09:27:20','2017-01-11 11:59:20','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (264,'C19F6EBDAA89EDF2C184E651AC811EB9','2017-01-11 09:32:01',NULL,'192.168.0.94','admin-PC',1,NULL),
 (265,'5F8D23586F3D83DCA1F83C270CC95C3A','2017-01-11 09:37:57',NULL,'192.168.0.16','admin-PC',1,NULL),
 (266,'88092085708505381AF82A30E06B5EA9','2017-01-11 09:43:23',NULL,'192.168.0.94','admin-PC',1,NULL),
 (267,'A6F6465344C56D395824411802FEF1DE','2017-01-11 10:37:48','2017-01-11 11:08:35','192.168.56.1','solomon_liu',1,NULL),
 (268,'62C77BD6A9BD773CE73E1ABADF1A6E8D','2017-01-11 10:42:29','2017-01-11 10:44:14','192.168.0.94','admin-PC',1,NULL),
 (269,'C91D911FDAD6E63AB0E5885E5A02BEB1','2017-01-11 11:17:22','2017-01-11 11:59:16','192.168.0.16','admin-PC',1,NULL),
 (270,'6F6DB7AC15ADB356812BB8D42D5B1053','2017-01-11 13:25:30','2017-01-11 13:55:44','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (271,'23A4F96F248BE2A64057999F1D89C506','2017-01-11 13:34:51',NULL,'192.168.0.16','admin-PC',1,NULL),
 (272,'862FC3A165A99334B78FD6721CBE27D4','2017-01-11 14:49:33','2017-01-11 14:51:44','192.168.0.94','admin-PC',1,NULL),
 (273,'B195BA451417208D579747C77A0F2F3F','2017-01-11 15:42:56',NULL,'192.168.0.16','admin-PC',1,NULL),
 (274,'7B44E99B52A5ED2B76173CDEC8476377','2017-01-11 15:58:33','2017-01-11 16:28:51','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (275,'A4EBB4E5ED81A3A2FDD540969D670C60','2017-01-11 16:04:48','2017-01-11 16:04:52','192.168.0.94','admin-PC',1,NULL),
 (276,'81472244A9A953B0C5680883386313D2','2017-01-11 16:05:00','2017-01-11 16:06:02','192.168.0.94','admin-PC',1,NULL),
 (277,'82BFF207034A68DD65A73128E2D46C0D','2017-01-11 16:06:11',NULL,'192.168.0.94','admin-PC',1,NULL),
 (278,'82BFF207034A68DD65A73128E2D46C0D','2017-01-11 16:12:05',NULL,'192.168.0.94','admin-PC',1,NULL),
 (279,'68EE1292D003F511296CA20B4D372263','2017-01-11 16:12:59',NULL,'192.168.0.16','admin-PC',1,NULL),
 (280,'147B111F813E232F34D124A7139395B0','2017-01-11 16:19:34',NULL,'192.168.0.16','admin-PC',1,NULL),
 (281,'CBC7E6961176F1C62D7FF23446E1C72F','2017-01-11 16:21:49','2017-01-11 16:21:54','192.168.0.94','admin-PC',1,NULL),
 (282,'934EA1AF3052138120C08E2681591851','2017-01-11 16:22:01',NULL,'192.168.0.94','admin-PC',1,NULL),
 (283,'0E8A63990D6ED196979F8D5912EB9652','2017-01-11 16:22:23',NULL,'192.168.0.16','admin-PC',1,NULL),
 (284,'8D8701151591A17C30C5B2E4F11AA4AB','2017-01-11 16:26:01','2017-01-11 16:57:47','192.168.0.16','admin-PC',1,NULL),
 (285,'E0D149E55A8A130873D5FD78FADCC12B','2017-01-11 16:33:07',NULL,'192.168.0.94','admin-PC',1,NULL),
 (286,'EFCC11AE219D22C05F9FEFCE6AE1CA92','2017-01-11 16:33:54',NULL,'192.168.0.94','admin-PC',1,NULL),
 (287,'CFA9B75E01CF23D240CEB6CCD0DB54AF','2017-01-11 16:41:00','2017-01-11 16:44:02','192.168.0.94','admin-PC',1,NULL),
 (288,'BE24024B2E88FDA3B80484FB5441DBC3','2017-01-11 16:43:09',NULL,'192.168.0.94','admin-PC',1,NULL),
 (289,'FCEF6BD8D6243E642B7A549ADF475B04','2017-01-11 16:44:16',NULL,'192.168.0.94','admin-PC',1,NULL),
 (290,'C4A08CA5348358A6934C97CB63CAAA7D','2017-01-11 17:15:20','2017-01-11 17:15:46','192.168.0.94','admin-PC',1,NULL),
 (291,'47F28331C1DEFCC2D23319EBD5596032','2017-01-11 17:15:51',NULL,'192.168.0.94','admin-PC',1,NULL),
 (292,'20D67B2D211E96F18EB7A817257EDF79','2017-01-11 17:20:12','2017-01-11 17:21:11','192.168.0.94','admin-PC',1,NULL),
 (293,'DF7B01185C01CDC4101B2931B5BD76BD','2017-01-11 17:21:18','2017-01-11 17:22:07','192.168.0.94','admin-PC',1,NULL),
 (294,'0491958BA48FAC3F41961E319C31E6C8','2017-01-11 18:01:05','2017-01-11 18:35:50','192.168.56.1','solomon_liu',1,NULL),
 (295,'84759A07EA0879A9C2E37089C81A1EEE','2017-01-12 09:03:18','2017-01-12 09:03:39','192.168.0.94','admin-PC',1,NULL),
 (296,'98DD690CD3F88646995149208D0434CA','2017-01-12 09:03:50',NULL,'192.168.0.94','admin-PC',1,NULL),
 (297,'92B7390B480502DF59F53FB32C141898','2017-01-12 10:48:42',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (298,'0E00BD8DA7D549EC1BA571896C0CD8DD','2017-01-12 10:49:35','2017-01-12 10:49:42','192.168.56.1','solomon_liu',1,NULL),
 (299,'02AAFEF6EDDACC070C652EE04415A7E0','2017-01-12 10:49:46',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (300,'381402382F2067BB2FB6069CC1D70844','2017-01-12 10:50:54',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (301,'C4A130D0D1D707BBA20AE53851FBFCFB','2017-01-12 10:53:08','2017-01-12 10:53:52','192.168.56.1','solomon_liu',1,NULL),
 (302,'234A73464EA9C4A2CC81A2CEE625F4D8','2017-01-12 10:53:54','2017-01-12 10:55:10','192.168.56.1','solomon_liu',1,NULL),
 (303,'85BBE850094811D800B0BBB4600CF1E5','2017-01-12 10:55:36',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (304,'68B0017AA7757DE13EA9EAC50CAD0D75','2017-01-12 10:58:51','2017-01-12 10:59:03','192.168.56.1','solomon_liu',1,NULL),
 (305,'A611E73EA39882FC29B981C2D8B381E0','2017-01-12 10:59:12',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (306,'D449DAF8FB65323348C49CE0AC1DCE41','2017-01-12 11:21:22','2017-01-12 12:03:13','192.168.56.1','solomon_liu',1,NULL),
 (307,'A306AFE7A66F1788EA643C2480B94F82','2017-01-12 12:27:27','2017-01-12 12:58:13','192.168.56.1','solomon_liu',1,NULL),
 (308,'165B61CE3DAD6FEC4DC329F3D0DCD405','2017-01-12 14:08:13','2017-01-12 14:33:04','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (309,'C9FF54D396EA05DA56AB78F80F1B5B76','2017-01-12 14:14:09',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (310,'106EB7FC60D15472C6DC0A88E3E9F033','2017-01-12 14:19:54','2017-01-12 14:56:39','192.168.56.1','solomon_liu',1,NULL),
 (311,'198FB3CDEAC0BE27689AEEF7AB02EE9C','2017-01-12 14:33:09','2017-01-12 15:01:44','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (312,'79A038BDA31FF7C092DF863E1A935846','2017-01-12 15:01:49','2017-01-12 15:38:00','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (313,'61C4129BE1857061340FBE2C07B2C317','2017-01-12 15:21:25','2017-01-12 15:54:12','192.168.56.1','solomon_liu',1,NULL),
 (314,'6D117112D8B8DC1DDBF85A33A0DA9B2B','2017-01-12 15:48:35','2017-01-12 15:51:48','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (315,'B4A6A82FA2B295B4F784E411E8CAF5F8','2017-01-12 15:51:53','2017-01-12 16:19:20','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (316,'3E0039431779A1AD3CD10762EC10749C','2017-01-12 16:19:25',NULL,'192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (317,'B47F145C9AD8D126C0D27E3C6F8A6EEC','2017-01-12 16:26:07','2017-01-12 16:59:12','192.168.56.1','solomon_liu',1,NULL),
 (318,'80F4A3C34D4785F18074014F82043485','2017-01-12 16:40:12',NULL,'192.168.0.94','admin-PC',1,NULL),
 (319,'9818BAE652986E9F25FBAF984DC17F95','2017-01-12 16:40:54',NULL,'192.168.0.94','admin-PC',1,NULL),
 (320,'8D5C5EE3C7685CFE142DE7B0BFAC4FD5','2017-01-12 16:44:28',NULL,'192.168.0.94','admin-PC',1,NULL),
 (321,'C56E7BB6FDB5B9CB2D08F82A148DEE45','2017-01-12 16:46:19',NULL,'192.168.0.94','admin-PC',1,NULL),
 (322,'2825E64B0101D532B2BC9A684B52847C','2017-01-12 16:47:59',NULL,'192.168.0.94','admin-PC',1,NULL),
 (323,'3F46E4878C3BE3CCA8C3F808343A7808','2017-01-12 16:48:37','2017-01-12 17:35:42','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (324,'20CD837B9A7BC1DF6D049C294BB7E71B','2017-01-12 16:50:38',NULL,'192.168.0.94','admin-PC',1,NULL),
 (325,'F39BE8A8D7589D1DC272DD81D1FBFB7B','2017-01-12 16:52:42',NULL,'192.168.0.94','admin-PC',1,NULL),
 (326,'E0DD65EEB6CF17068ABBF96CE5FD11AE','2017-01-12 17:05:35',NULL,'192.168.0.94','admin-PC',1,NULL),
 (327,'E05690B8766A38C2AA0E0721E7D3710F','2017-01-12 17:07:38',NULL,'192.168.0.94','admin-PC',1,NULL),
 (328,'54F55B54D00C830067879B5B2E6A9FA4','2017-01-12 17:10:55',NULL,'192.168.0.94','admin-PC',1,NULL),
 (329,'3478D5FD2434E4D3D4D5DDC1E3AC7554','2017-01-12 17:35:47','2017-01-12 18:06:42','192.168.0.77','DESKTOP-A6KK09U',1,NULL),
 (330,'42A1AD236E88164970B5422F54A8954C','2017-01-12 17:59:46',NULL,'192.168.0.94','admin-PC',1,NULL),
 (331,'6814CB5EFA47CD4E2992BB7BA06F65EF','2017-01-13 08:18:31','2017-01-13 08:18:40','192.168.56.1','solomon_liu',1,NULL),
 (332,'C31AF7315ACCD1FE1125C68B2CD06AFC','2017-01-13 08:18:43','2017-01-13 08:27:25','192.168.56.1','solomon_liu',1,NULL),
 (333,'5C5B6B57F0978AFDC5A2765BCB4CA7E1','2017-01-13 08:27:30','2017-01-13 08:27:59','192.168.56.1','solomon_liu',1,NULL),
 (334,'C89BC48E72B997D0A429F3E42CC61718','2017-01-13 08:28:01','2017-01-13 08:58:34','192.168.56.1','solomon_liu',1,NULL),
 (335,'88C0A74FE70F38A4BD65EC9709A0F4F1','2017-01-13 08:30:53','2017-01-13 08:33:31','192.168.0.94','admin-PC',1,NULL),
 (336,'B1E305A95B19412EDF8BF390B4342F2F','2017-01-13 08:33:38',NULL,'192.168.0.94','admin-PC',1,NULL),
 (337,'C409A42E3CA04C30B2FF6840284F5468','2017-01-13 08:45:44','2017-01-13 08:49:34','192.168.0.94','admin-PC',1,NULL),
 (338,'1006E568C71E973FB751F5F14453EC3D','2017-01-13 08:49:38','2017-01-13 08:50:28','192.168.0.94','admin-PC',1,NULL),
 (339,'AF7DE13780E54A54D4C0E3D232562D77','2017-01-13 08:50:35','2017-01-13 09:03:53','192.168.0.94','admin-PC',1,NULL),
 (340,'1E3619936F384E72FC0554D13C5FF46F','2017-01-13 09:04:09','2017-01-13 09:07:56','192.168.0.94','admin-PC',1,NULL),
 (341,'CDE1D55896F4225E604C93990E37EB4F','2017-01-13 09:08:05','2017-01-13 09:16:13','192.168.0.94','admin-PC',1,NULL),
 (342,'31896E5EC4F158FE514E624834CAD63E','2017-01-13 09:16:16','2017-01-13 09:17:07','192.168.0.94','admin-PC',1,NULL),
 (343,'29FB91319814EBDD61C096304432EC5B','2017-01-13 09:17:16','2017-01-13 09:18:01','192.168.0.94','admin-PC',1,NULL),
 (344,'12A91D56239F7B372BCD05B222230949','2017-01-13 09:18:08',NULL,'192.168.0.94','admin-PC',1,NULL),
 (345,'615A4DB36D8840D3FB0C708E693C17FF','2017-01-13 09:44:57',NULL,'192.168.0.94','admin-PC',1,NULL),
 (346,'E31B6ADED09706ABCED516E49B7BE5AD','2017-01-13 10:02:42',NULL,'192.168.0.94','admin-PC',1,NULL),
 (347,'C62DE7D4CABB817CD1AAFA1611E09758','2017-01-13 10:11:24',NULL,'172.16.21.125','solomon_liu',1,NULL),
 (348,'4F895BB137C0521CF3D5CD00840F75B1','2017-01-13 10:18:13',NULL,'192.168.0.94','admin-PC',1,NULL),
 (349,'93D918B8CE8161422825027966C95152','2017-01-13 10:33:23',NULL,'172.16.21.125','solomon_liu',1,NULL),
 (350,'E5417192743538E3ADBDEF6CE13B1C8C','2017-01-13 10:42:28',NULL,'172.16.21.125','solomon_liu',1,NULL),
 (351,'261F688AD58FAC189F415C7F459B22BA','2017-01-13 10:55:57',NULL,'172.16.21.125','solomon_liu',1,NULL),
 (352,'7EEBB588A9029B83E8987B525C9081C7','2017-01-13 11:02:04',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (353,'815145FF6EF7F2AF4A4034147D937550','2017-01-13 11:06:04',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (354,'7C07F2073DBDD3FAAF0ADEF7ED24BD06','2017-01-13 11:10:01','2017-01-13 11:50:47','192.168.56.1','solomon_liu',1,NULL),
 (355,'F0C268424827548C9A3ED6E55389176D','2017-01-13 11:14:21','2017-01-13 11:47:47','192.168.56.1','solomon_liu',1,NULL),
 (356,'135E4C000E10F773DA89818B54955FAB','2017-01-13 15:08:48','2017-01-13 15:43:47','192.168.56.1','solomon_liu',1,NULL),
 (357,'82010927A11A5D08900E6216B3996957','2017-01-13 17:32:29',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (358,'C6427C5242DA22B29B7B55B0B0707188','2017-01-16 10:51:57',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (359,'00615F8BD7AEA90D3C47695133743CEF','2017-01-16 13:17:00',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (360,'E454D991CBCEAFA7830A8AC980E88297','2017-01-16 16:17:55','2017-01-16 16:49:39','192.168.56.1','solomon_liu',1,NULL),
 (361,'11D8B87AC87C02E43E37E94588880344','2017-01-17 09:15:36','2017-01-17 09:46:22','192.168.56.1','solomon_liu',1,NULL),
 (362,'8DAF9B8060A50A71C59CA70B8F4AE8B5','2017-01-18 14:08:38',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (363,'DC17FC59CC5B8AA909E4CF223DD87FD0','2017-01-19 08:24:55','2017-01-19 09:04:49','192.168.56.1','solomon_liu',1,NULL),
 (364,'7683677E4DA9FB2265B3D597500D7C54','2017-01-19 09:15:49',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (365,'C4262CCD296EBF45F8F3FFF3E7638028','2017-01-20 09:43:59',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (366,'C4262CCD296EBF45F8F3FFF3E7638028','2017-01-20 09:45:39',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (367,'BF62001F07788DF5A7BC21C7A3B53909','2017-01-20 10:19:16','2017-01-20 10:52:47','192.168.56.1','solomon_liu',1,NULL),
 (368,'BB46F7BC06725C8DDC142FD45CE5E160','2017-01-20 11:58:51',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (369,'7F86981CEF681782BBBB5E63B3BC655B','2017-01-20 12:06:45',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (370,'088FF274BB323A7361E5EBD4C8DB291D','2017-01-20 12:08:12',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (371,'088FF274BB323A7361E5EBD4C8DB291D','2017-01-20 12:10:36',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (372,'088FF274BB323A7361E5EBD4C8DB291D','2017-01-20 12:15:05',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (373,'088FF274BB323A7361E5EBD4C8DB291D','2017-01-20 12:17:13',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (374,'088FF274BB323A7361E5EBD4C8DB291D','2017-01-20 12:18:48',NULL,'192.168.56.1','solomon_liu',1,NULL),
 (375,'8438260E92142C6051585423559702C7','2017-01-24 08:34:47','2017-01-24 09:05:34','192.168.56.1','solomon_liu',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTYzMjIwODcsImp0aSI6IkhveEhiWHdsSlpoYy1nMEZrejhITEEiLCJpYXQiOjE0ODUyMTgwODcsIm5iZiI6MTQ4NTIxNzk2Nywic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.l7XWuAGXowATn-SM2MImMZf3nmffQg6VBPBx7LcqCcJKNxvqPSm4BRX2qbH6hefczux2tTVAwu1UaWsXK8dgG6Ii9lHU4VC9meomLVizMTVgCgrAB4dGIQ8_ubP0yWiXNagsCEwvw5gEUd144zysTJo4bOUEbPpa0eX1oIIsMXqclrV8gZ0QxZguXJajLc5UOOrSzaw5BKTSvbStFdAL4NW1rM2EUm53Q1JDaSbNvYaHarPrwlMn2puw-JrcVI0Uop83XhzH10oMRjtlz9wOMklI4i2X5CLr9PwFMIzvtUade2zoRzjYo7LLIK9OjdGVELGJvlVRTNTbko9TeSzTkA'),
 (376,'5A16C0F1BEC8A7EB8F5456D552FFBC03','2017-01-24 09:14:50',NULL,'192.168.56.1','solomon_liu',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTYzMjQ0OTAsImp0aSI6IjdvU1dESmN5a2pqcWxualpReU10Z2ciLCJpYXQiOjE0ODUyMjA0OTAsIm5iZiI6MTQ4NTIyMDM3MCwic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.wczP2l-fUrTriYdHHukTB4nfi-01_m_S3VOs9GLzZ08W1HM35BOm3F8KZmhJwaykmRl9sVhWjw5HPOh9cwvk45sfuL7Ny8KuRcu-X3FLdq_U9bUH_GyMuqZh5sR9h9hD8Zc23YnjJ3TvU4qPnF59MrCCPBHV4mRgvWHn-1hKGVJmmlw5ch9kNBOK5my1PDsKUoJT77HK2W6r3i9tr7hfgXllCyh05QKvX5cB5Kpf8xmOR_z5FxLFQBP1afACr6HKriuG0hMuuKgnVPNnYrOotpM6-CU7KGQki7ISczTTHMHu007HlqCS9W0cUVpsVfXKu-rqAOJolp2t7Ob2zVFPlQ'),
 (377,'A1AA9952B060501B7D135CCB99D37599','2017-01-24 09:25:58',NULL,'192.168.56.1','solomon_liu',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTYzMjUxNTgsImp0aSI6Ii0tMkFGY3lRRU5RQ3lIbmJxUE9YZ1EiLCJpYXQiOjE0ODUyMjExNTgsIm5iZiI6MTQ4NTIyMTAzOCwic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.ZOZ3IfbLGFFvzbmZQWDoCUSVZOg6n9uPV7tjMPJKQeSmmrS1mvxjsmv9LLAqQuESw-M0DrkgHo2csRaD1FV_cQOsTir4w9C7Yd5SkFkP4YnAj1q59SRd1pwZsYajAhmIpaEX-_HFJP3hisY79dfuNx6R50kvDK2cBIruEKoEZkThKCewF_s7r274rxn67jMs61Go8pirUpYWHJiu59oTeRuiwUNhBasNBCbA7U_BafUWLHFwKBGliOPcWpGGtK1mO-RRyPjtE2gvIBcLdkJBVlxBxGoXlLAgHGYTclJS5hwJnm-aYcHuaMxU4n6Wmjwz3aiV-XmQ6A6LBM6NQMnyCA'),
 (378,'A1AA9952B060501B7D135CCB99D37599','2017-01-24 09:26:41',NULL,'192.168.56.1','solomon_liu',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTYzMjUyMDEsImp0aSI6IkMyT3lRVzlNYVBBWG1Nc3FzOVZlNnciLCJpYXQiOjE0ODUyMjEyMDEsIm5iZiI6MTQ4NTIyMTA4MSwic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.WRiYxZNqYKVfLZgjbLHOaN7POkp6QiPnYK2C_FQv1rWM_YCU-1AaH6UIxODjJ-X61Aaksg4qIHn_XPZmXChr9BmyyK1-Mqw7hnxUGPOP50GfvkjsrVgRnAlmlP_v4FnQ0lF1C0OXL65QpirJgRNkRiVrqDwRe1v9riNWt_kaBsDMbwD0PKVbYUo336FQ61skhZ6065c46HvL3TAtZbtr3uLlGsONN743pt2eFXoVJeCGjpMtWw9OOIxuqVZ9cUhCyPgRzXMSHUiZe3GTZae90S8i1CNZNuEBlR7yT215sO8vDPgKbtuFOFWqUFzUc8R6Xznq_BqZeUBXfkr9PIyCzQ'),
 (379,'8D5275ED2AE498F2F3F5F5BFEF8D26BE','2017-01-24 10:01:20',NULL,'192.168.56.1','solomon_liu',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTYzMjcyODAsImp0aSI6IlV1enptbHFwVmR2R3V5aVBSaWVsbkEiLCJpYXQiOjE0ODUyMjMyODAsIm5iZiI6MTQ4NTIyMzE2MCwic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.rIbxfEtBXMBngUd1HXHlK915gPpo1SgX6da5kNX6rYJ88YmN9jXnuMzBHeAYQGo4H0m3VfD-xtl-4v0ajKpBOPkhEWv1GK27GnE7BbVFM6dkTKbBSRCB02yZpCAG_Y2_sPuwiCZE1QQobS8LzKA2xhWR_MQAGSWhDRhN8hyt0ZFwKVB9xvzaVHyjWEe1xqGhfMNVjDA8E8rO2HdMnY8fxE8e6PLHtv-U85ChwOyhkGJjWCfr19xeQGwa6WXAc2uZzoPHFhPBHYxa0AbbslO1sot-ikfGURsqWA4oInTNbXuan04PwKqbMhNccSh_gsLDP9TRJLPaBiF0_M_tV8zR_g'),
 (380,'6841918DBB58AFFE93E57D7172857372','2017-01-24 10:25:49',NULL,'192.168.56.1','solomon_liu',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTYzMjg3NDksImp0aSI6ImZOblc4aWpDenNPSjdMQ0pNLWIydlEiLCJpYXQiOjE0ODUyMjQ3NDksIm5iZiI6MTQ4NTIyNDYyOSwic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.uA7HUSHS8a8R5YKN03PH6M63CpEyQZTVgD6AKfzUohRxqdr0uuP-icPWiRg6qIIoTRpBGnk4MsVJDRELNRGWMA2IbZZjUEHC0Fjk1V8nue92vxaYJf3-VP1xfGXrbJryaAvXZshmIjhzyOb1PJdkBkAzx_XJjlPeL7ZVU9Ckx01EB2jKhytIxPMp6hIMefLrnB8puVh40NyS23uaEw3ebKMDC6XKYAXYkEx4tYPGzeIBiuTot_4K71TSm9Y4ye-LYQ8XQXrtvgH0sJGHScjTdYKufv_SUcp5K5lWsiOwOTD7x2ticgXEfSD-0wskNeKDcWy2hX6K1f_krJlxnZrNag'),
 (381,'301214E8FB788F9625BA0C9AE5C8D9C2','2017-01-24 10:26:30',NULL,'192.168.56.1','solomon_liu',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTYzMjg3OTAsImp0aSI6IlRFR25ma2d5VG9Xbmx6S2RienRPY2ciLCJpYXQiOjE0ODUyMjQ3OTAsIm5iZiI6MTQ4NTIyNDY3MCwic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.SAS1Pwyhmt4k5n3yvBV04FDqMEW78Y8G_rR_VaGgqy_fNOSk0kYuV_x6qj4p6zRvkCa2uEKhUyqILXexxwJ0y1AxDCgDoOv6uAxXF5jm3C8HEAOqsQra58qEeuL89rgLy1i4lw42Ow_NrYA3WWz1QH2YrJiQe6bQatuGT8Gw61fJgMzk3iSzsnJoxMYiEKnlomFxpJWvWY7ad9jvAajgAEPRP2K1hEYVEENRP0upLAWi0XCX40g0lQ98uC5odk2hSrNjh27iMd75i1Ny8QuJFQoEVF2U2V-39IJSubNNbI7Z2_QUCM4K4_YBuW-HJuG7nm5vtWlxulZ8d_Yx5la6ig'),
 (382,'0653A475F11DE0C987DB65BDD9546E6E','2017-01-24 10:34:24',NULL,'192.168.56.1','solomon_liu',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTYzMjkyNjQsImp0aSI6IlBYNU9sZUhXYlhka2ZXM1ZVR1hKb0EiLCJpYXQiOjE0ODUyMjUyNjQsIm5iZiI6MTQ4NTIyNTE0NCwic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.pFwgePXka7_MOBAHjrSAXfchiBSZalPSMN2vpRI4Ms5Vln30Bt4jJYYHM27iMl3NxGoNq4yaFr37vUUL-CITkPBP_d-Qlbg61xwQFZ7S5nvS42im4EqI-RzeH5vNQwrJXe9RIPnkVvcOaae4v1vexPxu199UQ-FYZJq9u4fbv8HSYxOI-NqrIvgP05LWUtIiv5SbhWKFpdCYd_XMMtTPA41brb6Wz5oatMxrrryvQi37KkZ0TgF2Etui3l3RNuByd30sHHacH05dVfK3FsLwDHXnVlGfMyUfknC8kRwQsWR09MZL_Xzw85eHs00A8A0ea88_tKQAL8HIsp7cG9DTRw'),
 (383,'33B29E3054F2F0D7FEA5C31C5ECBC12A','2017-01-24 10:34:53',NULL,'192.168.56.1','solomon_liu',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTYzMjkyOTMsImp0aSI6InFEVGNoT2lpV1hvX1U5c2NLMUxBSnciLCJpYXQiOjE0ODUyMjUyOTMsIm5iZiI6MTQ4NTIyNTE3Mywic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.TFLx0d3YRAom_h7_oTALjFw0Ln4QewRyPAb3F8L_TCzGyHQuoEyH-EDKre5vw_9a49jyyPA9ON5b40mEIVpnBbR_P2cnKMySYnOYbAyuPiF4Gslv5nFRsElyUm4V_NJfudEEXJutqoQbvJ8ni3Scty37lSnLUP8ObR__nwK1lFyANKuRnsEijgrPlbj0iA_5MIL5OwW-9cd4oo6uotxKO6x9URI_RJZUde-SaHcFXkhVraqy1n3PCuJoK5gJf6lyaW5FaqPHdbc6_YJ3jgQ6rSUkelsvZaaj2mKO_yVlPt1i9ruXnJDs2HUQLolYw3-mtnt1_elE5GAm77d3bhpf-A'),
 (384,'4EB8B0C17F6255366DD53A985B90BB5E','2017-01-24 10:36:53',NULL,'192.168.56.1','solomon_liu',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTYzMjk0MTMsImp0aSI6IlZScXdZUWN0R2FGdGEzSHgtR1d0VmciLCJpYXQiOjE0ODUyMjU0MTMsIm5iZiI6MTQ4NTIyNTI5Mywic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.jC-Aa5snzvMOp5iOAmOh6eFfkswsQvhshBVxWv7Oe4qiaZ-LlgNByozm6TwD_1K87RUvM_TfJNw9O96K1B1A7B8mEvZ7s_hfBEwUjTEu2reFgM0rwCoJhr4dnwg4dT5FMKJxwiLVmBDToaIxJYkTS5yQzkEI2EA2jF9Q_6wpg0u0WO53dDCJ0RyDBpCs9PvWU0RkcmO3ozfXfqMuzQcxVZIlW-nw7QOmdXln-JSO4JcAEWijy8RZqqqMlNkyT00YzQA10dCznsGKa6MaUi4fMeSZVOavBu8qPIRbGzn0cmZiK5tLEUGcVuRDyt7lIqLFdPBj67v8uHfWSrao7ag4oA'),
 (385,'69B8877A57EA5FF9D0A611E4BB17E5AB','2017-01-24 10:38:44',NULL,'192.168.56.1','solomon_liu',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTYzMjk1MjQsImp0aSI6IlhDcWxUTzRPbjV0dFRDam9zMGdjc2ciLCJpYXQiOjE0ODUyMjU1MjQsIm5iZiI6MTQ4NTIyNTQwNCwic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.rXawc5FPQbDioxUjanJU_ALuzlITGyxlkuRMAN6eC8e3iuEJcdJ-Cr7_B6UvI2mGoWSb_5A-nCuV1VHv-vn0VXGR5c4i3oBirn720ucufbHPHQ5egsLGkUVVGVQv9DKJ4tWLqcq5MM_2mpXfvirVLVPX3_qoN8V_Vmu4s0vYyixLU54DbU8KsGBNvUdk3tTm-LEjqTTahfOTtKaNQx8e-cncvOJw0nsnuPMZgqTTQSRaoX6VMfdOpTi-TXIugr9w6D5HJaF9_tnvwFg_bTSl2itY4ySKaH3Y-wDZCgkF1ZcNh9e8-W2h5NoeVh9CPEM1Mrnt62AtYJUeVa-fAXI9Og'),
 (386,'E47242067DECBFBF8B6144FED8E695D1','2017-01-24 10:40:02',NULL,'192.168.56.1','solomon_liu',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTYzMjk2MDIsImp0aSI6IkhTNFFBYVg0VXFVSzJvamJTUnRhc2ciLCJpYXQiOjE0ODUyMjU2MDIsIm5iZiI6MTQ4NTIyNTQ4Miwic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.XZQJP-TdxYdp2PCcnsfzWSAY-5EIIk_WWwJEEfRl9DXs3mVCM-yNeVA-Sfql5yZV0qVOEu8IktTIA14FxGyW7Ne3talS0ijtIoe9qj3hb_W3j3drGbBQnPlzpb18oy5In4zYGmuqb3ixxt5_lzVJI2fmA-a-A__4hvDw4ZHENVVJl0jSu5ZWEHezZGA2G2DSbmoPhf9rYA5SRrXoyxJvq-Q6H9pCO15Vb04ybZ2nRedOy502tFvxeJI5KMZyE4LEsiTXT14HLcmql6sMMCrc2gn_cQX4CQjie1FguizXOjpE05EVgqrV7DnZ9TE_GB32e5vz47KkT0QdpRFr80ZsBg'),
 (387,'7FD699103120834213E9B7FC3859AECC','2017-01-24 10:43:58',NULL,'192.168.56.1','solomon_liu',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTYzMjk4MzgsImp0aSI6Ikkwa3RpSEZ4a29DZEdUamRLY0lXZXciLCJpYXQiOjE0ODUyMjU4MzgsIm5iZiI6MTQ4NTIyNTcxOCwic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.Mf-aQNgeDNX-yWGDIqoDx975f5Ecwatpudaoc4pgtwwwiqVwCwAaTyRKDc2bOvKZaCSJ35WDYDoSCmMETLDESFtUV7P25ca2-izMD3Ej0pEAJZCKRidnmAfTnGeP5txucM4-qemLCSHeejpglHlEqA5v6e67SzM6gciI_buVN--4bzIfrLvx9WG08C40apWrqrBkab4irzJW_RQVqQ2rrskpGijU9qPBNblHWTAwEaA1XOdWBhIv-t82kpA3TVKlphW35AWVRKfH_2Sm4w5DNIf6l1SXvtxjSMMET3a33WVBUCQPnRSUtIX2gAovFy_FLm9rcgeJYoYFdMznpFibYA'),
 (388,'08D281EDCEE424C1DBF83CC0FBD127F5','2017-01-24 10:56:00','2017-01-24 11:29:47','192.168.56.1','solomon_liu',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTYzMzA1NjAsImp0aSI6ImtTR0NST0N2dU1JeUNIVG1ibFJ0QkEiLCJpYXQiOjE0ODUyMjY1NjAsIm5iZiI6MTQ4NTIyNjQ0MCwic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.i7bfJcVtM9StOP_4ZFwLbfOXnynS_gikld8D31zakRLtp3zdLP56P4no6LSZ2yvkhle_Al7OktSj8PByikil4WohU4GQV7SLew5RI76jDWOxQb0gj-DDH-nY-M22hlvM6OZBLhvxwRaFmL8eYp6xXeqr5KubYych6oGfdCu2DSkwFD1uvwFgeiOJmw3HsHYm6IJns0SdAernH-vcNXPh0owoTfgGdv4WLop8ZYODA-pv1RYLNCZDkfhtkavt1_-hK1IB0eK-9yWF9KjO2HmDC_jQJILBRN-PuqCnZl-vWTAWxUr-ST7nS_ZA8WakakCx8dhqE1gn2ejCpYOAv4tUBQ'),
 (389,'56D750D72E1E12576333DC66A263EF2F','2017-01-24 13:30:41','2017-01-24 14:01:26','192.168.56.1','solomon_liu',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTYzMzk4NDEsImp0aSI6Ik1JQUN6Q1k2VGYzZFJaRFNvZUJDMkEiLCJpYXQiOjE0ODUyMzU4NDEsIm5iZiI6MTQ4NTIzNTcyMSwic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.GAgL93d_VsWX7iM0gkJKah8BHy2f88zDnKEWfmMS3kk4KxIE5wU6Mpr9RhklU51tjrDlxS8Mbvj7eJEcW0sNDyotHebmgCO6tm22e3cw0D9eeNaL3k_Wm0HF1TTKYOwmDC-GMiBWfHSCAlWdWSgV3zJYIA0Mrl5JjSK_18fOnPVNBlK4uhkjsz2Zh3sl4aw0n-R0FQy25TFHvt2eo2qFAo0eblndkPdTDGcggJu-NgeY_OelBUhxt14hOUgfAl54UABhksO-MPpo3YM24oCd_vTjdEReHED1tUOfxb-YlbLRdU9WqrbkJnHb4aHgtbKd6VXdiEIDUd7uw5rbtutzhw'),
 (390,'3D2193204189FDFAB9E44D9F06A86A41','2017-01-24 14:06:27',NULL,'192.168.56.1','solomon_liu',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTYzNDE5ODcsImp0aSI6InFISXRmRVQ5MlJEYTVPbkdJcDdLMHciLCJpYXQiOjE0ODUyMzc5ODcsIm5iZiI6MTQ4NTIzNzg2Nywic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.Ha5tLcgLUx7dvhk4Tlh_MBVvwOOZFvVhTlL7qSxczIczP1LlfX3krpuvvxSWA0wEwiEKZJ2W0xupW1rsEuFLyH2pIwDUavfEtRp91-cuvkmhls5-AmMYXBRVBrKQ9I2AvHdlLhNfJipmpy_UpedJMZIYIBaIrUvBC_7NegyONQkcdNa6iKOVF8NrpnyyeaS4BEXCHdQWQANnxEA2uEP39cmE3Nsbl9Z26aiO7X0TsGdBgxClNbL7pgYqFjhJSSqybX1_HoAHDZ4wfGDCuDji3UIIubNT1dxZ84iMvXYn-bWu4q0f5bjp7OtxgBE0KmxeVrbBSxrOP72XXsxXT2NAtw'),
 (391,'70D479F53021404E8B1CDFA0BE5B237B','2017-01-24 15:12:52',NULL,'192.168.56.1','solomon_liu',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTYzNDU5NzIsImp0aSI6InhxN2pGY2oybWxwYXplNHY0U19Lb2ciLCJpYXQiOjE0ODUyNDE5NzIsIm5iZiI6MTQ4NTI0MTg1Miwic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.O2RSxuZGE8To1TJPJM3DeMdZ_TlsoQSYlyrthpzA6f2PNjzYTINLSCOdJszLHnk6fr5d4ByLHttiH02I5a9djNeyly4Mg6GZ9ofnbidI4buS7rg-RT7i13zVXE5Gt3BcWmKv3QX8s3cwPSGuYF9_x8FhNDn14pI2PWq7FvDQ7Ual5V4EdjrGbhBINErK3xKXq97xGQXFOWgKqrlr3n2JI7SXjGEj9cCmKtwcjrwLeTZP-RIKelCEQwa4l_h2VEpnl7WeJ8Uxd97QF79yiRJPU2tAhYjQbjybcDQMl-h1YoyHzt3QM8yBtCYITBM17cOzjAnv28ibjuIaixggmuPEnA'),
 (392,'5AD87BD6AE501BFFF4A31470F3A14201','2017-01-24 15:31:17',NULL,'192.168.56.1','solomon_liu',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTYzNDcwNzcsImp0aSI6Ind5aU9VOTdUanVqcWVZUUxZRlI3UFEiLCJpYXQiOjE0ODUyNDMwNzcsIm5iZiI6MTQ4NTI0Mjk1Nywic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.sKvLNa6QVhSJdsOKPz1piccI6MCa5RHCrn3SkhUV57PT5k_1IpLMrorSAN86RHmb-vy4mLsD0oGOqOLQT7bw_-nWrmmNaKB8ktoqPcEOLYqWXrWT-l4nUVSSml27nbIuR_S2ZSn7QWOBzQAoKIU1HSSVYj8b3b1_zLIQWd4ers1rlA6UyRaDI9J_njBqGyHIPb-OJx_rTIo6iJB2Xypdx4wHIK0LLaU74kkhp6Ytw7hHSpB2VCR8UnchimElcgvrv9gUt118qnqg6-NyqymFAz3wuMcMQt9oyyLCt9vRpcX3eKoFcpj1QWeeGbvBOPJ7wJUUE1VKrN9NiCv_4_Ftgw'),
 (393,'455C597FBC560FB921F67303A9B50E31','2017-01-24 15:33:33',NULL,'192.168.56.1','solomon_liu',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTYzNDcyMTMsImp0aSI6IjNCRUJYWXk1N0J2aGRVaDhRZnpGcHciLCJpYXQiOjE0ODUyNDMyMTMsIm5iZiI6MTQ4NTI0MzA5Mywic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.VOiH_LSzGTTM_N_3Gq6Lp9aIyUIKJxydOQEDwq3DoeLpyaLjOAu-8vFKBKkbEsfkPvSxjnFAwWlm69fXT5nkzClyWuHd2r28ddZnDX3xm-TyIwB6oRNYMyG04GrFI6qeFGsylqEqqt-pPJIBLIzlb1XGQXAqTlwWZt3DEifnguS_pd0CTe3i2ah2cSNoxic2M12LOBsj6XO12ijUX-nIPuVDny-wZtgjcmgAOfGvqzu6fEiSHkvemEQ0qd5xJKsR0kRNeOThRCo7rJUNrSLXQtIZ1eOqZ05WYYjL298cFcBgKinApTY7CFThclzya5qQABnAIgGwYkZHjCcf0_Dguw'),
 (394,'430D1D27D4EA11E67D053279DE2DC4CD','2017-01-24 15:34:56',NULL,'192.168.56.1','solomon_liu',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTYzNDcyOTYsImp0aSI6ImpuSDhDMUZaSS02bUU4ZUdoX1RHMkEiLCJpYXQiOjE0ODUyNDMyOTYsIm5iZiI6MTQ4NTI0MzE3Niwic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.DEMD_0DmlAbUPD3T-Rze0NrcM6XtfHZzwIHbsjf0gUi-VN71qiTWRfVILSrghLUZS-XWJfasJcGrXwZ3gpmPFKh9wPuscifrR0pLr_wxppTZrApTKqpnjUH4LrtdvkzWCQh_P964-yhgD6DJu9uC9wcKktuMAW8AIszCESSQbsKEOZynokIbfYZAtnN2InRFyH8agfelQI4h9YOoGrEezb8PGAjINA2BNG-FogbJc5ZnLwZOGdLLl0vG4KSR0FPqdUQJlXKQyFkW5hojWTMGi3ueQzwubrihxxJ8ni0rOzV3kbKB8fEvmrHab4GetFXGjjupSSqCEBCT8VVO7-a1KA'),
 (395,'BC57D78C04B1A9380F9118BF2BE5F8DB','2017-01-24 23:54:24',NULL,'10.0.0.2','Gigabyte-PC',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTYzNzcyNjQsImp0aSI6InFFMTNTWUw5TW1GNlB4c1p6TlF0UVEiLCJpYXQiOjE0ODUyNzMyNjQsIm5iZiI6MTQ4NTI3MzE0NCwic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.Zq8vgjYva-SasCjujYK0nbj_WLWaZ_MrHBWhoBqPRb2oXIozPZ57UBXK5U7KBitB3zhI8z697qslJy7wuTrY_aZIldlPTBTD-NPcBmepWGUXscR7gmVvsgjDqx_oQ8zIS05-3zZTdYFiwqqVbQEQP5Ks7MQOX34U13TyEiUgrjV7WdlBys__O5QTrzo0TJ09DVTXOaOouwtykzNrxEjVBUzGW6f1bj8-qXEYQwLFlWB5wslKpn2RfYbLW9TC8YmotEyuk3p3R4noUcyLj8pOwo_0zrcZT8EC3JA4fAAzPngNKlDCTp_1lg23MExTNk0p1W6u-KMi26BNqUKS8c9_ng'),
 (396,'D933F0C44CAE79E46542FDC1646ECA93','2017-02-12 09:28:06',NULL,'10.0.0.2','Gigabyte-PC',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTc5NjY4ODYsImp0aSI6Ii1zdmxDLTBBSC1UMml5czlRNXBrOXciLCJpYXQiOjE0ODY4NjI4ODYsIm5iZiI6MTQ4Njg2Mjc2Niwic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.ZriXIVZIB83fN7XfXLQw501HeLOVOovBqUP4yjeZ2GniGMMI98FIpGJ3Eye1AlVE_CBQ4ObSX0ilvF_WW62ZjpRyTLSUMcCTtCKMpP__34b-sdWJAQ7qfjblbg12oH-l7S0nCwJHVAYJh1Qo9ie26TIcqez9GnBExswr6ss-AzprMKSe8yZMel0Ke8XKYNZZQ9ntbS5NSFkxD155wV6cqzm0j5rgrisS-o05gFLFs8Qw_dIAE9qQtKNpdjQtYrwxajqU_1-9bb7h7xp_TIQB9IH-eN3ktquXkIK2Fvgc3iN4BEWIoCs-ipz_uCTyqmQn62NKcZj6i-uRwPZAIW4JQw'),
 (397,'3FA923B0E0B3F0F7A8B6012FA45DF924','2017-02-12 09:31:15','2017-02-12 10:04:11','10.0.0.2','Gigabyte-PC',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTc5NjcwNzUsImp0aSI6Im9fTU9KM2tiZDJVazlqYVFNSmJSaVEiLCJpYXQiOjE0ODY4NjMwNzUsIm5iZiI6MTQ4Njg2Mjk1NSwic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.iCMSoEXUU_NJMmgiffARZ4j1Bg1eM9NQ3tkdUrzV4X90hvehBcKIZMbellIJnv_PBJ_Voqcwwji1LQGisR8-SuAn3VU4S-tyixZyKy4rFnR9LDzmGrfd6HQaQu3Gxm2pT7wVGWSIDXUIkxrb4UUVUCQafXf0CmvEk9U8vX7uI9QHyLcuqJpS81g8RjWBQL3VkKPWtHKUQ-DC6VHXh7hvwb9eltv--tTvTi161LIjfIFANfp2DjbWgW3lJGA94XcL9XyjSTTBPufjm_whX0vHQKJsf3e12OtqdaTqeLswpVxgjCUbtYvrX4TuHHfoEa3KVmjc0cVvS5XjKMNCApWisQ'),
 (398,'CDFFBBA1AB29E7B6BA19A57F31241B90','2017-02-12 22:56:08',NULL,'192.168.1.103','Gigabyte-PC',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTgwMTUzNjgsImp0aSI6IlRTVnJTYlRBd3B5Y3JmV2M3Zno1a2ciLCJpYXQiOjE0ODY5MTEzNjgsIm5iZiI6MTQ4NjkxMTI0OCwic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.Kwsy8L_qyFqc8cUrLSif_r6Estk2KUiWDmG78vJ-ZNZH-lXg05KqCeFeJqQvlITEJFOQ4G9CRJY3VXo5AmVi8h58flr3_pNClfRfnK7fRFNcUVapwJ11YUebgSlzcO4SMocixaC8npm4JW0AsCif3gS_yeKPlmT7SJI6dKE8vRO9eRSgP8Rely2i12nVuIUPzz4Eh0e4L4XlPiAITAzDpshpPKhtn25Rs-QBRWd_KDPByXmvjiIDVlX3wsjoguZIgiXosfTJ2-7rTHFracN-dCCvCq33Rixm9yB03xIJDfXVybhabY-SqcD3IhWWbF83hov00F8NOJPkmeq7Rqxn2w'),
 (399,'A88D6CEF514CD190D6B22D10C41F26EC','2017-02-12 23:04:38',NULL,'192.168.1.103','Gigabyte-PC',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTgwMTU4NzgsImp0aSI6IklrZ1hqWXozeF90Y0ZrUkhDejgwR2ciLCJpYXQiOjE0ODY5MTE4NzgsIm5iZiI6MTQ4NjkxMTc1OCwic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.ZVWlUIDzJcH0IPx4n9R0aUinCrOsqJkdZfCP7X6lYQNuvFnBCXpQE9PAOwuqz2_IulpDwI_BlpJjmsJpunwDY1036llTL6LcY5AukIPf5j9PIVT22-6ZsgfbXPOtydDKiFvVXt9XGuu-PTsN_gRkSbvI4K__8fZwytsmm1HHfFc8VW9rZsmzz38Z950IJo4ZylqujiCITmha2g17Gjoim-fywXvcw5C-eARg48ILexrBY0fFBl6CWT721bGlUeNI5kRLOvfUL8UYwgLQ9NbYEIYxWzaiVv5If50nG33beBCQspwIoTSs0D4cEcck-Z4rivO45KAIy1VQSab-HszbrA'),
 (400,'9881667843B3C917E1CFDB788A7938F4','2017-02-15 21:12:00',NULL,'192.168.1.103','Gigabyte-PC',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTgyNjgzMjAsImp0aSI6IkxXVGhmd3ZaNjB1dTVvVUdkdThROGciLCJpYXQiOjE0ODcxNjQzMjAsIm5iZiI6MTQ4NzE2NDIwMCwic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.NWXZJheoUvqC3s4C6zgM3KbEdxMXn6B94147iKKBgjY9L_N8kk9PWcA-TwxYHU4wSs3TM4vUpHtONMP_3Ryb977KdRvsn6GQSQrkBnCaqtKHt7XXzYcCetd_pGKrLEd3Q3AZBsxZtqeHngmsNf3RTJ_mFucJVXQpAtjMZkGQxenaYezl9hpR0fSbCRbT6Y-YUJWsXPMjA4TUU7x4hHPwWiRS0E9fOS8ySCpgM33pKz7p00aDg66NHPNRhM5ybl7kKtbs_sMQ3zBWya9-UUIsBrGISVWPVfOwNokVlB2IU7ipSRm1swBGpHuiV7smGy_xSgkF21xYgICwmQTj-Ycs7A'),
 (401,'E3412FD4C10312D7E88098D59F79A503','2017-02-16 00:06:41',NULL,'192.168.1.103','Gigabyte-PC',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTgyNzg4MDEsImp0aSI6ImFKZFl4WlhLZG1BdGpsbl9yZDJGeGciLCJpYXQiOjE0ODcxNzQ4MDEsIm5iZiI6MTQ4NzE3NDY4MSwic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.ZU5Q9HOaAS-dVtHBJfrUk4yZ6WjB1jVOxA5h1m9eti6A0AQnO_0EdMStOAhxpZwNcnpjGPnajOiqPfUsqEanl_PNUJWw2VKJtf0LvPpdP4aEE24fXV4AoszcgYH707uk1kzhCBz120K5urueznvIqz5ZIRE3yiZqkwS2MX9oAKfzgZourLGvy2khC7VyXJ7wAZHcmlvhi_CgkrGKGTFAwPkf7XjJKd5tceIMvbUmq08rtiKDrEYRc9HKOMnR0qNdtyUao3miFYupieb8Tn4NJqTnCvyIlaChPjoovjNrFNeR7_yOXSjA-CDeMFjOt8I86yV60hVsj_-Es6C7sICGhw'),
 (402,'F49BFF0ABE8565C7D1D2F955C5BB75AB','2017-02-16 23:06:31',NULL,'192.168.1.103','Gigabyte-PC',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTgzNjE1OTEsImp0aSI6ImlHdHNydjFGZGpPNm10NllxSm1Pa3ciLCJpYXQiOjE0ODcyNTc1OTEsIm5iZiI6MTQ4NzI1NzQ3MSwic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.YPZNBShAqMCPmaTFpYmdON9Jev1z_EMaSzvvjB4StDKLbdT7oxUch5tyMGGEfCWYFjz06UOwqBrptSQWfQFHJcW36-gKPM0AqSI93OzOdUgW27X9ifSK2jXORuvTpEBJoXRyp091vOc87ZiuSGR1azNYgimjwSZAuLPL97XigUPf-Fe7MYUM1Is9KsEWm81Fp60CoPNOS7cIBKoSS5HCamXdczydqRa4lZ8Nd8GSCoC7_Vg8WUlOmsSgTVSL_Yo_4nwnP93qQxsM8PjdTfhbEnHgaJgE6m_YGbbQlpH2lxNvl8ObFtxb0VZJ5alkTeUijlAheb8WqxHGB-bpclQX9g'),
 (403,'F49BFF0ABE8565C7D1D2F955C5BB75AB','2017-02-16 23:11:18',NULL,'192.168.1.103','Gigabyte-PC',1,'eyJraWQiOiJjaGluQWRFbG1hcjcjQDEwZiIsImFsZyI6IlJTMjU2In0.eyJpc3MiOiJlaWRlYSIsImF1ZCI6ImRzZGwiLCJleHAiOjE1MTgzNjE4NzgsImp0aSI6IkxubjhBRUo3Y1FMVExDTG5oOEd4b1EiLCJpYXQiOjE0ODcyNTc4NzgsIm5iZiI6MTQ4NzI1Nzc1OCwic3ViIjoiU2VjdXJpdHkiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW5pc3RyYXRvciIsIm5hbWUiOiLnrqHnkIblkZgifQ.ORiZGZbh7HqhEv8cS9Bm7wY-7hBptlxKX3pBG_HR9ttuvBTC1zi1pmMUJei5ikvWGUoaPw293-PB1_bhdNz97ek7UtYEh4P2VnWfRxkZxr4w5RG-ZIx0j4IjYhsquXOlUew70d3YTVeB1_m-SR06B93hp40jKuichY4ZML6tVoRptO2LWlKLYoOY3Wdrw-nG8fmyEmt0oCYOm4qLSjpBAs2bd3nOLweoSJzoXvHXgnF7O1_q3zRt3w9J457OirhshKo9FEAXw5FYjqSLgPFzGntwlgYaXUSx1QhDCPWeshrUQMpBWwp3T0lB7eSQDy9OIbFwDCfa8qmSVEx4DrYiaQ');
/*!40000 ALTER TABLE `sys_user_session` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
