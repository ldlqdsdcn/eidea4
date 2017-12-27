/**
 * Highcharts-zh_CN plugins v1.0.1 (2017-04-05)
 *
 * (c) 2017 Jianshu Technology co.,LTD (https://jianshukeji.com)
 *
 * Author: john@jianshukeji.com, Blue Monkey
 *
 * License: Creative Commons Attribution (CC)
 */

(function(H) {
  var protocol = window.location.protocol;

  var defaultOptionsZhCn = {
    lang: {
      // Highcharts
      decimalPoint: '.',
      downloadJPEG: "导出JPEG图片",
      downloadPDF: "导出PDF文档",
      downloadPNG: "导出PNG图片",
      downloadSVG: "导出SVG图片",
      loading: '加载中...',
      months: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月','十二月'],
      noData: "不显示日期",
      numericSymbols: null,
      Printchart: "打印图表",
      resetZoom: '重置缩放比',
      resetZoomTitle: '重置缩放标题',
      shortMonths: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月','十二月'],
      thousandsSep: ',',
      weekdays: ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日'],
      // Highmaps
      zoomIn: '放大',
      zoomOut: '缩小'
    },

    global: {
      useUTC: true,
      //timezoneOffset: -8 * 60,
      canvasToolsURL: protocol + '//cdn.hcharts.cn/highcharts/modules/canvas-tools.js',
      VMLRadialGradientURL: protocol + +'//cdn.hcharts.cn/highcharts/gfx/vml-radial-gradient.png'
    },

    tooltip: {
      dateTimeLabelFormats: {
        millisecond: '%H:%M:%S.%L',
        second: '%H:%M:%S',
        minute: '%H:%M',
        hour: '%H:%M',
        day: '%Y-%m-%d',
        week: 'Week from %A, %b %e, %Y',
        month: '%Y-%m',
        year: '%Y'
      }
    },
      credits: {
          enabled: true,
          text: 'XXXX软件股份有限公司 ',
          href: '# ',
          style: {
              color:'#c6c6cc',
              fontSize: '15px'
          }
      },
    xAxis: {
      dateTimeLabelFormats: {
        millisecond: '%H:%M:%S.%L',
        second: '%H:%M:%S',
        minute: '%H:%M',
        hour: '%H:%M',
        day: '%Y-%m-%d',
        week: '%e. %b',
        month: '%Y-%m',
        year: '%Y'
      }
    }
  };

  H.setOptions(defaultOptionsZhCn);
}(Highcharts));