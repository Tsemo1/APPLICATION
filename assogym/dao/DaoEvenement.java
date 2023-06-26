package assogym.dao;
///package assogym.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

//import assogym.data.evenement;
import assogym.data.Evenement;
import jakarta.inject.Inject;
import jfox.jdbc.UtilJdbc;
public class DaoEvenement {

	


		
		//-------
		// Champs
		//-------

		@Inject
		private DataSource		dataSource;
		@Inject
		private DaoRole			daoRole;

		
		//-------
		// Actions
		//-------

		public void inserer(Evenement evenement )  {

			Connection			cn		= null;
			PreparedStatement	stmt	= null;
			ResultSet 			rs 		= null;
			String				sql;

			try {
				cn = dataSource.getConnection();

				// Insère le evenement
				sql = "INSERT INTO evenement (date,montant,duree,debut,type,nom,id_salle) VALUES (?,?,?,?,?,?,?)" ;

				stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
							stmt.setObject( 1, evenement.getDate() );
							stmt.setObject( 2, evenement.getMontant() );
							stmt.setObject( 3, evenement.getDuree() );
				            stmt.setObject( 4, evenement.getDebut() );
						    stmt.setObject( 5, evenement.getType() == null ? null : evenement.getType() );
							stmt.setObject( 6, evenement.getNom() );
							stmt.setObject( 7, evenement.getId_salle() );
							
							stmt.executeUpdate();

				// Récupère l'identifiant généré par le SGBD
				rs = stmt.getGeneratedKeys();
				rs.next();
				evenement.setId( rs.getObject( 1, Integer.class) );
				
		
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				UtilJdbc.close( rs, stmt, cn );
			}

			// Insère les rôles
			//daoRole.mettreAJourPourEvenement( evenement );
		}
		
		public int nombreEvenement() {
			Connection cn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			String sql;

			try {
				cn = dataSource.getConnection();

				sql = "SELECT count(id_evenement) AS nombre FROM evenement";
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
		
		public int nombreVideGrenier() {
			Connection cn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			String sql;

			try {
				cn = dataSource.getConnection();

				sql = "SELECT count(id_evenement) AS nombre FROM evenement where type = 'VIDE GRENIER'";
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

		public void modifier( Evenement evenement )  {

			Connection			cn		= null;
			PreparedStatement	stmt	= null;
			String 				sql;

			try {
				cn = dataSource.getConnection();
				sql = "UPDATE evenement SET date = ?, montant = ?, duree = ?, debut = ?, type = ?, nom = ?, id_salle = ? WHERE id_evenement =  ?";
				stmt = cn.prepareStatement( sql );
				stmt.setObject( 1, evenement.getDate() );
				stmt.setObject( 2, evenement.getMontant() );
				stmt.setObject( 3, evenement.getDuree() );
	            stmt.setObject( 4, evenement.getDebut() );
			    stmt.setObject( 5, evenement.getType() == null ? null : evenement.getType() );
				stmt.setObject( 6, evenement.getNom() );
				stmt.setObject( 7, evenement.getId_salle() );
				stmt.setObject( 8, evenement.getId() );
				stmt.executeUpdate();
				
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				UtilJdbc.close( stmt, cn );
			}

			// Modifie les rôles
			//daoRole.mettreAJourPourevenement( evenement );

		}
		

		public void supprimer( int id )  {

			Connection			cn		= null;
			PreparedStatement	stmt	= null;
			String 				sql;

			try {
				cn = dataSource.getConnection();
				sql = "DELETE FROM evenement WHERE id_evenement = ? ";
				stmt = cn.prepareStatement( sql );
				stmt.setObject( 1, id);
				stmt.executeUpdate();

			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				UtilJdbc.close( stmt, cn );
			}
		}
		

		public Evenement retrouver( int id_evenement )  {

			Connection			cn		= null;
			PreparedStatement	stmt	= null;
			ResultSet 			rs 		= null;
			String				sql;

			try {
				cn = dataSource.getConnection();
				sql = "SELECT * FROM evenement WHERE id_evenement = ?";
				stmt = cn.prepareStatement( sql );
				stmt.setObject(1, id_evenement);
				rs = stmt.executeQuery();
				
	            if ( rs.next() ) {
	                return construireEvenement( rs );
	            } else {
	            	return null;
	            }
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				UtilJdbc.close( rs, stmt, cn );
			}
		}
		

		public List<Evenement> listerTout()   {

			Connection			cn		= null;
			PreparedStatement	stmt	= null;
			ResultSet 			rs 		= null;
			String				sql;

			try {
				cn = dataSource.getConnection();

				sql = "SELECT * FROM evenement ORDER BY nom";
				stmt = cn.prepareStatement( sql );
				rs = stmt.executeQuery();

				List<Evenement> liste = new ArrayList<>();
				while ( rs.next() ) {
					liste.add( construireEvenement( rs ) );
				}
				return liste;

			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				UtilJdbc.close( rs, stmt, cn );
			}
		}
		
		// nombre evenements
		
		public int nombreEvent() {
			Connection cn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			String sql;

			try {
				cn = dataSource.getConnection();

				sql = "SELECT count(id_evenement) AS nombre FROM evenement";
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

		

		public List<Evenement> rechercherEvenementsPourUtilisateur(String mail) throws SQLException {
			Connection cn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			String sql;
			try {
				cn = dataSource.getConnection();
				sql = 	"SELECT* FROM evenement e INNER JOIN participer p ON p.id_evenement =e.id_evenement INNER JOIN utilisateur u ON u.mail =p.mail WHERE p.mail = ? ORDER BY e.nom ";
				stmt = cn.prepareStatement(sql);
				stmt.setObject(1, mail);
				rs = stmt.executeQuery();
				List<Evenement> liste = new ArrayList<>();
				while ( rs.next() ) {
					liste.add( construireEvenement( rs ) );
				}
				return liste;

			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				UtilJdbc.close( rs, stmt, cn );
			}
			
			
		}
//
//		public Evenement validerAuthentification( String pseudo, String motDePasse )  {
//			
//			Connection			cn		= null;
//			PreparedStatement	stmt	= null;
//			ResultSet 			rs 		= null;
//			String				sql;
//
//			try {
//				cn = dataSource.getConnection();
//
//				sql = "SELECT * FROM evenement WHERE pseudo = ? AND motdepasse = ?";
//				stmt = cn.prepareStatement( sql );
//				stmt.setObject( 1, pseudo );
//				stmt.setObject( 2, motDePasse );
//				rs = stmt.executeQuery();
//
//				if ( rs.next() ) {
//					return construireevenement( rs, true );			
//				} else {
//					return null;
//				}
//			} catch (SQLException e) {
//				throw new RuntimeException(e);
//			} finally {
//				UtilJdbc.close( rs, stmt, cn );
//			}
//		}


//		public boolean verifierUnicitePseudo( String pseudo, Integer idevenement )   {
//
//			Connection			cn		= null;
//			PreparedStatement	stmt	= null;
//			ResultSet 			rs 		= null;
//			String				sql;
//
//			if ( idevenement == null ) idevenement = 0;
//			
//			try {
//				cn = dataSource.getConnection();
//
//				sql = "SELECT COUNT(*) = 0 AS unicite"
//						+ " FROM evenement WHERE pseudo = ? AND idevenement <> ?";
//				stmt = cn.prepareStatement( sql );
//				stmt.setObject(	1, pseudo );
//				stmt.setObject(	2, idevenement );
//				rs = stmt.executeQuery();
//				
//				rs.next();
//				return rs.getBoolean( "unicite" );
//		
//			} catch (SQLException e) {
//				throw new RuntimeException(e);
//			} finally {
//				UtilJdbc.close( rs, stmt, cn );
//			}
//		}
		
		
		//-------
		// Méthodes auxiliaires
		//-------
		
		protected Evenement construireEvenement( ResultSet rs ) throws SQLException {
			Evenement evenement = new Evenement();
			evenement.setId( rs.getObject( "id_evenement", Integer.class ) );
			evenement.setDate( rs.getObject( "date", LocalDate.class) );
			evenement.setMontant( rs.getObject( "montant", Integer.class ) );
			evenement.setDuree( rs.getObject( "duree", Integer.class ) );
			evenement.setDebut( rs.getObject( "debut", LocalTime.class ) );
			evenement.setType( rs.getObject( "type", String.class ) );
			evenement.setNom( rs.getObject( "nom", String.class ) );
			evenement.setId_salle( rs.getObject( "id_salle", String.class ) );
			
//			if ( flagComplet ) {
//				evenement.getRoles().setAll( daoRole.listerPourevenement( evenement ) );
//			}
		
		return evenement;
//		}
		
	}
	
}
