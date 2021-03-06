package com.revature.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.beans.Reimbursement;
import com.revature.beans.User;

public class ERS_DAO implements DAO {

	private final static String url = "jdbc:oracle:thin:@hien1701java.c5xsr9dthznr.us-east-1.rds.amazonaws.com:1521:orcl";
	private final static String username = "hien_1701java";
	private final static String password = "p4ssw0rd";
	
	//Get all information of the user who logged in, search by username
	@Override
	public User retrieveUserInfo(String uname) {
		try (Connection con = DriverManager.getConnection(url, username, password);) {
			User u = null;
			String sql = "SELECT * FROM ers_users eu JOIN ers_user_roles eur ON eu.ur_id = eur.ur_id WHERE eu.u_username = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, uname);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				u = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getInt(7), rs.getString(9));
			}
			return u;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//Get all information of the user who logged in, search by user ID
	@Override
	public User retrieveUserInfo(int uid) {
		try (Connection con = DriverManager.getConnection(url, username, password);) {
			User u = null;
			String sql = "SELECT * FROM ers_users eu JOIN ers_user_roles eur ON eu.ur_id = eur.ur_id WHERE eu.u_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, uid);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				u = new User(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getLong(7), rs.getString(9));
			}
			return u;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//Get all information of the user who logged in, search by user email
		@Override
		public User retrieveUserInfoByEmail(String email) {
			try (Connection con = DriverManager.getConnection(url, username, password);) {
				User u = null;
				String sql = "SELECT * FROM ers_users eu JOIN ers_user_roles eur ON eu.ur_id = eur.ur_id WHERE eu.u_email = ?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, email);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					u = new User(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
							rs.getString(6), rs.getLong(7), rs.getString(9));
				}
				return u;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}

	//Get email address of the reimbursement's author to send new status email
	@Override
	public String retrieveEmailInfo(long reID) {
		try (Connection con = DriverManager.getConnection(url, username, password);) {
			String email = null;
			String sql = "SELECT eu.u_email FROM ers_users eu JOIN ers_reimbursements er ON eu.u_id = er.u_id_author WHERE er.r_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, reID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				email = rs.getString("u_email");
			}
			return email;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//Get all users that are employee
	@Override
	public List<User> getAllEmployee() {
		try (Connection con = DriverManager.getConnection(url, username, password);) {
			User u = null;
			List<User> employeeList = new ArrayList<>();
			String sql = "SELECT * FROM ers_users eu JOIN ers_user_roles eur ON eu.ur_id = eur.ur_id WHERE eu.ur_id = 1";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				u = new User(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getLong(7), rs.getString(9));
				employeeList.add(u);
			}
			return employeeList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//Get the list of all reimbursement requests
	@Override
	public List<Reimbursement> getAllReimbursements() {
		try (Connection con = DriverManager.getConnection(url, username, password);) {
			Reimbursement r = null;
			List<Reimbursement> reimbursementList = new ArrayList<>();
			String sql = "SELECT * FROM ers_reimbursements er JOIN ers_reimbursement_status ers ON er.rs_status_id = ers.rs_id "
					+ "JOIN ers_reimbursement_type ert ON er.rt_type_id = ert.rt_id";

			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				byte[] imgData = null;
				if (rs.getBlob("r_receipt") != null)
					imgData = rs.getBlob("r_receipt").getBytes(1, (int) (rs.getBlob("r_receipt")).length());
				r = new Reimbursement(rs.getLong("r_id"), rs.getDouble("r_amount"), rs.getString("r_description"),
						imgData, rs.getTimestamp("r_submitted"), rs.getTimestamp("r_resolved"),
						rs.getLong("u_id_author"), rs.getLong("u_id_resolver"), rs.getLong("rt_type_id"),
						rs.getLong("rs_status_id"), rs.getString("rt_type"), rs.getString("rs_status"));
				reimbursementList.add(r);
			}
			return reimbursementList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//Get the list of all reimbursement requests of one employee
	@Override
	public List<Reimbursement> getReimbursementsByEmployee(User u) {
		try (Connection con = DriverManager.getConnection(url, username, password);) {
			Reimbursement r = null;
			List<Reimbursement> reimbursementList = new ArrayList<>();
			String sql = "SELECT * FROM ers_reimbursements er JOIN ers_reimbursement_status ers ON er.rs_status_id = ers.rs_id "
					+ "JOIN ers_reimbursement_type ert ON er.rt_type_id = ert.rt_id WHERE er.u_id_author = ?";

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, u.getUser_id());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				byte[] imgData = null;
				if (rs.getBlob("r_receipt") != null)
					imgData = rs.getBlob("r_receipt").getBytes(1, (int) (rs.getBlob("r_receipt")).length());
				r = new Reimbursement(rs.getLong("r_id"), rs.getDouble("r_amount"), rs.getString("r_description"),
						imgData, rs.getTimestamp("r_submitted"), rs.getTimestamp("r_resolved"),
						rs.getLong("u_id_author"), rs.getLong("u_id_resolver"), rs.getLong("rt_type_id"),
						rs.getLong("rs_status_id"), rs.getString("rt_type"), rs.getString("rs_status"));
				reimbursementList.add(r);
			}
			return reimbursementList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	//Get stored user password out of db to compare when a user try to log in
	@Override
	public String getStoredPass(String uname) {
		String result = null;

		try (Connection con = DriverManager.getConnection(url, username, password);) {
			String sql = "SELECT u_password FROM ers_users WHERE u_username = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, uname);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				result = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//Get the user id ( User Id is auto-generated when create new employee)
	@Override
	public long getUserID(String uname) {
		long result = 0;

		try (Connection con = DriverManager.getConnection(url, username, password);) {
			String sql = "SELECT u_id FROM ers_users WHERE u_username = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, uname);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				result = rs.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//Manager can insert new employee with random password
	@Override
	public void insertNewEmployee(User u) {
		try (Connection con = DriverManager.getConnection(url, username, password);) {
			String sqlInsert = "{call insert_new_employee(?,?,?,?,?)}";
			PreparedStatement ps = con.prepareStatement(sqlInsert);
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getFirstname());
			ps.setString(4, u.getLastname());
			ps.setString(5, u.getEmail());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//To create any user
	@Override
	public void createNewUser(User u) {
		try (Connection con = DriverManager.getConnection(url, username, password);) {
			String sqlInsert = "{call insert_new_user(?,?,?,?,?,?)}";
			PreparedStatement ps = con.prepareStatement(sqlInsert);
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getFirstname());
			ps.setString(4, u.getLastname());
			ps.setString(5, u.getEmail());
			ps.setLong(6, u.getRole_id());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Users can update they profile
	@Override
	public void updateUser(User u) {
		try (Connection con = DriverManager.getConnection(url, username, password);) {
			String sqlInsert = "{call update_user(?,?,?,?,?,?)}";
			PreparedStatement ps = con.prepareStatement(sqlInsert);
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getFirstname());
			ps.setString(4, u.getLastname());
			ps.setString(5, u.getEmail());
			ps.setLong(6, u.getRole_id());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Employee create new reimbursement requests
	@Override
	public void createNewReimbursement(Reimbursement r) {
		try (Connection con = DriverManager.getConnection(url, username, password);) {
			String sqlInsert = "{call create_new_reimbursement(?,?,?,?,?,?)}";
			PreparedStatement ps = con.prepareStatement(sqlInsert);
			ps.setDouble(1, r.getR_amount());
			ps.setString(2, r.getR_description());
			ps.setBytes(3, r.getR_receipt_byte());
			ps.setTimestamp(4, r.getR_submitted());
			ps.setLong(5, r.getUid_author());
			//System.out.println(r.getR_type_id());
			ps.setLong(6, r.getR_type_id());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Manager change statuses for one of more reimbursements
	@Override
	public void updateReimbursement(Reimbursement r) {
		try (Connection con = DriverManager.getConnection(url, username, password);) {
			String sqlInsert = "{call update_reimbursement(?,?,?,?)}";
			PreparedStatement ps = con.prepareStatement(sqlInsert);
			ps.setLong(1, r.getR_id());
			ps.setTimestamp(2, r.getR_resolved());
			ps.setLong(3, r.getUid_resolver());
			ps.setLong(4, r.getR_status_id());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Delete any user by username
	@Override
	public void deleteUser(String uname) {

		try (Connection con = DriverManager.getConnection(url, username, password);) {
			String sql = "DELETE FROM ers_users WHERE u_username = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, uname);
			ResultSet rs = ps.executeQuery();
			//con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Delete all reimbursement records belong to one username
	@Override
	public void deleteReimbursement(String uname) {
		long uid = new ERS_DAO().getUserID(uname);
		try (Connection con = DriverManager.getConnection(url, username, password);) {
			String sql = "DELETE FROM ers_reimbursements WHERE u_id_author = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, uid);
			ResultSet rs = ps.executeQuery();
			//con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
