package mainPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ActivityProvider extends Customer {

	private String providerName;
	private String phoneNumber;
	private List<Activity> activities = new ArrayList<>();

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public void addActivity(Activity a) {
		activities.add(a);
	}

	public void populateActivities() {
		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:SportVisorDB.db");

			System.out.println("Opened database successfully to fetch activities");

			stmt = c.createStatement();
			// getting all rows that match the username
			ResultSet rs = stmt.executeQuery("SELECT * FROM Activity WHERE Provider='" + this.getUserName() + "';");

			while (rs.next()) {

				int activityID = Integer.parseInt(rs.getString("ActivityID"));
				String name = rs.getString("Name");
				String type = rs.getString("Type");
				String description = rs.getString("Description");
				String location = rs.getString("Location");
				String postcode = rs.getString("Postcode");
				String provider = rs.getString("Provider");

				activities.add(new Activity(activityID, name, location, postcode, type, description, provider, 0));

			}

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	// not including the proper details yet because we need the corresponding
	// textfields in the register page
	public ActivityProvider(String userName, String firstName, String providerName, String lastName, String email,
			String phoneNumber, String password) {
		super(userName, firstName, lastName, email, password);
		this.providerName = providerName;
		this.phoneNumber = phoneNumber;
		// TODO Auto-generated constructor stub
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public void register() {
		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:SportVisorDB.db");

			System.out.println("Opened database successfully to register 3");

			stmt = c.createStatement();

			stmt.executeUpdate("INSERT INTO USER VALUES ('" + getUserName() + "','" + getPassword() + "','"
					+ getFirstName() + "','" + getLastName() + "','" + getPhoneNumber() + "','ActivityProvider','"
					+ getProviderName() + "','" + getEmail() + "');");
			UserRegistry.getInstance().addUserToRegistry(this);

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
}
