package assogym.view.evenement;
import java.io.File;

import assogym.commun.IMapper;
import assogym.dao.DaoEvenement;
//import assogym.commun.IMapper;
//	import assogym.dao.DaoEvenement;
//	import assogym.data.Evenement;
import assogym.data.Evenement;
import assogym.data.Salle;
import assogym.view.salle.ModelSalle;

import jakarta.inject.Inject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Mode;

public class ModelEvenement {

	// -------
	// Données observables
	// -------

	private final ObservableList<Evenement> list = FXCollections.observableArrayList();

	private final BooleanProperty flagRefreshingList = new SimpleBooleanProperty();

	private final Evenement draft = new Evenement();
	
	//private final Salle     draft2 = new Salle () ; // je cree un objet draft de type Salle

	private final ObjectProperty<Evenement> current = new SimpleObjectProperty<Evenement>();

	// -------
	// Autres champs
	// -------

	private Mode mode = Mode.NEW;
	@Inject
	private IMapper mapper;
	@Inject
	private DaoEvenement daoEvenement;
	@Inject
	private ModelSalle modelSalle ;

	// -------
	// Getters & Setters
	// -------

	public ObservableList<Evenement> getList() {
		return list;
	}

	public BooleanProperty flagRefreshingListProperty() {
		return flagRefreshingList;
	}

	public Evenement getDraft() {
		return draft;
	}

	public Property<Evenement> currentProperty() {
		return current;
	}

	public Evenement getCurrent() {
		return current.get();
	}
	
	public ObservableList<Salle> getListeSalle() {
		return modelSalle.getList();
	}

	public void setCurrent(Evenement item) {
		current.set(item);
	}

	public Mode getMode() {
		return mode;
	}

	// -------
	// Actions
	// -------

	public void refreshList() {
		// flagRefreshingList vaut true pendant la durée
		// du traitement de mise à jour de la liste
		flagRefreshingList.set(true);
		list.setAll(daoEvenement.listerTout());
		flagRefreshingList.set(false);
	}

	public void initDraft(Mode mode) {
		this.mode = mode;
		modelSalle.refreshList();
		if( mode == Mode.NEW ) {
			mapper.update( draft, new Evenement() );
			//draft.setFlagUrgent( false );
			//draft.setStatut("A");
		} else {
			setCurrent( daoEvenement.retrouver( getCurrent().getId() ) );
			mapper.update( draft, getCurrent() );
		}
	}

//		File chemin = getCheminImageCourante();
//		if ( chemin.exists() ) {
//			image.set( new Image( chemin.toURI().toString() ) );
//		} else {
//			image.set( null );
//		}
//		flagModifImage = false;
	

//		public boolean verifierUnicitePseudo( String pseudo) {
//			return daoEvenement.verifierUnicitePseudo( pseudo, draft.getId() );
//		}
//		public void saveDraftToDatabase() {
//		    Evenement evenement = getDraft();
//
//		   
//		}

	public void saveDraft() {

		// Vérifie la validité des données

//			StringBuilder message = new StringBuilder();
//			
//			if ( ! daoEvenement.verifierUnicitePseudo( draft.getPseudo(), draft.getId() ) ) {
//				message.append( "\nLe pseudo " + draft.getPseudo() + " est déjà utilisé." );
//			}
//			
//			if ( message.length() > 0 ) {
//				throw new ExceptionValidation( message.toString().substring(1) );
//			}

		// Enregistre les données dans la base

		if (mode == Mode.NEW) {
			// Insertion
			daoEvenement.inserer(draft);
			// Actualise le courant
			setCurrent(mapper.update(new Evenement(), draft));
		} else {
			// modficiation
			daoEvenement.modifier(draft);
			// Actualise le courant
			mapper.update(getCurrent(), draft);
		}
	}

	public void deleteCurrent() {
		// Effectue la suppression
		daoEvenement.supprimer(getCurrent().getId());
		// Détermine le nouveau courant
		setCurrent(UtilFX.findNext(list, getCurrent()));
	}

}
