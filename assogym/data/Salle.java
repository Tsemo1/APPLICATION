package assogym.data;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Salle {

	// -------
	// Données observables
	// -------
	private final StringProperty id_salle = new SimpleStringProperty();
	private final StringProperty adresse = new SimpleStringProperty();
	private final ObjectProperty<Integer> capacite = new SimpleObjectProperty<>();
	private final ObjectProperty<Integer> prix = new SimpleObjectProperty<>();
	private final StringProperty disponibilite = new SimpleStringProperty();
	private final ObjectProperty<Integer> nombreStand = new SimpleObjectProperty<>();
	//private final ObservableList<String> 	status 		= FXCollections.observableArrayList();

	//private final ObservableList<String> status = FXCollections.observableArrayList();

	// -------
	// Constructeurs
	// -------

	public Salle() {
	}

	public Salle(String id_salle, String adresse, int capacite, int prix, String disponibilite, int nombreStand) {
		
		setId_salle(id_salle);
		setAdresse(adresse);
		setCapacite(getCapacite());
		setPrix(getPrix());
		setDisponibilite(disponibilite);
		setNombreStand(nombreStand);
	}


	// -------
	// Getters et Setters on fait un clic droit dans la zone de code 
	// puis source -> JFX Getters
	// -------

	public final StringProperty id_salleProperty() {
		return this.id_salle;
	}
	
	public final String getId_salle() {
		return this.id_salleProperty().get();
	}
	
	public final void setId_salle(final String id_salle) {
		this.id_salleProperty().set(id_salle);
	}
	

	public final StringProperty adresseProperty() {
		return this.adresse;
	}
	

	public final String getAdresse() {
		return this.adresseProperty().get();
	}
	

	public final void setAdresse(final String adresse) {
		this.adresseProperty().set(adresse);
	}
	

	public final ObjectProperty<Integer> capaciteProperty() {
		return this.capacite;
	}
	

	public final Integer getCapacite() {
		return this.capaciteProperty().get();
	}
	

	public final void setCapacite(final Integer capacite) {
		this.capaciteProperty().set(capacite);
	}
	

	public final ObjectProperty<Integer> prixProperty() {
		return this.prix;
	}
	

	public final Integer getPrix() {
		return this.prixProperty().get();
	}
	

	public final void setPrix(final Integer prix) {
		this.prixProperty().set(prix);
	}
	

	public final StringProperty disponibiliteProperty() {
		return this.disponibilite;
	}
	

	public final String getDisponibilite() {
		return this.disponibiliteProperty().get();
	}
	

	public final void setDisponibilite(final String disponibilite) {
		this.disponibiliteProperty().set(disponibilite);
	}
	

	public final ObjectProperty<Integer> nombreStandProperty() {
		return this.nombreStand;
	}
	

	public final Integer getNombreStand() {
		return this.nombreStandProperty().get();
	}
	

	public final void setNombreStand(final Integer nombreStand) {
		this.nombreStandProperty().set(nombreStand);
	}
	
	

	
	

	//--------
	//Gestion des points d'un utilisateur
	//-------
	

	// -------
	// hashCode() & equals()
	// -------

	@Override
	public int hashCode() {
		return Objects.hash(id_salle.get());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Salle other = (Salle) obj;
		return Objects.equals(id_salle.get(), other.id_salle.get());
	}


}
