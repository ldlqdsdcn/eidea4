function StringBuffer() {
    this.__strings__ = new Array();
}
StringBuffer.prototype.append = function (str) {
    this.__strings__.push(str);
    return this;    //方便链式操作
}
StringBuffer.prototype.toString = function () {
    return this.__strings__.join("");
}
function postFormTimeout(second,theFunc)
{
    setTimeout(theFunc,second*1000);
}
Date.prototype.format=function(fmt) {
    var o = {
        "M+" : this.getMonth()+1, //月份
        "d+" : this.getDate(), //日
        "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时
        "H+" : this.getHours(), //小时
        "m+" : this.getMinutes(), //分
        "s+" : this.getSeconds(), //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S" : this.getMilliseconds() //毫秒
    };
    var week = {
        "0" : "\u65e5",
        "1" : "\u4e00",
        "2" : "\u4e8c",
        "3" : "\u4e09",
        "4" : "\u56db",
        "5" : "\u4e94",
        "6" : "\u516d"
    };
    if(/(y+)/.test(fmt)){
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    if(/(E+)/.test(fmt)){
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "\u661f\u671f" : "\u5468") : "")+week[this.getDay()+""]);
    }
    for(var k in o){
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
}
function formatLongDate(time,pattern) {
    if(time==null)
    {
        return "";
    }
    var date=	new Date();
    date.setTime(time);
    return date.format(pattern);
}
(function($){
    $.fn.serializeJson=function(serializeObj){
        if(serializeObj==null)
            serializeObj={};
        $(this.serializeArray()).each(function(){
            serializeObj[this.name]=this.value;
        });
        return serializeObj;
    };
})(jQuery);
/**
 * 简单Set集合类实现
 * @author 刘大磊
 * @constructor
 */
function ISet() {
    this._set_=new Array();
    this._error_set_=new Array();
}
ISet.prototype.add=function (item,hasError) {
    for(var i=0;i<this._set_.length;i++)
    {
        if(this._set_[i]==item)
        {
            return;
        }

    }
    this._set_.push(item);
}
ISet.prototype.toArray=function()
{
    return this._set_;
}
ISet.prototype.showErrorsStyle=function () {
    $(".has-error.has-feedback").removeClass("has-error has-feedback");
    for(var i=0;i<this._set_.length;i++)
    {
        $('#'+this._set_[i]).focus();
        break;
    }
    for(var i=0;i<this._set_.length;i++)
    {
        $('#'+this._set_[i]).parents('.form-group').addClass("has-error has-feedback");
    }
}