$.datetimepicker.setLocale('ch');
var datetimepicker = angular.module('xdan.datetimepicker', []);

datetimepicker.directive('xdanDatetimepicker', function() {
    return {
        require: '?ngModel',
        scope: {
            user_options: '=xdanDatetimepicker',
            picker_type_option: '@pickerType',
            view_format_option: '@viewFormat',
            model_format_option: '@modelFormat'
        },
        template: '<input type="text">',
        link: function(scope, element, attrs, ngModelController) {
            // The most famous date format in my country ;)
            var DEFAULT_VIEW_DATE_FORMAT = 'd-m-Y',
                DEFAULT_VIEW_TIME_FORMAT = 'H:i',
                DEFAULT_VIEW_FORMAT = DEFAULT_VIEW_DATE_FORMAT + ' ' + DEFAULT_VIEW_TIME_FORMAT,

                DEFAULT_MODEL_DATE_FORMAT = 'Y-m-d',
                DEFAULT_MODEL_TIME_FORMAT= 'H:i:s',
                DEFAULT_MODEL_FORMAT = DEFAULT_MODEL_DATE_FORMAT + ' ' + DEFAULT_MODEL_TIME_FORMAT;

            if (scope.picker_type_option) {
                scope.picker_type = scope.picker_type_option;
            } else {
                scope.picker_type = 'datetime';
            }

            if (scope.view_format_option) {
                scope.view_format = scope.view_format_option;
            } else {
                if (scope.picker_type == 'date') {
                    scope.view_format = DEFAULT_VIEW_DATE_FORMAT;
                } else if (scope.picker_type == 'time') {
                    scope.view_format = DEFAULT_VIEW_TIME_FORMAT;
                } else {
                    scope.view_format = DEFAULT_VIEW_FORMAT;
                }

            }

            if (scope.model_format_option && scope.picker_type == 'datetime') {
                scope.model_format = scope.model_format_option;
            } else {
                if (scope.picker_type == 'date') {
                    scope.model_format = DEFAULT_MODEL_DATE_FORMAT;
                } else if (scope.picker_type == 'time') {
                    scope.model_format = DEFAULT_MODEL_TIME_FORMAT;
                } else {
                    scope.model_format = DEFAULT_MODEL_FORMAT;
                }

            }

            // default options
            // I hate scrollMonth!!!
            scope.options = scope.user_options;
            if (scope.user_options) {
                angular.extend(scope.options, scope.user_options);
            }
            if (scope.user_options.onChangeDateTime) {
                scope.options.onChangeDateTime = function(current_time, input) {
                    current_time=element.find('input[type=text]').val();
                    scope.user_options.onChangeDateTime();
                    if (ngModelController) {
                        if (scope.picker_type == 'datetime') {
                            ngModelController.$setViewValue(current_time);
                        } else {
                            ngModelController.$setViewValue(current_time.dateFormat(scope.model_format));
                        }
                    }
                };
            } else {
                scope.options.onChangeDateTime = function(current_time, input) {
                    current_time=element.find('input[type=text]').val();
                    if (ngModelController) {
                        if (scope.picker_type != 'datetime') {
                            ngModelController.$setViewValue(current_time.dateFormat(scope.model_format));
                        } else {
                            ngModelController.$setViewValue(current_time);
                        }
                    }
                }
            }

            if (ngModelController) {
                ngModelController.$render = function() {
                    element.find('input[type=text]').datetimepicker(scope.options);
                };

                ngModelController.$formatters.push(function(modelValue) {
                    var date;
                    // todo: parse date/time/datetime
                    if (modelValue) {
                        if (scope.picker_type == 'date') {
                            date = new Date(modelValue + ' 00:00:00');
                        } else if (scope.picker_type == 'time') {
                            date = new Date('1970-01-01 ' + modelValue);
                        } else {
                            date = new Date(modelValue);
                        }

                        //date = date.dateFormat(scope.options.format);
                        //element.find('input[type=text]').val(modelValue);
                    }

                    return modelValue
                });
            }
        }
    }
});