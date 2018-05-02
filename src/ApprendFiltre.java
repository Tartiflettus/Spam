public class ApprendFiltre {
	
	public ApprendFiltre(String classifieur, String baseapp, int nombreSpam, int nombreHam) {
		String[] dico = ChargerDictionnaire.chargerDictionnaire("res/dictionnaire1000en.txt");
		Classifieur c = new Classifieur(dico);
		System.out.println("Apprentissage sur " + nombreSpam + "spams et " + nombreHam + "hams ...");
		c.apprendre(baseapp, nombreSpam, nombreHam);
		c.save(classifieur);
		System.out.println("Classifieur enregistré dans '" + classifieur + "'.");
	}
	
	
	public static void main(String[] args) {
		new ApprendFiltre(args[0], args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]));
	}

}
