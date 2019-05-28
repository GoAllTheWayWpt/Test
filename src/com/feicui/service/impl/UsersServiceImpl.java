package com.feicui.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feicui.dao.UsersMapper;
import com.feicui.model.Users;
import com.feicui.service.UsersService;
import com.feicui.utils.MailUtils;
@Service
public class UsersServiceImpl implements UsersService {
	@Autowired
	private UsersMapper usersMapper;

	@Override
	public int saveUsers(Users user) {
		
		
		int num = usersMapper.saveUsers(user);
		
		try {
			MailUtils.sendMail(user.getEmail(), user.getActivecode());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return num;
	}

	@Override
	public int delUsers(int id) {
		return usersMapper.delUsers(id);
	}

	@Override
	public int updateUsers(Users user) {
		return usersMapper.updateUsers(user);
	}

	@Override
	public Users findUserById(int id) {
		return usersMapper.findUserById(id);
	}

	@Override
	public List<Users> findUsersList() {
		return usersMapper.findUsersList();
	}

	@Override
	public Users login(Users user) {
		return usersMapper.login(user);
	}

	@Override
	public Users findUserByCode(String code) {
		// TODO Auto-generated method stub
		return usersMapper.findUserByCode(code);
	}

	@Override
	public Users findUserByName(String username) {
		// TODO Auto-generated method stub
		return usersMapper.findUserByName(username);
	}

}
