<%--
  Created by IntelliJ IDEA.
  User: 车东明
  Date: 2017/4/19
  Time: 10:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/inc/inc_ang_js_css.jsp" %>
<%@include file="/inc/highcharts_js.jsp" %>
<html>
<head>
    <title>3d柱状图</title>
</head>
<body>
<div class="page-header">
    <a href="<c:url value="/highcharts/columnchart3d.jsp"/>" class="btn  btn-primary btn-sm">柱状图</a>
    <a href="<c:url value="/highcharts/linecharts.jsp"/>" class="btn  btn-primary btn-sm">线性图</a>
    <a href="<c:url value="/highcharts/piecharts3d.jsp"/>" class="btn  btn-primary btn-sm">饼状图数据下钻</a>
    <a href="<c:url value="/highcharts/highchartpie.jsp"/>" class="btn  btn-primary btn-sm">饼状图</a>
</div>
<div id="container"></div>
<div id="sliders">
    <table>
        <tr>
            <td>α 角（内旋转角）</td>
            <td><input id="alpha" type="range" min="0" max="45" value="15"/> <span id="alpha-value"
                                                                                   class="value"></span></td>
        </tr>
        <tr>
            <td>β 角（外旋转角）</td>
            <td><input id="beta" type="range" min="-45" max="45" value="15"/> <span id="beta-value"
                                                                                    class="value"></span></td>
        </tr>
        <tr>
            <td>深度</td>
            <td><input id="depth" type="range" min="20" max="100" value="50"/> <span id="depth-value"
                                                                                     class="value"></span></td>
        </tr>
    </table>
</div>
<script type="text/javascript">
    $(function () {
        var chart = new Highcharts.Chart({
            chart: {
                renderTo: 'container',
                type: 'column',
                options3d: {
                    enabled: true,
                    alpha: 15,
                    beta: 15,
                    depth: 50,
                    viewDistance: 25
                }
            },
            //标题
            title: {
                text: '每个人吃的苹果量（个）'
            },
            //次标题
            subtitle: {
                text: 'DSDL'
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
            //版权
            credits: {
                enabled: true,
                text: '鼎商动力软件股份有限公司 ',
                href: 'http://www.dsdl.com ',
                style: {
                    color: '#c6c6cc',
                    fontSize: '15px'
                },
                position: {
                    align: 'right',
                    verticalalign: 'bottom'
                },
            },
            plotOptions: {
                column: {
                    depth: 25
                }
            },
            series: [{
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

        function showValues() {
            $('#alpha-value').html(chart.options.chart.options3d.alpha);
            $('#beta-value').html(chart.options.chart.options3d.beta);
            $('#depth-value').html(chart.options.chart.options3d.depth);
        }

        $('#sliders input').on('input change', function () {
            chart.options.chart.options3d[this.id] = this.value;
            showValues();
            chart.redraw(false);
        });
        showValues();
    });
</script>
</body>
</html>
