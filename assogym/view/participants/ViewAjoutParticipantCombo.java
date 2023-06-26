package assogym.view.participants;

import assogym.dao.DaoCompteParti;
import assogym.data.Parti;
//import assogym.data.Type;
import assogym.view.ManagerGui;
import jakarta.inject.Inject;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import jfox.javafx.util.converter.ConverterInteger;
import jfox.javafx.view.ControllerAbstract;

public class ViewAjoutParticipantCombo extends ControllerAbstract {

	 @FXML
	    private Label id;

	    @FXML
	    private TextField txfMail;

	    @FXML
	    private TextField txfNom;

	    @FXML
	    private TextField txfTelephone;
	    
		//-------
		// Autres champs
		//-------
		
		@Inject
		private ManagerGui			managerGui;
		
		@Inject
		private ModelCompteParti modelCompteParti;

		@Inject
		DaoCompteParti daoParti;
		
	    @FXML
	    void doAjouter(ActionEvent event) {
	    	try {
				Parti participant = new Parti(Integer.parseInt(txfTelephone.getText()),txfNom.getText(),txfMail.getText());
				daoParti.inserer(participant);
				viewParti.refresh();
				managerGui.closeDialog();
			} catch (NumberFormatException e) {
			}
	    }

	    @FXML
	    void doAnnuler(ActionEvent event) {
	    	managerGui.closeDialog();
	    }

	// -------
	// Initialisations
	// -------
	    
	    

		public void initialize() {
			
		}

		@Inject
		ViewParticipantCombo viewParti;

	@Override
	public void refresh() {
		
	}
	
	

}
