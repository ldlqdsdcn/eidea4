<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="eidea" uri="http://eidea.cn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dsdl.eidea.base.entity.bo.UserBo" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <title><eidea:label key="index.title"/></title>

    <!-- Bootstrap core CSS -->

    <link rel="stylesheet" href="<c:url value="/css/bootstrap/bootstrap.min.css"/>" type="text/css" media="all"/>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap/bootstrap-theme.min.css"/>" type="text/css" media="all"/>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap/bootstrap.addtabs.css"/>" type="text/css" media="all"/>
    <link rel="stylesheet" href="<c:url value="/vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css"/>" type="text/css" media="all"/>
    <script type='text/javascript' src='<c:url value="/js/jquery-3.1.1.min.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/bootstrap/bootstrap.min.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/bootbox.min.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/bootstrap/datetimepicker/bootstrap-datetimepicker.min.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/bootstrap/bootstrap.addtabs.js"/>'></script>
    <script type='text/javascript' src="<c:url value="/js/angular/angular.min.js"/>"></script>
    <script type='text/javascript' src="<c:url value="/js/angular/angular-route.min.js"/>"></script>
    <script type='text/javascript' src="<c:url value="/js/angular/jcs-auto-validate.min.js"/>"></script>
    <script type='text/javascript' src="<c:url value="/js/angular/ui-bootstrap-tpls-2.2.0.min.js"/>"></script>
    <script type='text/javascript' src="<c:url value="/js/consts.js"/>"></script>
    <script type='text/javascript' src="<c:url value="/js/eidea.util.js"/>"></script>

    <script type='text/javascript' src="<c:url value="/vendors/bootstrap-progressbar/bootstrap-progressbar.min.js"/>"></script>
    <link href="<c:url value="/css/font-awesome.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/animate.min.css"/>" rel="stylesheet">
    <script type='text/javascript' src="<c:url value="/js/md5.min.js"/>"></script>
    <!--sidebar-->
    <link href="<c:url value="/js/bootstrap/sidebar/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet">
    <script type='text/javascript' src='<c:url value="/js/bootstrap/sidebar/jquery.mCustomScrollbar.js"/>'></script>
    <!--sidebar-->

    <!-- Theme styling -->

    <link href="<c:url value="/css/custom.css"/>" rel="stylesheet">
    <!--[if lt IE 9]>
    <script type="text/javascript" src='<c:url value="/js/ie8-responsive-file-warning.js"/>'></script>
    <![endif]-->

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src='<c:url value="/js/html5shiv.min.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/respond.min.js"/>' type="text/javascript"></script>
    <![endif]-->
    <script type="text/javascript">

        function resizeIframe(obj) {
            obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';
        }
        function gotoUrl(url) {
            document.getElementById('content').src=url;
        }
    </script>
</head>
<%UserBo user=(UserBo)session.getAttribute("loginUser"); %>
<body class="nav-md gun_dong">
<div class="container body">
    <div class="main_container">
        <div class="col-md-3 left_col menu_fixed">
            <div class="left_col scroll-view">
                <div class="navbar nav_title" style="border: 0;">
                    <a href="<c:url value="/index.jsp"/> " class="site_title"><li class="fa" style="margin-top: 8px;"><img src="<c:url value="/img/favicon.png"/>" style="width:45px;height: 45px;"/></li> <span style="font-family: 微软雅黑"><eidea:label key="index.enterprise_thinking"/></span></a>
                </div>

                <div class="clearfix"></div>

                <!-- menu profile quick info -->
                <div class="profile clearfix">
                    <div class="profile_pic">
                        <img src="<c:url value="/img/header.png"/>" alt="..." class="img-circle profile_img">
                    </div>
                    <div class="profile_info">
                        <span>Welcome,</span>
                        <h2> <p><%=user.getName()%></p></h2>
                    </div>
                </div>
                <!-- /menu profile quick info -->

                <br>

                <!-- sidebar menu -->
                <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
                    <div class="menu_section">
                        ${sessionScope.menuString}
                    </div>
                </div>
                <!-- sidebar menu -->
            </div>
        </div>
        <!--left menu end-->
        <!--top navigation-->
        <div class="top_nav">
            <div class="nav_menu">
                <nav>
                    <div class="nav toggle">
                        <a id="menu_toggle"><i class="fa fa-bars"></i></a>
                    </div>

                    <ul class="nav navbar-nav navbar-right">
                        <li class="">
                            <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                                <img src="<c:url value="/img/header.png"/>" alt="">
                                <span class=" fa fa-angle-down"></span>
                            </a>
                            <ul class="dropdown-menu dropdown-usermenu pull-right">
                                <li><a href="javascript:void(0);" data-toggle="modal" data-target="#changePasswordModal"><eidea:label key="index.change_password"/></a></li>
                                <li><a href="javascript:gotoUrl('<c:url value="/common/profile.jsp"/>');"><eidea:label key="index.profile"/></a></li>
                                <li>
                                    <a href="javascript:;">
                                        <span class="badge bg-red pull-right"><eidea:label key="index.proportion"/></span>
                                        <span><eidea:label key="index.settings"/></span>
                                    </a>
                                </li>
                                <li><a href="javascript:;"><eidea:label key="index.help"/></a></li>
                                <li><a href="<c:url value="/logout"/>"><i class="fa fa-sign-out pull-right"></i><eidea:label key="index.log_out"/></a></li>
                            </ul>
                        </li>

                        <li role="presentation" class="dropdown">
                            <a href="javascript:;" class="dropdown-toggle info-number" data-toggle="dropdown" aria-expanded="false">
                                <i class="fa fa-volume-up la-ba"></i>
                                <span class="badge bg-green"><eidea:label key="index.email_size"/></span>
                            </a>
                            <ul id="menu1" class="dropdown-menu list-unstyled msg_list" role="menu">
                                <li>
                                    <a>
                                        <span class="image"><img src="<c:url value="/img/header.png"/>" alt="Profile Image"></span>
                                        <span>
                                          <span><eidea:label key="index.email_name"/></span>
                                          <span class="time"><eidea:label key="index.email_time"/></span>
                                        </span>
                                        <span class="message"><eidea:label key="index.email_message"/></span>
                                    </a>
                                </li>
                                <li>
                                    <div class="text-center">
                                        <a>
                                            <strong><eidea:label key="index.email_see_all"/></strong>
                                            <i class="fa fa-angle-right"></i>
                                        </a>
                                    </div>
                                </li>
                            </ul>
                        </li>
                        <li ng-app="changeLanguageApp" ng-controller="changeLanguageCtrl">
                            <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                                {{defaultLanguageName}}
                            </a>
                            <ul class="dropdown-menu dropdown-usermenu pull-right">
                                <li ng-repeat="language in languages">
                                    <a ng-click="swithLanguage(language.code)">{{language.name}}</a>
                                </li>
                            </ul>

                        </li>
                    </ul>
                </nav>
            </div>
            <!--用户修改密码-->
            <%@ include file="/common/change_password.jsp" %>
            <!--用户修改密码-->
        </div>
        <!--top navigation-->
        <!--page content-->
        <div class="right_col" role="main">
            <div id="tabs">
                <!-- Nav tabs -->
                <ul class="nav nav-tabs" role="tablist">
                    <li role="presentation" class="active">
                        <a href="#home" aria-controls="home" role="tab" data-toggle="tab"><eidea:label key="common.button.home.page"/></a></li>
                </ul>
                <!-- Tab panes -->
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane active" id="home">
                        <iframe src="<c:url value="/default.jsp"/>" width="100%" height="100%"  frameborder="0"></iframe>
                    </div>
                </div>
            </div>
        </div>
        <!--page content-->
    </div>
</div>
<script src='<c:url value="/js/custom.js"/>' type="text/javascript"></script>
<script type="text/javascript">
    //$("#content").load("<c:url value="/core/language/showList"/>");
    var app = angular.module('changeLanguageApp', ['ui.bootstrap', 'jcs-autoValidate']);
    app.controller('changeLanguageCtrl',function ($scope,$http) {
        //获取语言列表
        $http.get("<c:url value="/languages"/>").success(function (data) {
            if (data.success) {
                $scope.languages = data.data;
                var languageCode="<%=(request.getSession().getAttribute("language")==null)?request.getLocale().toString():request.getSession().getAttribute("language")%>";
                $.each($scope.languages,function (index,value) {
                    if(value.code == languageCode){
                        $scope.defaultLanguageName=value.name;
                    }
                })
            }else {
                $scope.serverReturnMessage = data.message;
            }
        });
        //切换语种
        $scope.swithLanguage=function (languageCode) {
            $.each($scope.languages,function (index,value) {
                if(value.code == languageCode){
                    $scope.defaultLanguageName=value.name;
                }
            })
            window.parent.location.href = "<c:url value="/common/changeLanguageCode"/>?language="+languageCode;
        }
    });
</script>
<script type="text/javascript">
    /**
     * Website: http://git.oschina.net/hbbcs/bootStrap-addTabs
     *
     * Version : 2.0
     *
     * Created by joe on 2016-2-4.Update 2017-03-14
     */

    (function ($) {

        var settings = {
            content: '', //直接指定所有页面TABS内容
            close: true, //是否可以关闭
            monitor: 'body', //监视的区域
            iframe: true, //使用iframe还是ajax
            iframeHeight: 1000, //固定TAB中IFRAME高度,根据需要自己修改
            target: '.nav-tabs',
            contextmenu: true, //是否使用右键菜单
            local: {
                'refreshLabel': '<eidea:label key="bootstrap.addtab.refresh.this.tab"/>',
                'closeThisLabel': '<eidea:label key="bootstrap.addtab.close.this.tab"/>',
                'closeOtherLabel': '<eidea:label key="bootstrap.addtab.close.other.tabs"/>',
                'closeLeftLabel': '<eidea:label key="bootstrap.addtab.close.left.tabs"/>',
                'closeRightLabel': '<eidea:label key="bootstrap.addtab.close.right.tabs"/>'
            },
            callback: function () { //关闭后回调函数
            }
        };

        var target;

        _click = function (obj) {
            var a_obj, a_target;

            a_obj = (typeof obj.data('addtab') == 'object') ? obj.data('addtab') : obj.data();

            if (!a_obj.id && !a_obj.addtab) {
                a_obj.id = Math.random().toString(36).substring(3, 35);
                obj.data('id', a_obj.id);
            }

            $.addtabs.add({
                'target': a_obj.target ? a_obj.target : target,
                'id': a_obj.pid,
                'title': a_obj.title ? a_obj.title : obj.html(),
                'content': settings.content ? settings.content : a_obj.content,
                'url': a_obj.url,
                'ajax': a_obj.ajax ? true : false
            });
        };

        _createMenu = function (right, icon, text) {
            return $('<a>', {
                'href': 'javascript:void(0);',
                'class': "list-group-item",
                'data-right': right
            }).append(
                    $('<i>', {
                        'class': 'glyphicon ' + icon
                    })
            ).append(text);
        }

        _pop = function (id, e, mouse) {
            $('body').find('#popMenu').remove();
            if(id != "home"){
                var refresh = e.attr('id') ? _createMenu('refresh', 'glyphicon-refresh', settings.local.refreshLabel) : '';
                var remove = e.attr('id') ? _createMenu('remove', 'glyphicon-remove', settings.local.closeThisLabel) : '';
                var left = e.prev('li').attr('id') ? _createMenu('remove-left', 'glyphicon-chevron-left', settings.local.closeLeftLabel) : '';
                var right = e.next('li').attr('id') ? _createMenu('remove-right', 'glyphicon-chevron-right', settings.local.closeRightLabel) : '';
            }
            var popHtml = $('<ul>', {
                'aria-controls': id,
                'class': 'rightMenu list-group',
                id: 'popMenu',
                'aria-url': e.attr('aria-url'),
                'aria-ajax': e.attr('aria-ajax')
            }).append(refresh)
                    .append(remove)
                    .append(_createMenu('remove-circle', 'glyphicon-remove-circle', settings.local.closeOtherLabel))
                    .append(left)
                    .append(right);

            popHtml.css({
                'top': mouse.pageY,
                'left': mouse.pageX
            });
            popHtml.appendTo($('body')).fadeIn('slow');
            //刷新页面
            $('ul.rightMenu a[data-right=refresh]').on('click', function () {
                var id = $(this).parent('ul').attr("aria-controls").substring(4);
                var url = $(this).parent('ul').attr('aria-url');
                var ajax = $(this).parent('ul').attr('aria-ajax');
                $.addtabs.add({
                    'id': id,
                    'url': url,
                    'refresh': true,
                    'ajax': ajax
                });
            });

            //关闭自身
            $('ul.rightMenu a[data-right=remove]').on('click', function () {
                var id = $(this).parent("ul").attr("aria-controls");
                if (id.substring(0, 4) != 'tab_') return;
                $.addtabs.close({
                    "id": id
                });
                $.addtabs.drop();
            });

            //关闭其他
            $('ul.rightMenu a[data-right=remove-circle]').on('click', function () {
                var tab_id = $(this).parent('ul').attr("aria-controls");
                target.find('li').each(function () {
                    var id = $(this).attr('id');
                    if (id && id != 'tab_' + tab_id) {
                        $.addtabs.close({
                            "id": $(this).children('a').attr('aria-controls')
                        });
                    }
                });
                $.addtabs.drop();
            });

            //关闭左侧
            $('ul.rightMenu a[data-right=remove-left]').on('click', function () {
                var tab_id = $(this).parent('ul').attr("aria-controls");
                $('#tab_' + tab_id).prevUntil().each(function () {
                    var id = $(this).attr('id');
                    if (id && id != 'tab_' + tab_id) {
                        $.addtabs.close({
                            "id": $(this).children('a').attr('aria-controls')
                        });
                    }
                });
                $.addtabs.drop();
            });

            //关闭右侧
            $('ul.rightMenu a[data-right=remove-right]').on('click', function () {

                var tab_id = $(this).parent('ul').attr("aria-controls");
                $('#tab_' + tab_id).nextUntil().each(function () {
                    var id = $(this).attr('id');
                    if (id && id != 'tab_' + tab_id) {
                        $.addtabs.close({
                            "id": $(this).children('a').attr('aria-controls')
                        });
                    }
                });
                $.addtabs.drop();
            });
            popHtml.mouseleave(function () {
                $(this).fadeOut('slow');
            });
            $('body').click(function () {
                popHtml.fadeOut('slow');
            })
        };

        _listen = function () {
            $(settings.monitor).on('click', '[data-addtab]', function () {
                _click($(this));
                $.addtabs.drop();
            });

            $('body').on('click', '.close-tab', function () {
                var id = $(this).prev("a").attr("aria-controls");
                $.addtabs.close({
                    'id': id
                });
                $.addtabs.drop();
            });

            if (settings.contextmenu) {
                //obj上禁用右键菜单
                $('body').on('contextmenu', 'li[role=presentation]', function (e) {
                    var id = $(this).children('a').attr('aria-controls');
                    _pop(id, $(this), e);
                    return false;
                });
            }

            var el;
            $('body').on('dragstart.h5s', '.nav-tabs li', function (e) {
                el = $(this);
            }).on('dragover.h5s dragenter.h5s drop.h5s', '.nav-tabs li', function (e) {
                if (el == $(this)) return;
                $('.dragBack').removeClass('dragBack');
                $(this).addClass('dragBack');
                el.insertAfter($(this))
            }).on('dragend.h5s', '.nav-tabs li', function () {
                $('.dragBack').removeClass('dragBack');
            });

        };

        $.addtabs = function (options) {
            $.addtabs.set(options);
            _listen();
        };

        $.addtabs.set = function () {
            if (arguments[0]) {
                if (typeof arguments[0] == 'object') {
                    settings = $.extend(settings, arguments[0] || {});
                } else {
                    settings[arguments[0]] = arguments[1];
                }
            }
            if (typeof settings.target == 'object') {
                target = settings.target;
            } else {
                target = $('body').find(settings.target).length > 0 ? $(settings.target).first() : $('body').find('.nav-tabs').first();
            }
        }

        $.addtabs.add = function (opts) {
            var a_target, content;
            opts.id = opts.id ? opts.id : Math.random().toString(36).substring(3, 35);
            if (typeof opts.target == 'object') {
                a_target = opts.target;
            } else if (typeof opts.target == 'string') {
                a_target = $('body').find(opts.target);
            } else {
                a_target = target;
            }
            var id = 'tab_' + opts.id;
            var tab_li = a_target;

            var tab_content = tab_li.next('.tab-content');

            tab_li.find('li[role = "presentation"].active').removeClass('active');
            tab_content.find('div[role = "tabpanel"].active').removeClass('active');
            //如果TAB不存在，创建一个新的TAB
            if (tab_li.find('#tab_' + id).length < 1) {
                //创建新TAB的title
                var title = $('<li>', {
                    'role': 'presentation',
                    'id': 'tab_' + id,
                    'aria-url': opts.url,
                    'aria-ajax': opts.ajax ? true : false
                }).append(
                        $('<a>', {
                            'href': '#' + id,
                            'aria-controls': id,
                            'role': 'tab',
                            'data-toggle': 'tab',
                            'ondragstart':'return false'
                        }).html(opts.title)
                );

                //是否允许关闭
                if (settings.close) {
                    title.append(
                            $('<i>', {
                                'class': 'close-tab glyphicon glyphicon-remove'
                            })
                    );
                }
                //创建新TAB的内容
                var content = $('<div>', {
                    'class': 'tab-pane',
                    'id': id,
                    'role': 'tabpanel'
                });

                //加入TABS
                tab_li.append(title);
                tab_content.append(content);
            } else if (!opts.refresh) {
                $('#tab_' + id).addClass('active');
                $('#' + id).addClass('active');
                return;
            } else {
                content = $('#' + id);
                content.html('');
            }

            //是否指定TAB内容
            if (opts.content) {
                content.append(opts.content);
            } else if (settings.iframe == true && (opts.ajax == 'false' || !opts.ajax)) { //没有内容，使用IFRAME打开链接
                content.append(
                        $('<iframe>', {
                            'class': 'iframeClass',
                            'height': 100+'%',
                            'frameborder': "no",
                            'border': "0",
                            'src': opts.url
                        })
                );
            } else {
                content.load(opts.url);
            }

            //激活TAB
            tab_li.find('#tab_' + id).addClass('active');
            tab_content.find('#' + id).addClass('active');
        };

        $.addtabs.close = function (opts) {
            //如果关闭的是当前激活的TAB，激活他的前一个TAB
            if ($("#tab_" + opts.id).hasClass('active')) {
                if ($('#tab_' + opts.id).parents('li.tabdrop').length > 0 && !$('#tab_' + opts.id).parents('li.tabdrop').hasClass('hide')) {
                    $('#tab_' + opts.id).parents('.nav-tabs').find('li').last().addClass('active');
                } else {
                    $("#tab_" + opts.id).prev('li').addClass('active');
                }
                $("#" + opts.id).prev().addClass('active');
            }
            //关闭TAB
            $("#tab_" + opts.id).remove();
            $("#" + opts.id).remove();
            $.addtabs.drop();
            settings.callback();
        };

        $.addtabs.closeAll = function (target) {
            if (typeof target == 'string') {
                target = $('body').find(target);
            }
            $.each(target.find('li[id]'), function () {
                var id = $(this).children('a').attr('aria-controls');
                $("#tab_" + id).remove();
                $("#" + id).remove();
            });
            target.find('li[role = "presentation"]').first().addClass('active');
            var firstID = target.find('li[role = "presentation"]').first().children('a').attr('aria-controls');
            $('#' + firstID).addClass('active');
            $.addtabs.drop();
        };

        $.addtabs.drop = function () {
            //创建下拉标签
            var dropdown = $('<li>', {
                'class': 'dropdown pull-right hide tabdrop tab-drop'
            }).append(
                    $('<a>', {
                        'class': 'dropdown-toggle',
                        'data-toggle': 'dropdown',
                        'href': '#'
                    }).append(
                            $('<i>', {
                                'class': "glyphicon glyphicon-align-justify"
                            })
                    ).append(
                            $('<b>', {
                                'class': 'caret'
                            })
                    )
            ).append(
                    $('<ul>', {
                        'class': "dropdown-menu"
                    })
            )


            $('body').find('.nav-tabs').each(function () {
                var element = $(this);
                //检测是否已增加
                if (element.find('.tabdrop').length < 1) {
                    dropdown.prependTo(element);
                } else {
                    dropdown = element.find('.tabdrop');
                }
                //检测是否有下拉样式
                if (element.parent().is('.tabs-below')) {
                    dropdown.addClass('dropup');
                }
                var collection = 0;

                //检查超过一行的标签页
                element.append(dropdown.find('li'))
                        .find('>li')
                        .not('.tabdrop')
                        .each(function () {
                            if (this.offsetTop > 0 || element.width() - $(this).position().left - $(this).width() < 83) {
                                dropdown.find('ul').prepend($(this));
                                collection++;
                            }
                        });

                //如果有超出的，显示下拉标签
                if (collection > 0) {
                    dropdown.removeClass('hide');
                    if (dropdown.find('.active').length == 1) {
                        dropdown.addClass('active');
                    } else {
                        dropdown.removeClass('active');
                    }
                } else {
                    dropdown.addClass('hide');
                }
            })

        }

    })(jQuery);

    $(function () {
        $.addtabs();
    })

</script>
</body>
</html>
