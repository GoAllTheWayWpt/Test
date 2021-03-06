<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!--将jstl标签库引入到当前的页面来  -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>购物车</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/public.css">
</head>

<body>
    <div class="topbar">
        <div class="topbar_content">
            <div class="topbar_content_left">
                欢迎光临Estore图书商城
            </div>
            <div class="topbar_content_right">
                <ul>
                    <c:if test="${empty user }">
						<li><a href="${pageContext.request.contextPath }/showLogin"
							style="color: rgb(241, 187, 10)">亲，请登录</a></li>
						<li><a href="${pageContext.request.contextPath }/showReg"
							style="color: rgb(241, 187, 10)">免费注册</a></li>
					</c:if>
					<c:if test="${!empty user }">
						<li><a href="#" style="color: rgb(241, 187, 10)">${user.nickname }</a>
						</li>
						<li><a href="${pageContext.request.contextPath }/logout"
							style="color: rgb(241, 187, 10)">退出登录</a></li>
					</c:if>
					<li><a href="${pageContext.request.contextPath }/showIndex">首页</a>
					</li>
					<li>|</li>
					<li><a href="${pageContext.request.contextPath }/showCart"">购物车</a></li>
					<li>|</li>
					<li><a href="${pageContext.request.contextPath }/showOrder">我的订单</a></li>
                </ul>
            </div>
        </div>
    </div>

    <div class="cart_content">
        <div class="header">
            <div class="header_png"></div>
            <div class="estore" align="center">
                <h1 align="center" style="font-size: 28px;color: #87520E;margin: 40px 0 10px 0;">Estore图书商城</h1>
                <img src="images/TB1yeWeIFXXXXX5XFXXuAZJYXXX-210-210.png_50x50.jpg" style="border-radius:50%">
                <br/>
                <font size="2">Hi!你好</font>
                <br/>
                <br/>
                <button>注册</button>
                <button>登录</button>
            </div>
        </div>

        <div align="center" id="cart">
            <table style="width:100%;" class="caption">
                <tr>
                    <td style="width:25%;">商品名称</td>
                    <td style="width:25%;">商品单价</td>
                    <td style="width:22%;">购买的数量</td>
                    <td style="width:20%;">可购买数量</td>
                    <td>订单操作</td>
                </tr>
            </table>
            <c:if test="${empty cart }">
            <table border="1" class="maintable"><tr><td>购物车无信息</td></tr></table>
            </c:if>
            <c:if test="${!empty cart }">
             
             <table border="1" class="maintable">
             <c:set var="money" value="0"></c:set>
             
             <c:forEach items="${cart }" var="pro">
                <tr>
                    <td style="width:25%;">商品名称：${pro.key.name }</td>
                    <td style="width:25%;">商品单价:${pro.key.price }</td>
                    <td style="width:22%;">
                        <input type="button" value="-" onclick="changeCount('${pro.key.id}',${pro.value-1 },${pro.key.pnum })" class="btn">

                        <input type='text' value="${pro.value }" style="text-align:center;color:#87520E;width:120px;height:25px;" onkeydown="numbText(event);"
                            onchange="changeCount('${pro.key.id}',${pro.value },${pro.key.pnum })">

                        <input type="button" value='+' onclick="changeCount('${pro.key.id}',${pro.value+1 },${pro.key.pnum })" class="btn">
                    </td>


                    <td style="width:20%;">可购买数量:${pro.key.pnum }</td>
                    <td>
                        <a href="" onclick="changeCount('${pro.key.id}',0,${pro.key.pnum })">删除</a>

                    </td>
                </tr>
                <c:set var="money" value="${pro.key.price*pro.value+money }"></c:set>
                </c:forEach>
                
                <tr>
                    <td colspan="5" align="right">总价:￥${money }元</td>
                </tr>
                <tr>
                    <td colspan="5" align="right">
                        <button onclick="gotoorder()" style="cursor:pointer;background:#87520E;border-radius:5px;line-height:40px;border:none;width:160px;color:white;font-size:18px;">进入结算</button>
                    </td>
                </tr>
                </table>
            </c:if>
           
             

        </div>





        </div>

    <div class="footer1">
        <p align="center">
            总部地址:北京市海淀区小南庄怡秀园甲一号亿德大厦10层 电话：010-61943240
        </p>
        <p align="center"> Copyright © 2005-2020 北京翡翠教育科技有限公司，All Rights Reserved 京ICP备12036804号-23</p>
    </div>

</body>
<script type="text/javascript">
function changeCount(id,count,max){
	if(count<0){
		count=1;
	}
	else if(count==0){
		var f=confirm("确定删除吗？");
		if(!f){
			return;
		}
		
	}
	else if(count>max){
		count=max;
		alere("最多购买"+max+"件商品");
	}
	window.location.href="${pageContext.request.contextPath }/updateCart?id="+id+"&count="+count;
}
 function gotoorder(){
	 window.location.href="${pageContext.request.contextPath }/showCreateOrder";
 }
</script>
</html>