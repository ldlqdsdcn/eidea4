/**
 * Created by 刘大磊 on 2017/2/6.
 */
var eideaValidator={};
eideaValidator.trim=function (s) {
    return s.replace(/^\s*/, "").replace(/\s*$/, "");
}
eideaValidator.isEmpty=function(s) {
    if (this.trim(s).length == 0) {
        return true;
    } else {
        return false;
    }
}
eideaValidator.isNotEmpty=function (s)
{
    return !this.isEmpty(s);
}
eideaValidator.isEmptyInput=function (id)
{
    var input=document.getElementById(id);
    if(input==null) return true;
    return this.isEmpty(input.value);
}
eideaValidator.isEmptyCheckBox=function(checkBoxName)
{
    var checks=document.getElementsByName(checkBoxName);
    if(checks==null)return true;
    for(var i=0;i<checks.length;i++)
    {
        if(checks[i].checked==true)
        {
            return false;
        }
    }
    return true;
}
eideaValidator.isDouble=function (str) {
    var doublePat = /^[-+]?d+(.d+)?$/;
    var matchArray = str.match(doublePat);
    if (matchArray != null) {
        return true;
    } else {
        return false;
    }
}
eideaValidator.isFloat=function (fieldValue) {
    var isdot;
    isdot = 0;
    if (fieldValue == '') {
        return true;
    } else {
        tmp = fieldValue;
        for (j = 0; j < tmp.length; j++) {
            // alert(tmp.substring(j,j+1));
            if (isNaN(parseInt(tmp.substring(j, j + 1)))) {
                if (j == 0 && tmp.substring(j, j + 1) == "-") {
                } else if ((isdot == 0) && (tmp.substring(j, j + 1) == ".")) {
                    isdot = 1;
                } else {
                    return false;
                }
            }
        }
    }
    return true;
}
eideaValidator.startWith=function (value,str){
    var reg=new RegExp("^"+str);
    return reg.test(value);
}
eideaValidator.endWith=function(value,str){
    var reg=new RegExp(str+"$");
    return reg.test(value);
}
eideaValidator.isInt=function (fieldValue) {
    var reg = new RegExp("^[0-9]*$");
    if(!reg.test(fieldValue)){
        return false;
    }
    return true;
}
eideaValidator.validateDate=
function (value) {
    if (isEmpty(value))
        return false;
    re = /^(0[1-9]|1[0-9]|2[0-9]|3[0-1])\/(0[1-9]|1[0-2])\/(19[0-9]{2}|20[0-9]{2})$/;

    if (value.match(re) != null)
        return false;
    else
        return true;
}
//TODO 日期时间格式暂时不做验证
eideaValidator.validateDateTime=
function (str){
    return true;
}
eideaValidator.selectAll=
function (checkboxName,check)
{
    var checkrows=document.getElementsByName(checkboxName);

    for(var i=0;i<checkrows.length;i++)
    {
        checkrows[i].checked=check.checked;
    }
}
eideaValidator.ismail=
function (mail)
{
    return(new RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).test(mail));
}
eideaValidator.confirmDelete=
function(){
    return confirm("确认删除吗？");

}
eideaValidator.isEmptyCheckBox=
function (checkboxName)
{
    var checkrows=document.getElementsByName(checkboxName);
    if(checkrows==null) return true;
    var candelete=true;
    for(var i=0;i<checkrows.length;i++)
    {
        if(checkrows[i].checked==true)
        {
            candelete=false;
            break;
        }
    }
    return candelete;
}
eideaValidator.confirmListDelete=
function (checkboxName)
{
    var checkrows=document.getElementsByName(checkboxName);
    var candelete=false;
    for(var i=0;i<checkrows.length;i++)
    {
        if(checkrows[i].checked==true)
        {
            candelete=true;
            break;
        }
    }
    if(!candelete)
    {
        alert('请先选择数据，然后删除！');
        //alert('Please select the data, and then delete!');
        return false;
    }
    return confirm("确认删除选择数据吗？");
}