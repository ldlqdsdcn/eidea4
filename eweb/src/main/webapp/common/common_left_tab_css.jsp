<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<style type="text/css">
    .vertical-tab {
        width: 8%;
        height: 100%;
        float: left;
        overflow: hidden;
        background-color: #EDEDED;
    }

    .vertical-tab > li {
        text-align: center;
    }

    .vertical-tab > li.active > a, .vertical-tab > li.active > a:focus, .vertical-tab > li.active > a:hover {
        border: solid #ccc;
        border-width: 1px 0px 1px 1px;
        background-color: #ffffff;
        border-right: 1px solid #ffffff;
        z-index: 2;
    }

    .vertical-tab > li > a {
        border-radius: 4px 0px 0px 4px;
        padding: 6px 15px;
        color:#000000;
    }

    .vertical-tab-content {
        float: left;
        width: 91%;
        height: 100%;
        padding: 5px;
        margin-left: -1px;
        margin-bottom: 2px;
        border-radius: 4px 4px 4px 4px;
        border: solid 1px #ccc;
        color: #666;
    }

    .tab-not-active{
        background-color:#AAAAAA;
    }



</style>