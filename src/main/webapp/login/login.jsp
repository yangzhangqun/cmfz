<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"  pageEncoding="UTF-8" %>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap Login Form Template</title>
    <!-- CSS -->
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="assets/css/form-elements.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="shortcut icon" href="assets/ico/favicon.png">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="assets/ico/apple-touch-icon-57-precomposed.png">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/jquery.backstretch.min.js"></script>
    <script src="assets/js/scripts.js"></script>
    <script src="../boot/js/jquery.validate.min.js"></script>
    <script src="../boot/js/jquery.validate.min.js"></script>
    <script>
        $(function(){
            $("#captchaImage").click(function(){
                $("#captchaImage").prop("src","${pageContext.request.contextPath}/code/code?time="+new Date().getTime());
            });
            var flag1=false;
            var flag2=false;
            var flag3=false;
            $("#form-username").blur(function () {
                if($("#form-username").val()==""){
                    $("#span1").html("<span style='color: red'>用户名不能为空！</span>");
                    return flag1;
                }else{
                    $("#span1").empty();
                    flag1=true;
                    return flag1;
                }
            });
            $("#form-password").blur(function () {
                if($("#form-password").val()==""){
                    $("#span2").html("<span style='color: red'>密码不能为空！</span>");
                    return flag2;
                }else{
                    $("#span2").empty();
                    flag2=true;
                    return flag2;
                }
            });
            $("#form-code").blur(function () {
                if($("#form-code").val()==""){
                    $("#span3").html("<span style='color: red'>验证码不能为空！</span>");
                    return flag3;
                }else{
                    $("#span3").empty();
                    flag3=true;
                    return flag3;
                }
            });

                $("#loginButtonId").click(function(){
                    var username=  $("#form-username").val();
                    var password=  $("#form-password").val();
                    var code=  $("#form-code").val();
                    if(flag1&&flag2&flag3){
                        $.ajax({
                            url:"${pageContext.request.contextPath}/admin/login",
                            data:"username="+username+"&password="+password+"&code="+code,
                            dataType:"json",
                            type:"post",
                            success:function(map){
                                var ret=eval(map);
                                var message= ret.message;
                                if(message!=null){
                                    $("#span").html(message);
                                }else{
                                    location.href="${pageContext.request.contextPath}/login/main.jsp";
                                }
                            }
                        });
                    }
                });
        });
    </script>
</head>

<body>

<!-- Top content -->
<div class="top-content">

    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <h1><strong>CMFZ</strong> Login Form</h1>
                    <div class="description">
                        <p>
                            <a href="#"><strong>CMFZ</strong></a>
                        </p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="form-top" style="width: 450px">
                        <div class="form-top-left">
                            <h3>Login to showall</h3>
                            <p><span id="span"></span></p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-key"></i>
                        </div>
                    </div>
                    <div class="form-bottom" style="width: 450px">
                        <form role="form" action="" method="post" class="login-form" id="loginForm">
                            <span id="msgDiv"></span>
                            <div class="form-group">
                                <label class="sr-only" for="form-username">Username</label>
                                <input type="text" name="username" placeholder="请输入用户名..."
                                       class="form-username form-control" id="form-username">
                                <span id="span1"></span>
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-password">Password</label>
                                <input type="password" name="password" placeholder="请输入密码..."
                                       class="form-password form-control" id="form-password">
                                <span id="span2"></span>
                            </div>
                            <div class="form-group">
                                <img id="captchaImage" style="height: 48px" class="captchaImage"
                                     src="${pageContext.request.contextPath}/code/code">
                                <input style="width: 289px;height: 50px;border:3px solid #ddd;border-radius: 4px;"
                                       type="test" name="enCode" id="form-code">
                                <span id="span3"></span>
                            </div>
                            <input type="button" style="width: 400px;border:1px solid #9d9d9d;border-radius: 4px;" id="loginButtonId" value="登录">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>


</body>

</html>