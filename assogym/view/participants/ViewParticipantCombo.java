package assogym.view.participants;

import assogym.dao.DaoCompteParti;
import assogym.dao.DaoUtilisateur;
import assogym.data.Parti;
import assogym.data.Utilisateur;
import assogym.view.ManagerGui;
import assogym.view.acceuil.ViewAcceuilCombo;
import assogym.view.evenement.ViewEvenementCombo;
import assogym.view.systeme.ViewAbout;
import assogym.view.systeme.ViewConnexion;
import assogym.view.utilisateur.ModelUtilisateur;
import assogym.view.utilisateur.ViewUtilisateurCombo;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.ControllerAbstract;
import jfox.javafx.view.Mode;

public class ViewParticipantCombo extends ControllerAbstract {

	@FXML
	private Button btnAjouter;

	@FXML
	private Button btnSupprimer;

	@FXML
	private Button btnValider;

	@FXML
	private Button event;

	@FXML
	private ImageView image;

	@FXML
	private Label lbInterface;

	@FXML
	private ListView<Parti> lsvUtilisateurs;

	@FXML
	private ListView<Utilisateur> lsvUtilisateurs1;

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

	// -------
	// Autres champs
	// -------

	@Inject
	private ManagerGui managerGui;
	@Inject
	private ModelCompteParti modelCompteParti;
	@Inject
	private ModelUtilisateur modelUtilisateur;
	@Inject
	private DaoUtilisateur daoUtilisateur;

	// ------
	// Initialisation
	// ------

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

		infoUser(ViewConnexion.nom2, ViewConnexion.photo2);

		// ListView des Participants

		lsvUtilisateurs.setItems(modelCompteParti.getList());
		UtilFX.setCellFactory(lsvUtilisateurs, "nom");
		bindBidirectional(lsvUtilisateurs, modelCompteParti.currentProperty(),
				modelCompteParti.flagRefreshingListProperty());

		// ListView des Adhérents

		lsvUtilisateurs1.setItems(modelUtilisateur.getList());
		UtilFX.setCellFactory(lsvUtilisateurs1, "nom");
		bindBidirectional(lsvUtilisateurs1, modelUtilisateur.currentProperty(),
				modelUtilisateur.flagRefreshingListProperty());

		// Comportement si modificaiton de la séleciton

		lsvUtilisateurs1.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv) -> {
			initDraft();
			configurerBoutons();
		});

		lsvUtilisateurs.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv) -> {
			initDraft();
			configurerBoutons();
		});

		initDraft();
		configurerBoutons();

		// Bouton Ajouter
		/*
		 * btnAjouter.disableProperty().bind(validator.invalidProperty());
		 * user.disableProperty().bind(validator.invalidProperty());
		 */
	}

	@Override
	public void refresh() {
		modelCompteParti.refreshList();
		modelUtilisateur.refreshList();
		try {
			lsvUtilisateurs.requestFocus();
			lsvUtilisateurs1.requestFocus();
		} catch (Exception e) {
		}
	}

	@FXML
	private TextField txfRechercher;

	@FXML
	void doRechercher(ActionEvent event) {
		// ListView des Adhérents
		String nom = txfRechercher.getText();
		if ( nom != null) {
			lsvUtilisateurs1.setItems(daoUser.retrouverNom(nom));
			UtilFX.setCellFactory(lsvUtilisateurs1, "nom");
		} else if(txfRechercher.getText() == null){
		}
	}

	@FXML
	void doAnnuler(ActionEvent event) {

		if (modelCompteParti.getCurrent() == null) {
			initDraft();
		}
		if (modelUtilisateur.getCurrent() == null) {
			initDraft();
		}
		refresh();
	}

	@FXML
	void doImprimer(ActionEvent event) {

	}

	@Inject
	DaoUtilisateur daoUser;

	@FXML
	void doSupprimer(ActionEvent event) {

		if (managerGui.showDialogConfirm("Confirmez-vous la suppresion ?")) {

			try {
				String mail = modelCompteParti.getCurrent().getEmail();
				for (var item : modelUtilisateur.getList()) {
					if (item.getMail() == mail) {
						item.setPoints(item.getPoints() - 5);
						daoUser.modifier(item);
						break;
					}
				}
				modelCompteParti.deleteCurrent();
				refresh();
			} catch (Exception e) {
			}
		}
	}

	@FXML
	void newParticipant() {
//		  managerGui.showView(ViewAjoutParticipantCombo.class);
		managerGui.showDialog(ViewAjoutParticipantCombo.class);
	}

	@FXML
	void event(ActionEvent event) {
		managerGui.showView(ViewEvenementCombo.class);
	}

	@FXML
	void home(ActionEvent event) {
		managerGui.showView(ViewAcceuilCombo.class);
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

	@Inject
	DaoCompteParti daoParti;

	@FXML
	void doAjouter(ActionEvent event) {
		var draft = modelUtilisateur.getDraft();
		Parti participant = new Parti(draft.getTelephone(), draft.getNom(), draft.getMail());
		daoParti.inserer(participant);
		refresh();
		draft.setPoints(draft.getPoints() + 5);
		modelUtilisateur.saveDraft();
		System.out.println(draft.getPoints());
//		managerGui.showView(ViewAjoutParticipantCombo.class);

		/*
		 * modelCompteParti.initDraft( Mode.NEW ); managerGui.showDialog(
		 * ViewParticipantCombo.class ); lsvUtilisateurs.requestFocus();
		 */

	}

	@FXML
	private void doValider() {
		modelUtilisateur.saveDraft();
		refresh();
	}

	// Clic sur la liste
	@FXML
	private void gererClicSurListe(MouseEvent event) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if (lsvUtilisateurs.getSelectionModel().getSelectedIndex() == -1) {
					managerGui.showDialogError("Aucun Participant n'est sélectionné dans la liste.");
				} else {
					modelCompteParti.saveDraft();
					refresh();
				}
			}
		}
	}

	@FXML
	private void gererClicSurListe1(MouseEvent event) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if (lsvUtilisateurs1.getSelectionModel().getSelectedIndex() == -1) {
					managerGui.showDialogError("Aucun Participant n'est sélectionné dans la liste.");
				} else {
					doValider();
				}
			}
		}
	}

	// -------
	// Méthodes auxiliaires
	// -------

	private void initDraft() {

		if (lsvUtilisateurs1.getSelectionModel().getSelectedItem() == null) {
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

}
