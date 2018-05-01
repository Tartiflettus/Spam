import java.io.File;

public class Classifieur {
	
	private double probaSpam;
	private double[] probaMotSpam;
	
	private double probaHam;
	private double[] probaMotHam;
	
	private static double EPSILON = 1; //permet le lissage des parametres

	public Classifieur(String[] dico) {
		probaSpam = 0;
		probaMotSpam = new double[dico.length];
		probaHam = 0;
		probaMotHam = new double[dico.length];
	}
	
	/**
	 * 
	 * @param dico
	 * @param nombreHam
	 * @param nombreSpam
	 */
	public void apprendre(String[] dico, int nombreHam, int nombreSpam) {
		this.probaSpam = (double)nombreSpam / (double)(nombreHam + nombreSpam);
		this.probaHam = 1. - probaSpam;
		
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
		for(int i=0; i < nombreSpam; i++) {
			probaMotSpam[i] = (probaMotSpam[i] + EPSILON) / (nombreSpam + 2 * EPSILON);
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
		
		for(int i=0; i < nombreHam; i++) {
			probaMotHam[i] = (probaMotHam[i] + EPSILON) / (nombreHam + 2 * EPSILON);
		}
	}

	
	/**
	 * Permet de classifier des messages comme des spams ou des hams
	 * @param msg message lu par LectureMessage, que l'on veut classifier
	 * @return true si le message est classifi� en spam
	 */
	public boolean classifierSpam(boolean[] msg) {
		//pour rendre le calcul faisable, au lieu de calculer un �norme produit de nombres tr�s petits...
		//... on calcule le log de ce produit, c'est � dire la somme des log des termes
		
		double pSpam = 0.;
		for(int i=0; i < msg.length; i++) {
			final double probaActu = Math.log(msg[i] ? probaMotSpam[i] : (1. - probaMotSpam[i]));
			
			pSpam += probaActu;
		}
		pSpam += Math.log(probaSpam);
		
		
		double pHam = 0.;
		for(int i=0; i < msg.length; i++) {
			final double probaActu = Math.log(msg[i] ? probaMotHam[i] : (1. - probaMotHam[i]));
			
			pHam += probaActu;
		}
		pHam += Math.log(1. - probaSpam);
		
		System.out.println("pSpam = " + pSpam);
		System.out.println("pHam = " + pHam);
		
		return pSpam > pHam;
	}

}
