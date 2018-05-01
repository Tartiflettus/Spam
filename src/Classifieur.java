
public class Classifieur {
	
	private double probaSpam;
	private double[] probaMotSpam;
	private double[] probaMotHam;

	public Classifieur() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public void apprendre(String[] dico, int nombreHam, int nombreSpam) {
		
	}

	
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
		pHam += Math.log(1. - probaSpam);
		
		return pSpam >= pHam;
	}
}
