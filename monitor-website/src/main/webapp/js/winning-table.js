(function ($) {
    'use strict';

    var WinningTable = function (el, options) {
        this.options = $.extend({language: WinningTable.Language}, WinningTable.DEFAULTS, options);
        this.$el = $(el);
        //this.$dataTable = null;
        this.dataTables = null;

        this.init();
    };

    WinningTable.DEFAULTS = {
        classes: 'table',
        select: {style: 'os'},
        dom: "<'row'<'col-sm-6 button-toolbar'><'col-sm-6'Bf>>" +
        "<'row'<'col-sm-12'tr>>" +
        "<'row'<'col-sm-5'i><'col-sm-7'p>>",
        pageLength: 50,
        lengthChange: false,
        ordering: false,
        searching: false,
        info: true,
        processing: true,
        fixedHeader: {
            headerOffset: 50
        },
        "pagingType":   "full_numbers"
    };


    WinningTable.Language = {
        sProcessing: "处理中...",
        sLengthMenu: "显示 _MENU_ 项结果",
        sZeroRecords: "没有匹配结果",
        sInfo: "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
        sInfoEmpty: "显示第 0 至 0 项结果，共 0 项",
        sInfoFiltered: "(由 _MAX_ 项结果过滤)",
        sInfoPostFix: "",
        sSearch: "搜索：",
        sUrl: "",
        sEmptyTable: "表中数据为空",
        sLoadingRecords: "载入中...",
        // old, obsolete, using sThousands instead
        sInfoThousands: ",",
        sThousands: ",",
        oPaginate: {
            sFirst: "首页",
            sPrevious: "上页",
            sNext: "下页",
            sLast: "末页"
        },
        oAria: {
            sSortAscending: ": 以升序排列此列",
            sSortDescending: ": 以降序排列此列"
        },
        select: {
            rows: {
                _: "已选择 %d 行",
                0: "点击选择行",
                1: "已选择 1 行"

            }
        }
    };

    WinningTable.prototype.init = function () {
        this.refreshDataTable(this.options);
        //$.fn.dataTable.FixedHeader(this.dataTables);
    };

    WinningTable.prototype.refreshDataTable = function (option) {
        this.dataTables = $(this.$el).DataTable(option);

        if (this.options.toolbar != null) {
            var toolbar = $(this.options.toolbar).clone(true);
            (toolbar).appendTo($('.button-toolbar'));
            toolbar.removeClass("hidden");
            $(this.options.toolbar).addClass("hidden");
        }
    };

    WinningTable.prototype.destoryDataTable = function () {
        var newOptions = jQuery.extend(true, {}, this.options);
        this.dataTables.destroy();
        this.options = newOptions;
        $(this.$el.selector).empty(); //清除表头
    }
    //querydata
    WinningTable.prototype.queryDataInPage = function (ajaxurl, reqdatas) {
        this.destoryDataTable();

        var queryOption = {
            serverSide: true,
            ajax: function (data, callback, settings) {
                var reqParams = {
                    pageSize: data.length,
                    start: data.start,
                    search: data.search.value
                };
                var params = $.extend(reqParams, reqdatas);
                $.post(ajaxurl, {datas:JSON.stringify(params)}, function (res) {
                    console.log(res);
                    callback({
                        recordsTotal: res.totalSize,
                        recordsFiltered: res.totalSize,
                        data: res.transactionMessages
                    });
                });
            }
        };

        queryOption = $.extend(queryOption, this.options);

        this.refreshDataTable(queryOption);
    };

    WinningTable.prototype.queryData = function (ajaxurl, reqdatas) {
        //{}
        this.destoryDataTable();
        var queryOption = {
            //ajax: {
            //    url: ajaxurl,
            //    dataSrc: 'datas',
            //    data: reqdatas
            //}
            ajax: function (data, callback, settings) {
                $.post(ajaxurl, {datas:JSON.stringify(reqdatas)}, function (res) {
                    if (!res.success) {
                        swal("发生错误", res.errorMsg, "error");
                        callback({
                            recordsTotal: 0,
                            data: []
                        });
                        return;
                    }
                    callback({
                        recordsTotal: res.datas.length,
                        data: res.datas
                    });
                });
            }
        };
        queryOption = $.extend(queryOption, this.options);
        this.refreshDataTable(queryOption);
    }

    WinningTable.prototype.getSelectedDatas = function () {
        return this.dataTables.rows({selected: true}).data();
    };

    WinningTable.prototype.getSelectedRows = function () {
        return this.dataTables.rows({selected: true});
    };

    WinningTable.prototype.draw = function () {
        return this.dataTables.draw();
    };

    $.fn.winningTable = function (option) {
        var $this = $(this);
        return new WinningTable($this, option);
    }

})(jQuery);