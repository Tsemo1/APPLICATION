package assogym.view.systeme;

import assogym.view.ManagerGui;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import jfox.javafx.view.ControllerAbstract;

public class ViewAbout extends ControllerAbstract {
	
	
	//-------
	// Composants de la vue
	//-------
	
	@FXML
	private Label		labTitre;
	@FXML
	private Label		labVersion;
	@FXML
	private Label		labAuteur;
	@FXML
	private Hyperlink	hlkEmail;
	
	
	//-------
	// Champs
	//-------
	
	@Inject
	private ManagerGui		managerGui;
	
	
	//-------
	// Initialisations
	//-------
	
	@FXML
	private void initialize() {
		
		labTitre.setText( "AssoGYM" );
		labVersion.setText( "2023-06" );
		labAuteur.setText( "Elèves 3iL I1" );
		hlkEmail.setText( "" );
		
		managerGui.getStage().setResizable(false);
		
	}

	//-------
	// Actions
	//-------
	
	@FXML
	private void doSendEmail() {
		if (hlkEmail.getText() != null && ! hlkEmail.getText().isBlank() ) {
			var dest = "mailto:" + labAuteur.getText() + "<" + hlkEmail.getText() + ">";
			managerGui.showDocument( dest );
		}
	}
	
	@FXML
	private void doFermer() {
		managerGui.closeDialog();
	}

}
