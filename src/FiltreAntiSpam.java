import java.io.File;

public class FiltreAntiSpam {
	
	public FiltreAntiSpam(String dico) {
		String[] dictionnaire = ChargerDictionnaire.chargerDictionnaire(dico);
		Classifieur classifieur = new Classifieur(dictionnaire);
		classifieur.apprendre(dictionnaire, 200, 200);
		int nbErreursSpam = 0, nbErreursHam = 0;
		for (int i = 0; i < 100; i++) {
			String[] message = LectureMessage.lireMessage(new File("res/basetest/spam/" + i + ".txt"));
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
		for (int i = 0; i < 200; i++) {
			String[] message = LectureMessage.lireMessage(new File("res/basetest/ham/" + i + ".txt"));
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
		System.out.println("Ratio d'erreurs sur les spams : " + ((double)nbErreursSpam) / 100.);
		System.out.println("Ratio d'erreurs sur les hams : " + ((double)nbErreursHam) / 200.);
		System.out.println("Ratio d'erreurs total : " + ((double)(nbErreursSpam + nbErreursHam)) / 300.);
	}
	
	public static void main(String[] args) {
		new FiltreAntiSpam("res/dictionnaire1000en.txt");
	}

}
