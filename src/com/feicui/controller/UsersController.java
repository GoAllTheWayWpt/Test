package com.feicui.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feicui.model.Users;
import com.feicui.service.UsersService;
import com.feicui.utils.DateUtils;
import com.feicui.utils.Md5Utils;
import com.feicui.utils.UUIDUtils;

@Controller
public class UsersController {
	@Autowired
	private UsersService userService;

	@RequestMapping("showReg")
	public String showReg(String type,HttpServletRequest request){
		if(type != null && type.equals("1")){
			request.setAttribute("msg", "两次输入密码不一致");
		}else if(type != null && type.equals("2")){
			request.setAttribute("msg", "验证码错误");
		}else if(type != null && type.equals("3")){
			request.setAttribute("msg", "注册失败");
		}
		return "reg";
	}
	
	@RequestMapping("reg")
	public  String reg(Users user,String repassword,String checkcode,HttpServletRequest request){
		user.setRole("user");
		user.setUpdatetime(DateUtils.format(new Date()));
		user.setState(0);//0:��ʾû�м��1����

		
		//��������ȷ��������бȽ�
		String password = user.getPassword();
		
		if(!password.equals(repassword)){
			return "redirect:showReg.action?type=1";
		}
		
		//ʹ��md5���������
		user.setPassword(Md5Utils.md5(password));
		//����һ��������
		String uuid = UUIDUtils.getUUID();
		user.setActivecode(uuid);
		
		//��֤��֤���Ƿ���ȷ
		//��session�л�ȡ������֤��
		String  checkcode_session = (String)request.getSession().getAttribute("checkcode_session");
		
		//���û��������֤�������ɵ���֤��Ƚ�
		if(!checkcode.equals(checkcode_session)){
			return "redirect:showReg.action?type=2";
		}
		
		//����service���б���
		int num = userService.saveUsers(user);
		if(num == 0){
			return "redirect:showReg.action?type=3";
		}
	
		return "redirect:showLogin.action?type=1";
	}
	
	@RequestMapping("showLogin")
	public String showLogin(String type,Model model){
		if("1".equals(type)){
			model.addAttribute("msg", "注册成功，请登录");
		}else if("2".equals(type)) {
			model.addAttribute("msg", "用户名或密码不正确");
		}else {
			model.addAttribute("msg","");
		}
			
		return "login";
	}
	//激活账号的方法
	@RequestMapping("activation")
	public String activation(String code) {
		//根据激活码查询用户
		Users user=userService.findUserByCode(code);
		
		//将账户的状态改为1
		user.setState(1);
		userService.updateUsers(user);
		return "login";
	}
	//异步校验用户名
	@RequestMapping("checkName")
	@ResponseBody
	public String checkName(String username) {
		System.out.println(username);
		//根据用户名查询是否存在该用户名
		Users user=userService.findUserByName(username);
		
		//当对象不为空，说明用户名存在
		if(user!=null) {
			return "{\"msg\":\"false\"}";
		}
		return "{\"msg\":\"true\"}";
	}
	//登录
	@RequestMapping("login")
	public String login(Users user,String autologin,String remember,HttpServletResponse response,HttpServletRequest request) throws UnsupportedEncodingException {
		user.setPassword(Md5Utils.md5(user.getPassword()));
		System.out.println(user);
		 Users users = userService.login(user);
		
		if(users==null) {
			return "redirect:showLogin?type=2";
		}
		//记住用户
		//判断用户是否勾选记住用户
		if(remember!=null&&remember.equals("on")) {
			//将用户名以cookie的形式发送到客户端
			Cookie cookie = new Cookie("username",URLEncoder.encode(user.getUsername(), "utf-8"));
			//cookie默认是会话级别，会随着浏览器的关闭消失
			//通过setMaxAge设置cookie的存活时间
			cookie.setMaxAge(60*60*24*7);
			response.addCookie(cookie);
			Cookie cookie1 = new Cookie("save","on");
			//cookie默认是会话级别，会随着浏览器的关闭消失
			//通过setMaxAge设置cookie的存活时间
			cookie.setMaxAge(60*60*24*7);
			response.addCookie(cookie1);
		}else {
			//将用户名以cookie的形式发送到客户端
			Cookie cookie = new Cookie("username","");
			//cookie默认是会话级别，会随着浏览器的关闭消失
			//通过setMaxAge设置cookie的存活时间
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			Cookie cookie1 = new Cookie("save","");
			//cookie默认是会话级别，会随着浏览器的关闭消失
			//通过setMaxAge设置cookie的存活时间
			cookie.setMaxAge(0);
			response.addCookie(cookie1);
		}
		//自动登录
		if(autologin!=null&&autologin.equals("on")) {
			Cookie cookie=new Cookie("autologin",URLEncoder.encode(user.getUsername(), "utf-8")+"-"+user.getPassword());
			cookie.setMaxAge(60*60*24*7);
			response.addCookie(cookie);
			
		}
		//将登录的用户存放到session中
		request.getSession().setAttribute("user", users);
		//跳转admin页面
		if(users.getRole().equals("admin")) {
			return "redirect:showAdminIndex";
		}
		return "redirect:showIndex";
	}
	
	//退出登录
	@RequestMapping("logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		//删除session当中存放的登录对象
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		//销毁session
		//session.invalidate();
		//去除自动登陆的功能
		//将自动登录cookie中的信息清空
		
		Cookie cookie = new Cookie("autologin","");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return "redirect:showIndex";
	}
}
