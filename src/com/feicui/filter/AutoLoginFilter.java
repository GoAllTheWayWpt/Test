package com.feicui.filter;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.feicui.dao.UsersMapper;
import com.feicui.model.Users;
import com.feicui.service.UsersService;
import com.feicui.service.impl.UsersServiceImpl;
import com.feicui.utils.CookieUtils;

/**
 * 
 * @author 过滤器 每一个过滤器类应该实现filter接口 可以使用过滤器拦截所有请求，对请求进行验证，如果请求合法，放行，如果不合法，进行拦截
 *
 */
public class AutoLoginFilter implements Filter {

	private UsersMapper userMapper;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	// 在对象销毁时调用此方法

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		// 验证
		System.out.println("拦截到用户请求");
		// 向下转型
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		// 获取用户的请求路径
		String uri = request.getRequestURI();
		String contextPath =request.getContextPath();
		System.out.println(contextPath);
		// 对于登录的请求，注册的请求不需要自动登录
		if (uri.indexOf("login") == -1 && uri.indexOf("Login") == -1 && uri.indexOf("reg") == -1
				&& uri.indexOf("Reg") == -1) {

			// 获取session
			HttpSession session = request.getSession();
			// 获取session中登录的用户对象
			Users users = (Users) session.getAttribute("user");
			// 判断用户是否登录
			// 如果users为null，说明没有登录
			if (users == null) {
				// 自动登录
				// 获取所有cookie
				Cookie[] cookies = request.getCookies();
				// 获取所需要的cookie
				Cookie cookie = CookieUtils.getCookie(cookies, "autologin");
				if (cookie != null) {
					// 获取cookie中存放的用户信息
					String autoLogin = cookie.getValue();
					System.out.println(autoLogin);
					// 判断cookies是否有信息，如果有
					if (autoLogin != null && !autoLogin.equals("")) {
						//autologin存放的用户名密码格式为“用户名-密码”，需要拆开
						String[] names = autoLogin.split("-");
						for (String st : names) {
							System.out.println(st);
						}
						Users user = new Users();
						user.setUsername(URLEncoder.encode(names[0], "utf-8"));
						user.setPassword(names[1]);
						// 调用userMapper进行登录
						Users users2 = userMapper.login(user);

						// 将登录的对象存放到session中
						session.setAttribute("user", users2);
					}
				}else {
					
					//如果请求的是与订单的请求，要求用户必须登录
					if(uri.indexOf("order")!=-1||uri.indexOf("Order")!=-1){
						//跳转到登录页面
						response.sendRedirect("showLogin");
						return;
					}
					
					
					
				}
			}
		}

		// 放行

		chain.doFilter(req, resp);
	}

	// 在对象创建时自动调用init方法对对象初始化
	@Override
	public void init(FilterConfig config) throws ServletException {
		// 获取spring容器
		
		//ServletContext：代表整个web应用
		ServletContext context = config.getServletContext();
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(context);
		userMapper = ac.getBean(UsersMapper.class);
	}

}
