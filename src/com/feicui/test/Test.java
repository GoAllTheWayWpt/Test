package com.feicui.test;
import com.feicui.utils.MailUtils;;
public class Test {
	public static void main(String[] args) throws Exception {
		String code = "<a href='http://localhost:8080/bookshopping/showIndex.action";
		System.out.println("发送");
		MailUtils.sendMail("user1@zhao.com", code);
		System.out.println("发送成功");
	}

}
