<%@page pageEncoding="UTF-8" contentType="text/html; UTF-8"  isELIgnored="false" %>
<script>
    $(function () {
        $("#test").jqGrid({
            url:"${pageContext.request.contextPath}/banner/show",
            editurl:"${pageContext.request.contextPath}/banner/edit",
            datatype:"json",
            colNames:["编号","标题","状态","创建日期","描述","图片"],
            colModel:[
                {name:"id"},
                {name:"title",editable:true},
                {name:"status",editable:true, edittype:"select",
                    editoptions: {value: "展示:展示;不展示:不展示"}
                },
                {name:"create_date",editable:true,edittype:"date"},
                {name:"description",editable:true},
                {name:"img_pic",editable:true,edittype:"file",
                    formatter:function (cellvalue,options,rowObject) {
                        return "<img style='height:80px;width:80px' src='${pageContext.request.contextPath}/upload/img/"+cellvalue+"'>"
                    },
                },
            ],
            styleUI:"Bootstrap",
            pager:"#bannerPager",//分页
            rowNum:3,//每页显示条数
            rowList:[2,4,6],
            viewrecords:true,//显示总记录数
            autowidth:true,//自适应宽度
            multiselect:true,
            rownumbers:true
        }).jqGrid("navGrid","#bannerPager",
            {addtext:"添加",edittext:"修改",deltext:"删除",search:false},
            {//修改
                closeAfterEdit:true,
                afterSubmit:function (response) {
                    if(response.responseJSON.id!=''){
                        var bannerId = response.responseJSON.id;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/banner/upload",
                        fileElementId:"img_pic",
                        type:"post",
                        data:{bannerId:bannerId},
                        success:function(data){
                            $("#test").trigger("reloadGrid");
                            $("#bannerMsgId").show().html("修改成功");
                            setTimeout(function(){
                                $("#bannerMsgId").hide()
                            }, 3000);
                        }
                    })
                    }
                    return response;
                }

            },
            {//添加
                closeAfterAdd:true,
                afterSubmit:function (response) {
                    var bannerId = response.responseJSON.bannerId;
                    var msg = response.responseJSON.msg;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/banner/upload",
                        fileElementId:"img_pic",
                        type:"post",
                        data:{bannerId:bannerId},
                        success:function(){
                            $("#test").trigger("reloadGrid")
                             $("#bannerMsgId").show().html(msg);
                            setTimeout(function(){
                                $("#bannerMsgId").hide()
                            }, 3000);
                        }
                    })
                    return response;
                }
            },
            {//删除
             afterComplete:function (response) {
                 var msg = response.responseJSON.msg;
                 $("#bannerMsgId").show().html(msg);
                 setTimeout(function(){
                     $("#bannerMsgId").hide()
                 }, 3000);
             }
            }
        );
    })



</script>
<h3>轮播图管理</h3>
<div>
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab"
                                                  data-toggle="tab"><b>轮播图信息</b></a></li>
    </ul>
</div>
<table id="test"></table>
<div  id="bannerPager" style="height:80px;width: 80px"></div>
<div id="bannerMsgId" class="alert alert-warning alert-dismissible" role="alert" style="display:none">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <strong>Warning!</strong>
</div>
