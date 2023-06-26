package assogym.data;


	import java.time.LocalDate;
	import java.time.LocalTime;
	import java.util.Objects;

	import javafx.beans.property.ObjectProperty;
	import javafx.beans.property.SimpleObjectProperty;
	import javafx.beans.property.SimpleStringProperty;
	import javafx.beans.property.StringProperty;
	import javafx.collections.FXCollections;
	import javafx.collections.ObservableList;
	
	public class Evenement {

	
		
		
		// Champs
		
		private final ObjectProperty<Integer>	id_evenement 			= new SimpleObjectProperty<>();
		private final ObjectProperty<Integer>   montant		= new SimpleObjectProperty<>();
		private final ObjectProperty<Integer>   duree	    = new SimpleObjectProperty<>();
		private final ObjectProperty<LocalTime>	debut	    = new SimpleObjectProperty<>();
		private final ObjectProperty<LocalDate>	date	    = new SimpleObjectProperty<>();
		private final StringProperty	        type	    = new SimpleStringProperty();
		private final StringProperty			nom		    = new SimpleStringProperty();
		private final StringProperty	        id_salle    = new SimpleStringProperty();
//		private final ObjectProperty<BigDecimal> budget		= new SimpleObjectProperty<>();
//		private final ObjectProperty<LocalDate> echeance	= new SimpleObjectProperty<>();
//		private final ObjectProperty<LocalTime> heure		= new SimpleObjectProperty<>();
//		private final ObservableList<Compte> 	abonnes 	= FXCollections.observableArrayList();	
//		private final ObservableList<Agir> 		acteurs 	= FXCollections.observableArrayList();	
		
		// Getters & Setters
		
		public final ObjectProperty<Integer> idProperty() {
			return this.id_evenement;
		}
		

		public final Integer getId() {
			return this.idProperty().get();
		}
		

		public final void setId(final Integer id) {
			this.idProperty().set(id);
		}
		

		public final ObjectProperty<Integer> montantProperty() {
			return this.montant;
		}
		

		public final Integer getMontant() {
			return this.montantProperty().get();
		}
		

		public final void setMontant(final Integer montant) {
			this.montantProperty().set(montant);
		}
		

		public final ObjectProperty<Integer> dureeProperty() {
			return this.duree;
		}
		

		public final Integer getDuree() {
			return this.dureeProperty().get();
		}
		

		public final void setDuree(final Integer duree) {
			this.dureeProperty().set(duree);
		}
		

		public final ObjectProperty<LocalTime> debutProperty() {
			return this.debut;
		}
		

		public final LocalTime getDebut() {
			return this.debutProperty().get()   ;
		}
		

		public final void setDebut(final LocalTime debut) {
			this.debutProperty().set(debut);
		}
		

		public final StringProperty typeProperty() {
			return this.type;
		}
		

		public final String getType() {
			return this.typeProperty().get();
		}
		

		public final void setType(final String type) {
			this.typeProperty().set(type);
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
		

		public final StringProperty id_salleProperty() {
			return this.id_salle;
		}
		

		public final String getId_salle() {
			return this.id_salleProperty().get();
		}
		

		public final void setId_salle(final String id_salle) {
			this.id_salleProperty().set(id_salle);
		}
		
		public final ObjectProperty<LocalDate> dateProperty() {
			return this.date;
		}
		
		public final LocalDate getDate() {
			return this.dateProperty().get();
		}
		


		public final void setDate(final LocalDate date) {
			this.dateProperty().set(date);
		}
		

		
		// hashCode() & equals()
		
		@Override
		public int hashCode() {
			return Objects.hash(id_evenement.get());
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Evenement other = (Evenement) obj;
			return Objects.equals(id_evenement.get(), other.id_evenement.get());
		}


		
		


		
		

		

	}


	
	
	
	


