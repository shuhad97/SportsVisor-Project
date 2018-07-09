package mainPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ActivityRegistry {

private final static ActivityRegistry instance = new ActivityRegistry();
	

	
	//creates single instance
	public static ActivityRegistry getInstance() {
		return instance;
	}
	
	private static List<Activity> activityList = new ArrayList<Activity>();
	
	
	//adding data to the activity registry
	public void populateRegistry() {
			
			Connection c = null;
			Statement stmt = null;
			
				try {
					Class.forName("org.sqlite.JDBC");
					c = DriverManager.getConnection("jdbc:sqlite:SportVisorDB.db");
		
					System.out.println("Opened database successfully to register");
		
					stmt = c.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT * FROM Activity;");
					
					while(rs.next()) {
						
						int activityID = Integer.parseInt(rs.getString("ActivityID"));
						String name = rs.getString("Name");
						String type = rs.getString("Type");
						String description = rs.getString("Description");
						String location = rs.getString("Location");
						String postcode = rs.getString("Postcode");
						String provider = rs.getString("Provider");
						Double rating = Double.parseDouble(rs.getString("Rating"));
						 
						activityList.add(new Activity(activityID,name,location,postcode,type,description,provider,rating));
						
						
					}
					
				}catch (Exception e) {
					System.err.println(e.getClass().getName() + ": " + e.getMessage());
					System.exit(0);
				}
	}
	
	
	
	public void printReg() {
		for(Activity a : activityList) {
			System.out.println(a.getActivityName());
		}
	}
	

	public List<Activity> getActivityList() {
		return activityList;
	}
	

	public void setActivityList(List<Activity> activityList) {
		this.activityList = activityList;
	}

	public boolean isInRegistry(int id) {
		for(Activity a : activityList) {
			if(a.getActivityID()==id) return true;
		}
		return false;
	}
	
	public void addToActivityRegistry(Activity a) {
		if(!isInRegistry(a.getActivityID())) {
			activityList.add(a);
			updateActivityDatabase(a);
			((ActivityProvider) CurrentUser.getInstance().getUser()).addActivity(a);
		}
	} 
	
	public void updateActivityDatabase(Activity a) {
		Connection c = null;
		Statement stmt = null;
		
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:SportVisorDB.db");
	
				System.out.println("Opened database successfully to add activity");
	
				stmt = c.createStatement();
				//ResultSet rs = stmt.executeQuery("SELECT * FROM User;");
				
				
				int activityID = a.getActivityID();
				String name = a.getActivityName();
				String type = a.getActivityType();
				String description = a.getActivityDescription();
				String location = a.getActivityLocation();
				String postcode = a.getActivityPostcode();
				String provider = CurrentUser.getInstance().getUsername();
				stmt.close();
				
				stmt.executeUpdate("INSERT INTO ACTIVITY VALUES ("+activityID+",'"+name+"','"+type+"','"+description+"','"+location+"','"+postcode+"','"+provider+"',"+0+");");
							
				//closing connection
				c.close();
						
			}catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
	}
	
	public List<Activity> findActivityList(String keyword) {
		List<Activity> searchRes = new ArrayList<>();
		
		for(Activity a : getActivityList()) {

			if(a.getActivityName().toLowerCase().contains(keyword.toLowerCase())) {
				searchRes.add(a);
			}
		}
		return searchRes;
	}
	
	public Activity findActivity(String keyword) {
		
		for(Activity a : getActivityList()) {

			if(a.getActivityName().equals(keyword)) 
				return a;
		}
		return null;
	}
	
	public Activity findActivityByID(int id) {
		for(Activity a : getActivityList()) {
			if(a.getActivityID()==id)
				return a;
		}
		return null;
	}
	
	
}
