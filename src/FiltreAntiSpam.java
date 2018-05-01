import java.io.File;

public class FiltreAntiSpam {
	
	public FiltreAntiSpam(String dico) {
		String[] dictionnaire = ChargerDictionnaire.chargerDictionnaire(dico);
		Classifieur classifieur = new Classifieur(dictionnaire);
		classifieur.apprendre(dictionnaire, 200, 200);
		for (int i = 0; i < 100; i++) {
			String[] message = LectureMessage.lireMessage(new File("res/basetest/spam/" + i + ".txt"));
			boolean[] b = LectureMessage.comparaisonDico(dictionnaire, message);
			System.out.println(classifieur.classifierSpam(b));
		}
	}
	
	public static void main(String[] args) {
		new FiltreAntiSpam("res/dictionnaire1000en.txt");
	}

}
