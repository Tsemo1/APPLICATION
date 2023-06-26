//package assogym.data;
//
//import java.util.HashMap;
//
//public class GestionPointsFidelite {
//	
//    private HashMap<String, Integer> pointsFidelite;
//
//    public GestionPointsFidelite() {
//        pointsFidelite = new HashMap<>();
//    }
//
//    public void ajouterPoints(String client, int montantAchat) {
//        int pointsActuels = pointsFidelite.getOrDefault(client, 0);
//        int nouveauxPoints = montantAchat / 10; // Exemple : 1 point par tranche de 10 unités monétaires dépensées
//        pointsFidelite.put(client, pointsActuels + nouveauxPoints);
//    }
//
//    public int consulterPoints(String client) {
//        return pointsFidelite.getOrDefault(client, 0);
//    }
//    
//   
//
//        public void retirerPoints(String utilisateur, int points) {
//            int pointsActuels = pointsUtilisateur.getOrDefault(utilisateur, 0);
//            int nouveauxPoints = Math.max(pointsActuels - points, 0);
//            pointsUtilisateur.put(utilisateur, nouveauxPoints);
//        }
//
//        // Méthode pour attribuer des points en fonction de la participation à un événement
//        public void attribuerPointsEvenement(String utilisateur, int points) {
//            ajouterPoints(utilisateur, points);
//        }
//
//        // Méthode pour retirer des points en cas d'annulation ou de non-participation à un événement
//        public void retirerPointsEvenement(String utilisateur, int points) {
//            retirerPoints(utilisateur, points);
//        }
//
//       /* public static void main(String[] args) {
//            GestionPointsUtilisateur gestionPoints = new GestionPointsUtilisateur();
//
//            // Exemple d'utilisation
//            gestionPoints.attribuerPointsEvenement("UtilisateurA", 50); // UtilisateurA participe à un événement et reçoit 50 points
//            gestionPoints.attribuerPointsEvenement("UtilisateurB", 30); // UtilisateurB participe à un événement et reçoit 30 points
//
//            int pointsUtilisateurA = gestionPoints.consulterPoints("UtilisateurA");
//            int pointsUtilisateurB = gestionPoints.consulterPoints("UtilisateurB");
//
//            System.out.println("Points de UtilisateurA : " + pointsUtilisateurA);
//            System.out.println("Points de UtilisateurB : " + pointsUtilisateurB);
//        }
//    }*/
//
//
//    /*
//    public static void main(String[] args) {
//        GestionPointsFidelite gestionPoints = new GestionPointsFidelite();
//
//        // Exemple d'utilisation
//        gestionPoints.ajouterPoints("ClientA", 120); // ClientA effectue un achat de 120 unités monétaires
//        gestionPoints.ajouterPoints("ClientB", 75);  // ClientB effectue un achat de 75 unités monétaires
//
//        int pointsClientA = gestionPoints.consulterPoints("ClientA");
//        int pointsClientB = gestionPoints.consulterPoints("ClientB");
//
//        System.out.println("Points de fidélité de ClientA : " + pointsClientA);
//        System.out.println("Points de fidélité de ClientB : " + pointsClientB);
//    }*/
//}
