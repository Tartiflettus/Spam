public class FiltreMail {

	/**
	 * 
	 * @param nomClassifieur nom du classifieur
	 * @param message message à tester
	 */
	public FiltreMail(String nomClassifieur, String message) {
		boolean spam = estUnSpam(nomClassifieur, message);
		System.out.println("D'après '" + nomClassifieur + "', le message '" + message + "' est un " + (spam ? "SPAM" : "HAM"));
	}
	
	/**
	 * nomClassifieur vérifie si le message est un spam ou non
	 * @param nomClassifieur nom du classifieur
	 * @param message message à tester
	 * @return
	 */
	public static boolean estUnSpam(String nomClassifieur, String message) {
		Classifieur c = Classifieur.load(nomClassifieur);
		return c.classifierSpam(message);
	}
	
	public static void main(String[] args) {
		new FiltreMail(args[0], args[1]);
	}

}
