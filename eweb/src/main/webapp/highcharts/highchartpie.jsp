<%--
  Created by IntelliJ IDEA.
  User: 车东明
  Date: 2017/4/24
  Time: 8:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/inc/inc_ang_js_css.jsp" %>
<html>
<head>
    <title>3d饼图2</title>
</head>
<body>
<div class="page-header">
    <a href="http://localhost:8080/eweb/highcharts/columncharts3d.jsp" class="btn  btn-primary btn-sm" >柱状图<%--新建--%></a>
    <a href="http://localhost:8080/eweb/highcharts/linecharts.jsp" class="btn  btn-primary btn-sm">线性图<%--新建--%></a>
    <a href="http://localhost:8080/eweb/highcharts/piecharts3d.jsp" class="btn  btn-primary btn-sm">饼状图数据下钻<%--新建--%></a>
    <a href="http://localhost:8080/eweb/highcharts/columnchart3d.jsp" class="btn  btn-primary btn-sm">柱状图2<%--新建--%></a>
</div>
<div ng-app='myModule' ng-controller="myController">
    <hc-pie-chart title="各地每月平均气温" subtitle="DSDL" data="pieData">piechart</hc-pie-chart>
</div>
<script type="text/javascript">
    var app = angular.module('myModule', [])
        .directive('hcPieChart', function () {
            return {
                restrict: 'E',
                template: '<div></div>',
                scope: {
                    title: '@',
                    subtitle:'@',
                    data: '='
                },
                link: function (scope, element) {
                    Highcharts.chart(element[0], {
                        chart: {
                            type: 'pie',
                            options3d: {
                                enabled: true,
                                alpha: 45,
                                beta: 0
                            }
                        },
                        title: {
                            text: scope.title
                        },
                        subtitle: {
                            text: scope.subtitle
                        },
                        tooltip: {
                            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                        },
                        plotOptions: {
                            pie: {
                                allowPointSelect: true,
                                cursor: 'pointer',
                                depth: 35,
                                dataLabels: {
                                    enabled: true,
                                    format: '{point.name}'
                                }
                            }
                        },
                        series: [{
                            name: '浏览器市场占有率',
                            data: scope.data
                        }]
                    });
                }
            }
        });
    app.controller('myController', function ($scope) {
        $scope.pieData = [{
            name: 'Microsoft Internet Explorer',
            y: 56.33
        }, {
            name: "Chrome",
            y: 24.03,
            sliced: true,
            selected: true
        }, {
            name: "Firefox",
            y: 10.38
        }, {
            name: "Safari",
            y: 4.77
        }, {
            name: "Opera",
            y: 0.91
        }, {
            name: "Proprietary or Undetectable",
            y: 0.2
        }]
    });


</script>
</body>
</html>
