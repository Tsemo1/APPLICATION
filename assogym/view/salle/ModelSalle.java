package assogym.view.salle;
import assogym.commun.IMapper;
import assogym.dao.DaoSalle;
//import assogym.commun.IMapper;
//	import assogym.dao.DaoSalle;
//	import assogym.data.Salle;
import assogym.data.Salle;

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

public class ModelSalle {

	// -------
	// Données observables
	// -------

	private final ObservableList<Salle> list = FXCollections.observableArrayList();

	private final BooleanProperty flagRefreshingList = new SimpleBooleanProperty();

	private final Salle draft = new Salle();

	private final ObjectProperty<Salle> current = new SimpleObjectProperty<Salle>();

	// -------
	// Autres champs
	// -------

	private Mode mode = Mode.NEW;
	@Inject
	private IMapper mapper;
	@Inject
	private DaoSalle daoSalle;

	// -------
	// Getters & Setters
	// -------

	public ObservableList<Salle> getList() {
		return list;
	}

	public BooleanProperty flagRefreshingListProperty() {
		return flagRefreshingList;
	}

	public Salle getDraft() {
		return draft;
	}


	public Property<Salle> currentProperty() {
		return current;
	}

	public Salle getCurrent() {
		return current.get();
	}

	public void setCurrent(Salle item) {
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
		list.setAll(daoSalle.listerTout());
		flagRefreshingList.set(false);
	}

	public void initDraft(Mode mode) {
		this.mode = mode;
		if (mode == Mode.NEW) {
			mapper.update(draft, new Salle());
		} else {

			setCurrent(daoSalle.retrouver(getCurrent().getId_salle()));
			mapper.update(draft, getCurrent());
		}
	}

//		public boolean verifierUnicitePseudo( String pseudo) {
//			return daoSalle.verifierUnicitePseudo( pseudo, draft.getId() );
//		}
//		public void saveDraftToDatabase() {
//		    Salle Salle = getDraft();
//
//		   
//		}

	public void saveDraft() {

		// Enregistre les données dans la base

		if (mode == Mode.NEW) {
			// Insertion
			daoSalle.inserer(draft);
			// Actualise le courant
			setCurrent(mapper.update(new Salle(), draft));
		} else {
			// modficiation
			daoSalle.modifier(draft);
			// Actualise le courant
			mapper.update(getCurrent(), draft);
		}
	}

	public void deleteCurrent() {
		// Effectue la suppression
		daoSalle.supprimer(getCurrent().getId_salle());
		// Détermine le nouveau courant
		setCurrent(UtilFX.findNext(list, getCurrent()));
	}

}
