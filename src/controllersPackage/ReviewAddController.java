package controllersPackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.util.Duration;
import mainPackage.CurrentActivity;
import mainPackage.CurrentUser;
import mainPackage.ParentSV;
import mainPackage.Review;

public class ReviewAddController extends Thread{

	@FXML
	private Button ConfirmReview;
	@FXML
	private TextArea ReviewArea;
	@FXML
	private ComboBox<String> comboBoxScore;
	
	@FXML
	private Button returnButton;
	
	@FXML
	private Label statusMessage;
	
	ObservableList<String> scores = FXCollections.observableArrayList("1","2","3","4","5");
	
	public void initialize() {
		comboBoxScore.setItems(scores);
		
		
	}
	@FXML
	protected void handleConfirmReview(ActionEvent event)throws IOException, InterruptedException{
		
		Connection c = null;
		Statement stmt = null;
		
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:SportVisorDB.db");

			System.out.println("Opened database successfully to add review");

			stmt = c.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM Review;");
			
			
			boolean alreadyReviewed = false;
			if(rs!=null)
			while (rs.next()) {
				//check if review already added
				if (CurrentUser.getInstance().getUsername().equals(rs.getString("Username")) 
						&& CurrentActivity.getInstance().getActivity().getActivityID()==Integer.parseInt(rs.getString("ActivityID"))) {
					alreadyReviewed=true;
					System.out.println("User has already reviewed the activity");
					statusMessage.setText("You already reviewed this activity");
					statusMessage.setVisible(true);
					
					//timing the redirection so that the user manages to see the error/success message 
					Timeline timeline = new Timeline(new KeyFrame(
					        Duration.millis(2000),
					        ae -> {
								try {
									redirectReturnPage(event);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}));
					timeline.play();

					break;
				}
			}
			
			int countRev = stmt.executeQuery("SELECT COUNT(*) FROM REVIEW;").getInt(1);
			
			if (!alreadyReviewed) {
				countRev++;
				String description = ReviewArea.getText();
				
				String score = (String) comboBoxScore.getValue();
				String username = CurrentUser.getInstance().getUsername();
				int actID = CurrentActivity.getInstance().getActivity().getActivityID();
				int revID = countRev; //test
				Date d = new Date();
				String dateString = d.toString();
				
				//test print
				System.out.println("id: " + actID + " by " + username + ": " + description + ", " + score);
							
				stmt.executeUpdate("INSERT INTO REVIEW VALUES (" + revID + ",'" + username + "'," + actID
									+ ",'" + description + "','"+d+"',"+score+")");
				
				Review r = new Review(revID,username,actID,description,dateString,Double.parseDouble(score));
				
				((ParentSV) CurrentUser.getInstance().getUser()).addReview(r);
				CurrentActivity.getInstance().getActivity().addReview(r);
				CurrentActivity.getInstance().getActivity().updateRating(Double.parseDouble(score));
				
				statusMessage.setText("Review successfully added");
				statusMessage.setVisible(true);
				
				//timing the redirection so that the user manages to see the error/success message 
				Timeline timeline = new Timeline(new KeyFrame(
				        Duration.millis(2000),
				        ae -> {
							try {
								redirectReturnPage(event);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}));
				timeline.play();
				
			}
							
		}
		catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
		}
		
	}
	
	@FXML
	protected void redirectReturnPage(ActionEvent event) throws IOException{
		
		Parent blah = FXMLLoader.load(getClass().getResource("/fxmlPackage/mainProgram.fxml"));
		Scene scene = new Scene(blah);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		appStage.setScene(scene);
		appStage.setTitle("SportsVisor");
		appStage.setWidth(1280);
		appStage.setHeight(800);
		appStage.show();
	}
	
}
