import java.io.File;

public class ApprendFiltreEnLigne {

	public ApprendFiltreEnLigne(String classifieur, String message, boolean estSpam) {
		Classifieur cl = Classifieur.load(classifieur);
		String[] dico = ChargerDictionnaire.chargerDictionnaire("res/dictionnaire1000en.txt");
		boolean[] msg = LectureMessage.comparaisonDico(dico, LectureMessage.lireMessage(new File(message)));
		
		System.out.println("Modification du filtre '" + classifieur + "' par apprentissage sur le " + (estSpam ? "SPAM" : "HAM") + " '" + message + "'.");
		cl.lisser(dico, msg, estSpam);
		
		cl.save(classifieur);
	}

	
	public static void main(String[] args) {
		new ApprendFiltreEnLigne(args[0], args[1], args[2].trim().toUpperCase().equals("SPAM") ? true : false);
	}
}
