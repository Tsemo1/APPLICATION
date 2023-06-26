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

public class Utilisateur {

	// -------
	// Données observables
	// -------
	private final StringProperty mail = new SimpleStringProperty();
	private final StringProperty nom = new SimpleStringProperty();
	private final ObjectProperty<Integer> telephone = new SimpleObjectProperty<>();
	private final StringProperty adresse = new SimpleStringProperty();
	private final ObjectProperty<Integer> points = new SimpleObjectProperty<>();
	private final ObjectProperty<LocalDate> dateN = new SimpleObjectProperty<>();
	private final StringProperty photo = new SimpleStringProperty();
	private final ObjectProperty<Integer> anneeN = new SimpleObjectProperty<>();
	private final StringProperty sexe = new SimpleStringProperty();
	private final StringProperty motDePasse = new SimpleStringProperty();
	private final ObservableList<String> 	status 		= FXCollections.observableArrayList();
	private final ObservableList<Integer> 	annee 		= FXCollections.observableArrayList();

	//private final ObservableList<String> status = FXCollections.observableArrayList();

	// -------
	// Constructeurs
	// -------

	public Utilisateur() {
		setPoints(0);
	}

	public Utilisateur(String mail, String nom, int telephone, String adresse, int points, String photo, LocalDate dateN, String sexe, String motDePasse) {
		setMail(mail);
		setNom(nom);
		setTelephone(getTelephone());
		setAdresse(adresse);
		setPoints(0);
		setPhoto(photo);
		setDateN(dateN);
		setSexe(sexe);
		setMotDePasse(motDePasse);
	}

	// -------
	// Getters et Setters
	// -------

	public final StringProperty mailProperty() {
		return this.mail;
	}

	public final String getMail() {
		return this.mailProperty().get();
	}

	public final void setMail(final String mail) {
		this.mailProperty().set(mail);
	}

	public final StringProperty nomProperty() {
		return this.nom;
	}

	public final String getNom() {
		return this.nomProperty().get();
	}

	public final void setNom(final String nom) {
		this.nomProperty().set(nom);
	}

	public final ObjectProperty<Integer> telephoneProperty() {
		return this.telephone;
	}

	public final Integer getTelephone() {
		return this.telephoneProperty().get();
	}

	public final void setTelephone(final Integer telephone) {
		this.telephoneProperty().set(telephone);
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

	public final ObjectProperty<Integer> pointsProperty() {
		return this.points;
	}

	public final Integer getPoints() {
		return this.pointsProperty().get();
	}

	public final void setPoints(final Integer points) {
		this.pointsProperty().set(points);
	}

	public final StringProperty photoProperty() {
		return this.photo;
	}

	public final String getPhoto() {
		return this.photoProperty().get();
	}

	public final void setPhoto(final String photo) {
		this.photoProperty().set(photo);
	}

	public final ObjectProperty<Integer> anneeNProperty() {
		return this.anneeN;
	}

	public final Integer getAnneeN() {
		return this.anneeNProperty().get();
	}

	public final void setAnneeN(final Integer anneeN) {
		this.anneeNProperty().set(anneeN);
	}

	public final StringProperty sexeProperty() {
		return this.sexe;
	}

	public final String getSexe() {
		return this.sexeProperty().get();
	}

	public final void setSexe(final String sexe) {
		this.sexeProperty().set(sexe);
	}

	public final StringProperty motDePasseProperty() {
		return this.motDePasse;
	}

	public final String getMotDePasse() {
		return this.motDePasseProperty().get();
	}

	public final void setMotDePasse(final String motDePasse) {
		this.motDePasseProperty().set(motDePasse);
	}
	
	
	public final ObservableList<String> getStatus() {
		return this.status;
	}

	
	public boolean isInStatus( String statu ) {
		
		if ( statu != null ) {
			for ( String r : status ) {
				if ( statu.equals( r ) ) {
					return true;
				}
			}
		}
		return false;
	}
	
	public final ObservableList<Integer> getAnnee() {
		return this.annee;
	}

	
	public boolean isInAnnee( int annee ) {
		
		if ( annee != 0 ) {
			for ( int r : this.annee ) {
				if ( annee == r ) {
					return true;
				}
			}
		}
		return false;
	}

	// -------
	// hashCode() & equals()
	// -------

	@Override
	public int hashCode() {
		return Objects.hash(mail.get());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utilisateur other = (Utilisateur) obj;
		return Objects.equals(mail.get(), other.mail.get());
	}

	public final ObjectProperty<LocalDate> dateNProperty() {
		return this.dateN;
	}
	

	public final LocalDate getDateN() {
		return this.dateNProperty().get();
	}
	

	public final void setDateN(final LocalDate dateN) {
		this.dateNProperty().set(dateN);
	}
	

}
