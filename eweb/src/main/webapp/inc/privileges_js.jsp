<%--
  Created by IntelliJ IDEA.
  User: 刘大磊
  Date: 2017/1/5
  Time: 10:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
app.service('PrivilegeService', function () {
this.hasPrivilege = function (opeartor) {
var privileges =${pagePrivileges};
for (var i = 0; i < privileges.length; i++) {
if (opeartor == privileges[i]) {
return true;
}
}
return false;
}
});
