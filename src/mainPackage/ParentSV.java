package mainPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ParentSV extends Customer {
	
	private List<Activity> favourites = new ArrayList<>();
	private List<Review> reviews = new ArrayList<>();

	public ParentSV(String userName, String firstName, String lastName, String email,String password) {
		super(userName, firstName, lastName, email,password); //t
		// TODO Auto-generated constructor stub
	}

	public List<Activity> getFavourites() {
		return favourites;
	}

	public void setFavourites(List<Activity> favourites) {
		this.favourites = favourites;
	}
	
	//adding favourite activities from ActivityRegistry 
	public void populateFavourites() {
		
		Connection c = null;
		Statement stmt = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:SportVisorDB.db");

			System.out.println("Opened database successfully to fetch favourites");

			stmt = c.createStatement();
			
			//getting all rows that match the username
			ResultSet rs = stmt.executeQuery("SELECT * FROM FavouriteActivity WHERE Username='" +this.getUserName()+"';");
			
			while (rs.next()) {
				
				//finding the actual Activity objects stored in ActivityRegistry
				//based on the the activity id, and adding them to the list
				Activity a = ActivityRegistry.getInstance().findActivityByID(rs.getInt("FavouriteActivityID"));
				favourites.add(a);
			}
							
		}
		catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
		}
		
	}
	
	public void populateReviews() {
		Connection c = null;
		Statement stmt = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:SportVisorDB.db");

			System.out.println("Opened database successfully to fetch review");

			stmt = c.createStatement();
			
			//getting all rows that match the username
			ResultSet rs = stmt.executeQuery("SELECT * FROM Review WHERE Username='" +this.getUserName()+"';");
			
			while (rs.next()) {
				
				//finding the actual Activity objects stored in ActivityRegistry
				//based on the the activity id, and adding them to the list
				Review r = new Review(rs.getInt("ReviewID"),getUserName(),rs.getInt("ActivityID"),rs.getString("Description"),rs.getString("Date"),rs.getDouble("Rating"));
				reviews.add(r);
				System.out.println(getUserName() + ": " + r.getDescription());
			}
							
		}
		catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
		}
	}
	
	public void addReview(Review r) {
		reviews.add(r);
	}
	
	//method adds activity to favourite list of parent if it is not there already
	public boolean addFavourite(Activity a) {
		if(!favourites.contains(a)) {
			favourites.add(a);
			updateDBFavourite(a);
			return true;
		}
		return false;
	}
	
	@Override
	public void register() {
		Connection c = null;
		Statement stmt = null;
		
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:SportVisorDB.db");
	
				System.out.println("Opened database successfully to register 2");
	
				stmt = c.createStatement();
				
				
				stmt.executeUpdate("INSERT INTO USER VALUES ('"+getUserName()+"','"+getPassword()+"','"+getFirstName()+"','"+getLastName()+"','','Parent','','"+getEmail()+"');");
						
				UserRegistry.getInstance().addUserToRegistry(this);
			}catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
	}
	
	public void updateDBFavourite(Activity a) {
		Connection c = null;
		Statement stmt = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:SportVisorDB.db");

			System.out.println("Opened database successfully to add favourite");

			stmt = c.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM FavouriteActivity;");
			
			boolean alreadyAdded=false;
			while (rs.next()) {
				//check if review already added
				if (CurrentUser.getInstance().getUsername().equals(rs.getString("Username")) 
						&& CurrentActivity.getInstance().getActivity().getActivityID()==Integer.parseInt(rs.getString("FavouriteActivityID"))) {
					alreadyAdded=true;
					System.out.println("User has already favourited the activity");
					break;
				}
			}
			
			if (!alreadyAdded) {
				
				String username = CurrentUser.getInstance().getUsername();
				int actID = CurrentActivity.getInstance().getActivity().getActivityID();
							
				//adding new activity to db
				stmt.executeUpdate("INSERT INTO FavouriteActivity VALUES (" + actID + ",'"+username+"')");
				
			}
							
		}
		catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
		}
	}
}
