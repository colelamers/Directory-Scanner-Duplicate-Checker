package application;
//Created by Cole Lamers
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class jfsdcMain extends Application 
{
	public static Stage primaryStage;
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		Parent root =
				FXMLLoader.load(getClass().getResource("jfsdc.fxml"));

		Scene scene = new Scene(root);
		primaryStage.setTitle("Java Duplicate Checker");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		this.primaryStage = primaryStage;

	}

	public static void main(String[] args) 
	{
		launch(args);
	}
}
