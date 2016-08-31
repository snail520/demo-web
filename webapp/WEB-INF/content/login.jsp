<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录页面</title>
<script type="text/javascript" src="${ctx}/res/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
function userLogin(){
	var userName = $("#userName").val();
	var password = $("#password").val();
	$.ajax({
		   type: "POST",
		   url: "${ctx}/checkLogin",
		   data: "userName="+userName+"&password="+password,
		   dataType : "json",
		   success: function(result){		
		   if(result.state=='0'){
		     	alert("登录成功");
			}else if(result.state=='2'){
				alert("密码错误");
			}
		   else{
				alert("登录失败");
			}
		   }
		});
}
function userRegister(){
	var userName = $("#userName").val();
	var password = $("#password").val();
	$.ajax({
		   type: "POST",
		   url: "${ctx}/userRegister",
		   data: "userName="+userName+"&password="+password,
		   dataType : "json",
		   success: function(result){		
		   if(result.state=='0'){
		     	alert("注册成功");
			}else{
				alert("注册失败");
			}
		   }
		});
}
function userDelete(){
	var id = $("#id").val();
	$.ajax({
		   type: "POST",
		   url: "${ctx}/userDelete",
		   data: "id="+id,
		   dataType : "json",
		   success: function(result){		
		   if(result.state=='0'){
		     	alert("删除成功");
			}else{
				alert("删除失败");
			}
		   }
		});
}
</script>
</head>
<body>
   <form id="login" name="login" action="" method="post">
     <table>
       <tr>
         <td>用户名:<input id="userName" name="userName" type="text"/></td>
       </tr>
       <tr>
        <td>密码:<input id="password" name="password" type="password"/></td>
       </tr>
       <tr>
        <td>id:<input id="id" name="id" type="text"/></td>
       </tr>
       <tr>
         <td><input type="button" value="登录" onclick="userLogin();"/></td>
         <td><input type="button" value="注册" onclick="userRegister();"/></td>
         <td><input type="button" value="删除" onclick="userDelete();"/></td>
       </tr>
     </table>
   </form>
</body>
</html>