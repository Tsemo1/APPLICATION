package assogym.view.acceuil;

import java.util.ResourceBundle;

import assogym.dao.DaoEvenement;
import assogym.dao.DaoUtilisateur;
import assogym.view.ManagerGui;
import assogym.view.systeme.ViewConnexion;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

public class Diagramme {

	@FXML
	private PieChart diagramme;

	@Inject
	private ManagerGui managerGui;

	@Inject
	private DaoUtilisateur daoUser;

	@Inject
	private DaoEvenement daoEvent;

	void pieChart() {
		ObservableList<PieChart.Data> pie = FXCollections.observableArrayList(
				new PieChart.Data("" + daoUser.nombreUser() + " Adhérants", daoUser.nombreUser()),
				new PieChart.Data("" + daoEvent.nombreEvenement() + " Evènements", daoUser.nombreUser()),
				new PieChart.Data("" + daoEvent.nombreVideGrenier() + " Vide Grenier", daoUser.nombreUser()));
		diagramme.setData(pie);
	}

	public void initialize() {
		// TODO Auto-generated method stub
		System.out.println(ViewConnexion.nom2);
		diagramme.setTitle("Récapitulatifs des différentes données de l'association");
		pieChart();

	}

}
