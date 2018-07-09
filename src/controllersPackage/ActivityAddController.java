package controllersPackage;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainPackage.Activity;
import mainPackage.ActivityProvider;
import mainPackage.ActivityRegistry;
import mainPackage.CurrentUser;

public class ActivityAddController {
	ObservableList<String> activityType = FXCollections.observableArrayList("Select", "Indoor", "Outdoor");

	@FXML
	private TextField addActivityName;
	@FXML
	private TextArea addDescription;
	@FXML // register button
	private TextField addLocation;
	@FXML
	private TextField addPostcode;
	@FXML
	private Button buttonSubmit;
	@FXML // register password field
	private Label errorActivity;
	@FXML
	private Button redirectMainPage;
	@FXML // activity choice box
	private ChoiceBox<String> outdoorIndoor;

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
	private void initialize() {
		outdoorIndoor.setValue("Select");
		outdoorIndoor.setItems(activityType);

	}

	@FXML
	protected void addActivityHandler(ActionEvent event) throws IOException {

		int id = ActivityRegistry.getInstance().getActivityList().size();
		id++;

		String type = outdoorIndoor.getValue();
		String name = addActivityName.getText();
		String desc = addDescription.getText();
		String location = addLocation.getText();
		String postcode = addPostcode.getText();
		String provider = CurrentUser.getInstance().getUsername();

		Activity a = new Activity(id, name, location, postcode, type, desc, provider, 0);
		ActivityRegistry.getInstance().addToActivityRegistry(a);
		// ActivityRegistry.getInstance().updateActivityDatabase(a);

		ActivityRegistry.getInstance().printReg();

		List<Activity> la = ((ActivityProvider) CurrentUser.getInstance().getUser()).getActivities();

		for (Activity ac : la) {
			System.out.println(CurrentUser.getInstance().getUsername() + ": " + ac.getActivityName());
		}

		redirectReturnPage(event);
	}

}
