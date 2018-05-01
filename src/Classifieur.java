import java.io.File;

public class Classifieur {
	
	private double probaSpam;
	private double[] probaMotSpam;
	
	private double probaHam;
	private double[] probaMotHam;

	public Classifieur() {

	}
	
	/**
	 * 
	 * @param dico
	 * @param nombreHam
	 * @param nombreSpam
	 */
	public void apprendre(String[] dico, int nombreHam, int nombreSpam) {
		this.probaSpam = nombreSpam / (nombreHam + nombreSpam);
		this.probaHam = 1 - probaSpam;
		
		// proba mot spam
		for (int i = 0; i < nombreSpam; i++) {
			String[] message = LectureMessage.lireMessage(new File("res/baseapp/spam/" + i + ".txt"));
			boolean[] b = LectureMessage.comparaisonDico(dico, message);
			for (int j = 0; j < dico.length; j++) {
				if (b[j]) {
					probaMotSpam[j]++;
				}
			}
		}
		
		// proba mot ham
		for (int i = 0; i < nombreHam; i++) {
			String[] message = LectureMessage.lireMessage(new File("res/baseapp/ham/" + i + ".txt"));
			boolean[] b = LectureMessage.comparaisonDico(dico, message);
			for (int j = 0; j < dico.length; j++) {
				if (b[j]) {
					probaMotHam[j]++;
				}
			}
		}
		
		double produit = 0;
		for (int j = 0; j < dico.length - 1; j++) {
			
		}
	}

}
