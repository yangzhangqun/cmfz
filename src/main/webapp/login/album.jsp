<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; utf-8" %>
<script>
    $(function () {
        $("#albumList").jqGrid({
            url:"${pageContext.request.contextPath}/album/get",
            editurl:"${pageContext.request.contextPath}/album/edit",
            datatype: "json",
            colNames:["id","标题","分数","作者","播音员","章节数","专辑简介","状态","发行时间","插图"],
            colModel:[
                {name:"id"},
                {name:"title",editable:true},
                {name:"score",editable:true},
                {name:"author",editable:true},
                {name:"brodcast",editable:true},
                {name:"count",editable:true},
                {name:"brief",editable:true},
                {name:"status",editable:true, edittype:"select",
                    editoptions: {value: "展示:展示;不展示:不展示"}
                },
                {name:"publicTime",editable:true,edittype:"date"},
                {name:"coverImg",editable:true,edittype:"file",
                    formatter:function (cellvalue,options,rowObject) {
                        return "<img style='height:80px;width:80px' src='${pageContext.request.contextPath}/upload/img/"+cellvalue+"'>"
                },
                },
            ],
            pager: "#albumPager",
            rowNum: 3,
            rowList: [3, 4, 7],
            viewrecords: true,
            height: '200px',
            multiselect: true,
            rownumbers: true,
            styleUI: "Bootstrap",
            autowidth: true,
            subGrid:true,
            subGridRowExpanded:function (subGidId,album_id) {
                addSubGrid(subGidId,album_id);
            }
        }).jqGrid("navGrid","#albumPager",
            {addtext:"添加",edittext:"修改",deltext:"删除",search:false},
            {//修改
                closeAfterEdit:true,
                afterSubmit:function (response) {
                    if(response.responseJSON.id!=''){
                    var albumId = response.responseJSON.id;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/album/upload",
                        fileElementId:"coverImg",
                        type:"post",
                        data:{albumId:albumId},
                        success:function(data){
                            $("#albumList").trigger("reloadGrid");
                            $("#albumMsgId").show().html("修改成功");
                            setTimeout(function(){
                                $("#albumMsgId").hide()
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
                    var albumId = response.responseJSON.albumId;
                    var msg = response.responseJSON.msg;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/album/upload",
                        fileElementId:"coverImg",
                        type:"post",
                        data:{albumId:albumId},
                        success:function(){
                            $("#albumList").trigger("reloadGrid")
                            $("#albumMsgId").show().html(msg);
                            setTimeout(function(){
                                $("#albumMsgId").hide()
                            }, 3000);
                        }
                    })
                    return response;
                }
            },
            {//删除
                afterComplete:function (response) {
                    var msg = response.responseJSON.msg;
                    $("#albumMsgId").show().html(msg);
                    setTimeout(function(){
                        $("#albumMsgId").hide()
                    }, 3000);
                }
            }
        );
    })

    function addSubGrid(subGidId,album_id) {
        var subGridTableId = subGidId + "table";
        var subGridPagerId = subGidId + "pager";
        $("#"+subGidId).html(
            "<table id='"+subGridTableId+"' class=\"table table-striped\"></table>" +
            "<div id='"+subGridPagerId+"' style=\"height: 50px\"></div>")

        $("#"+subGridTableId).jqGrid({
            url: "${pageContext.request.contextPath}/chapter/show?album_id="+album_id,
            editurl: "${pageContext.request.contextPath}/chapter/editChapter?album_id=" + album_id,
            datatype: "json",
            colNames:["id","标题","大小","时长","上传时间","操作"],
            colModel:[
                {name:"id"},
                {name:"title",editable:true},
                {name:"size"},
                {name:"duration"},
                {name:"createTime"},
                /*{name:"audio",editable:true,edittype:"file",},*/
                {name:"audio",editable:true,edittype:"file",
                    formatter:function (cellValue,options,value) {
                        return "<a href='#' onclick=\"playAudio('"+cellValue+"')\"><span class='glyphicon glyphicon-play-circle'></span></a>&nbsp;&nbsp;&nbsp;" +
                            "<a href='#' onclick=\"downLoadAudio('"+cellValue+"')\"><span class='glyphicon glyphicon-download'></span></a>";
                    }
                },
            ],
            pager: "#"+subGridPagerId,
            rowNum: 5,
            rowList: [3, 4, 7],
            viewrecords: true,
            height: '200px',
            multiselect: true,
            rownumbers: true,
            viewrecords: true,
            styleUI: "Bootstrap",
            autowidth: true
        }).jqGrid("navGrid", "#" + subGridPagerId, {addtext:"添加",edittext:"修改",deltext:"删除",search:false},
            {},
            {   closeAfterAdd:true,
                afterSubmit: function (response) {
                    var chapterId = response.responseJSON.chapterId
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/chapter/upload",
                        type: "post",
                        fileElementId: "audio",
                        data: {chapterId: chapterId},
                        success: function () {
                        }
                    })
                    return response;
                }
            }
        )
    }
    function playAudio(cellvalue) {
       $("#playAudio").modal("show")
        $("#audioId").attr("src","${pageContext.request.contextPath}/upload/audio/"+cellvalue);
    }
    function downLoadAudio(cellvalue) {
        location.href="${pageContext.request.contextPath}/chapter/downLoadAudio?audioName="+cellvalue;
    }
</script>
<h3>专辑管理</h3>
<div>
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab"
                                                  data-toggle="tab"><b>专辑信息</b></a></li>
    </ul>
</div>

<div id="playAudio" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
       <audio id="audioId" src="" controls></audio>
    </div>
</div>
<table id="albumList" class="table table-striped"></table>
<div id="albumPager" style="height: 50px"></div>
<div id="albumMsgId" class="alert alert-warning alert-dismissible" role="alert" style="display:none">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <strong>Warning!</strong>
</div>