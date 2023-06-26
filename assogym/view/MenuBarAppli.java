package assogym.view;

import assogym.commun.Status;
import assogym.report.EnumReport;
import assogym.report.ManagerReport;
import assogym.view.acceuil.ViewAcceuilCombo;
import assogym.view.compte.ViewCompteCombo;
import assogym.view.utilisateur.ViewUtilisateurCombo;
import assogym.view.systeme.ModelConnexion;
import assogym.view.systeme.ViewAbout;
import assogym.view.systeme.ViewConnexion;
import assogym.view.test.ViewTestDaoCompte;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Menu;
import jfox.context.Dependent;
import jfox.javafx.control.MenuBarAbstract;

@Dependent
public class MenuBarAppli extends MenuBarAbstract {

	// Champs

	private final BooleanProperty flagConnexion = new SimpleBooleanProperty();
	private final BooleanProperty flagStatuUtil = new SimpleBooleanProperty();
	private final BooleanProperty flagStatuAdmin = new SimpleBooleanProperty();

	@Inject
	private ManagerGui managerGui;
//	
//	@Inject
//	private ManagerReport managerReport;
	
	@Inject
	private ModelConnexion modelConnexion;
	@Inject
	private ManagerReport managerReport;

	// Initialisation

	@PostConstruct
	public void init() {

		// Variables de travail
		Menu menu;

		// Menu Système
		menu = addMenu("Donnees", flagStatuUtil.or(flagStatuAdmin));

		addMenuItem("Acceuil ", menu, flagStatuAdmin, e -> managerGui.showView(ViewAcceuilCombo.class));
		// Menu Etats
		
		menu = addMenu( "Etats" );
		addMenuItem( "Liste des Adherents (Viewer)", menu,
		e -> managerReport.showViewer( EnumReport.AdherentsListe ) );
		
		addMenuItem( "Liste des Evenements (PDF)", menu,
		e -> managerReport.openFilePdf( EnumReport.EvenementListeSimple ) );
				
		addMenuItem( "Liste des Evenements (Viewer)", menu,
		e -> managerReport.showViewer( EnumReport.EvenementListeSimple ) );
		
		menu = addMenu("Système");

		addMenuItem("Se déconnecter", menu, flagConnexion, e -> managerGui.showView(ViewConnexion.class));

		addMenuItem("Quitter", menu, e -> managerGui.exit());


		// Menu Aide

		menu = addMenu("?");

		addMenuItem("A propos", menu, e -> managerGui.showDialog(ViewAbout.class));

		// Gestion des droits d'accès

		final var compteActif = modelConnexion.utilisateurActifProperty();
		flagConnexion.bind(compteActif.isNotNull());
		flagStatuUtil.bind(Bindings.createBooleanBinding(
				() -> flagConnexion.get() && compteActif.get().isInStatus(Status.UTILISATEUR), compteActif));
		flagStatuAdmin.bind(Bindings.createBooleanBinding(
				() -> flagConnexion.get() && compteActif.get().isInStatus(Status.ADMINISTRATEUR), compteActif));

	}
}
