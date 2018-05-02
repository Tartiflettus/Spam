public class FiltreMail {
	
	public FiltreMail(String nomClassifieur, String message) {
		boolean spam = estUnSpam(nomClassifieur, message);
		System.out.println("D'après '" + nomClassifieur + "', le message '" + message + "' est un " + (spam ? "SPAM" : "HAM"));
	}
	
	public static boolean estUnSpam(String nomClassifieur, String message) {
		Classifieur c = Classifieur.load(nomClassifieur);
		return c.classifierSpam(message);
	}
	
	public static void main(String[] args) {
		new FiltreMail(args[0], args[1]);
	}

}
