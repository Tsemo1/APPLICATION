
package assogym.view.salle;

import assogym.view.acceuil.ViewAcceuilCombo;
import assogym.view.evenement.ModelEvenement;
import assogym.view.evenement.ViewEvenementCombo;
import assogym.view.salle.ModelSalle;
import assogym.view.salle.ViewAjoutSalleCombo;
import assogym.view.utilisateur.ViewUtilisateurCombo;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.time.LocalDate;
import java.util.ArrayList;

import assogym.commun.Types;
import assogym.data.Salle;
import assogym.commun.Disponib ;
import assogym.view.ManagerGui;
import assogym.commun.Disponib;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import jfox.javafx.util.UtilFX;
import jfox.javafx.util.converter.ConverterInteger;
import jfox.javafx.util.converter.ConverterLocalTime;
import jfox.javafx.view.ControllerAbstract;
import jfox.javafx.view.Mode;

public class ViewAjoutSalleCombo extends ControllerAbstract {

	@FXML
	private Button btnAjouter;

	@FXML
	private Button btnSupprimer;

	@FXML
	private Button btnValider;

	@FXML
	private ComboBox<String> cmbDispo;

	@FXML
	private Button event;

	@FXML
	private ImageView image;

	@FXML
	private ImageView imgUtilisateur;

	@FXML
	private ListView<Salle> lsvSalles;

	@FXML
	private Label nom;

	@FXML
	private GridPane paneForm;

	@FXML
	private Button plan;

	@FXML
	private Button setting;

	@FXML
	private Button story;

	@FXML
	private TextField tfxAdresse;

	@FXML
	private TextField tfxCapacite;

	@FXML
	private TextField tfxNom;

	@FXML
	private TextField tfxPrix;

	@FXML
	private TextField tfxStands;

	@FXML
	private Button user;

	@Inject
	private ManagerGui managerGui;
	@Inject
	private ModelSalle modelSalle;

	// -------
	// Initialisations
	// -------

	@FXML
	private void initialize() {

		// Partie liste

		ArrayList<String> list = new ArrayList<String>();
		//list.add("");

		ObservableList<String> disponibilite = FXCollections.observableArrayList(list);
		cmbDispo.setItems(disponibilite);

		// ListView des Salless

		lsvSalles.setItems(modelSalle.getList());
		UtilFX.setCellFactory(lsvSalles, "id_salle");
		
		bindBidirectional(lsvSalles, modelSalle.currentProperty(), modelSalle.flagRefreshingListProperty());

		// Comportement si modificaiton de la séleciton

		lsvSalles.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv) -> {
			initDraft();
			configurerBoutons();
		});
		initDraft();
		configurerBoutons();

		// Gestion des salles 

//					configurerBoutons();

		// Partie formulaire

		var draft = modelSalle.getDraft();

		// Id
		bindBidirectional(tfxPrix, draft.prixProperty(), new ConverterInteger());

		// Nom de l'Salles

		bindBidirectional(tfxNom, draft.id_salleProperty());
		validator.addRuleNotBlank(tfxNom);
		validator.addRuleMinLength(tfxNom, 4);
		validator.addRuleMaxLength(tfxNom, 25);
		// validator.addRule(tfxname, "Ce Nom est deja utilisé :( ",
		// modelSalles::verifierUnicitePseudo );

		// Adresse des Salles
		
		bindBidirectional(tfxAdresse, draft.adresseProperty());
		validator.addRuleNotBlank(tfxAdresse);
		validator.addRuleMinLength(tfxAdresse, 5);
		validator.addRuleMaxLength(tfxAdresse, 200);

		// Capacite
		
		bindBidirectional(tfxCapacite, draft.capaciteProperty(), new ConverterInteger());
		validator.addRuleNotBlank(tfxCapacite);
		validator.addRuleMinLength(tfxCapacite, 1);
		validator.addRuleMaxLength(tfxCapacite, 200);

		// Disponibilités des Salles

		cmbDispo.setItems( Disponib.getDisponib()); // .getList // une liste d'Salles conteniu dans le model
		bindBidirectional( cmbDispo, draft.disponibiliteProperty());
		// UtilFX.setCellFactory( cmbtype, );
		validator.addRuleNotNull(cmbDispo);


		// Nombre de stands

		bindBidirectional(tfxStands, draft.nombreStandProperty(), new ConverterInteger());


		// Bouton Ajouter
		btnAjouter.disableProperty().bind(validator.invalidProperty());
		// btnAjoutParticipant.disableProperty().bind(validator.invalidProperty());
	}

	// afin que le type soit sur un precis

	@Override
	public void refresh() {
		modelSalle.refreshList();
		lsvSalles.requestFocus();
		
	}

// methode pour ajouter une salle 	    

	@FXML
	void doAjouter(ActionEvent event) {

		// Le reste de la logique pour ajouter l'événement si la date est valide
		lsvSalles.getSelectionModel().select(null);
		tfxNom.requestFocus();
		
		 //modelEvenement.saveDraft(); // Ajouter l'événement
		// refresh();
	}

	// methode pour annuler une salle
	// @FXML
//	    void doAnnuler(ActionEvent event) {
//
//	    }

	// methode pour imprimer une salle
	@FXML
	void doImprimer(ActionEvent event) {

	}

	// methode pour supprimer une salle
	@FXML
	void doSupprimer(ActionEvent event) {
		if (managerGui.showDialogConfirm("Confirmez-vous la suppresion de la Salle ?")) {
			modelSalle.deleteCurrent();
			refresh();
		}
	}
	
	@Inject
	ModelEvenement modelEvenement;

	// methode pour valider une salle
	@FXML
	void doValider() {
		modelSalle.saveDraft();
		refresh();
		modelEvenement.refreshList();
		managerGui.closeDialog();
	}

	// ----
	// Actions des boutons generaux de l'application
	// ---------

	@FXML
	void event(ActionEvent event) {
		managerGui.showView(ViewEvenementCombo.class);
	}

	@FXML
	void plan(ActionEvent event) {
		managerGui.showView(ViewAcceuilCombo.class);
	}

	@FXML
	void setting(ActionEvent event) {
		managerGui.showView(ViewAcceuilCombo.class);
	}

	@FXML
	void story(ActionEvent event) {
		managerGui.showView(ViewAcceuilCombo.class);
	}

	@FXML
	void user(ActionEvent event) {
		managerGui.showView(ViewUtilisateurCombo.class);
	}
	
	//-------
			// Méthodes auxiliaires
			//-------
			
			private void initDraft() {
				if ( lsvSalles.getSelectionModel().getSelectedItem() == null ) {
					modelSalle.initDraft( Mode.NEW );
				} else {
					modelSalle.initDraft( Mode.EDIT );
				}
				validator.reset();
			}
			
			private void configurerBoutons() {
				var flagDisable = lsvSalles.getSelectionModel().getSelectedItem() == null;
				btnSupprimer.setDisable( flagDisable );
			}

	// Gerer le clic sur liste
	@FXML
	private void gererClicSurListe(MouseEvent event) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if (lsvSalles.getSelectionModel().getSelectedIndex() == -1) {
					managerGui.showDialogError("Aucun Evenement n'est sélectionné dans la liste.");
				} else {
					doValider();
				}
			}
		}
	}

}
