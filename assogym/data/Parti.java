package assogym.data;

import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Parti {

	// -------
	// Données observables
	// -------

	private final ObjectProperty<Integer> telephone = new SimpleObjectProperty<>();;
	private final StringProperty nom = new SimpleStringProperty();
	private final StringProperty email = new SimpleStringProperty();
	private final ObservableList<String> roles = FXCollections.observableArrayList();
	private final ObjectProperty<Integer> id_participants = new SimpleObjectProperty<>();

	// -------
	// Constructeurs
	// -------

	public Parti() {
	}

	public Parti(int telephone, String nom, String email) {
		setId(0);
		setTelephone(telephone);
		setNom(nom);
		setEmail(email);
	}

	// -------
	// Getters et Setters
	// -------

	public final ObjectProperty<Integer> idProperty() {
		return this.id_participants;
	}

	public final Integer getId_participants() {
		return this.idProperty().get();
	}

	public final void setId(final Integer id_participants) {
		this.idProperty().set(id_participants);
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

	public final StringProperty nomProperty() {
		return this.nom;
	}

	public final String getNom() {
		return this.nomProperty().get();
	}

	public final void setNom(final String nom) {
		this.nomProperty().set(nom);
	}

	public final StringProperty emailProperty() {
		return this.email;
	}

	public final String getEmail() {
		return this.emailProperty().get();
	}

	public final void setEmail(final String email) {
		this.emailProperty().set(email);
	}

	public final ObservableList<String> getRoles() {
		return this.roles;
	}

	public boolean isInRole(String role) {

		if (role != null) {
			for (String r : roles) {
				if (role.equals(r)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id_participants, nom, roles, telephone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parti other = (Parti) obj;
		return Objects.equals(email, other.email) && Objects.equals(id_participants, other.id_participants)
				&& Objects.equals(nom, other.nom) && Objects.equals(roles, other.roles)
				&& Objects.equals(telephone, other.telephone);
	}

	public void setParti(Parti draft) {
		// TODO Auto-generated method stub
		
	}

}