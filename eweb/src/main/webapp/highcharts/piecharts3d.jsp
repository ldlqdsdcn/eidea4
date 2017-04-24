<%--
  Created by IntelliJ IDEA.
  User: 车东明
  Date: 2017/4/19
  Time: 8:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/inc/inc_ang_js_css.jsp" %>
<html>
<head>
    <title>3d饼图</title>
</head>
<body>
<div class="page-header">
    <a href="http://localhost:8080/eweb/highcharts/columncharts3d.jsp" class="btn  btn-primary btn-sm" >柱状图<%--新建--%></a>
    <a href="http://localhost:8080/eweb/highcharts/linecharts.jsp" class="btn  btn-primary btn-sm">线性图<%--新建--%></a>
    <a href="http://localhost:8080/eweb/highcharts/highchartpie.jsp" class="btn  btn-primary btn-sm">饼状图数据下钻<%--新建--%></a>
    <a href="http://localhost:8080/eweb/highcharts/columnchart3d.jsp" class="btn  btn-primary btn-sm">柱状图2<%--新建--%></a>
</div>
<div id="container"></div>
<script type="text/javascript">
    $(function () {
        $('#container').highcharts({
            chart: {
                type: 'pie',
                options3d: {
                    enabled: true,
                    alpha: 45,
                    beta: 0
                }
            },
            title: {
                text: '聊天软件市场占有率'
            },
            subtitle: {
                text: '部分模块可单击进行数据下钻'
            },
            credits: {
                enabled: true,
                text: '鼎商动力软件股份有限公司 ',
                href: 'http://www.dsdl.com ',
                style: {
                    color:'#c6c6cc',
                    fontSize: '15px'
                },
                position: {
                    align: 'right',
                    verticalalign:'bottom'
                },
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
                type: 'pie',
                colorByPoint: true,
                name: '聊天软件市场占有率',
                data: [{
                    name: 'Tencent',
                    y: 51.2,
                    drilldown: 'tencent'
                }, {
                    name: 'Apple',
                    y: 0.7
                }, {
                    name: 'YY',
                    y: 8.5
                }, {
                    name: '网易',
                    y: 8.5,
                    drilldown: 'yy'
                }]
            }],
            drilldown: {
                drillUpButton: {
                    relativeTo: 'spacingBox',
                    position: {
                        y: 0,
                        x: 0
                    },
                    theme: {
                        fill: 'white',
                        'stroke-width': 1,
                        stroke: 'silver',
                        r: 0,
                        states: {
                            hover: {
                                fill: '#ed402f'
                            },
                            select: {
                                stroke: '#5e9932',
                                fill: '#a4edba'
                            }
                        }
                    }

                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                },
                series: [{
                    id: 'tencent',
                    name: '详细市场占有率',
                    data: [
                        ['QQ', 22.2],
                        ['WeiXin', 26],
                        ['WQQ', 3]
                    ]
                }, {
                    id: 'yy',
                    name: '详细市场占有率',
                    data: [
                        ['虎牙', 4],
                        ['YY', 4.5]
                    ]
                }]
            }
        });
    });

</script>
</body>
</html>
