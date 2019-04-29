package com.revature.daos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.revature.beans.User;
import com.revature.util.ConnectionUtil;

public class UserDao {

	public void saveUser(User user) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO users " +
		"(first_name, last_name, userName, passWord) VALUES " + 
		"(?, ?, ?, ?) RETURNING id";
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getUserName());
			ps.setString(4, user.getPassWord());

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				user.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static User getUserById(int id) {
		User user = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT first_name, last_name, username, password FROM users WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String userName = rs.getString("userName");
				String passWord = rs.getString("passWord");
				user = new User(id, firstName, lastName, userName, passWord);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

}