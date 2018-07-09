package controllersPackage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/* Import javafx, java, mainPackage */
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import mainPackage.Activity;
import mainPackage.ActivityRegistry;
import mainPackage.CurrentActivity;
import mainPackage.CurrentUser;
//import mainPackage.web;
import mainPackage.ParentSV;
import mainPackage.Review;
import mainPackage.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainProgramController {
	@FXML
	private Button loginFromMain;
	@FXML
	private Button registerFromMain;
	@FXML
	private Button LogoutButton;
	@FXML
	private Button addActivity;
	@FXML
	private VBox SearchResults;
	@FXML
	private Button searchMap;
	@FXML
	private TextField SearchBar;

	@FXML
	private Label activityIDLabel;

	@FXML
	private Label activityNameLabel;

	@FXML
	private Label activityTypeLabel;

	@FXML
	private Label activityDescriptionLabel;

	@FXML
	private Label activityLocationLabel;

	@FXML
	private Label activityPostcodeLabel;

	@FXML
	private Button addReviewButton;

	@FXML
	private Button SearchActivities;

	@FXML
	private Pane mainBackground;

	@FXML
	private Button addToFavourites;

	@FXML
	private Button showReviews;

	@FXML
	private Button showFaves;
	@FXML
	private RadioButton indoorChoice;
	@FXML
	private RadioButton outdoorChoice;
	@FXML
	private Label averageRating;

	// getting username from the singleton CurrentUser to see if a USER EXISTS
	String USERNAME = CurrentUser.getInstance().getUsername();
	String AccountType = CurrentUser.getInstance().getAccountType();

	ObservableList<String> entries = FXCollections.observableArrayList();
	ListView<String> list = new ListView<>();
	ObservableList<String> updatedEntries = FXCollections.observableArrayList();

	private List<Activity> activityList = new ArrayList<Activity>();

	public void initialize() {

		if (CurrentUser.getInstance().getUser() instanceof ParentSV)
			showFaves.setVisible(true);

		// populate activity list
		activityList = ActivityRegistry.getInstance().getActivityList();

		for (Activity a : activityList) {
			entries.add(a.getActivityName());
		}
		list.setItems(entries);
		list.setVisible(false);

		indoorChoice.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				SearchBar.setText("");
				ObservableList<String> indoorEntries = FXCollections.observableArrayList();
				if (indoorChoice.isSelected() && outdoorChoice.isSelected()
						|| !indoorChoice.isSelected() && !outdoorChoice.isSelected()) {
					for (Activity ac : activityList) {
						indoorEntries.add(ac.getActivityName());
					}
					list.setItems(indoorEntries);
				} else if (indoorChoice.isSelected()) {
					for (Activity ac : activityList) {
						if (ac.getActivityType().equals("Indoor")) {
							indoorEntries.add(ac.getActivityName());
						}
					}
					list.setItems(indoorEntries);
				} else {
					for (Activity ac : activityList) {
						if (ac.getActivityType().equals("Outdoor")) {
							indoorEntries.add(ac.getActivityName());
						}
					}
					list.setItems(indoorEntries);
				}
			}
		});

		outdoorChoice.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent a) {
				SearchBar.setText("");
				ObservableList<String> outdoorEntries = FXCollections.observableArrayList();
				if (indoorChoice.isSelected() && outdoorChoice.isSelected()
						|| !indoorChoice.isSelected() && !outdoorChoice.isSelected()) {
					for (Activity ac : activityList) {
						outdoorEntries.add(ac.getActivityName());
					}
					list.setItems(outdoorEntries);
				} else if (outdoorChoice.isSelected()) {
					for (Activity ac : activityList) {
						if (ac.getActivityType().equals("Outdoor")) {
							outdoorEntries.add(ac.getActivityName());
						}
					}
					list.setItems(outdoorEntries);
				} else {
					for (Activity ac : activityList) {
						if (ac.getActivityType().equals("Indoor")) {
							outdoorEntries.add(ac.getActivityName());
						}
					}
					list.setItems(outdoorEntries);
				}
			}
		});

		// handling the searching
		SearchBar.textProperty().addListener(new ChangeListener<Object>() {
			public void changed(ObservableValue<?> observable, Object oldVal, Object newVal) {
				list.setVisible(true);
				handleSearchBar((String) oldVal, (String) newVal);
			}
		});

		// resets contents of search bar on new click
		SearchBar.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				SearchBar.setText("");
				list.setVisible(true);
				hideActivityDetails();

			}
		});

		list.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				list.getSelectionModel().getSelectedItem();
				String currentlySelected = list.getSelectionModel().getSelectedItem();
				if (currentlySelected != null) {
					System.out.println("Currently pressed " + currentlySelected);
					list.setVisible(false);
					// finding the Activity object corresponding to keyword and setting it to
					// CurrentActivity singleton
					// same principle as with CurrentUser
					CurrentActivity.getInstance().setActivity(currentlySelected);
					Activity a = CurrentActivity.getInstance().getActivity();
					// setting label values
					activityNameLabel.setText("Name: " + a.getActivityName());
					activityTypeLabel.setText("Type: " + a.getActivityType());
					activityDescriptionLabel.setText("Description: " + a.getActivityDescription());
					activityLocationLabel.setText("Address: " + a.getActivityLocation());
					activityPostcodeLabel.setText("Postcode: " + a.getActivityPostcode());
					// setting visibility
					activityNameLabel.setVisible(true);
					activityTypeLabel.setVisible(true);
					activityDescriptionLabel.setVisible(true);
					averageRating.setText("Rating: " + Double.toString(a.getRating()));
					averageRating.setVisible(true);
					activityLocationLabel.setVisible(true);
					activityPostcodeLabel.setVisible(true);

					// setting addReview and addFavourites buttons to visible only if user logged in
					// and is a parent
					if (CurrentUser.getInstance().getUsername() != null
							&& CurrentUser.getInstance().getUser() instanceof ParentSV) {
						addReviewButton.setVisible(true);
						addToFavourites.setVisible(true);
						showReviews.setVisible(true);

					}

					searchMap.setVisible(true);

					SearchResults.setOnMouseExited(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
							list.setVisible(false);
						}
					});
				}
			}
		});

		updatedEntries = list.getItems();
		// int height = 25*updatedEntries.size();
		list.setMaxHeight(150);
		SearchResults.getChildren().add(list);

		LogoutButton.setVisible(false);
		addActivity.setVisible(false);
		if (USERNAME == null) {
			loginFromMain.setVisible(true);
			registerFromMain.setVisible(true);
		} else {
			registerFromMain.setVisible(false);
			loginFromMain.setVisible(false);
			LogoutButton.setVisible(true);
			// depends which account type is logged in
			if (AccountType.equals("Parent")) {

			} else if (AccountType.equals("ActivityProvider")) {
				addActivity.setVisible(true);
			}
		}

	}

	private void hideActivityDetails() {

		activityNameLabel.setVisible(false);
		activityTypeLabel.setVisible(false);
		activityDescriptionLabel.setVisible(false);
		activityLocationLabel.setVisible(false);
		activityPostcodeLabel.setVisible(false);
		searchMap.setVisible(false);
		addToFavourites.setVisible(false);
		showReviews.setVisible(false);
		addReviewButton.setVisible(false);
		averageRating.setVisible(false);
	}

	public void handleSearchBar(String oldVal, String newVal) {

		if (oldVal != null && (newVal.length() < oldVal.length())) {
			list.setItems(entries);
		}

		String[] parts = newVal.toUpperCase().split(" ");
		// Filter out the entries that don't contain the entered text
		ObservableList<String> subentries = FXCollections.observableArrayList();

		for (Object entry : list.getItems()) {
			boolean match = true;
			String entryText = (String) entry;
			for (String part : parts) {// search through all the characters
				if (!entryText.toUpperCase().contains(part)) {
					match = false;
					break;

				}
			}

			if (match) {

				Activity act = ActivityRegistry.getInstance().findActivity(entryText);

				if (indoorChoice.isSelected() && outdoorChoice.isSelected()
						|| !indoorChoice.isSelected() && !outdoorChoice.isSelected()) {
					subentries.add(entryText);
				} else if (indoorChoice.isSelected()) {
					if (act.getActivityType().equals("Indoor"))
						subentries.add(entryText);
				} else {
					if (act.getActivityType().equals("Outdoor"))
						subentries.add(entryText);
				}

			}

		} // end foreach

		list.setItems(subentries);

	}

	@FXML
	protected void redirectAddReview(ActionEvent e) throws IOException {
		Parent blah = FXMLLoader.load(getClass().getResource("/fxmlPackage/addReviewPage.fxml"));
		Scene scene = new Scene(blah);
		Stage appStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		appStage.setScene(scene);
		appStage.setTitle("SportsVisor");
		appStage.setWidth(1280);
		appStage.setHeight(800);
		appStage.show();
	}

	@FXML
	protected void redirectAddActivityPage(ActionEvent event) throws IOException {
		Parent blah = FXMLLoader.load(getClass().getResource("/fxmlPackage/addActivity.fxml"));
		Scene scene = new Scene(blah);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		appStage.setScene(scene);
		appStage.setTitle("SportsVisor");
		appStage.setWidth(1280);
		appStage.setHeight(800);
		appStage.show();
	}

	@FXML
	protected void handleAddToFavourites(ActionEvent e) throws IOException {
		User current = CurrentUser.getInstance().getUser();
		// casting User to ParentSV, updating the favourite list
		boolean in = ((ParentSV) current).addFavourite(CurrentActivity.getInstance().getActivity());
		Label l = new Label();
		if (in) {
			l.setText("Successfully added to favourites!");

		} else {
			l.setText("You have already added this activity");
		}

		Stage stage = new Stage();

		// Create the VBox
		VBox root = new VBox();

		// Set the Style-properties of the VBox
		root.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");

		stage.setTitle("Favourite activities of " + CurrentUser.getInstance().getUsername());
		stage.setWidth(400.0);
		stage.setHeight(100.0);
		root.getChildren().add(l);

		// Create the Scene
		Scene scene = new Scene(root);

		// Add the Scene to the Stage
		stage.setScene(scene);
		// Display the Stage
		stage.show();

	}

	@FXML
	protected void handleLogOutPage(ActionEvent event) throws IOException {
		CurrentUser.getInstance().resetCurrentUser();
		handleLogInPage(event);
	}

	@FXML
	protected void handleLogInPage(ActionEvent event) throws IOException {
		Parent blah = FXMLLoader.load(getClass().getResource("/fxmlPackage/loginPage.fxml"));
		Scene scene = new Scene(blah);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		appStage.setScene(scene);
		appStage.setTitle("SportsVisor");
		appStage.setWidth(1280);
		appStage.setHeight(800);
		appStage.show();
	}

	@FXML
	protected void handleRegisterPage(ActionEvent event) throws IOException {
		Parent blah = FXMLLoader.load(getClass().getResource("/fxmlPackage/registerPage.fxml"));
		Scene scene = new Scene(blah);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		appStage.setScene(scene);
		appStage.setTitle("SportsVisor");
		appStage.setWidth(1280);
		appStage.setHeight(800);
		appStage.show();
	}

	@FXML
	protected void handleShowFavourites(ActionEvent event) throws IOException {
		// Application.launch(web.class);
		// Create the WebView
		Stage stage = new Stage();

		// Create the VBox
		VBox root = new VBox();

		// Set the Style-properties of the VBox
		root.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");

		stage.setTitle("Favourite activities of " + CurrentUser.getInstance().getUsername());
		stage.setWidth(600.0);
		stage.setHeight(300.0);
		List<Activity> list = ((ParentSV) CurrentUser.getInstance().getUser()).getFavourites();
		double posY = 20.0;
		double posX = 40.0;

		for (Activity a : list) {
			Label l = new Label(a.getActivityName());
			l.setLayoutX(posX);
			l.setLayoutY(posY);

			// Add the Children to the VBox
			root.getChildren().add(l);

			posY += 40.0;
		}
		// Create the Scene
		Scene scene = new Scene(root);

		// Add the Scene to the Stage
		stage.setScene(scene);
		// Display the Stage
		stage.show();

	}

	@FXML
	protected void handleSearchMapButton(ActionEvent event) throws IOException {

		// Application.launch(web.class);
		// Create the WebView
		Stage stage = new Stage();
		WebView webView = new WebView();

		// Disable the context menu
		webView.setContextMenuEnabled(false);

		// Increase the text font size by 20%
		webView.setFontScale(1.20);

		// Set the Zoom 20%
		webView.setZoom(1.20);

		// Set font smoothing type to GRAY
		webView.setFontSmoothingType(FontSmoothingType.GRAY);

		// Create the WebEngine
		final WebEngine webEngine = webView.getEngine();
		// Load the StartPage
		// webEngine.load("http://www.googlemaps.com");
		webEngine.load("https://www.google.co.uk/maps/place/"
				+ CurrentActivity.getInstance().getActivity().getActivityName().replaceAll("\\s+", ""));

		// Update the stage title when a new web page title is available
		webEngine.titleProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> ov, final String oldvalue, final String newvalue) {
				stage.setTitle(newvalue);
			}
		});

		// Create the VBox
		VBox root = new VBox();
		// Add the Children to the VBox
		root.getChildren().add(webView);

		// Set the Style-properties of the VBox
		root.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");

		// Create the Scene
		Scene scene = new Scene(root);
		// Add the Scene to the Stage
		stage.setScene(scene);
		// Display the Stage
		stage.show();

	}

	@FXML
	protected void handleShowReviews(ActionEvent event) throws IOException {
		// Application.launch(web.class);
		// Create the WebView
		Stage stage = new Stage();

		// Create the VBox
		VBox root = new VBox();

		// Set the Style-properties of the VBox
		root.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");

		stage.setTitle("Reviews of " + CurrentActivity.getInstance().getCurrentActivityName());
		stage.setWidth(600.0);
		stage.setHeight(300.0);
		List<Review> list = CurrentActivity.getInstance().getActivity().getReviews();
		double posY = 20.0;
		double posX = 40.0;

		int i = list.size() - 1;
		while (i > -1) {
			Review r = list.get(i);
			Label l = new Label(r.getDescription() + " added on " + r.getTimestamp() + " by " + r.getUsername()
					+ " rated: " + r.getRating());
			l.setLayoutX(posX);
			l.setLayoutY(posY);

			// Add the Children to the VBox
			root.getChildren().add(l);

			posY += 40.0;
			i--;
		}

		// Create the Scene
		Scene scene = new Scene(root);

		// Add the Scene to the Stage
		stage.setScene(scene);
		// Display the Stage
		stage.show();

	}
}
