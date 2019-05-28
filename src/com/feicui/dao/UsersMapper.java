package com.feicui.dao;

import java.util.List;

import com.feicui.model.Users;

public interface UsersMapper {
	
	public int saveUsers(Users user);
	public int delUsers(int id);
	public int updateUsers(Users user);
	public Users findUserById(int id);
	public List<Users> findUsersList();
	public Users login(Users user);
	public Users findUserByCode(String code);
	public Users findUserByName(String username);
}
