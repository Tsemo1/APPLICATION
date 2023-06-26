package assogym.view.acceuil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import assogym.commun.Roles;
import assogym.dao.DaoUtilisateur;
import assogym.data.Utilisateur;
import assogym.view.ManagerGui;
import assogym.view.evenement.ViewEvenementCombo;
import assogym.view.systeme.ViewConnexion;
import assogym.view.utilisateur.ModelUtilisateur;
import assogym.view.utilisateur.ViewUtilisateurCombo;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Pair;
import jfox.context.IContext;
import jfox.javafx.util.BindingPairCheckList;
import jfox.javafx.util.UtilFX;
import jfox.javafx.util.converter.ConverterInteger;
import jfox.javafx.view.ControllerAbstract;
import jfox.javafx.view.Mode;
import jfox.javafx.view.View;

public class ViewAcceuilCombo extends ControllerAbstract implements Initializable {

	// -------
	// Composants de la vue
	// -------
	@FXML
	private ImageView image;

	@FXML
	private Label nom;

	@FXML
	private Label lbInterface;

	@FXML
	private PieChart diagramme;

	@FXML
	private Button event;

	@FXML
	private Pane paneAcceuil;

	@FXML
	private Button plan;

	@FXML
	private Button setting;

	@FXML
	private Button story;

	@FXML
	private Button user;

	@FXML
	private Parent fxml;

	@Inject
	private ManagerGui managerGui;

	@Inject
	private DaoUtilisateur daoUser;

	void pieChart() {
		ObservableList<PieChart.Data> pie = FXCollections.observableArrayList(
				new PieChart.Data("" + daoUser.nombreUser() + " Adhérants", daoUser.nombreUser()),
				new PieChart.Data("" + daoUser.nombreUser() + " Evènements", daoUser.nombreUser()),
				new PieChart.Data("" + daoUser.nombreUser() + " Vide Grenier", daoUser.nombreUser()),
				new PieChart.Data("" + daoUser.nombreUser() + " Officiels", daoUser.nombreUser()));
		diagramme.setData(pie);
//		Group root = new Group(diagramme);
	}

	@FXML
	void event(ActionEvent event) {
		managerGui.showView(ViewEvenementCombo.class);
	}

	@FXML
	void home(ActionEvent event) throws IOException {
		test();
	}

	@FXML
	void plan(ActionEvent event) {
//		managerGui.showView(ViewAcceuilCombo.class);
	}

	@FXML
	void setting(ActionEvent event) {
//		managerGui.showView(ViewAcceuilCombo.class);
	}

	@FXML
	void story(ActionEvent event) {
		managerGui.showView(ViewAcceuilCombo.class);
	}

	@FXML
	private Parent fxml1;

	@Inject
	private IContext context;
	
	@Inject
	private ViewUtilisateurCombo conUser;
	

	@FXML
	void user(ActionEvent event) throws IOException {
		managerGui.showView(ViewUtilisateurCombo.class);
//		lbInterface.setText("Adhérants");
// 		View view = new View(ViewUtilisateurCombo.class.getResource("ViewUtilisateurCombo.fxml"));
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("/assogym/view/utilisateur/ViewUtilisateurCombo.fxml"));
//		context.addBean(conUser);
//		loader.setControllerFactory(context::getBean);
//		fxml = loader.load();
//		paneAcceuil.getChildren().removeAll();
//		paneAcceuil.getChildren().setAll(fxml);
	}

	void test() throws IOException {
		lbInterface.setText("Acceuil");
		var loader = new FXMLLoader(Diagramme.class.getResource("PieChart.fxml"));
		ViewUtilisateurCombo view = new ViewUtilisateurCombo();
		context.addBean(view);
		loader.setControllerFactory(context::getBean);
		fxml = loader.load();
		paneAcceuil.getChildren().removeAll();
		paneAcceuil.getChildren().setAll(fxml);

	}

	public void infoUser(String nom1, String photo) {
		try {
			nom.setText(nom1);
			Image image1 = new Image(photo, image.getFitWidth(), image.getFitHeight(), true, true);
			image.setImage(image1);
		} catch (Exception e) {
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		try {
			test();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		infoUser(ViewConnexion.nom2, ViewConnexion.photo2);

	}

}
