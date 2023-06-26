package test.git;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Test_TMWV extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		
		System.out.println( "Début"  );

		var root = FXMLLoader.<Parent>load( getClass().getResource("View_TMWV.fxml") );
		stage.setScene( new Scene(root) );
		stage.show();
		
		System.out.println( "Fin"  );
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
