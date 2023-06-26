package assogym.view.adherent;

import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import assogym.commun.Roles;
import assogym.dao.DaoEvenement;
import assogym.dao.DaoUtilisateur;
import assogym.data.Evenement;
import assogym.view.ManagerGui;
import assogym.view.evenement.ModelEvenement;
import assogym.view.systeme.ViewConnexion;
import assogym.view.utilisateur.ModelUtilisateur;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import jfox.javafx.util.UtilFX;
import jfox.javafx.util.converter.ConverterInteger;
import jfox.javafx.view.ControllerAbstract;
import jfox.javafx.view.Mode;

public class ViewAdherentCombo extends ControllerAbstract {

	// -------
	// Composants de la vue
	// ------

	@FXML
	private ImageView imgUtilisateur;

	@FXML
	private Button btnImporter;

	@FXML
	private TableView<Evenement> liste;

	@FXML
	private TextField txfPhoto;

	@FXML
	private GridPane paneForm;

	@FXML
	private TextField txfNom;

	@FXML
	private TextField txfMotDePasse;

	@FXML
	private TextField txfTelephone;

//	    @FXML
//	    private ListView<String> lsvStatus;

	@FXML
	private TextField txfMail;

	@FXML
	private Label points;

	@FXML
	private ComboBox<String> cmbSexe;

	@FXML
	private DatePicker dateN;

	@FXML
	private TextField txfadresse;

	@FXML
	private Button btnValider;

	@FXML
	private TableColumn<Evenement, String> colevenements;

	@FXML
	private TableColumn<Evenement, LocalDate> coldate;

	@FXML
	private TableColumn<Evenement, LocalTime> colduree;

	@FXML
	private TableColumn<Evenement, Integer> colprix;

	private ManagerGui managerGui;

	@Inject
	private ModelUtilisateur modelUtilisateur;
	@Inject
	private ModelEvenement  modelEvenement;
	@Inject
	private DaoUtilisateur daoUtilisateur;
	@Inject
	private DaoEvenement daoEvents;

	// -------
	// Initialisations
	// -------

	public void infoUser(String nom1, String photo) {
		try {
			txfNom.setText(nom1);
			Image image1 = new Image(photo, imgUtilisateur.getFitWidth(), imgUtilisateur.getFitHeight(), true, true);
			imgUtilisateur.setImage(image1);
		} catch (Exception e) {
		}
	}

	public void infoUser2(String photo) {
//		Image image1 = new Image(photo, image.getFitWidth(), image.getFitHeight(), true, true);
//		imgUtilisateur.setImage(image1);
	}

	@FXML
	private void initialize() throws SQLException {


		// Partie liste
		System.out.println(ViewConnexion.nom2);
		infoUser(ViewConnexion.nom2, ViewConnexion.photo2);
		
	

		// tableview
		ObservableList<Evenement> even = FXCollections.observableArrayList(daoEvents.rechercherEvenementsPourUtilisateur(ViewConnexion.utilisateurEnCours.getMail()));
		liste.setItems(even);
	
		UtilFX.setValueFactory( colevenements, "nom" );
		UtilFX.setValueFactory(coldate , "date" );
		UtilFX.setValueFactory(colduree , "duree" );
		UtilFX.setValueFactory(colprix, "montant" );
		modelUtilisateur.flagRefreshingListProperty();

		
		// Partie formulaire

				

		// remplissage du comboBox de sexe
		ArrayList<String> list = new ArrayList<String>();
		list.add("masculin");
		list.add("feminin");
		ObservableList<String> sexe = FXCollections.observableArrayList(list);
		cmbSexe.setItems(sexe);

		var draft = ViewConnexion.utilisateurEnCours;

		// points
		bind(points, draft.pointsProperty(), new ConverterInteger());

		// nom
		bindBidirectional(txfNom, draft.nomProperty());
		validator.addRuleNotBlank(txfNom);
		validator.addRuleMinLength(txfNom, 3);
		validator.addRuleMaxLength(txfNom, 25);
//		validator.addRule(txfNom, "Ce pseudo est déjà utilisé", modelUtilisateur::verifierUnicitePseudo  );

		// Mot de passe
		bindBidirectional(txfMotDePasse, draft.motDePasseProperty());
		validator.addRuleNotBlank(txfMotDePasse);
		validator.addRuleMinLength(txfMotDePasse, 3);
		validator.addRuleMaxLength(txfMotDePasse, 25);

		// Date
		bindBidirectional(dateN, draft.dateNProperty());
		validator.addRuleNotBlank(dateN);

		// Adresse e-mail
		bindBidirectional(txfMail, draft.mailProperty());
		validator.addRuleNotBlank(txfMail);
		validator.addRuleMaxLength(txfMail, 100);
		validator.addRuleEmail(txfMail);

		// photo
		bindBidirectional(txfPhoto, draft.photoProperty());
		validator.addRuleNotBlank(txfPhoto);

//		// ListView des roles
//		var binding = new BindingPairCheckList<>(Roles.getRoles(), draft.getStatus());
//		binding.configureListView(lsvStatus, item -> Roles.getLibelle(item.getKey()));

		// telephone
		bindBidirectional(txfTelephone, draft.telephoneProperty(), new ConverterInteger());
		validator.addRuleNotBlank(txfTelephone);

		// sexe
		bindBidirectional(cmbSexe, draft.sexeProperty());
		validator.addRuleNotBlank(cmbSexe);

		// Bouton Valider
		btnValider.disableProperty().bind(validator.invalidProperty());
	}

	@Override
	public void refresh() {
//		afficherPhoto(txfPhoto.getText());
		modelUtilisateur.refreshList();
//		lsvUtilisateurs.requestFocus();
	}

	// -------
	// Actions
	// -------

	@FXML
	void doPhoto(MouseEvent event) {
		System.out.println(txfPhoto.getText());
		infoUser2(txfPhoto.getText());
	}

//	@FXML
//	private void doAjouter() {
//		lsvUtilisateurs.getSelectionModel().select(null);
//		txfNom.requestFocus();
//	}

//	@FXML
//	private void doSupprimer() {
//		if (managerGui.showDialogConfirm("Confirmez-vous la suppresion ?")) {
//			modelUtilisateur.deleteCurrent();
//			refresh();
//		}
//	}

	@FXML
	private void doAnnuler() {
		if (modelUtilisateur.getCurrent() == null) {
//			initDraft();
		}
		refresh();
	}

	@FXML
	private void doValider() {
		modelUtilisateur.saveDraft();
		refresh();
	}

	@FXML
	private void doImprimer() {
	}

	// -------
	// Méthodes auxiliaires
	// -------

	@FXML
	void doImporter() {
		FileChooser fo = new FileChooser();
		fo.getExtensionFilters().add(new ExtensionFilter("Images Files", "*.png", "*.jpg"));
		File f = fo.showOpenDialog(null);
		if (f != null) {
			txfPhoto.setText(f.getAbsolutePath());
			Image image = new Image(f.toURI().toString(), imgUtilisateur.getFitWidth(), imgUtilisateur.getFitHeight(),
					true, true);
			imgUtilisateur.setImage(image);
		}
	}

}
