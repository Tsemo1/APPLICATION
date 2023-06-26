package assogym.commun;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Types { // vu qu'il ya pas de table type 

	private final static ObservableList<String> types = FXCollections.observableArrayList() ;
	
	// methode anonyme 
	
	static {
		
		types.add("VIDE GRENIER");
		types.add("Vide Ta Chambre");
		types.add("Forum des Associations de la Commune");
		types.add("Telethon");
		types.add("Stage decouverte");
		types.add("Soirée Zumba");
		types.add("Animation ecole primire");
		types.add("Feu de St Jean");
		types.add("VENTE GATEAUX");
	
		
	}
	
	public static ObservableList<String> getTypes() { // recuperer la liste des types
		return types;
	}
	
}
