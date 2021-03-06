package controllersPackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import mainPackage.CurrentUser;
import mainPackage.ParentSV;
import mainPackage.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LoggedInController {
	
	@FXML
	private Label usernameLabel;
	
	@FXML
	private Label firstNameLabel;
	
	@FXML
	private Label lastNameLabel;
	
	@FXML
	private Label accountTypeLabel;
	
	@FXML
	private Button redirectHomepage;
	
	
	//initialize is run as soon as the corresponding page is opened (here userPage.fxml)
	//has access to all the elements in the page
	@FXML
	public void initialize() {
		usernameLabel.setText("Welcome to SportsVisor " + CurrentUser.getInstance().getUsername() + "!");
		
		//fetching the rest of the data
		fetchUserData();
		
	}
	
	@FXML
	protected void handleRedirectHomepage(ActionEvent event) throws IOException{
		Parent blah = FXMLLoader.load(getClass().getResource("/fxmlPackage/mainProgram.fxml"));
		Scene scene = new Scene(blah);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		appStage.setScene(scene);
		appStage.setTitle("SportsVisor");
		appStage.setWidth(1280);
		appStage.setHeight(800);
		appStage.show();
	}
	
	//fetches user data from database 
	public void fetchUserData() {
		String firstN = "default";
		String lastN = "default";
		String accType = "default";
		
		User current = CurrentUser.getInstance().getUser();
		
		firstN = current.getFirstName();
		lastN = current.getLastName();
		if(current instanceof ParentSV) 
			accType="Parent";
		else
			accType="ActivityProvider";
		
		
// DON'T DELETE, we might need it if sth goes wrong		
//		Connection c = null;
//		Statement stmt = null;
//		try {
//			Class.forName("org.sqlite.JDBC");
//			c = DriverManager.getConnection("jdbc:sqlite:SportVisorDB.db");
//
//			System.out.println("Opened database successfully");
//
//			stmt = c.createStatement();
//			//getting the row that with user data based on username
//			ResultSet rs = stmt.executeQuery("SELECT * FROM USER WHERE Username='" + USERNAME + "';");
//			
//			while (rs.next()) { 
//				//setting values
//				firstN = rs.getString("FirstName");
//				lastN = rs.getString("LastName");
//				accType = rs.getString("AccountType");
//				}
//				
//			rs.close();
//			stmt.close();
//			c.close();
//			
//		}catch (Exception e) {
//			System.err.println(e.getClass().getName() + ": " + e.getMessage());
//			//usernameLabel.setText(username);	System.exit(0);
//		}
		
		
		//setting the labels
		firstNameLabel.setText("first name: " + firstN);
		lastNameLabel.setText("last name: " + lastN);
		accountTypeLabel.setText("account type: " + accType);
	}
	
	
}
