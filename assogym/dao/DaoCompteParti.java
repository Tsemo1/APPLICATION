package assogym.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import assogym.data.Parti;
import jakarta.inject.Inject;
import jfox.jdbc.UtilJdbc;

public class DaoCompteParti {

	// Champs

	@Inject
	private DataSource dataSource;
	@Inject
	private DaoRole daoRole;

	// Actions

	public void inserer(Parti participant) {
		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			// insère le participant
			sql = "insert into participant (mail, nom, telephone) values (?, ?, ?)";
			stmt = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, participant.getEmail());
			stmt.setString(2, participant.getNom());
			stmt.setInt(3, participant.getTelephone());
			// stmt.setObject(4, participant.getId_participants());
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			participant.setId(rs.getObject(1, Integer.class));

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(rs, stmt, cn);
		}

		// met à jour les rôles
		//daoRole.mettreAJourPourParti(participant);
	}

	/*
	 * public void modifier(Parti participant) { Connection cn = null;
	 * PreparedStatement stmt = null; String sql;
	 * 
	 * try { cn = dataSource.getConnection();
	 * 
	 * // Modifie le participant sql =
	 * "UPDATE participant SET nom = ?, telephone = ? WHERE id = ?"; stmt =
	 * cn.prepareStatement(sql); stmt.setString(1, participant.getNom());
	 * stmt.setString(2, participant.getTelephone()); stmt.setString(3,
	 * participant.getEmail()); stmt.executeUpdate(); } catch (SQLException e) {
	 * throw new RuntimeException(e); } finally { UtilJdbc.close(stmt, cn); }
	 * 
	 * // Met à jour les rôles daoRole.mettreAJourPourParti(participant); }
	 */

	public void supprimer(String nom) {
		Connection cn = null;
		PreparedStatement stmt = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			// Supprime le participant
			sql = "DELETE FROM participant WHERE mail = ?";
			stmt = cn.prepareStatement(sql);
			stmt.setString(1, nom);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(stmt, cn);
		}
	}

	public Parti retrouver(String string) {
		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM participant WHERE nom = ?";
			stmt = cn.prepareStatement(sql);
			stmt.setObject(1, string);
			rs = stmt.executeQuery();

			if (rs.next()) {
				return construireParticipant(rs, true);
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(rs, stmt, cn);
		}
	}

	public List<Parti> listerTout() {
		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM participant ORDER BY nom";
			stmt = cn.prepareStatement(sql);
			rs = stmt.executeQuery();

			List<Parti> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add(construireParticipant(rs, false));
			}
			return liste;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(rs, stmt, cn);
		}
	}

	public boolean verifierUniciteNom(String nom, String email) {
		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		if (email == null)
			email = "";

		try {
			cn = dataSource.getConnection();

			sql = "SELECT COUNT(*) = 0 AS unicite FROM participant WHERE nom = ? AND mail <> ?";
			stmt = cn.prepareStatement(sql);
			stmt.setString(1, nom);
			stmt.setString(2, email);
			rs = stmt.executeQuery();

			rs.next();
			return rs.getBoolean("unicite");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(rs, stmt, cn);
		}
	}

	// Méthodes auxiliaires

	protected Parti construireParticipant(ResultSet rs, boolean flagComplet) throws SQLException {
		Parti participant = new Parti();
		participant.setEmail(rs.getString("mail"));
		participant.setNom(rs.getString("nom"));
		participant.setTelephone(rs.getInt("telephone"));
		if (flagComplet) {
			participant.getRoles().setAll(daoRole.listerPourParti(participant));
		}
		return participant;
	}

}
