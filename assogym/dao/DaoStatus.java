package assogym.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.sql.DataSource;

import assogym.data.Annee;
import assogym.data.Utilisateur;
import jakarta.inject.Inject;
import javafx.collections.ObservableList;
import jfox.jdbc.UtilJdbc;

public class DaoStatus {

	// -------
	// Champs
	// -------

	@Inject
	private DataSource dataSource;

	// -------
	// Actions
	// -------

	public void mettreAJourPourUtilisateur(Utilisateur utilisateur) {

		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {

			// Crée le Set des éléments à conserver ou à insérer
			var set = new HashSet<>();
			for (var item : utilisateur.getStatus()) {
				set.add(item);
			}

			// Crée le RésultSet des éléments présents dans la base
			cn = dataSource.getConnection();
			sql = "SELECT * FROM adherer WHERE mail = ?";
			stmt = cn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
			stmt.setObject(1, utilisateur.getMail());
			// stmt.setObject( 2, utilisateur.getAnneeN() );
			rs = stmt.executeQuery();

//			 Parcourt les éléments du ResultSet
//			 Supprime ceux qui ne sont pas dans la Map
//			 Retire du Set ceux qui y sont trouvés
//			 Ainsi, à la fin, le Set ne contient plus que les éléments à insérer
			while (rs.next()) {
				var item = rs.getObject("status", String.class);
				if (!set.contains(item)) {
					rs.deleteRow();
				} else {
					set.remove(item);
				}
			}

			// Insère les éléments encore présents dans le Set
			for (var item : set) {
				rs.moveToInsertRow();
				rs.updateObject("mail", utilisateur.getMail());
				rs.updateObject("status", item);
				rs.updateObject("annee", LocalDate.now().getYear());
				rs.insertRow();
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(rs, stmt, cn);
		}
	}

	public void mettreAJourPourAnnee(Utilisateur utilisateur) {
		Connection cn = null;
		PreparedStatement stmt = null, stmt1=null;
		ResultSet rs = null, rs1 = null;
		String sql,sql1;

		try {

			// Crée le Set des éléments à conserver ou à insérer
			var set = new HashSet<>();
			for (var item : utilisateur.getAnnee()) {
				set.add(item);
			}
			
			var set1 = new HashSet<>();
			for (var item : utilisateur.getAnnee()) {
				set.add(item);
			}

			// Crée le RésultSet des éléments présents dans la base
			cn = dataSource.getConnection();
			sql = "SELECT * FROM annee a INNER JOIN adherer ad ON a.annee = ad.annee WHERE ad.mail = ?";
			stmt = cn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
			stmt.setObject(1, utilisateur.getMail());
			// stmt.setObject( 2, utilisateur.getAnneeN() );
			rs = stmt.executeQuery();
			
			sql1 = "SELECT * FROM adherer WHERE mail = ?";
			stmt1 = cn.prepareStatement(sql1, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
			stmt1.setObject(1, utilisateur.getMail());
			// stmt.setObject( 2, utilisateur.getAnneeN() );
			rs1 = stmt1.executeQuery();

//			 Parcourt les éléments du ResultSet
//			 Supprime ceux qui ne sont pas dans la Map
//			 Retire du Set ceux qui y sont trouvés
//			 Ainsi, à la fin, le Set ne contient plus que les éléments à insérer
			while (rs.next()) {
				var item = rs.getObject("annee", Integer.class);
				if (!set.contains(item)) {
					rs.deleteRow();
				} else {
					set.remove(item);
				}
			}

			// Insère les éléments encore présents dans le Set
			for (var item : set) {
				rs.moveToInsertRow();
				rs1.moveToCurrentRow();
				rs.updateObject("annee", item);
				rs1.updateObject("annee", item);
				rs1.deleteRow();
				rs.insertRow();
				rs1.insertRow();
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(rs, rs1, stmt, cn);
		}
	}

	public List<String> listerPourUtilisateur(Utilisateur utilisateur) {

		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM adherer WHERE mail = ? ORDER BY status";
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

//	public void mettreAJourPourAnnee(Annee annee) {
//
//		Connection cn = null;
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		String sql;
//
//		try {
//
//			// declaration da map qui va stocker le mail et le status
//			HashMap<String, ObservableList<String>> map = new HashMap<String, ObservableList<String>>();
//			for (var item : annee.getUtilisateurs()) {
//				map.put(item.getMail(), item.getStatus());
//			}
//
//			// Crée le RésultSet des éléments présents dans la base
//			cn = dataSource.getConnection();
//			sql = "SELECT * FROM adherer WHERE annee = ?";
//			stmt = cn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
//			stmt.setObject(1, annee.getAnnee());
//			// stmt.setObject( 2, utilisateur.getAnneeN() );
//			rs = stmt.executeQuery();
//			// rs1 = stmt.executeQuery();
//
//			// Parcourt les éléments du ResultSet
//			// Supprime ceux qui ne sont pas dans la Map
//			// Retire du Set ceux qui y sont trouvés
//			// Ainsi, à la fin, le Set ne contient plus que les éléments à insérer
//			while (rs.next()) {
//				var item = rs.getObject("mail", String.class);
//				if (!map.keySet().contains(item)) {
//					rs.deleteRow();
//				} else {
//					map.remove(item);
//				}
//			}
//
//			// Insère les éléments encore présents dans le Set
//			for (var item : map.keySet()) {
//				for (var item1 : map.get(item)) {
//					rs.moveToInsertRow();
//					rs.updateObject("mail", item);
//					rs.updateObject("status", item1);
//					rs.updateObject("annee", annee.getAnnee());
//					rs.insertRow();
//				}
//			}
//
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		} finally {
//			UtilJdbc.close(rs, stmt, cn);
//		}
//	}
}
