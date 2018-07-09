package mainPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserRegistry {

	private final static UserRegistry instance = new UserRegistry();

	// creates single instance
	public static UserRegistry getInstance() {
		return instance;
	}

	private List<User> userList = new ArrayList<User>();

	// method returns userlist
	public List<User> getUserList() {
		return userList;
	}

	// method populates the user registry - fetches the data from database and
	// stores them in registry
	public void populateRegistry() {

		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:SportVisorDB.db");

			System.out.println("Opened database successfully to register");

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM User;");

			// flag to indicate if the username is in current DB
			// I HATE ECLIPSE

			while (rs.next()) {

				String username = rs.getString("Username");
				String password = rs.getString("Password");
				String firstName = rs.getString("FirstName");
				String lastName = rs.getString("LastName");
				String type = rs.getString("AccountType");
				String email = rs.getString("Email");
				String provName = rs.getString("ProviderName");
				String phoneNr = rs.getString("PhoneNumber");
				// String securityAnswer = rs.getString("securityAnswer");

				if (type.equals("Parent"))
					userList.add(new ParentSV(username, firstName, lastName, email, password));
				else
					userList.add(
							new ActivityProvider(username, firstName, provName, lastName, email, phoneNr, password));

			}

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	// method prints usernames of users in registry
	public void printReg() {
		for (User u : userList) {
			System.out.println(u.getFirstName());
		}
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	// method adds user passed as argument to the UserRegistry
	public void addUserToRegistry(User u) {
		userList.add(u);
	}

	// method finds user based on the username and password passed
	// used in loginController, to verify the login details
	// returns true is user successfully logged in, false otherwise
	public boolean recoverUsername(String email) {
		boolean value = false;
		for (User u : userList) {
			if (u.getEmail().equals(email)) {
				value = true;
				break;
			}
		}
		return value;
	}

	public boolean recoverPassword(String username, String email) {
		boolean value = false;
		for (User u : userList) {
			if (u.getUserName().equals(username)) {
				if (u.getEmail().equals(email)) {
					value = true;
					break;
				}
			}
		}
		return value;
	}

	public boolean verifyUser(String username, String password) {

		User currentlyLoggingIn = null;
		for (User u : userList) {
			if (u.getUserName().equals(username)) {
				currentlyLoggingIn = u;
				break;
			}
		}

		boolean loginSuccessful = false;
		// if there is a user in UserRegistry with username provided
		if (currentlyLoggingIn != null) {
			if ((currentlyLoggingIn.getPassword()).equals(password)) {
				System.out.println("correct details");
				// setting the user that's currently logged in
				CurrentUser.getInstance().setUser(currentlyLoggingIn);
				loginSuccessful = true;
			}
		}
		return loginSuccessful;
	}

	// method populates the list of favourite activities of each Parent
	public void populateParentSVFavouriteActivityList() {
		for (User u : getUserList()) {
			if (u instanceof ParentSV)
				((ParentSV) u).populateFavourites();
		}
	}

	// method populates the list of reviews given by each parent
	public void populateParentSVReviewList() {
		for (User u : getUserList()) {
			if (u instanceof ParentSV)
				((ParentSV) u).populateReviews();
		}
	}

	public void populateActivityProviderActivities() {
		for (User u : getUserList()) {
			if (u instanceof ActivityProvider)
				((ActivityProvider) u).populateActivities();
		}
	}

}
