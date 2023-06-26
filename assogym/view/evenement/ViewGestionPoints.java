//package assogym.view.evenement;
//
//import java.util.ArrayList;
//
//import assogym.data.Utilisateur;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.scene.control.ComboBox;
//import jfox.javafx.util.UtilFX;
//import jfox.javafx.view.ControllerAbstract;
//
//public class ViewGestionPoints extends ControllerAbstract {
//
//	 @FXML
//	    private ComboBox<Utilisateur> cmbUsers;
//	 
//	 @FXML
//		private void initialize() {
//			
//
//			// Partie liste
//			
//			ArrayList<String> list = new ArrayList<String>();
//			list.add("");
//			
//			ObservableList<String> types = FXCollections.observableArrayList(list);
//			//cmbUsers.setItems(Utilisateur.lsvUtilisateurs.get);
//			
//			
//			
//			// ListView des Evenements
//			
//			lsvEvenements.setItems( modelEvenement.getList() );
//			UtilFX.setCellFactory( lsvEvenements, "nom" );
//			bindBidirectional( lsvEvenements, modelEvenement.currentProperty(), modelEvenement.flagRefreshingListProperty() );
//
//			// Comportement si modificaiton de la séleciton
//			
//			lsvEvenements.getSelectionModel().selectedItemProperty().addListener(( obs, ov, nv) -> {
//				initDraft();
//				configurerBoutons( );
//			});
//			initDraft();
//			configurerBoutons();
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//}
