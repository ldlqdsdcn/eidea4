<%--
  Created by IntelliJ IDEA.
  User: 车东明
  Date: 2017/4/24
  Time: 11:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/inc/inc_ang_js_css.jsp" %>
<%@include file="/inc/highcharts_js.jsp" %>
<html>
<head>
    <title>3d饼图2</title>
</head>
<body>
<div class="page-header">
    <a href="<c:url value="/highcharts/columncharts3d.jsp"/>" class="btn  btn-primary btn-sm">柱状图</a>
    <a href="<c:url value="/highcharts/linecharts.jsp"/>" class="btn  btn-primary btn-sm">线性图</a>
    <a href="<c:url value="/highcharts/piecharts3d.jsp"/>" class="btn  btn-primary btn-sm">饼状图数据下钻</a>
    <a href="<c:url value="/highcharts/highchartpie.jsp"/>" class="btn  btn-primary btn-sm">饼状图</a>
</div>
<div ng-app='myModule' ng-controller="myController">
    <hc-column-chart title="苹果数量" subtitle="DSDL" data="columnData"style="width: 100%;height: 50%">piechart</hc-column-chart>
</div>
<script type="text/javascript">
    var app = angular.module('myModule', [])
        .directive('hcColumnChart', function () {
            return {
                restrict: 'E',
                template: '<div></div>',
                scope: {
                    title: '@',
                    subtitle: '@',
                    data: '='
                },
                link: function (scope, element) {
                    Highcharts.chart(element[0], {
                        chart: {
                            type: 'column',
                            options3d: {
                                enabled: true,
                                alpha: 15,
                                beta: 5,
                                depth: 50,
                                viewDistance: 25
                            }
                        },
                        title: {
                            text: scope.title
                        },
                        subtitle: {
                            text: scope.subtitle
                        },
                        xAxis: {
                            categories: Highcharts.getOptions().lang.months
                        },
                        yAxis: {
                            title: {
                                text: '数量',
                                align: 'high',
                                rotation: '0'
                            }
                        },
                        plotOptions: {
                            column: {
                                depth: 15
                            }
                        },
                        series: scope.data
                    });
                }
            }
        });
    app.controller('myController', function ($scope) {
        $scope.columnData = [{
            name: '小张',
            data: [5, 3, 4, 7, 2, 2, 3, 4, 5, 6, 1, 2],
            stack: 'XZ'
        }, {
            name: '小王',
            data: [3, 4, 4, 2, 5, 2, 3, 4, 5, 6, 1, 2],
            stack: 'XW'
        }, {
            name: '小彭',
            data: [2, 5, 6, 2, 1, 2, 3, 4, 5, 6, 1, 2],
            stack: 'XPE'
        }, {
            name: '小潘',
            data: [3, 0, 4, 4, 3, 2, 3, 4, 5, 6, 1, 2],
            stack: 'XP'
        }]
    });

</script>
</body>
</html>

