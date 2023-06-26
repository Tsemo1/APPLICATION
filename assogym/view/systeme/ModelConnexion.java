package assogym.view.systeme;

import assogym.dao.DaoUtilisateur;
import assogym.data.Utilisateur;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import jfox.exception.ExceptionValidation;


public class ModelConnexion {
	
	
	// Données observables 
	
	// Vue connexion
	private final Utilisateur			draft = new Utilisateur();

	// Utilisateur connecté
	private final ObjectProperty<Utilisateur>	utilisateurActif = new SimpleObjectProperty<>();

	
	// Autres champs
	@Inject
	private DaoUtilisateur	daoUtilisateur;
	

	// Getters 
	
	public Utilisateur getDraft() {
		return draft;
	}
	
	public ObjectProperty<Utilisateur> utilisateurActifProperty() {
		return utilisateurActif;
	}
	
	public Utilisateur getUtilisateurActif() {
		return utilisateurActif.getValue();
	}
	
	
	// Initialisation
	
	@PostConstruct
	public void init() {
		draft.setMail( "geek@3il.fr" );
		draft.setMotDePasse( "geek" );
	}
	
	
	// Actions


	public void ouvrirSessionUtilisateur() {

		Utilisateur utilisateur = daoUtilisateur.validerAuthentification(
					draft.mailProperty().getValue(), draft.motDePasseProperty().getValue() );
		
		if( utilisateur == null ) {
			throw new ExceptionValidation( "Indentifiant ou mot de passe invalide." );
		} else {
			Platform.runLater( () -> utilisateurActif.setValue( utilisateur ) );
		}
	}
	

	public void fermerSessionUtilisateur() {
		utilisateurActif.setValue( null );
	}

}
