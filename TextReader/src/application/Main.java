package application;
	
import java.io.File;
import java.util.Scanner;

import application.model.Support_Methods;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	static String User_home = System.getProperty("user.home").toString();
	public static Stage stage;
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			stage.setTitle("Text Reader Project");
			Parent root = FXMLLoader.load(getClass().getResource("/application/view/Main.fxml"));
			stage.setScene(new Scene(root,600,600));
			Support_Methods.CreateDirectory();
			File file = new File(User_home+"/AppData/Local/TextReader_app/settings.txt");
			Scanner reader = new Scanner(file);
			String data = reader.nextLine();
			reader.close();
			String arr[]=data.split(",");
			String defaultcss = arr[1];
			stage.getScene().getStylesheets().add(getClass().getResource(defaultcss).toExternalForm());
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
