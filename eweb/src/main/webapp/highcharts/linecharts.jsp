<%--
  Created by IntelliJ IDEA.
  User: 车东明
  Date: 2017/4/19
  Time: 10:15
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
    <a href="http://localhost:8080/eweb/highcharts/columnchart3d.jsp" class="btn  btn-primary btn-sm">柱状图2<%--新建--%></a>
    <a href="http://localhost:8080/eweb/highcharts/piecharts3d.jsp" class="btn  btn-primary btn-sm">饼状图数据下钻<%--新建--%></a>
    <a href="http://localhost:8080/eweb/highcharts/highchartpie.jsp" class="btn  btn-primary btn-sm">饼状图<%--新建--%></a>
</div>
<div ng-app='myModule' ng-controller="myController">
    <hc-line-chart title="聊天市场占有率" subtitle="青岛" data="lineData">piechart</hc-line-chart>
</div>
<script type="text/javascript">
    var app = angular.module('myModule', [])
        .directive('hcLineChart', function () {
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
                            zoomType:'x'
                        },
                        title: {
                            text: scope.title
                        },
                        subtitle: {
                            text: scope.subtitle
                        },
                        xAxis: {
                            //十字准星线
                            crosshair:{
                                color:'#c6c6cc',
                                snap:true,
                                width:1
                            },
                            categories: Highcharts.getOptions().lang.months
                        },
                        yAxis: {
                            crosshair:{
                                color:'#9f9ccc'
                            },
                            title: {
                                text: '温度 (°C)',
                                align:'high',
                                rotation:'0'
                            }, plotLines: [{
                                value: 0,
                                width: 1,
                                color: '#cf2e7d'
                            }]
                        },
                        tooltip: {
                            valueSuffix: '°C'
                        },
                        legend: {
                            layout: 'vertical',
                            align: 'top',
                            verticalAlign: 'middle',
                            borderWidth: 0
                        },
                        plotOptions:{

                            area:{
                                fillColor: {
                                    linearGradient: {
                                        x1: 0,
                                        y1: 0,
                                        x2: 0,
                                        y2: 1
                                    },
                                    stops: [
                                        [0, Highcharts.getOptions().colors[0]],
                                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('#cccccc')]
                                    ]
                                }
                            },
                            series: {
                                cursor:'pointer',
                                point: {
                                    events: {
                                        click: function (event) {
                                            $('.message').html(this.series.name + "平均气温：" + this.y);
                                        }
                                    }
                                }
                            }
                        },
                        series:scope.data
                    });
                }
            }
        });
    app.controller('myController', function ($scope) {
        $scope.lineData =  [{
            name: '青岛',
            data: [-3, -2, 2, 8, 13, 18, 25.5, 27.4, 22, 15.6, 9.5, 3.0]
        },{
            name: '东京',
            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
        }, {
            name: '纽约',
            data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
        }, {
            name: '柏林',
            data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
        }, {
            name: '伦敦',
            data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
        }]
    });


</script>
</body>
</html>
