/**
 * Created by 刘大磊 on 2016/12/28.
 */

var PrivilegeService = {
    privileges: $("script[data-privileges]").attr('data-privileges'),
    hasPrivilege: function (opeartor) {
        return true;
        // var privileges = JSON.parse(PrivilegeService.privileges);
        // for (var i = 0; i < privileges.length; i++) {
        //     if (opeartor == privileges[i]) {
        //         return true;
        //     }
        // }
        // return false;
    }
};


