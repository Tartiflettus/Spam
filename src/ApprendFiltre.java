public class ApprendFiltre {
	
	/**
	 * Permet � un classifieur d'apprendre et de s'enregistrer dans un fichier
	 * @param classifieur le nom du classifieur
	 * @param baseapp la base d'apprentissage
	 * @param nombreSpam nombre de spam � apprendre
	 * @param nombreHam nombre de ham � apprendre
	 */
	public ApprendFiltre(String classifieur, String baseapp, int nombreSpam, int nombreHam) {
		// Chargement du dictionnaire
		String[] dico = ChargerDictionnaire.chargerDictionnaire("res/dictionnaire1000en.txt");
		// Cr�ation du classifieur
		Classifieur c = new Classifieur(dico);
		System.out.println("Apprentissage sur " + nombreSpam + " spams et " + nombreHam + " hams ...");
		// Apprentissage
		c.apprendre(baseapp, nombreSpam, nombreHam);
		// Sauvegarde du classifieur
		c.save(classifieur);
		System.out.println("Classifieur enregistr� dans '" + classifieur + "'.");
	}
	
	
	public static void main(String[] args) {
		new ApprendFiltre(args[0], args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]));
	}

}
