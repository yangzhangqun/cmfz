<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; utf-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <!--引入移动设备优先  必须写-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--引入BootStrap的全局样式-->
    <link rel="stylesheet" href="../boot/css/bootstrap.css">
    <script src="../boot/js/jquery-1.10.1.min.js"></script>
    <script src="../boot/js/bootstrap.js"></script>
    <%-- ajaxFileUpload  --%>
    <script src="../boot/js/ajaxfileupload.js"></script>
    <!--引入JRGRID-->
    <!--JQGRID的全局CSS样式-->
    <link rel="stylesheet" href="../jqgrid/bootstrap/css/trirand/ui.jqgrid-bootstrap.css">
    <!--国际化的JS-->
    <script src="../jqgrid/js/i18n/grid.locale-cn.js"></script>
    <!--JQGRID的核心JS-->
    <script src="../echarts/echarts.min.js"></script>
    <script src="../echarts/china.js"></script>
    <script src="../jqgrid/bootstrap/js/trirand/jquery.jqGrid.min.js"></script>
    <script type="application/javascript">
        $(function () {
            $("#userList").jqGrid({
                url: "${pageContext.request.contextPath}/user/showAllPage",
                editurl: "${pageContext.request.contextPath}/user/edit",
                datatype: "json",
                colNames: ["id", "头像", "法号", "姓名", "性别", "省", "市", "签名", "手机号", "密码", "创建日期", "状态", "操作"],
                colModel: [
                    {name: "id"},
                    {
                        name: "head_pic", editable: true, edittype: "file",
                        formatter: function (cellvalue, options, rowObject) {
                            return "<img style='height:80px;width:80px' src='${pageContext.request.contextPath}/upload/img/" + cellvalue + "'>"
                        },
                    },
                    {name: "dharma", editable: true},
                    {name: "name", editable: true},
                    {
                        name: "sex", editable: true, edittype: "select",
                        editoptions: {value: "男:男;女:女"}
                    },
                    {name: "province", editable: true},
                    {name: "city", editable: true},
                    {name: "sign", editable: true},
                    {name: "phone_num", editable: true},
                    {name: "password", editable: true},
                    {name: "create_date"},
                    {
                        name: "status", editable: true, edittype: "select",
                        editoptions: {value: "正常:正常;冻结:冻结"}
                    },
                    {
                        name: "",
                        formatter: function (cellValue, opo, value) {
                            return "<button type=\"button\" class=\"btn btn-primary\" onclick=\"updateStatus('" + value.id + "')\">修改状态</button>\n";
                        }
                    },
                ],
                pager: "#userPager",
                rowNum: 3,
                rowList: [3, 4, 7],
                viewrecords: true,
                height: '200px',
                multiselect: true,
                rownumbers: true,
                styleUI: "Bootstrap",
                autowidth: true,
            }).jqGrid("navGrid", "#userPager",
                {addtext: "添加", edittext: "修改", deltext: "删除", search: false},
                {},
                {//添加
                    closeAfterAdd: true,
                    afterSubmit: function (response) {
                        var userId = response.responseJSON.userId;
                        var msg = response.responseJSON.msg;
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/user/upload",
                            fileElementId: "head_pic",
                            type: "post",
                            data: {userId: userId},
                            success: function () {
                                $("#userList").trigger("reloadGrid")
                                $("#userMsgId").show().html(msg);
                                setTimeout(function () {
                                    $("#userMsgId").hide()
                                }, 3000);
                            }
                        })
                        return response;
                    }
                },
                {//删除
                    afterComplete: function (response) {
                        var msg = response.responseJSON.msg;
                        $("#userMsgId").show().html(msg);
                        setTimeout(function () {
                            $("#userMsgId").hide()
                        }, 3000);
                    }
                }
            );
        })

        function outUserMsg() {
            location.href = "${pageContext.request.contextPath}/user/getAll"
        }

        function updateStatus(id) {
            $.ajax({
                url: "${pageContext.request.contextPath}/user/updateStatus",
                datatype: "json",
                type: "post",
                data: "id=" + id,
                success: function (data) {
                    $("#userList").trigger("reloadGrid")
                    $("#userMsgId").show().html(msg);
                    setTimeout(function () {
                        $("#userMsgId").hide()
                    }, 3000);
                }
            })
        }
    </script>
    <script>
        function recentUserMsg() {
            $("#myModal").modal("show");
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '近七天人员柱形图'
                },
                tooltip: {},
                legend: {
                    data: ['近七天的人员注册柱形图']
                },
                xAxis: {
                    data: []
                },
                yAxis: {},
                series: [{
                    name: '人数',
                    type: 'bar',

                }]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
            $.ajax({
                url: "${pageContext.request.contextPath}/user/queryTime",
                type: "get",
                datatype: "json",
                success: function (data) {
                    myChart.setOption({
                        xAxis: {
                            data: data[0].date
                        },
                        series: [
                            {
                                data: data[0].count
                            }
                        ]
                    })
                }
            })
        }
    </script>
    <script>
        function year() {
            $("#myModal").modal("show");
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '年度人员走势图'
                },
                tooltip: {},
                legend: {
                    data: ['年度人员注册走势图']
                },
                xAxis: {
                    data: []
                },
                yAxis: {},
                series: [{
                    name: '人数',
                    type: 'line',

                }]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
            $.ajax({
                url: "${pageContext.request.contextPath}/user/getTime",
                type: "get",
                datatype: "json",
                success: function (data) {
                    myChart.setOption({
                        xAxis: {
                            data: data[0].date
                        },
                        series: [
                            {
                                data: data[0].count
                            }
                        ]
                    })
                }
            })
        }
    </script>
    <script>
        function ditu() {
            $("#myModal").modal("show");
            var myChart = echarts.init(document.getElementById('main'));
            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '持名法州注册人员分布图',
                    subtext: '人员分布',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ['人数']
                },
                visualMap: {
                    min: 0,
                    max: 2500,
                    left: 'left',
                    top: 'bottom',
                    text: ['高', '低'],           // 文本，默认为数值文本
                    calculable: true
                },
                toolbox: {
                    show: true,
                    orient: 'vertical',
                    left: 'right',
                    top: 'center',
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                series: [
                    {
                        name: '人数',
                        type: 'map',
                        mapType: 'china',
                        roam: false,
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        }
                    }
                ]
            };


            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
            $.ajax({
                url: "${pageContext.request.contextPath}/user/getProvince",
                type: "get",
                datatype: "json",
                success: function (data) {
                    myChart.setOption({
                        series: [
                            {
                                data: data
                            }
                        ]
                    })
                }
            })
        }
    </script>
    <h3>用 户管理</h3>
    <div>
        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab"
                                                      data-toggle="tab"><b>用户信息</b></a></li>
            <li><a href="#" onclick="outUserMsg()">导出用户信息</a></li>
            <li><a href="javascript:void(0)" onclick="recentUserMsg()"><b>七天人员注册图</b></a></li>
            <li><a href="javascript:void(0)" onclick="year()"><b>年度人员注册图</b></a></li>
            <li><a href="javascript:void(0)" onclick="ditu()"><b>人员位置分布图</b></a></li>
        </ul>
    </div>
    <table id="userList" class="table table-striped"></table>
    <div id="userPager" style="height: 50px"></div>
    <div id="userMsgId" class="alert alert-warning alert-dismissible" role="alert" style="display:none">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                aria-hidden="true">&times;</span>
        </button>
        <strong>Warning!</strong>
    </div>
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">统计最近7天用户走势图</h4>
                </div>
                <div class="modal-body">

                    <div id="main" style="width: 600px;height:400px;"></div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary">Save changes</button>
                </div>
            </div>
        </div>
    </div>