package com.revature.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.revature.beans.User;
import com.revature.daos.UserDao;
import com.revature.util.ConnectionUtil;
import com.revature.util.ScannerUtil;

public class UserService {

	public UserDao userDao=new UserDao();

	public void setDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * Handles creation workflow for User bean
	 */
	public void createUserAccount() {
		System.out.println("Please enter your first name: ");
		String firstName = ScannerUtil.getLine();

		System.out.println("Please enter your last name: ");
		String lastName = ScannerUtil.getLine();

		System.out.println("Please enter a username: ");
		String userName = ScannerUtil.getLine();

		System.out.println("Please enter a password: ");
		String passWord = ScannerUtil.getLine();

		User user = new User(0, firstName, lastName, userName, passWord);

		userDao.saveUser(user);
		System.out.println(user);

	}

	public int userLogin() {
		Set<String> userNames = new HashSet<>();
		Set<String> passwords = new HashSet<>();
		Boolean userNameExists = false;

		System.out.println("Please enter a username: ");
		String userName = ScannerUtil.getLine();

		System.out.println("Please enter a password: ");
		String passWord = ScannerUtil.getLine();
		int idCheck = 0;

		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select username, password, id from users";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while(rs.next() && userNameExists == false) {
				userNames.add(rs.getString(1));
				passwords.add(rs.getString(2));		
				if (userNames.contains(userName) && passwords.contains(passWord))  {					
					userNameExists = true;
					idCheck = rs.getInt(3);
					System.out.println("Welcome back, " + userName);		
				}		
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}		

		if (userNameExists == false) 
		{ 
			System.out.println("Please enter correct credentials.");
		}
		return idCheck ;
	}


	public double checkBalance(User u) {
		double balance = 0;
		//select balance from accounts where account_id = (select account_id from ownership where ownership.id = 1);
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select balance from accounts where account_id = (select account_id from ownership where ownership.id = " + u.getId() + ")";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				balance += rs.getDouble(1);	
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Your balance across all your accounts is: $" + balance);
		return balance;
	}
	

	public User addMoney(User u) {
		
		
		System.out.println("Please enter the amount you are depositing: ");
		double add = ScannerUtil.getDouble();
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "update accounts set balance = balance + " + add + "where account_id = " + u.getId();
			PreparedStatement ps = conn.prepareStatement(sql);
			double rs = ps.executeUpdate();
	
			

		}catch(SQLException e) {
			e.printStackTrace();
		
		}
		System.out.println("Your primary account balance has increased by $" + add + "\n");
		return u;
	}
	
	public User openAccount(User u) {


//		insert into accounts (account_id , balance)
//		values ('1', '0') returning account_id;
//
//		insert into ownership (id, account_id)
//		values (1, 1)

		try(Connection conn = ConnectionUtil.getConnection()) {
			int id = u.getId();
			String sql = "insert into accounts (account_id , balance) values (" + id + ", '0')";					
			PreparedStatement ps = conn.prepareStatement(sql);
			int rs = ps.executeUpdate();

			String sql2 = "insert into ownership (id, account_id)     values (" + id + ", " + id + ")" ;
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			int rs2 = ps2.executeUpdate();

		}catch(SQLException e) {
			e.printStackTrace();

		}
		System.out.println("Account has been created" + "\n");
		return u;
	}
	
	public User makePayment(User u) {

		System.out.println("Please enter the recipient's account number: ");
		int acc = ScannerUtil.getInt();
		
		System.out.println("Please enter the payment amount: ");
		double amt = ScannerUtil.getDouble();
		
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "update accounts set balance = balance - " + amt + " where account_id = " + u.getId(); 	
			PreparedStatement ps = conn.prepareStatement(sql);
			double rs = ps.executeUpdate();

			String sql2 = "update accounts set balance = balance + " + amt + " where account_id = " + acc; 	
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			double rs2 = ps2.executeUpdate();


		}catch(SQLException e) {
			e.printStackTrace();

		}
		System.out.println("A payment of $" + amt + " has been made to account #" + acc +"\n");
		return u;
	}
}



