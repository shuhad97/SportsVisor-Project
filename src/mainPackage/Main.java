package mainPackage;


import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Main extends Application {
	@FXML
	private Button closeButton;
	
    public static void main(String[] args) {
    	
    	UserRegistry.getInstance().populateRegistry();
    	//UserRegistry.getInstance().printReg();
    	
    	ActivityRegistry.getInstance().populateRegistry();
    	//ActivityRegistry.getInstance().printReg();
    	
    	//adding the fav activities to list of each parent
    	UserRegistry.getInstance().populateParentSVFavouriteActivityList();
    	UserRegistry.getInstance().populateParentSVReviewList();
    	UserRegistry.getInstance().populateActivityProviderActivities();
    	
        Application.launch(Main.class, args);
        
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxmlPackage/mainProgram.fxml"));
        stage.setTitle("SportsVisor");
        stage.setScene(new Scene(root, 1280, 800));
        stage.show();
        

    }
}
