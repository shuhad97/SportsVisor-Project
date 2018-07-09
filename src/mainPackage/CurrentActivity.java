package mainPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CurrentActivity {

private final static CurrentActivity instance = new CurrentActivity();
	
	//creates single instance
	public static CurrentActivity getInstance() {
		return instance;
	}
	
	private Activity currentActivity = new Activity();
	
	public Activity getActivity() {
		return currentActivity;
	}
	
	//method for finding revs for certain activity
	//there will be a button "show reviews" or sth like that
	//also will need method to order them by date
//	public void findReviews() {
//		
//		List<Review> revList = new ArrayList<>();
//		Connection c = null;
//		Statement stmt = null;
//		
//			try {
//				Class.forName("org.sqlite.JDBC");
//				c = DriverManager.getConnection("jdbc:sqlite:SportVisorDB.db");
//	
//				System.out.println("Opened database successfully to find reviews");
//	
//				stmt = c.createStatement();
//				ResultSet rs = stmt.executeQuery("SELECT * FROM Review WHERE ActivityID=" + getActivity().getActivityID()+";");
//				
//				
//				
//				int revID = rs.getInt("ReviewID");
//				String user = rs.getString("Username");
//				String desc = rs.getString("Description");
//				Date date = rs.getDate("Date");
//				
//				Review r = new Review(revID,user,getActivity().getActivityID(),desc,date);
//				revList.add(r);
//			
//						
//			}catch (Exception e) {
//				System.err.println(e.getClass().getName() + ": " + e.getMessage());
//				System.exit(0);
//			}
//			
//			getActivity().setReviews(revList);
//	}
	
	public void setActivity(String name) {
		Activity a = ActivityRegistry.getInstance().findActivity(name);
		currentActivity=a;
	}
	
	public void setUser(Activity a) {
		currentActivity = a;
	}
	
	public String getCurrentActivityName() {
		return currentActivity.getActivityName();
	}
}
