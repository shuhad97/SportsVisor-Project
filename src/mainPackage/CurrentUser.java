package mainPackage;

//singleton 
//so whenever we create instance of CurrentUser we can access the User
//so we have access to user data anywhere
public class CurrentUser {

	
	
	private final static CurrentUser instance = new CurrentUser();
	
	
	//creates single instance
	public static CurrentUser getInstance() {
		return instance;
	}
	
	//field for username
	//private String username;
	//field for activity
	//private String AccountType;
	private User currentUser = new User();
	
	public User getUser() {
		return currentUser;
	}
	
	public void setUser(User u) {
		currentUser = u;
	}
	
	public String getUsername() {
		return currentUser.getUserName();
	}
	
	public void setUsername(String username) {
		currentUser.setUserName(username);
	}
	
	//returns the usertype using instanceof
	public String getAccountType(){
		if(currentUser instanceof ParentSV)
			return "Parent";
		return "ActivityProvider";	
		
	}
	
	
	//to reset user after they log out
	public void resetCurrentUser() {
		currentUser=new User();
	}
}
