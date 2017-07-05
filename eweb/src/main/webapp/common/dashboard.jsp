<%--
  Created by 刘大磊.
  User: 刘大磊
  Date: 2017/6/30
  Time: 10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/inc/inc_ang_js_css.jsp" %>
<%@include file="/inc/highcharts_js.jsp" %>
<html>
<head>
    <title>dashboard</title>
</head>
<body>

<div class="container-fluid" ng-app='dashboardApp'>
    <div class="col-sm-12"   id="totalChart" style="min-width: 500px;	max-width: 1200px;	height: 400px;	margin: 0 auto"></div>
    <div class="col-sm-6"   id="pieChart" style="min-width: 310px;	max-width: 700px;	height: 400px;	margin: 0 auto">
    </div>
<div class="col-sm-6"   id="lineChart" style="min-width: 310px;	max-width: 700px;	height: 400px;	margin: 0 auto">
</div>
    <div ng-controller="myController"  class="col-sm-12">
        <hc-column-chart title="各地区新房源" subtitle="房产数据" data="columnData" style="width: 1200px;	height: 400px;	margin: 0 auto" >各地区新房源</hc-column-chart>
    </div>

</div>
<script type="text/javascript">
    var app = angular.module('dashboardApp', [])
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
            name: '市南',
            data: [parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000)],
            stack: '市南'
        }, {
            name: '市北',
            data: [parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000)],
            stack: '市北'
        }, {
            name: '崂山',
            data: [parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000)],
            stack: '崂山'
        }, {
            name: '黄岛',
            data: [parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000)],
            stack: '黄岛'
        },{
            name: '平度',
            data: [parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000)],
                stack: '平度'
        },{
            name: '即墨',
            data: [parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000)],
            stack: '即墨'
        },{
            name: '莱西',
            data: [parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000)],
            stack: '莱西'
        },{
            name: '城阳',
            data: [parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000)],
            stack: '城阳'
        },{
            name: '胶州',
            data: [parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000)],
            stack: '胶州'
        },{
            name: '胶南',
            data: [parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000),parseInt(Math.random()*1000)],
            stack: '胶南'
        }]
    });


    Highcharts.chart('lineChart', {
        chart: {
            type: 'line'
        },
        title: {
            text: '房价走势图'
        },
        subtitle: {
            text: '1年内房价走势'
        },
        xAxis: {
            categories: ['2016.07', '2016.08', '2016.09', '2016.10', '2016.11', '2016.12', '2017.01', '2017.02', '2017.03', '2017.04', '2017.05', '2017.06']
        },
        yAxis: {
            title: {
                text: '单价:元/平米'
            }
        },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true
                },
                enableMouseTracking: false
            }
        },
        series: [{
            name: '市北区',
            data: [20000, 23000, 23123, 26127, 27123, 22168, 30129, 21109, 18796, 16854, 18865, 17759]
        }, {
            name: '市南区',
            data: [23987, 24658, 26549, 25987, 22310, 18950, 19956, 17753, 19990, 29990, 39900, 30005]
        }]
    });
    Highcharts.chart('pieChart', {
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: '客户使用浏览器统计'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                }
            }
        },
        series: [{
            name: 'Brands',
            colorByPoint: true,
            data: [{
                name: '搜狗浏览器',
                y: 49.33
            }, {
                name: 'Chrome',
                y: 24.03,
                sliced: true,
                selected: true
            }, {
                name: 'Firefox（火狐）',
                y: 10.38
            }, {
                name: 'Safari（苹果）',
                y: 4.77
            }, {
                name: 'Opera',
                y: 0.91
            }, {
                name: '未知',
                y: 7.2
            }]
        }]
    });
    Highcharts.chart('totalChart', {
        title: {
            text: '房产综合统计'
        },
        xAxis: {
            categories: ['城阳','崂山', '市北', '市南', '李沧']
        },
        yAxis: {
            title: {
                text: '单价:元/平米'
            }
        },
        labels: {
            items: [{
                html: '总的房产购买量',
                style: {
                    left: '50px',
                    top: '18px',
                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'black'
                }
            }]
        },
        series: [{
            type: 'column',
            name: '4月',
            data: [11000,21300, 19100, 23100, 12600]
        }, {
            type: 'column',
            name: '5月',
            data: [8000,27300, 22100, 26100, 14600]
        }, {
            type: 'column',
            name: '六月',
            data: [9801,33300, 24100, 28100, 13600]
        }, {
            type: 'spline',
            name: '平均值',
            data: [9500,31000, 21298, 27192, 13102],
            marker: {
                lineWidth: 2,
                lineColor: Highcharts.getOptions().colors[3],
                fillColor: 'white'
            }
        }, {
            type: 'pie',
            name: '总销售量',
            data: [{
                name: '4月',
                y: 4590,
                color: Highcharts.getOptions().colors[0] // Jane's color
            }, {
                name: '5月',
                y: 5400,
                color: Highcharts.getOptions().colors[1] // John's color
            }, {
                name: '六月',
                y: 1900,
                color: Highcharts.getOptions().colors[2] // Joe's color
            }],
            center: [100, 80],
            size: 100,
            showInLegend: false,
            dataLabels: {
                enabled: false
            }
        }]
    });

</script>
</body>
</html>

