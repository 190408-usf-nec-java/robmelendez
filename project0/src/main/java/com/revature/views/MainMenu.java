package com.revature.views;

import com.revature.services.UserService;
import com.revature.util.ScannerUtil;

public class MainMenu implements View {
	
	UserService userLog = new UserService();
		
	@Override
	public View printOptions() {
		System.out.println("Project0 Banking App");
		System.out.println("Please select an option:");
		System.out.println("1. Login");
		System.out.println("2. Create a New Account");
		System.out.println("3. Quit");
		
		int selection = ScannerUtil.getNumericChoice(3);
		
		switch(selection) {
		case 1: return new Login();
		case 2: return new CreateUser();
		default: return null;
		}
	}

}
