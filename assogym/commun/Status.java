package assogym.commun;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public final class Status {
	
	//-------
	// Champs statiques
	//-------
	
	public static final String ADMINISTRATEUR	= "ADMINISTRATEUR";
	public static final String UTILISATEUR		= "UTILISATEUR";
	
	
	private static final List<String>	status = Collections.unmodifiableList( Arrays.asList( 
			ADMINISTRATEUR,			
			UTILISATEUR
	) );

	private static final String[]	 	libelles = new String[] {
			"Administrateur",
			"Utilisateur",
	};

	//-------
	// Constructeurs
	//-------
	
	// Constructeur privé qui empêche l'instanciation de la classe
	private Status() {
	}
	
	//-------
	// Actions
	//-------

	public static List<String> getStatus() {
		return status;
	}
	
	public static String getLibelle( String statu ) {
		int index = status.indexOf( statu );
		if ( index == -1 ) {
			throw new IllegalArgumentException();
		} 
		return libelles[index];
	}

}
