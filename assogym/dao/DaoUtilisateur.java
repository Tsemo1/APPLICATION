package assogym.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import assogym.data.Utilisateur;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.jdbc.UtilJdbc;

public class DaoUtilisateur {

	// -------
	// Champs
	// -------

	@Inject
	private DataSource dataSource;
	@Inject
	private DaoStatus daoStatus;

	// -------
	// Actions
	// -------

	public void inserer(Utilisateur Utilisateur) {

		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			// Insère le Utilisateur
			sql = "INSERT INTO utilisateur (mail, nom, telephone, adresse, points, photo, dateN, sexe, password) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )";
			stmt = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setObject(1, Utilisateur.getMail());
			stmt.setObject(2, Utilisateur.getNom());
			stmt.setObject(3, Utilisateur.getTelephone());
			stmt.setObject(4, "Utilisateur.getAdresse()");
			stmt.setObject(5, 0);
			stmt.setObject(6, Utilisateur.getPhoto());
			stmt.setObject(7, Utilisateur.getDateN());
			stmt.setObject(8, Utilisateur.getSexe());
			stmt.setObject(9, Utilisateur.getMotDePasse());

			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
//			rs = stmt.getGeneratedKeys();
//			rs.next();
//			Utilisateur.setMail( rs.getObject( 1, String.class) );

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(rs, stmt, cn);
		}
//		Annee annee = new Annee(Utilisateur.getDateN().getYear());

		// Insère les rôles
		try {
			daoStatus.mettreAJourPourAnnee(Utilisateur);
			daoStatus.mettreAJourPourUtilisateur(Utilisateur);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insert(Utilisateur Utilisateur) {

		Connection cn = null;
		PreparedStatement stmt = null, stmt1 = null, stmt2 = null;
		ResultSet rs = null, rs1 = null, rs2 = null;
		String sql, sql1, sql2;

		try {
			cn = dataSource.getConnection();

			// Insère le Utilisateur
			sql = "INSERT INTO utilisateur (mail, nom, telephone, adresse, points, photo, dateN, sexe, password) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )";
			stmt = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setObject(1, Utilisateur.getMail());
			stmt.setObject(2, Utilisateur.getNom());
			stmt.setObject(3, Utilisateur.getTelephone());
			stmt.setObject(4, "Utilisateur.getAdresse()");
			stmt.setObject(5, Utilisateur.getPoints());
			stmt.setObject(6, Utilisateur.getPhoto());
			stmt.setObject(7, Utilisateur.getDateN());
			stmt.setObject(8, Utilisateur.getSexe());
			stmt.setObject(9, Utilisateur.getMotDePasse());

			if (!verifierAnnee(Utilisateur.getDateN().getYear())) {
				sql1 = "INSERT INTO annee (annee) VALUES (?)";
				stmt1 = cn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
				stmt1.setObject(1, Utilisateur.getDateN().getYear());
				stmt1.executeUpdate();
			}

			sql2 = "INSERT INTO adherer (annee,mail,status) VALUES (?,?,?)";
			stmt2 = cn.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
			stmt2.setObject(1, Utilisateur.getDateN().getYear());
			stmt2.setObject(2, Utilisateur.getMail());
			if (Utilisateur.isInStatus("ADMINISTRATEUR"))
				stmt2.setObject(3, "ADMINISTRATEUR");
			if (Utilisateur.isInStatus("UTILISATEUR"))
				stmt2.setObject(3, "UTILISATEUR");

			stmt.executeUpdate();
			stmt2.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(rs, rs1, rs2, stmt, stmt1, stmt2, cn);
		}
//		daoStatus.mettreAJourPourAnnee(Utilisateur);
//		daoStatus.mettreAJourPourUtilisateur(Utilisateur);
	}

	public Boolean verifierAnnee(int annee) {
		boolean vrai = false;
		for (var item : selectAnnee()) {
			if (item == annee) {
				vrai = true;
				break;
			}
		}
		return vrai;
	}

	public void modifier(Utilisateur Utilisateur) {

		Connection cn = null;
		PreparedStatement stmt = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			// Modifie le Utilisateur
			sql = "UPDATE Utilisateur SET mail = ?, nom = ?, telephone = ?, adresse = ?, points = ?, photo = ?, dateN = ?, sexe = ?, password = ? WHERE mail =  ?";
			stmt = cn.prepareStatement(sql);
			stmt.setObject(1, Utilisateur.getMail());
			stmt.setObject(2, Utilisateur.getNom());
			stmt.setObject(3, Utilisateur.getTelephone());
			stmt.setObject(4, "Utilisateur.getAdresse()");
			stmt.setObject(5, Utilisateur.getPoints());
			stmt.setObject(6, Utilisateur.getPhoto());
			stmt.setObject(7, Utilisateur.getDateN());
			stmt.setObject(8, Utilisateur.getSexe());
			stmt.setObject(9, Utilisateur.getMotDePasse());
			stmt.setObject(10, Utilisateur.getMail());
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(stmt, cn);
		}

		try {
			// Modifie les rôles
//			Annee annee = new Annee(Utilisateur.getDateN().getYear());

			// Insère les rôles
//			daoStatus.mettreAJourPourAnnee(Utilisateur);
			daoStatus.mettreAJourPourUtilisateur(Utilisateur);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void supprimer(String mail) {

		Connection cn = null;
		PreparedStatement stmt = null, stmt1 = null;
		String sql, sql1;

		try {
			cn = dataSource.getConnection();

			// Supprime le Utilisateur
			sql1 = "DELETE FROM adherer WHERE mail = ? ";
			sql = "DELETE FROM utilisateur WHERE mail = ? ";
			stmt1 = cn.prepareStatement(sql1);
			stmt = cn.prepareStatement(sql);
			stmt1.setObject(1, mail);
			stmt1.executeUpdate();
			stmt.setObject(1, mail);
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(stmt, stmt1, cn);
		}
	}

	public List<Integer> selectAnnee() {

		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			// Supprime le Utilisateur
			sql = "SELECT * FROM annee ORDER BY annee";
			stmt = cn.prepareStatement(sql);
			rs = stmt.executeQuery();
			List<Integer> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add(rs.getObject("annee", Integer.class));
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(stmt, cn);
		}
	}

	public Utilisateur retrouver(String mail) {

		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM Utilisateur WHERE mail = ?";
			stmt = cn.prepareStatement(sql);
			stmt.setObject(1, mail);
			rs = stmt.executeQuery();

			if (rs.next()) {
				return construireUtilisateur(rs, true);
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(rs, stmt, cn);
		}
	}
	
	public ObservableList<Utilisateur> retrouverNom(String nom) {

		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM utilisateur WHERE nom  LIKE ?";
			stmt = cn.prepareStatement(sql);
			stmt.setObject(1, "%"+nom+"%");
			rs = stmt.executeQuery();
            ObservableList<Utilisateur> liste = FXCollections.observableArrayList();
			while (rs.next()) {
				liste.add(construireUtilisateur(rs, true));
			}
			return liste;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(rs, stmt, cn);
		}
	}

	public List<Utilisateur> listerTout() {

		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM utilisateur ORDER BY nom";
			stmt = cn.prepareStatement(sql);
			rs = stmt.executeQuery();

			List<Utilisateur> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add(construireUtilisateur(rs, false));
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(rs, stmt, cn);
		}
	}

	public Utilisateur validerAuthentification(String mail, String motDePasse) {

		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM Utilisateur WHERE mail = ? AND password = ?";
			stmt = cn.prepareStatement(sql);
			stmt.setObject(1, mail);
			stmt.setObject(2, motDePasse);
			rs = stmt.executeQuery();

			if (rs.next()) {
				return construireUtilisateur(rs, true);
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(rs, stmt, cn);
		}
	}

	public int nombreUser() {
		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT count(mail) AS nombre FROM Utilisateur";
			stmt = cn.prepareStatement(sql);
			rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt("nombre");
			} else {
				return 0;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(rs, stmt, cn);
		}
	}

	public ArrayList<String> infosConnexion(String mail) {
		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;
		ArrayList<String> liste = new ArrayList<String>();

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM Utilisateur WHERE mail = ?";
			stmt = cn.prepareStatement(sql);
			stmt.setObject(1, mail);
			rs = stmt.executeQuery();

			if (rs.next()) {
				liste.add(rs.getString("nom"));
				liste.add(rs.getString("photo"));
				return liste;
			} else {
				return liste;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(rs, stmt, cn);
		}
	}

//	public boolean verifierUnicitePseudo( String mail, Integer idUtilisateur )   {
//
//		Connection			cn		= null;
//		PreparedStatement	stmt	= null;
//		ResultSet 			rs 		= null;
//		String				sql;
//
//		if ( idUtilisateur == null ) idUtilisateur = 0;
//		
//		try {
//			cn = dataSource.getConnection();
//
//			sql = "SELECT COUNT(*) = 0 AS unicite"
//					+ " FROM Utilisateur WHERE mail = ? AND idUtilisateur <> ?";
//			stmt = cn.prepareStatement( sql );
//			stmt.setObject(	1, mail );
//			stmt.setObject(	2, idUtilisateur );
//			rs = stmt.executeQuery();
//			
//			rs.next();
//			return rs.getBoolean( "unicite" );
//	
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		} finally {
//			UtilJdbc.close( rs, stmt, cn );
//		}
//	}

	// -------
	// Méthodes auxiliaires
	// -------

	protected Utilisateur construireUtilisateur(ResultSet rs, boolean flagComplet) throws SQLException {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setMail(rs.getObject("mail", String.class));
		utilisateur.setNom(rs.getObject("nom", String.class));
		utilisateur.setTelephone(rs.getObject("telephone", Integer.class));
		utilisateur.setAdresse(rs.getObject("adresse", String.class));
		utilisateur.setPoints(rs.getObject("points", Integer.class));
		utilisateur.setPhoto(rs.getObject("photo", String.class));
		utilisateur.setDateN(rs.getObject("dateN", LocalDate.class));
		utilisateur.setSexe(rs.getObject("sexe", String.class));
		utilisateur.setMotDePasse(rs.getObject("password", String.class));
		if (flagComplet) {
			utilisateur.getStatus().setAll(daoStatus.listerPourUtilisateur(utilisateur));
		}
		return utilisateur;
	}

}
