package assogym.commun;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Disponib { // vu qu'il ya pas de table type 

	private final static ObservableList<String> disponibilite = FXCollections.observableArrayList() ;
	
	// methode anonyme 
	
	static {
		
		disponibilite.add("Oui");
		disponibilite.add("Bientot");
		disponibilite.add("Non");	
	
		
	}
	
	public static ObservableList<String> getDisponib() { // recuperer la liste des types
		return disponibilite;
	}
	
}
