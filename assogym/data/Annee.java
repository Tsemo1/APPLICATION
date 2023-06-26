package assogym.data;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Annee {
	private final ObjectProperty<Integer> annee = new SimpleObjectProperty<>();
	private final ObservableList<Utilisateur> utilisateurs =  FXCollections.observableArrayList();

	public Annee() {
	}
	
	public Annee(int annee) {
		setAnnee(annee);
	}

	public final ObjectProperty<Integer> anneeProperty() {
		return this.annee;
	}
	

	public final Integer getAnnee() {
		return this.anneeProperty().get();
	}
	

	public final void setAnnee(final Integer annee) {
		this.anneeProperty().set(annee);
	}
	
	public final ObservableList<Utilisateur> getUtilisateurs() {
		return this.utilisateurs;
	}
	
}
