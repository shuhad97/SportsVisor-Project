package controllersPackage;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import mainPackage.Activity;
import mainPackage.CurrentActivity;
import mainPackage.CurrentUser;
import mainPackage.ParentSV;
import mainPackage.Review;
import mainPackage.UserRegistry;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import mainPackage.User;

public class LoginController {
	@FXML
	private Button helpButton;
	@FXML
	private PasswordField passwordField;
	@FXML
	private TextField usernameField;
	@FXML
	private Label labelMessage;
	@FXML // register button
	private Button buttonRegister;
	@FXML
	private TextField createName;
	@FXML
	private TextField createUsername;
	@FXML // register password field
	private PasswordField confirmPasswordField;
	@FXML // register password field
	private PasswordField createPasswordField;
	@FXML // error message on UI
	private Label errorRegister;
	@FXML // handle forgetting button
	private Button forgotPasswordButton;
	@FXML
	private CheckBox confirmTermsBox;
	@FXML
	private TextField usernameRecovery;
	@FXML
	private TextField recoveryQuestion;
	@FXML
	private Label recoveryLabel;
	@FXML
	private Label recoveryAnswer;
	@FXML
	private Button recoveryButton;
	@FXML
	private Button recoveryButtonUsername;
	@FXML
	private Button forgotUsernameButton;

	public void initialize() {
		recoveryLabel.setVisible(false);
		recoveryButton.setVisible(false);
		recoveryAnswer.setVisible(false);
		recoveryQuestion.setVisible(false);
		usernameRecovery.setVisible(false);
		recoveryButtonUsername.setVisible(false);
	}

	@FXML
	protected void redirectReturnPage(ActionEvent event) throws IOException {

		Parent blah = FXMLLoader.load(getClass().getResource("/fxmlPackage/mainProgram.fxml"));
		Scene scene = new Scene(blah);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		appStage.setScene(scene);
		appStage.setTitle("SportsVisor");
		appStage.setWidth(1280);
		appStage.setHeight(800);
		appStage.show();
	}

	@FXML
	protected void redirectCurrentUserPageParent(ActionEvent event) throws IOException {
		Parent blah = FXMLLoader.load(getClass().getResource("/fxmlPackage/parentPage.fxml"));
		Scene scene = new Scene(blah);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		appStage.setScene(scene);
		appStage.setTitle("SportsVisor");
		appStage.setWidth(1280);
		appStage.setHeight(800);
		appStage.show();
	}

	@FXML
	protected void redirectCurrentUserPageActivityProvider(ActionEvent event) throws IOException {
		Parent blah = FXMLLoader.load(getClass().getResource("/fxmlPackage/activityProviderPage.fxml"));
		Scene scene = new Scene(blah);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		appStage.setScene(scene);
		appStage.setTitle("SportsVisor");
		appStage.setWidth(1280);
		appStage.setHeight(800);
		appStage.show();
	}

	@FXML
	protected void handleForgotPassword(ActionEvent event) throws IOException {
		usernameRecovery.setVisible(true);
		recoveryQuestion.setVisible(true);
		recoveryLabel.setVisible(true);
		recoveryButton.setVisible(true);
		
		recoveryAnswer.setText("");
		usernameRecovery.setText("");
		recoveryQuestion.setText("");
		recoveryAnswer.setVisible(false);
	}

	@FXML
	protected void handleRecoverPassword(ActionEvent event) throws IOException {
		String username = usernameRecovery.getText();
		String answer = recoveryQuestion.getText();
		if (UserRegistry.getInstance().recoverPassword(username, answer)) {
			recoveryAnswer.setText("Details sent to email");
			recoveryAnswer.setVisible(true);
		} else {
			recoveryAnswer.setText("Wrong username or answer");
			System.out.println("Wrong username or answer");
			recoveryAnswer.setVisible(true);
		}
	}
	
	@FXML
	protected void handleRemindUsername(ActionEvent event) throws IOException {
		usernameRecovery.setVisible(false);
		recoveryQuestion.setVisible(true);
		recoveryLabel.setVisible(true);
		recoveryButtonUsername.setVisible(true);
		
		recoveryAnswer.setText("");
		usernameRecovery.setText("");
		recoveryQuestion.setText("");
		recoveryAnswer.setVisible(false);
	}

	@FXML
	protected void handleRecoverUsername(ActionEvent event) throws IOException {
		String answer = recoveryQuestion.getText();
		if (UserRegistry.getInstance().recoverUsername(answer)) {
			recoveryAnswer.setText("Details sent to email");
			recoveryAnswer.setVisible(true);
		} else {
			recoveryAnswer.setText("Email does not exist");
			recoveryAnswer.setVisible(true);
		}
	}
	

	@FXML
	protected void handleHelpButton(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		VBox root = new VBox();
		
		 ScrollPane scrollPane = new ScrollPane();
	        scrollPane.setContent(root);

		// Set the Style-properties of the VBox
		root.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");

		stage.setTitle("Help");
		stage.setWidth(1000.0);
		stage.setHeight(600.0);
		
		
		String helptext = "Help? I forgot my username/password\r\n" + "\n" + 
				"Step 1) Click the Log in button and you will be redirected to the Login Page. \r\n" + 
				"Step 2) Click on the Forgot Password button if you forgot your password or click on the Remind Username button if you forgot your username. \r\n" + 
				"Step 3) Enter your username and/or email address in the recovery fields. You will be emailed a link to reset your password or get your username.\r\n" + 
				"How to search for an indoor or outdoor activity? (Logged in)\r\n" + 
				"Select the option if you want to search for an indoor and/or outdoor activity\r\n" + 
				"Enter the name of the activity in the search field\r\n" + 
				"Click on the activity in the results box\r\n" + 
				"The description of the activity including the rating will be displayed.\r\n" + 
				"Click on the show on map button to get the Google Maps address of the sports centre\r\n" + "\n" +
				"How to add an activity (Activity Providers only)\r\n" + 
				"Click the Login button in the upper right area \r\n" + 
				"Enter your username and password\r\n" + 
				"Click the add activity button. \r\n" + 
				"Select the activity type (indoor or outdoor)\r\n" + 
				"Fill in the required information. Enter the activity name, description, location, postcode and click the submit button.\r\n" + 
				"Now an activity is added to the Sport Visor System!\r\n" + 
				"How to add a review and show the reviews?  \r\n" + 
				"Login\r\n" + 
				"Search for an activity\r\n" + 
				"Click on the activity \r\n" + 
				"Click on the add review button \r\n" + 
				"Enter a comment regarding your reviews, score the sports centre and click submit\r\n" + 
				"Then click on the activity again and click show reviews \r\n" + "\n" +
				"How to view favourite activities?\r\n" +
				"Search for an activity\r\n" + 
				"Select the activity \r\n" + 
				"Click on the add to favourite button\r\n" + 
				"Then to show your favourite activities click show favourite activities. \r\n" + 
				"\r\n" + 
				"\r\n";
		
		double posY=20.0;
		double posX=40.0;
		
		
			Label l = new Label(helptext);
			l.setLayoutX(posX);
			l.setLayoutY(posY);
			
			
			// Add the Children to the VBox
			root.getChildren().add(l);
	
		

		// Create the Scene
		Scene scene = new Scene(root);
		// Add the Scene to the Stage
		stage.setScene(scene);
		// Display the Stage
		stage.show();
	}


	@FXML
	protected void handleLogInAction(ActionEvent event) throws IOException {
		labelMessage.setVisible(false);

		// User currentlyLoggingIn =
		// UserRegistry.getInstance().findUser(usernameField.getText());
		String username = usernameField.getText();
		String password = passwordField.getText();
		if (UserRegistry.getInstance().verifyUser(username, password))
			redirectReturnPage(event);
		else {
			labelMessage.setVisible(true);
		}

	}
}
