package assogym.view.participants;


import assogym.commun.IMapper;
import assogym.dao.DaoCompteParti;
import assogym.data.Parti;
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


public class ModelCompteParti {
	
	
	//-------
	// Données observables 
	//-------
	
	private final ObservableList<Parti>	list 	= FXCollections.observableArrayList(); 
	
	private final BooleanProperty			flagRefreshingList = new SimpleBooleanProperty();
	
	private final Parti					draft 	= new Parti();
	
	private final ObjectProperty<Parti>	current	= new SimpleObjectProperty<>();
	
	
	//-------
	// Autres champs
	//-------
	
	private Mode		mode = Mode.NEW;
    @Inject
	private IMapper		mapper;
    @Inject
	private DaoCompteParti	daoCompteParti;
	

	//-------
	// Getters & Setters
	//-------
	
	public ObservableList<Parti> getList() {
		return list;
	}

	public BooleanProperty flagRefreshingListProperty() {
		return flagRefreshingList;
	}

	public Parti getDraft() {
		return draft;
	}

	public Property<Parti> currentProperty() {
		return current;
	}

	public Parti getCurrent() {
		return current.get();
	}

	public void setCurrent(Parti item) {
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
		list.setAll( daoCompteParti.listerTout() );
		flagRefreshingList.set(false);
 	}

	public void initDraft(Mode mode) {
		this.mode = mode;
		if( mode == Mode.NEW ) {
			mapper.update( draft, new Parti() );
		} else {
			setCurrent( daoCompteParti.retrouver(getDraft().getNom()) ) ;
			//setCurrent( daoCompteParti.inserer(getDraft()));
			mapper.update( draft, getCurrent() );
		}
		
		
	}
	
	
	public boolean verifierUniciteNom( String nom) {
		return daoCompteParti.verifierUniciteNom( nom, draft.getNom() );
	}
	
	public boolean verifierUniciteEmail( String email) {
		return daoCompteParti.verifierUniciteNom( email, draft.getNom() );
	}

	
	public void saveDraft() {

		// Vérifie la validité des données
		
//		StringBuilder message = new StringBuilder();
//		
//		if ( ! daoParti.verifierUnicitePseudo( draft.getPseudo(), draft.getId() ) ) {
//			message.append( "\nLe pseudo " + draft.getPseudo() + " est déjà utilisé." );
//		}
//		
//		if ( message.length() > 0 ) {
//			throw new ExceptionValidation( message.toString().substring(1) );
//		}
		
		// Enregistre les données dans la base
		
		if ( mode == Mode.NEW ) {
			// Insertion
			//daoCompteParti.inserer( draft );
			// Actualise le courant
			setCurrent( mapper.update( new Parti(), draft ) );
		} else {
			// modficiation
			//daoCompteParti.modifier( draft );
			// Actualise le courant
			mapper.update( getCurrent(), draft );
		}
	}
	
	
	public void deleteCurrent() {
		// Effectue la suppression
		daoCompteParti.supprimer( getCurrent().getEmail() );
		// Détermine le nouveau courant
		setCurrent( UtilFX.findNext( list, getCurrent() ) );
	}

}
