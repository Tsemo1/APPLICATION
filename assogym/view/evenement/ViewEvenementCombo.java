package assogym.view.evenement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import assogym.commun.Roles;
import assogym.commun.Types;
import assogym.data.Evenement;
import assogym.data.Salle;
//import assogym.data.Type;
import assogym.view.ManagerGui;
import assogym.view.acceuil.ViewAcceuilCombo;
import assogym.view.participants.ViewAjoutParticipantCombo;
import assogym.view.participants.ViewParticipantCombo;
import assogym.view.salle.ViewAjoutSalleCombo;
import assogym.view.systeme.ViewConnexion;
import assogym.view.utilisateur.ViewUtilisateurCombo;
import jakarta.inject.Inject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import jfox.javafx.util.BindingPairCheckList;
import jfox.javafx.util.UtilFX;
import jfox.javafx.util.converter.ConverterBigDecimal;
import jfox.javafx.util.converter.ConverterInteger;
import jfox.javafx.util.converter.ConverterLocalDate;
import jfox.javafx.util.converter.ConverterLocalTime;
import jfox.javafx.view.ControllerAbstract;
import jfox.javafx.view.Mode;

public class ViewEvenementCombo extends ControllerAbstract {

	// -------
	// Composants de la vue
	// -------

	@FXML
	private Button btnAjouter;

	@FXML
	private Label nom;

	@FXML
	private ImageView image;

	@FXML
	private Button btnModifer;

	@FXML
	private Button btnAjoutSalle;

	@FXML
	private Button btnAjoutParticipant;

	@FXML
	private Button btnValider;

	@FXML
	private Button btnReserver;

	@FXML
	private Button btnSupprimer;

	@FXML
	private ComboBox<String> cmbtype;

	@FXML
	private ComboBox<String> cmbSalle;

	@FXML
	private DatePicker date;

	@FXML
	private Label lbl_event;

	@FXML
	private ListView<Evenement> lsvEvenements;

	@FXML
	private TextField tfx_duree;

	@FXML
	private TextField tfx_hdebut;

	@FXML
	private TextField tfx_montant;

//	    @FXML
//	    private TextField tfx_salleevent;

	@FXML
	private TextField tfxname;

	// -------
	// Autres champs
	// -------

	@Inject
	private ManagerGui managerGui;
	@Inject
	private ModelEvenement modelEvenement;

	// -------
	// Initialisations
	// -------

	public void infoUser(String nom1, String photo) {
		try {
			nom.setText(nom1);
			Image image1 = new Image(photo, image.getFitWidth(), image.getFitHeight(), true, true);
			image.setImage(image1);
		} catch (Exception e) {

		}
	}

	@FXML
	private void initialize() {

		// Partie liste
		
		infoUser(ViewConnexion.nom2, ViewConnexion.photo2);

		ArrayList<String> list = new ArrayList<String>();
		list.add("");

		ObservableList<String> types = FXCollections.observableArrayList(list);
		cmbtype.setItems(types);

		// ListView des Evenements

		lsvEvenements.setItems(modelEvenement.getList());
		UtilFX.setCellFactory(lsvEvenements, "nom");
		bindBidirectional(lsvEvenements, modelEvenement.currentProperty(), modelEvenement.flagRefreshingListProperty());

		// Comportement si modificaiton de la séleciton

		lsvEvenements.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv) -> {
			initDraft();
			configurerBoutons();
		});
		initDraft();
		configurerBoutons();

		//

//			configurerBoutons();

		// Partie formulaire

		var draft = modelEvenement.getDraft();

		// Id
		bind(lbl_event, draft.idProperty(), new ConverterInteger());

		// Nom de l'evenement

		bindBidirectional(tfxname, draft.nomProperty());
		validator.addRuleNotBlank(tfxname);
		validator.addRuleMinLength(tfxname, 4);
		validator.addRuleMaxLength(tfxname, 25);
		// validator.addRule(tfxname, "Ce Nom est deja utilisé :( ",
		// modelEvenement::verifierUnicitePseudo );

		// Montant de l'evenement

		bindBidirectional(tfx_montant, draft.montantProperty(), new ConverterInteger());
		validator.addRuleNotBlank(tfx_montant);
		validator.addRuleMinLength(tfx_montant, 1);
		validator.addRuleMaxLength(tfx_montant, 25);

		// Type de l'evenement

		cmbtype.setItems(Types.getTypes()); // .getList // une liste d'evenement conteniu dans le model
		bindBidirectional(cmbtype, draft.typeProperty());
		// UtilFX.setCellFactory( cmbtype, );
		validator.addRuleNotNull(cmbtype);

		// Salle de l'evenement

		ArrayList<String> liste = new ArrayList<String>();
		for (var item : modelEvenement.getListeSalle()) {
			liste.add(item.getId_salle());
		}
		ObservableList<String> liste2 = FXCollections.observableArrayList(liste);
		cmbSalle.setItems(liste2);
		bindBidirectional(cmbSalle, draft.id_salleProperty());
//		UtilFX.setCellFactory(cmbSalle, "id_salle");
		validator.addRuleNotNull(cmbtype);

		// Date de l'evenement

		bindBidirectional(date, draft.dateProperty());

		validator.addRuleNotBlank(date);
		validator.addRuleMinValue(date, LocalDate.now());
		// validator.addRuleMinLength( tfxname, 4 );

		// Heure de debut de l'evenement

		bindBidirectional(tfx_hdebut, draft.debutProperty(), new ConverterLocalTime());
		// validator.addRuleMinValue( tfx_hdebut, LocalTime.of(14, 30));
		// bindBidirectional( txf_hdebut, draft.heureProperty(), new
		// ConverterLocalTime() );

		// duree de l'evenement

		bindBidirectional(tfx_duree, draft.dureeProperty(), new ConverterInteger());

		// ID_Salle de l'evenement
//						
//
//						validator.addRuleNotBlank( tfx_salleevent );
//						validator.addRuleMinLength( tfx_salleevent, 4 );
//						validator.addRuleMaxLength( tfx_salleevent, 25 );

		// Bouton Ajouter
		btnAjouter.disableProperty().bind(validator.invalidProperty());
		btnAjoutParticipant.disableProperty().bind(validator.invalidProperty());
	}

	// afin que le type soit sur un precis

	@Override
	public void refresh() {
		modelEvenement.refreshList();
		lsvEvenements.requestFocus();
	}

	// -------
	// Actions
	// -------

	@FXML
	private void doAjouter() {

		// Le reste de la logique pour ajouter l'événement si la date est valide
		lsvEvenements.getSelectionModel().select(null);
		tfxname.requestFocus();
		// modelEvenement.saveDraft(); // Ajouter l'événement
		// refresh();
	}

	@FXML
	void doReserver(ActionEvent event) {

	}

	@FXML
	private void doValider() {

		LocalDate selectedDate = date.getValue();
		LocalDate currentDate = LocalDate.now();
		if (selectedDate == null || selectedDate.isBefore(currentDate)) {
			// Afficher un message d'erreur ou une notification indiquant que la date est
			// invalide
			managerGui.showDialogError("La date sélectionnée pour evenement est antérieure à la date du jour.");
//		        return;
		} else {
			modelEvenement.saveDraft();
			refresh();
		}

	}

	@FXML
	private void doAjoutSalle() {
		managerGui.showDialog(ViewAjoutSalleCombo.class);

	}

	@FXML
	private void doSupprimer() {
		if (managerGui.showDialogConfirm("Confirmez-vous la suppresion ?")) {
			modelEvenement.deleteCurrent();
			refresh();
		}
	}

	@FXML
	private void doAnnuler() {
		if (modelEvenement.getCurrent() == null) {
			initDraft();
		}
		refresh();
	}

	@FXML
	private void doImprimer() {

	}

	@FXML
	private void doModifier() {
//			if( modelEvenement.getCurrent() == null ) {
//				initDraft();
//			}

//			modelEvenement.initDraft( Mode.EDIT );
		// managerGui.showView( ViewEvenementCombo.class );
//			refresh();

		// modelEvenement.saveDraft();
		// refresh();
	}

	@FXML
	private void doAjoutParticipant() {
		managerGui.showView(ViewParticipantCombo.class);
	}

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
	void home(ActionEvent event) {
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

	// -------
	// Méthodes auxiliaires
	// -------

	private void initDraft() {
		if (lsvEvenements.getSelectionModel().getSelectedItem() == null) {
			modelEvenement.initDraft(Mode.NEW);
		} else {
			modelEvenement.initDraft(Mode.EDIT);
		}
		validator.reset();
	}

	private void configurerBoutons() {
		var flagDisable = lsvEvenements.getSelectionModel().getSelectedItem() == null;
		btnSupprimer.setDisable(flagDisable);
	}

	// -------
	// Gestion des évènements
	// -------

	// Clic sur la liste
	@FXML
	private void gererClicSurListe(MouseEvent event) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if (lsvEvenements.getSelectionModel().getSelectedIndex() == -1) {
					managerGui.showDialogError("Aucun Evenement n'est sélectionné dans la liste.");
				} else {
					doValider();
				}
			}
		}
	}

}
