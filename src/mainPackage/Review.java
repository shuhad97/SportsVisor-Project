package mainPackage;

import java.util.Date;

public class Review {
	
	private int reviewID;
	private String username;
	private int activityID;
	private String description;
	private String timestamp;
	private double rating;

	public Review(int rev, String usr, int act, String desc, String time, double r) {
		reviewID=rev;
		username=usr;
		activityID=act;
		description=desc;
		timestamp=time;
		rating=r;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public int getReviewID() {
		return reviewID;
	}

	public void setReviewID(int reviewID) {
		this.reviewID = reviewID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getActivityID() {
		return activityID;
	}

	public void setActivityID(int activityID) {
		this.activityID = activityID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
