package assogym.view.systeme;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import assogym.dao.DaoEvenement;
import assogym.dao.DaoUtilisateur;
import assogym.data.Evenement;
import assogym.data.Utilisateur;
import assogym.view.ManagerGui;
import assogym.view.acceuil.ViewAcceuilCombo;
import assogym.view.adherent.ViewAdherentCombo;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import jfox.javafx.view.ControllerAbstract;


public class ViewConnexion extends ControllerAbstract {
	

	//-------
	// Composants de la vue
	//-------
	
	@FXML
	private TextField		txfPseudo;
	@FXML
	private PasswordField	pwfMotDePasse;
	@Inject
	private DataSource dataSource;

	
	//-------
	// Autres champs
	//-------
	@Inject
    public static Utilisateur utilisateurEnCours;
	
	@Inject
	private ManagerGui		managerGui;
	@Inject
	private ModelConnexion	modelConnexion;
	@Inject
	private ModelInfo		modelInfo;
	
	
	//-------
	// Initialisations
	//-------
	
	@FXML
	private void initialize() {

		var draft = modelConnexion.getDraft();
		
		// Data binding
		bindBidirectional( txfPseudo, draft.mailProperty() );
		bindBidirectional( pwfMotDePasse, draft.motDePasseProperty() );

	}
	
	
	@Override
	public void refresh() {
		// Ferem la session si elle est ouverte
		if ( modelConnexion.getUtilisateurActif() != null ) {
			modelConnexion.fermerSessionUtilisateur();
		}
	}
	

	//-------
	// Actions
	//-------
	
	@Inject
	private DaoUtilisateur daoUser;
	@Inject
	private DaoEvenement daoEvents;
	
	@Inject
	ViewAcceuilCombo acceuil;
	
	public static String nom2 = "";
	public static String photo2 = "";
	
	@FXML
	private void doConnexion() throws SQLException {
		managerGui.execTask( () -> {
			modelConnexion.ouvrirSessionUtilisateur();
			Platform.runLater( () -> {
				try {
					if (rechercher(txfPseudo.getText()).equals("ADMINISTRATEUR")) {
						managerGui.showView(ViewAcceuilCombo.class);
					} else {
						managerGui.showView(ViewAdherentCombo.class);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});
		});
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/acceuil/ViewAcceuilCombo.java"));
		
		ArrayList<String> liste = daoUser.infosConnexion(txfPseudo.getText());
		System.out.println(liste.get(0));
		System.out.println(liste.get(1));
		nom2 = liste.get(0);
		photo2 = liste.get(1);
		utilisateurEnCours = daoUser.retrouver(txfPseudo.getText());
		
		
	}
	
	
	private String rechercher(String mail) throws SQLException {
		Connection cn = null;
		PreparedStatement stmt = null;
		String sql, statu = null;
		ResultSet rs;

		cn = dataSource.getConnection();

		// Modifie le Utilisateur
		sql = "SELECT status from adherer  WHERE mail =  ?";
		stmt = cn.prepareStatement(sql);
		stmt.setObject(1, mail);
		rs = stmt.executeQuery();
		while (rs.next()) {
			statu = rs.getObject("status", String.class);
		}
		return statu;

	}


}
