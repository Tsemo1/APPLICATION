package assogym.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import assogym.data.Annee;
import assogym.data.Utilisateur;
import jakarta.inject.Inject;
import javafx.collections.ObservableList;
import jfox.jdbc.UtilJdbc;

public class DaoAdherer {

	// -------
	// Champs
	// -------

	@Inject
	private DataSource dataSource;

	// -------
	// Actions
	// -------

	public void mettreAJourPourAnnee(Annee annee) {

		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {

			// Crée le Set des éléments à conserver ou à insérer
//			var set = new HashSet<>();
//			for (var item : annee.getUtilisateurs()) {
//				set.add(item.getMail());
//			}

			// declaration da map qui va stocker le mail et le status
			HashMap<String, ObservableList<String>> map = new HashMap<String, ObservableList<String>>();
			for (var item : annee.getUtilisateurs()) {
				map.put(item.getMail(), item.getStatus());
			}

			// Crée le RésultSet des éléments présents dans la base
			cn = dataSource.getConnection();
			sql = "SELECT * FROM adherer WHERE annee = ?";
			stmt = cn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
			stmt.setObject(1, annee.getAnnee());
			// stmt.setObject( 2, utilisateur.getAnneeN() );
			rs = stmt.executeQuery();
			// rs1 = stmt.executeQuery();

			// Parcourt les éléments du ResultSet
			// Supprime ceux qui ne sont pas dans la Map
			// Retire du Set ceux qui y sont trouvés
			// Ainsi, à la fin, le Set ne contient plus que les éléments à insérer
			while (rs.next()) {
				var item = rs.getObject("mail", String.class);
				if (!map.keySet().contains(item)) {
					rs.deleteRow();
				} else {
					map.remove(item);
				}
			}

			// Insère les éléments encore présents dans le Set
			for (var item : map.keySet()) {
				for (var item1 : map.get(item)) {
					rs.moveToInsertRow();
					rs.updateObject("mail", item);
					rs.updateObject("status", item1);
					rs.updateObject("annee", annee.getAnnee());
					rs.insertRow();
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(rs, stmt, cn);
		}
	}

	public List<String> listerPourUtilisateur(Utilisateur utilisateur) {

		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM adherer WHERE mail = ? ORDER BY mail";
			stmt = cn.prepareStatement(sql);
			stmt.setObject(1, utilisateur.getMail());
			rs = stmt.executeQuery();

			List<String> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add(rs.getObject("status", String.class));
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(rs, stmt, cn);
		}
	}

}
