package controllersPackage;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainPackage.User;
import mainPackage.ActivityProvider;
import mainPackage.ParentSV;

public class RegisterController {
	ObservableList<String> userType = FXCollections.observableArrayList("Select", "ActivityProvider", "Parent");

	@FXML // register password field
	private PasswordField confirmPasswordField;
	@FXML
	private CheckBox confirmTermsBox;
	@FXML
	private TextField createUsername;
	@FXML // error message on UI
	private Label errorRegister;
	@FXML // gender choice box
	private ChoiceBox<String> choiceType;
	@FXML
	private TextField createfirstName;
	@FXML
	private TextField createlastName;
	@FXML // register password field
	private PasswordField createPasswordField;
	@FXML
	private TextField createproviderName;
	@FXML
	private TextField createphoneNumber;
	
	@FXML
	private TextField createEmail;
	
	@FXML
	private TextField createAnswer;

	@FXML
	private void initialize() {
		choiceType.setValue("Select");
		choiceType.setItems(userType);

		choiceType.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
               //arg2 argument gets latest index position from choiceBox
				if (arg2.equals(2)) {
					createproviderName.setText("");
					createphoneNumber.setText("");;
					createproviderName.setDisable(true);
					createphoneNumber.setDisable(true);
				}
				else {
					createproviderName.setText("");
					createphoneNumber.setText("");;
					createproviderName.setDisable(false);
					createphoneNumber.setDisable(false);
				}
			}
		});

	}

	@FXML
	protected void handleReturnPage(ActionEvent event) throws IOException {
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
	protected void handleRegisterAction(ActionEvent event) throws IOException {
		// need to make sure the password is 8 characters long looking at requirements

		Connection c = null;
		Statement stmt = null;
		if (confirmTermsBox.isSelected()) {
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:SportVisorDB.db");

				System.out.println("Opened database successfully to register");

				stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM User;");

				// flag to indicate if the username is in current DB
				boolean usernameAlreadyInDB = false;
				while (rs.next()) {
					if (createUsername.getText().equals(rs.getString("Username"))) {
						usernameAlreadyInDB = true;
						errorRegister.setText("Username already exists!");
						break;
					}
				}

				if (!usernameAlreadyInDB) {
					errorRegister.setText("");
					if(!createfirstName.getText().equals("") || !createlastName.getText().equals("")){
						if (confirmPasswordField.getText().equals(createPasswordField.getText())) {
							if (!confirmPasswordField.getText().equals("")) {
									if (!choiceType.getValue().equals("Select")) {
										 
											String username = createUsername.getText();
											String firstName = createfirstName.getText();
											String lastName = createlastName.getText();
											String type = choiceType.getValue(); // need to add this to database
											String password = confirmPasswordField.getText();
											String providerName = createproviderName.getText();
											String phoneNumber = createphoneNumber.getText();
											String email = createEmail.getText();
											// printing to test
											User newUser;
											if (type.equals("Parent")) {
												newUser = new ParentSV(username, firstName, lastName, email, password);
												((ParentSV) newUser).register();
											}
											else {
												newUser = new ActivityProvider(username, firstName, providerName, lastName, email,phoneNumber,password);
												((ActivityProvider) newUser).register();
											}
				
											handleReturnPage(event);
										}else{
											 errorRegister.setText("please choose Account Type");
										 }
								}else{
										errorRegister.setText("please enter a password");
								}
							}
						}else {
							errorRegister.setText("Passwords are not identical!");
						}
					}else {
						errorRegister.setText("Username already exists");//error?
					}
				}
			
			catch (Exception e) {
					System.err.println(e.getClass().getName() + ": " + e.getMessage());
					System.exit(0);
				}
					} else {
						errorRegister.setText("Please Accept Terms and Conditions!");
				}
	}

}
