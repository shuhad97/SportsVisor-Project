package mainPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Activity {
	private int activityID;
	private String activityName;
	private String activityLocation;
	private String activityPostcode;
	private String provider;
	private List<Review> reviews;
	private double rating;
	
	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	
	public void addReview(Review r) {
		reviews.add(r);
	}

	public Activity(int activityID, String activityName, String activityLocation, String activityPostcode,
			
			String activityType, String activityDescription, String provider, double rating) {
		
		this.activityID = activityID;
		this.activityName = activityName;
		this.activityLocation = activityLocation;
		this.activityPostcode = activityPostcode;
		this.activityType = activityType;
		this.activityDescription = activityDescription;
		this.provider=provider;
		setReviews();
		this.rating=rating;
	}
	
	public void setReviews() {
		List<Review> revList = new ArrayList<>();
		Connection c = null;
		Statement stmt = null;
		
		double avgRating=0;
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:SportVisorDB.db");
	
				System.out.println("Opened database successfully to find reviews");
	
				stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM Review WHERE ActivityID=" + activityID+";");
				
				while(rs.next()) {
				
					int revID = rs.getInt("ReviewID");
					String user = rs.getString("Username");
					String desc = rs.getString("Description");
					String dateString = rs.getString("Date");
					double rating = rs.getDouble("Rating");
					Review r = new Review(revID,user,activityID,desc,dateString,rating);
					revList.add(r);
					avgRating+=rating;
					
				}
				
				//calculating avg rating
				avgRating = avgRating/revList.size();
				//setting avg rating
				rating=avgRating;
						
			}catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}	
		this.reviews = revList;
	}
	
	public Activity() {
		
	}
	
	public void updateRating(double d) {
		System.out.println(rating);
		
		//if rating previously added
		if(rating!=0)
			rating=(rating*(reviews.size()-1)+d)/(reviews.size());
		else
			rating = d;
		System.out.println(rating +  " rating");
		Connection c = null;
		PreparedStatement stmt = null;
		
		
			try {
				
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:SportVisorDB.db");
				String update = "UPDATE Activity SET Rating = ? WHERE ActivityID =" + getActivityID();
				stmt = c.prepareStatement(update);
				stmt.setDouble(1,rating);
				System.out.println("Opened database successfully to find reviews");
				stmt.executeUpdate();
						
			}catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
		
	}
	
	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getActivityID() {
		return activityID;
	}
	public void setActivityID(int activityID) {
		this.activityID = activityID;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getActivityLocation() {
		return activityLocation;
	}
	public void setActivityLocation(String activityLocation) {
		this.activityLocation = activityLocation;
	}
	public String getActivityPostcode() {
		return activityPostcode;
	}
	public void setActivityPostcode(String activityPostcode) {
		this.activityPostcode = activityPostcode;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getActivityDescription() {
		return activityDescription;
	}
	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}
	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
	private String activityType;
	private String activityDescription;
}
