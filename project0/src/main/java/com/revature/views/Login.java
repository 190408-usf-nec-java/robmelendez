package com.revature.views;

import com.revature.beans.User;
import com.revature.daos.UserDao;
import com.revature.services.UserService;
import com.revature.util.ScannerUtil;

public class Login implements View {

	UserService userLog = new UserService();
	User u = null;
	
	@Override
	public View printOptions() {
		System.out.println("Login Menu");
		System.out.println("1. Login");
		System.out.println("0. Back");

		int selection = ScannerUtil.getNumericChoice(2);

		switch (selection) {
		case 1:
			int id = userLog.userLogin();
		    if (id == 0) return this;
		    else
		    u = UserDao.getUserById(id);
			return new AccountManagement(u);
		    		
			
		default:
			return new MainMenu();
		}
	}
}
