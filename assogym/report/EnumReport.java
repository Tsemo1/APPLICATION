package assogym.report;

import jfox.jasperreports.IEnumReport;

public enum EnumReport implements IEnumReport {

	// -------
	// Valeurs
	// -------

	AdherentsListe		( "utilisateur/ListeUtilisateur.jrxml"), PersonneAnnuaire("personne/Annuaire.jrxml"),EvenementListeSimple ( "evenement/ListeSimple.jrxml")
	;

	// -------
	// Champs
	// -------

	private String path;

	// -------
	// Constructeur
	// -------

	EnumReport(String path) {
		this.path = path;
	}

	// -------
	// Getters & setters
	// -------

	@Override
	public String getPath() {
		return path;
	}

}
