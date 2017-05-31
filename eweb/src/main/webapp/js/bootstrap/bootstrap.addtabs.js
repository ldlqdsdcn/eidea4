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
            'refreshLabel': $("#refreshTabs").val(),
            'closeThisLabel': $("#closeTabs").val(),
            'closeOtherLabel': $("#otherTabs").val(),
            'closeLeftLabel': $("#leftTabs").val(),
            'closeRightLabel': $("#rightTabs").val()
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