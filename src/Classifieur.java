import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Classifieur, permettant de distinguer les spams des hams
 * Serializable, pour pouvoir l'enregistrer et le lire facilement
 * @author victor
 *
 */
public class Classifieur implements Serializable{
	
	private int nbSpamApprentissage;
	private int nbHamApprentissage;
	
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
		this.nbSpamApprentissage = nombreSpam;
		this.nbHamApprentissage = nombreHam;
		
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
	
	
	/**
	 * Sauvegarder le classifieur dans un fichier
	 * @param f fichier dans lequel sauvegarder le classifieur
	 */
	public void save(File f) {
		try {
			FileOutputStream fo = new FileOutputStream(f);
			ObjectOutputStream oo = new ObjectOutputStream(fo);
			oo.writeObject(this);
		}
		catch(IOException e) {
			e.printStackTrace(System.err);
		}
	}
	
	/**
	 * Sauvegarder le classifieur dans un fichier
	 * @param f fichier dans lequel sauvegarder le classifieur
	 */
	public void save(String f) {
		save(new File(f));
	}
	
	
	/**
	 * Lire le classifieur depuis un fichier
	 * @param f fichier depuis lequel lire le classifieur
	 */
	public static Classifieur load(File f) {
		try {
			FileInputStream fi = new FileInputStream(f);
			ObjectInputStream oi = new ObjectInputStream(fi);
			Classifieur cl = (Classifieur) oi.readObject();
			
			oi.close();
			return cl;
		}
		catch(IOException | ClassNotFoundException e) {
			e.printStackTrace(System.err);
		}
		
		
		return null;
	}
	
	
	/**
	 * Lire le classifieur depuis un fichier
	 * @param f fichier depuis lequel lire le classifieur
	 */
	public static Classifieur load(String f) {
		return load(new File(f));
	}
	
	
	
	public static void main(String[] args) {
		String[] dict = ChargerDictionnaire.chargerDictionnaire("res/dictionnaire1000en.txt");
		Classifieur cl = new Classifieur(dict);
		cl.apprendre(dict, 200, 200);
		cl.save("classifieur.output");
		cl = null;
		cl = Classifieur.load("classifieur.output");
		System.out.println("spam/ham: " + cl.nbSpamApprentissage + " ; " + cl.nbSpamApprentissage);
		System.out.println("Première proba spam: " + cl.probaMotSpam[0]);
		System.out.println("Première proba ham: " + cl.probaMotHam[0]);
		System.out.println("epsilon: " + cl.EPSILON);
	}

}
