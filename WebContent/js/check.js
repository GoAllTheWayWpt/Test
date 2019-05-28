//改变验证码的函数
function change(){
	//htmldom：js操作html标签的方式
	//获取验证码img标签
	var obj=document.getElementById("im");
	//改变验证码路径 实现验证码请求刷新
	obj.src="${pageContext.request.contextPath }/checkImg?date="+new Date();
}
//对用户名校验是否存在
function checkname(){
	var f=false;
	//javascript所有的变量都是以var定义的
	//javascript的变量属于弱类型
	//获取用户输入的用户名
	var name = document.getElementById("username").value;
	//去除字符串两端空格
	name=name.trim();
	//判断是否为空
	if(name==""){
		document.getElementById("username_span").innerHTML="用户名不能为空";
	}else if(name.length<6||name.length>16){
		document.getElementById("username_span").innerHTML="用户名的长度为6-16位";
	}
	else{
		document.getElementById("username_span").innerHTML="";
		//校验用户名是否存在
		//使用ajax异步校验用户名
		$.ajax({
			url:"/bookshopping/checkName",
			type:"post",
			data:{"username":name},
			dataType:"json",
			async:false,
			success:function(data){
				if(data.msg=="false"){
					document.getElementById("username_span").innerHTML="用户名已存在";
				}else{
					document.getElementById("username_span").innerHTML="用户名可用";
					f=true;
				}
			}
		});	
		
	}
	return f;
}
function checkPassword(){
	var password=document.getElementById("password").value;
	var reg=/^[a-zA-Z]\w{5,15}$/;
	var f = reg.test(password);
	if(f==false){
		document.getElementById("password_span").innerHTML="密码格式错误";
		return false;
	}else{
		document.getElementById("password_span").innerHTML="";
		return true;
	}
}
function checkRepassword(){
	var password=document.getElementById("password").value;
	var repassword=document.getElementById("repassword").value;
	if(password==repassword){
		document.getElementById("repassword_span").innerHTML="";
		return true;
	}else{
		document.getElementById("repassword_span").innerHTML="两次输入密码不一致";
		return false;
	}
}
function checkNickname(){
	var nickname = document.getElementById("nickname").value;
	var reg=/^[\u4e00-\u9fa5]{2,6}$/
	var f = reg.test(nickname);
	if(f==false){
		document.getElementById("nickname_span").innerHTML="昵称格式错误";
		return false;
	}else{
		document.getElementById("nickname_span").innerHTML="";
		return true;
	}
}

//验证邮箱
function checkEmail(){
	var email=document.getElementById("email").value;
	//两种引用正则表达式方式
	//var reg = new RegExp("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$");
	var reg=/^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/;
	var f = reg.test(email);
	if(f==false){
		document.getElementById("email_span").innerHTML="邮箱格式错误";
		return false;
	}else{
		document.getElementById("email_span").innerHTML="";
		return true;
	}
}
function check(){
	var name = checkname();
	var password = checkPassword();
	var repassword = checkRepassword();
	var nickname = checkNickname();
	var email=  checkEmail();
	if(name&&password&&repassword&&nickname&&email)
		return true;
	else
		return false;
}	