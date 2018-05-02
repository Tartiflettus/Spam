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
	public void apprendre(String[] dico, int nombreSpam, int nombreHam) {
		this.probaSpam = ((double)nombreSpam) / ((double)(nombreHam + nombreSpam));
		this.probaHam = 1. - probaSpam;
		
		// proba mot spam
		for (int i = 0; i < nombreSpam; i++) {
			String[] message = LectureMessage.lireMessage(new File("res/baseapp/spam/" + i + ".txt"));
			boolean[] b = LectureMessage.comparaisonDico(dico, message);
			for (int j = 0; j < dico.length; j++) {
				if (b[j]) {
					probaMotSpam[j] += 1.;
				}
			}
		}
		for(int i=0; i < dico.length; i++) {
			probaMotSpam[i] = ((double)(probaMotSpam[i] + EPSILON)) / ((double)(nombreSpam + 2 * EPSILON));
		}
		
		// proba mot ham
		for (int i = 0; i < nombreHam; i++) {
			String[] message = LectureMessage.lireMessage(new File("res/baseapp/ham/" + i + ".txt"));
			boolean[] b = LectureMessage.comparaisonDico(dico, message);
			for (int j = 0; j < dico.length; j++) {
				if (b[j]) {
					probaMotHam[j] += 1.;
				}
			}
		}
		
		for(int i=0; i < dico.length; i++) {
			probaMotHam[i] = ((double)(probaMotHam[i] + EPSILON)) / ((double)(nombreHam + 2 * EPSILON));
		}
	}

	
	/**
	 * Permet de classifier des messages comme des spams ou des hams
	 * @param msg message lu par LectureMessage, que l'on veut classifier
	 * @return la probabilité que ce message soit un mail, ainsi que celle qu'il soit un ham
	 * @deprecated non fonctionnelle. La formule utilisée est fausse
	 */
	/*@Deprecated
	public Probabilite probaSpam(boolean[] msg) {
		//pour rendre le calcul faisable, au lieu de calculer un énorme produit de nombres très petits...
		//... on calcule le log de ce produit, c'est à dire la somme des log des termes
		double pSpam = 0.;
		for(int i=0; i < msg.length; i++) {
			final double probaActu = Math.log(msg[i] ? probaMotSpam[i] : (1. - probaMotSpam[i]));
			
			pSpam += probaActu;
		}
		//pSpam += Math.log(probaSpam);

		
		double pHam = 0.;
		for(int i=0; i < msg.length; i++) {
			final double probaActu = Math.log(msg[i] ? probaMotHam[i] : (1. - probaMotHam[i]));
			
			pHam += probaActu;
		}
		//pHam += Math.log(probaHam);
		
		//probabilité à priori du message
		final double pMsg = pSpam * probaSpam + pHam * probaHam;
		
		return new Probabilite(
				Math.exp(pSpam + Math.log(probaSpam) - pMsg),
				Math.exp(pHam + Math.log(probaHam) - pMsg)
				);
	}*/
	
	
	
	
	/**
	 * Permet de classifier des messages comme des spams ou des hams
	 * @param msg message lu par LectureMessage, que l'on veut classifier
	 * @return true si le message est classifié en spam
	 */
	public boolean classifierSpam(boolean[] msg) {
		//pour rendre le calcul faisable, au lieu de calculer un énorme produit de nombres très petits...
		//... on calcule le log de ce produit, c'est à dire la somme des log des termes
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
		pHam += Math.log(probaHam);
		
		//System.out.println("pSpam : " + pSpam + " ; pHam : " + pHam);
		return pSpam > pHam;
	}

}
