package org.architech.assignment.action;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

import org.architech.assignment.model.Account;
import com.opensymphony.xwork2.ActionSupport;

public class SaveRegistrationAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private static final String CREATE_TABLE_STATEMENT = "CREATE TABLE accounts (login VARCHAR(50) NOT NULL, password VARCHAR(50) NOT NULL)";
	private static final String INSERT_STATEMENT = "INSERT INTO accounts (login, password) VALUES (?, ?)";
	private static final String SELECT_STATEMENT = "SELECT * FROM accounts WHERE login = ?";	

	private Account account;
	private String submit;
	private static Connection conn;

	public String execute() throws Exception {
		// Save the account to the database
		setupConnection();
		PreparedStatement stmt = conn.prepareStatement(INSERT_STATEMENT);
		stmt.setString(1, account.getLogin());
		stmt.setString(2, account.getPassword());
		stmt.executeUpdate();
		stmt.close();

		addActionMessage(account.getLogin() + ", " + getText("success.accountCreated"));
		account.setLogin(null);

		return SUCCESS;
	}

	private static void setupConnection() throws SQLException, ClassNotFoundException {
		if (conn == null) {
			String driver = "org.apache.derby.jdbc.EmbeddedDriver";
			String connectionURL = "jdbc:derby:memory:testDB;create=true";
			Class.forName(driver);
			conn = DriverManager.getConnection(connectionURL);

			Statement stmnt = conn.createStatement();
			stmnt.executeUpdate(CREATE_TABLE_STATEMENT);
			stmnt.close();
		}
	}


	public void validate() {
		try {
			setupConnection();

			PreparedStatement stmt = conn.prepareStatement(SELECT_STATEMENT);
			stmt.setString(1, account.getLogin());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				addFieldError("account.login", getText("error.accountExists"));
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			addActionError(getText("error.database"));
		} catch (ClassNotFoundException e) {
			addActionError(getText("error.database"));
		}

		// validate the Name field
		if (account.getLogin().length() < 5 ||
				!account.getLogin().matches("[A-Za-z0-9]+")) {
			addFieldError("account.login", getText("error.accountBadFormat"));
		}

		// validate the Password field
		if (account.getPassword().length() < 8 ||
				(!Pattern.compile("[0-9]").matcher(account.getPassword()).find() ||
						!Pattern.compile("[a-z]").matcher(account.getPassword()).find() ||
						!Pattern.compile("[A-Z]").matcher(account.getPassword()).find())) {
			addFieldError("account.password", getText("error.password"));
		}

		// validate the Password Confirm field
		if (!account.getPasswordConfirm().equals(account.getPassword())) {
			addFieldError("account.passwordConfirm", getText("error.passwordConfirm"));
		}
	}


	public Account getAccount() {
		return account;
	}


	public void setAccount(Account account) {
		this.account = account;
	}

	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

}