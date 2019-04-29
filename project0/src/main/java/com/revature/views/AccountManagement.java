package com.revature.views;

import com.revature.beans.User;
import com.revature.services.UserService;
import com.revature.util.ScannerUtil;

public class AccountManagement extends Login implements View {

	User user = null;
	
	public AccountManagement(User u) {
		super();
		this.user = u;
	}
	
	UserService userServ = new UserService();
	
	@Override
	public View printOptions() {

		System.out.println("Welcome to Account Management, " + user.getFirstName());
		
		
		System.out.println("1. Open an Account");
		System.out.println("2. Check Balance");
		System.out.println("3. Make a Payment");
		System.out.println("4. Make a deposit");
		System.out.println("0. Back");

		int selection = ScannerUtil.getNumericChoice(5);

		switch (selection) {
		case 1:
			//userServ.openAccount(user);
			return this;
		case 2:
			userServ.checkBalance(user);
			return this;
		case 3:
			userServ.makePayment(user);
			return this;
		case 4:
			userServ.addMoney(user);
			return this;
		case 5:
		    
			
		default:
			return null;
		}
		
	}

}
