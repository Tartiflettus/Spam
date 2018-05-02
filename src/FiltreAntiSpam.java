import java.io.File;
import java.util.Scanner;

public class FiltreAntiSpam {
	
	public FiltreAntiSpam(String dico, String basetest, int nombreSpam, int nombreHam) {
		// Chargement du dictionnaire
		String[] dictionnaire = ChargerDictionnaire.chargerDictionnaire(dico);
		
		// Recuperation de la base d'apprentissage
		Scanner sc = new Scanner(System.in);
		System.out.print("Combien de SPAM dans la base d'apprentissage ? ");
		int nbSpamApprentissage = sc.nextInt();
		System.out.print("Combien de HAM dans la base d'apprentissage ? ");
		int nbHamApprentissage = sc.nextInt();
		
		Classifieur classifieur = new Classifieur(dictionnaire);
		
		// Apprentissage
		System.out.println("\nApprentissage ...\n");
		classifieur.apprendre(dictionnaire, nbSpamApprentissage, nbHamApprentissage);
		
		// Classification
		System.out.println("Test :");
		int nbErreursSpam = 0, nbErreursHam = 0;
		for (int i = 0; i < nombreSpam; i++) {
			String[] message = LectureMessage.lireMessage(new File(basetest + "/spam/" + i + ".txt"));
			boolean[] b = LectureMessage.comparaisonDico(dictionnaire, message);
			
			//Probabilite proba = classifieur.probaSpam(b);
			boolean sp = classifieur.classifierSpam(b);
			System.out.print("SPAM n°" + i + " détecté comme " + (sp ? "SPAM" : "HAM"));
			if(sp) {
				System.out.println(" : OK");
			}else {
				System.out.println(" : **ERREUR**");
				nbErreursSpam++;
			}
			//System.out.println("P(Y=SPAM | X=x) = " + proba.spam + " ; P(Y=HAM | X=x) = " + proba.ham);
		}
		for (int i = 0; i < nombreHam; i++) {
			String[] message = LectureMessage.lireMessage(new File(basetest + "/ham/" + i + ".txt"));
			boolean[] b = LectureMessage.comparaisonDico(dictionnaire, message);
			
			//Probabilite proba = classifieur.probaSpam(b);
			boolean sp = classifieur.classifierSpam(b);
			System.out.print("HAM n°" + i + " détecté comme " + (sp ? "SPAM" : "HAM"));
			if(sp) {
				System.out.println(" : **ERREUR**");
				nbErreursHam++;
			}else {
				System.out.println(" : OK");
			}
			//System.out.println("P(Y=SPAM | X=x) = " + proba.spam + " ; P(Y=HAM | X=x) = " + proba.ham);
		}
		
		System.out.println("=====================================================================================");	
		System.out.println("Erreur de test sur les " + nombreSpam + " SPAM : " + ((double)nbErreursSpam) / ((double)nombreSpam) * 100 + " %");
		System.out.println("Erreur de test sur les " + nombreHam + " HAM : " + ((double)nbErreursHam) / ((double)nombreHam) * 100 + " %");
		System.out.println("Erreur de test globale sur " + (nombreSpam + nombreHam) + " mails : " + ((double)(nbErreursSpam + nbErreursHam)) / ((double)(nombreSpam + nombreHam)) * 100 + " %");
	}
	
	public static void main(String[] args) {
		new FiltreAntiSpam("res/dictionnaire1000en.txt", args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
	}

}
