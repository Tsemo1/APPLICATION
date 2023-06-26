package assogym.view.utilisateur;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import assogym.commun.Roles;
import assogym.commun.Status;
import assogym.dao.DaoUtilisateur;
import assogym.data.Utilisateur;
import assogym.view.ManagerGui;
import assogym.view.acceuil.Diagramme;
import assogym.view.acceuil.ViewAcceuilCombo;
import assogym.view.evenement.ViewEvenementCombo;
import assogym.view.systeme.ViewConnexion;
import jakarta.inject.Inject;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Pair;
import jfox.context.IContext;
import jfox.javafx.util.BindingPairCheckList;
import jfox.javafx.util.UtilFX;
import jfox.javafx.util.converter.ConverterInteger;
import jfox.javafx.view.ControllerAbstract;
import jfox.javafx.view.Mode;

public class ViewUtilisateurCombo extends ControllerAbstract {

	// -------
	// Composants de la vue
	// -------

	@FXML
	private Button event;

	@FXML
	private ImageView image;

	@FXML
	private Label nom;

	@FXML
	private Button plan;

	@FXML
	private Button setting;

	@FXML
	private Button story;

	@FXML
	private Button user;

	@FXML
	private AnchorPane paneUtilisateur;

	@FXML
	private Button btnAjouter;

	@FXML
	private Button btnImporter;

	@FXML
	private Button btnSupprimer;

	@FXML
	private Button btnValider;

	@FXML
	private ComboBox<String> cmbSexe;

//	@FXML
//	private ComboBox<Integer> cmbAnnee;

	@FXML
	private ImageView imgUtilisateur;

	@FXML
	private ListView<Pair<String, BooleanProperty>> lsvStatus;

	@FXML
	private ListView<Utilisateur> lsvUtilisateurs;

	@FXML
	private GridPane paneForm;

	@FXML
	private Label points;

	@FXML
	private TextField txfPhoto;

	@FXML
	private TextField txfMail;

	@FXML
	private TextField txfMotDePasse;

	@FXML
	private TextField txfNom;

	@FXML
	private TextField txfTelephone;

	@FXML
	private DatePicker dateN;

	@FXML
	private Label lbInterface;

	@Inject
	public static IContext context;

	// -------
	// Autres champs
	// -------

	@Inject
	private ManagerGui managerGui;
	@Inject
	private ModelUtilisateur modelUtilisateur;
	@Inject
	private DaoUtilisateur daoUtilisateur;

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

	public void infoUser2(String photo) {
		Image image1 = new Image(photo, image.getFitWidth(), image.getFitHeight(), true, true);
		imgUtilisateur.setImage(image1);
	}

	@FXML
	public void initialize() {

		// Partie liste
//		System.out.println(ViewConnexion.nom2);
		infoUser(ViewConnexion.nom2, ViewConnexion.photo2);

		// remplissage du comboBox de sexe
		ArrayList<String> list = new ArrayList<String>();
		list.add("");
		list.add("masculin");
		list.add("feminin");
		ObservableList<String> sexe = FXCollections.observableArrayList(list);
		cmbSexe.setItems(sexe);

		// ListView des comptes
		lsvUtilisateurs.setItems(modelUtilisateur.getList());
		UtilFX.setCellFactory(lsvUtilisateurs, "nom");
		bindBidirectional(lsvUtilisateurs, modelUtilisateur.currentProperty(),
				modelUtilisateur.flagRefreshingListProperty());

		// Comportement si modificaiton de la séleciton
		lsvUtilisateurs.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv) -> {
			initDraft();
			configurerBoutons();
		});
		initDraft();
		configurerBoutons();

		// Partie formulaire

		var draft = modelUtilisateur.getDraft();

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
		if (draft.photoProperty() != null && draft.getPhoto() != null) {
			Image image2 = new Image(draft.getPhoto(), imgUtilisateur.getFitWidth(), imgUtilisateur.getFitHeight(),
					true, true);
			imgUtilisateur.setImage(image2);
		}
		validator.addRuleNotBlank(txfPhoto);

		// ListView des roles
		var binding = new BindingPairCheckList<>(Status.getStatus(), draft.getStatus());
		binding.configureListView(lsvStatus, item -> Status.getLibelle(item.getKey()));

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
		lsvUtilisateurs.requestFocus();

	}

	// -------
	// Actions
	// -------

	@FXML
	void drag() {
		try {
			var draft = modelUtilisateur.getDraft();
			if (draft.photoProperty() != null && draft.getPhoto() != null) {
				Image image2 = new Image(draft.getPhoto(), imgUtilisateur.getFitWidth(), imgUtilisateur.getFitHeight(),
						true, true);
				imgUtilisateur.setImage(image2);
			}
		} catch (Exception e) {
		}
	}

	@FXML
	void doPhoto(MouseEvent event) {
		System.out.println(txfPhoto.getText());
		infoUser2(txfPhoto.getText());
	}

	@FXML
	private void doAjouter() {
		lsvUtilisateurs.getSelectionModel().select(null);
		txfNom.requestFocus();
	}

	@FXML
	private void doSupprimer() {
		if (managerGui.showDialogConfirm("Confirmez-vous la suppresion ?")) {
			modelUtilisateur.deleteCurrent();
			refresh();
		}
	}

	@FXML
	private void doAnnuler() {
		if (modelUtilisateur.getCurrent() == null) {
			initDraft();
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

	private void initDraft() {
		if (lsvUtilisateurs.getSelectionModel().getSelectedItem() == null) {
			modelUtilisateur.initDraft(Mode.NEW);
		} else {
			modelUtilisateur.initDraft(Mode.EDIT);
		}
		validator.reset();
	}

	private void configurerBoutons() {
		var flagDisable = lsvUtilisateurs.getSelectionModel().getSelectedItem() == null;
		btnSupprimer.setDisable(flagDisable);
	}

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

	@FXML
	void event(ActionEvent event) {
		managerGui.showView(ViewEvenementCombo.class);
	}

	@FXML
	void plan(ActionEvent event) {
		managerGui.showView(ViewAcceuilCombo.class);
	}

	@FXML
	void home() throws IOException {
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

}
