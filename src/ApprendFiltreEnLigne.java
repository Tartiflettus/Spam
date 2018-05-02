import java.io.File;

public class ApprendFiltreEnLigne {

	/**
	 * Permet d'ajouter un message sans devoir réaliser l'apprentissage complet
	 * @param classifieur nom du classifieur
	 * @param message le message à ajouter
	 * @param estSpam indique si c'est un spam ou non
	 */
	public ApprendFiltreEnLigne(String classifieur, String message, boolean estSpam) {
		// Chargement du classifieur
		Classifieur cl = Classifieur.load(classifieur);
		// Chargement du dictionnaire
		String[] dico = ChargerDictionnaire.chargerDictionnaire("res/dictionnaire1000en.txt");
		boolean[] msg = LectureMessage.comparaisonDico(dico, LectureMessage.lireMessage(new File(message)));
		
		System.out.println("Modification du filtre '" + classifieur + "' par apprentissage sur le " + (estSpam ? "SPAM" : "HAM") + " '" + message + "'.");
		// Lissage des paramètres
		cl.lisser(dico, msg, estSpam);
		
		// Sauvegarde du classifieur modifié
		cl.save(classifieur);
	}

	
	public static void main(String[] args) {
		new ApprendFiltreEnLigne(args[0], args[1], args[2].trim().toUpperCase().equals("SPAM") ? true : false);
	}
}
