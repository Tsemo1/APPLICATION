package assogym.view.acceuil;


import assogym.commun.IMapper;
import assogym.dao.DaoUtilisateur;
import assogym.data.Utilisateur;
import jakarta.inject.Inject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Mode;


public class ModelAcceuil {
	
	
	//-------
	// Données observables 
	//-------
	
	private final ObservableList<Utilisateur>	list 	= FXCollections.observableArrayList(); 
	
	private final BooleanProperty			flagRefreshingList = new SimpleBooleanProperty();
	
	private final Utilisateur					draft 	= new Utilisateur();
	
	private final ObjectProperty<Utilisateur>	current	= new SimpleObjectProperty<>();
	
	
	//-------
	// Autres champs
	//-------
	
	private Mode		mode = Mode.NEW;
    @Inject
	private IMapper		mapper;
    @Inject
	private DaoUtilisateur	daoUtilisateur;
	

	//-------
	// Getters & Setters
	//-------
	
	public ObservableList<Utilisateur> getList() {
		return list;
	}

	public BooleanProperty flagRefreshingListProperty() {
		return flagRefreshingList;
	}

	public Utilisateur getDraft() {
		return draft;
	}

	public Property<Utilisateur> currentProperty() {
		return current;
	}

	public Utilisateur getCurrent() {
		return current.get();
	}

	public void setCurrent(Utilisateur item) {
		current.set(item);
	}
	
	public Mode getMode() {
		return mode;
	}
	
	
	//-------
	// Actions
	//-------
	
	public void refreshList() {
		// flagRefreshingList vaut true pendant la durée  
		// du traitement de mise à jour de la liste
		flagRefreshingList.set(true);
		list.setAll( daoUtilisateur.listerTout() );
		flagRefreshingList.set(false);
 	}

	public void initDraft(Mode mode) {
		this.mode = mode;
		if( mode == Mode.NEW ) {
			mapper.update( draft, new Utilisateur() );
		} else {
			setCurrent( daoUtilisateur.retrouver( getCurrent().getMail() ) );
			mapper.update( draft, getCurrent() );
		}
	}
	
	
//	public boolean verifierUnicitePseudo( String pseudo) {
//		return daoUtilisateur.verifierUnicitePseudo( pseudo, draft.getMail() );
//	}

	
	public void saveDraft() {

		// Vérifie la validité des données
		
//		StringBuilder message = new StringBuilder();
//		
//		if ( ! daoUtilisateur.verifierUnicitePseudo( draft.getPseudo(), draft.getId() ) ) {
//			message.append( "\nLe pseudo " + draft.getPseudo() + " est déjà utilisé." );
//		}
//		
//		if ( message.length() > 0 ) {
//			throw new ExceptionValidation( message.toString().substring(1) );
//		}
		
		// Enregistre les données dans la base
		
		if ( mode == Mode.NEW ) {
			// Insertion
			daoUtilisateur.inserer( draft );
			// Actualise le courant
			setCurrent( mapper.update( new Utilisateur(), draft ) );
		} else {
			// modficiation
			daoUtilisateur.modifier( draft );
			// Actualise le courant
			mapper.update( getCurrent(), draft );
		}
	}
	
	
	public void deleteCurrent() {
		// Effectue la suppression
		daoUtilisateur.supprimer( getCurrent().getMail() );
		// Détermine le nouveau courant
		setCurrent( UtilFX.findNext( list, getCurrent() ) );
	}

}
