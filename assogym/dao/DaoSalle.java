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

//import assogym.data.salle;
import assogym.data.Salle;
import jakarta.inject.Inject;
import jfox.jdbc.UtilJdbc;
public class DaoSalle {

	


		
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

		public void inserer(Salle salle )  {

			Connection			cn		= null;
			PreparedStatement	stmt	= null;
			ResultSet 			rs 		= null;
			String				sql;

			try {
				cn = dataSource.getConnection();

				// Insère le salle
				sql = "INSERT INTO salle (id_salle,adresse,capacite,prix,disponibilite,nombreStand) VALUES (?,?,?,?,?,?)" ;

				stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
				stmt.setObject( 1, salle.getId_salle() );
				stmt.setObject( 2, salle.getAdresse() );
							stmt.setObject( 3, salle.getCapacite() );
							stmt.setObject( 4, salle.getPrix() );
				            stmt.setObject( 5, salle.getDisponibilite() );
						   // stmt.setObject( 5, salle.getType() == null ? null : salle.getType() );
							stmt.setObject( 6, salle.getNombreStand() );
							//stmt.setObject( 7, salle.getId_salle() );
							
							stmt.executeUpdate();

				// Récupère l'identifiant généré par le SGBD
				rs = stmt.getGeneratedKeys();
				rs.next();
				salle.setId_salle( rs.getObject( 1, String.class) );
				
		
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				UtilJdbc.close( rs, stmt, cn );
			}

			// Insère les rôles
			//daoRole.mettreAJourPoursalle( salle );
		}
		

		public void modifier( Salle salle )  {

			Connection			cn		= null;
			PreparedStatement	stmt	= null;
			String 				sql;

			try {
				cn = dataSource.getConnection();
				sql = "UPDATE salle SET adresse = ?, capacite = ?, prix = ?, disponibilite = ?, nombreStand = ? WHERE id_salle =  ?";
				stmt = cn.prepareStatement( sql );
				stmt.setObject( 1, salle.getAdresse() );
				stmt.setObject( 2, salle.getCapacite() );
				stmt.setObject( 3, salle.getPrix() );
	            stmt.setObject( 4, salle.getDisponibilite() );
//			    stmt.setObject( 5, salle.getType() == null ? null : salle.getType() );
				stmt.setObject( 5, salle.getNombreStand() );
				//stmt.setObject( 7, salle.getId_salle() );
				stmt.setObject( 6, salle.getId_salle() );
				stmt.executeUpdate();
				
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				UtilJdbc.close( stmt, cn );
			}

			// Modifie les rôles
			//daoRole.mettreAJourPoursalle( salle );

		}
		

		public void supprimer( String string )  {

			Connection			cn		= null;
			PreparedStatement	stmt	= null;
			String 				sql;

			try {
				cn = dataSource.getConnection();
				sql = "DELETE FROM salle WHERE id_salle = ? ";
				stmt = cn.prepareStatement( sql );
				stmt.setObject( 1, string);
				stmt.executeUpdate();

			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				UtilJdbc.close( stmt, cn );
			}
		}
		

		public Salle retrouver( String string )  {

			Connection			cn		= null;
			PreparedStatement	stmt	= null;
			ResultSet 			rs 		= null;
			String				sql;

			try {
				cn = dataSource.getConnection();
				sql = "SELECT * FROM salle WHERE id_salle = ?";
				stmt = cn.prepareStatement( sql );
				stmt.setObject(1, string);
				rs = stmt.executeQuery();
				
	            if ( rs.next() ) {
	                return construireSalle( rs );
	            } else {
	            	return null;
	            }
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				UtilJdbc.close( rs, stmt, cn );
			}
		}
		

		public List<Salle> listerTout()   {

			Connection			cn		= null;
			PreparedStatement	stmt	= null;
			ResultSet 			rs 		= null;
			String				sql;

			try {
				cn = dataSource.getConnection();

				sql = "SELECT * FROM salle ORDER BY id_salle";
				stmt = cn.prepareStatement( sql );
				rs = stmt.executeQuery();

				List<Salle> liste = new ArrayList<>();
				while ( rs.next() ) {
					liste.add( construireSalle( rs ) );
				}
				return liste;

			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				UtilJdbc.close( rs, stmt, cn );
			}
		}
		
		// nombre salles
		
		public int nombreSalle() {
			Connection cn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			String sql;

			try {
				cn = dataSource.getConnection();

				sql = "SELECT count(id_salle) AS nombre FROM salle";
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

//
//		public salle validerAuthentification( String pseudo, String motDePasse )  {
//			
//			Connection			cn		= null;
//			PreparedStatement	stmt	= null;
//			ResultSet 			rs 		= null;
//			String				sql;
//
//			try {
//				cn = dataSource.getConnection();
//
//				sql = "SELECT * FROM salle WHERE pseudo = ? AND motdepasse = ?";
//				stmt = cn.prepareStatement( sql );
//				stmt.setObject( 1, pseudo );
//				stmt.setObject( 2, motDePasse );
//				rs = stmt.executeQuery();
//
//				if ( rs.next() ) {
//					return construiresalle( rs, true );			
//				} else {
//					return null;
//				}
//			} catch (SQLException e) {
//				throw new RuntimeException(e);
//			} finally {
//				UtilJdbc.close( rs, stmt, cn );
//			}
//		}


//		public boolean verifierUnicitePseudo( String pseudo, Integer idsalle )   {
//
//			Connection			cn		= null;
//			PreparedStatement	stmt	= null;
//			ResultSet 			rs 		= null;
//			String				sql;
//
//			if ( idsalle == null ) idsalle = 0;
//			
//			try {
//				cn = dataSource.getConnection();
//
//				sql = "SELECT COUNT(*) = 0 AS unicite"
//						+ " FROM salle WHERE pseudo = ? AND idsalle <> ?";
//				stmt = cn.prepareStatement( sql );
//				stmt.setObject(	1, pseudo );
//				stmt.setObject(	2, idsalle );
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
		
		protected Salle construireSalle( ResultSet rs ) throws SQLException {
			Salle salle = new Salle();
			salle.setId_salle( rs.getObject( "id_salle", String.class ) );
			salle.setAdresse( rs.getObject( "adresse", String.class) );
			salle.setCapacite( rs.getObject( "capacite", Integer.class ) );
			salle.setPrix( rs.getObject( "prix", Integer.class ) );
			salle.setDisponibilite( rs.getObject( "disponibilite", String.class ) );
			salle.setNombreStand( rs.getObject( "nombreStand", Integer.class ) );
		
		return salle;
//		}
		
	}
	
}
