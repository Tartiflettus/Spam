import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Lire un message
 * @author Victor
 *
 */
public class LectureMessage {
	
	/**
	 * Nécessaire pour déduire le type d'un tableau (voir List<>.toArray)
	 */
	private static String[] STR_ARRAY = new String[0];

	public LectureMessage() {
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * Transformer un fichier texte en tableau
	 * @param f fichier dans lequel lire le message
	 * @return Tableau contenant les mots du message (sans les mots de < 3 lettres) en minuscules
	 */
	public static String[] lireMessage(File f) {
		Scanner sc = null;
		try {
			sc = new Scanner(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<String> reponse = new LinkedList<>();
		Pattern pat = Pattern.compile("[A-Za-z]+"); //regexp pour un mot

		//lire chaque ligne jusqu'à la fin du fichier
		while(sc.hasNextLine()) {
			String ligne = sc.nextLine();
			Matcher mat = pat.matcher(ligne);
			while(mat.find()) {
				//extraire le mot trouvé dans la ligne
				String mot = ligne.substring(mat.start(), mat.end());
				if(mot.length() > 3) { //ajouter le mot s'in n'est pas un mot outil
					//System.out.println(mot);
					mot = mot.trim(); //supprimer les espaces en début et fin de mot
					mot = mot.toLowerCase(); //mettre en minuscules le mot potentiellement en majuscule
					reponse.add(mot);
				}
			}
		}
		
		sc.close();
		return reponse.toArray(new String[0]);
	}
	
	
	
	/**
	 * 
	 * @param dico dictionnaire
	 * @return un vecteur indiquant si les mots du dictionnaire sont dans le message donné
	 */
	public static boolean[] comparaisonDico(String[] dico, String[] msg) {
		boolean[] presence = new boolean[dico.length];
		
		for(int i=0; i < msg.length; i++) {
			for(int j=0; j < dico.length; j++) {
				if(msg[i].equals(dico[j])) {
					presence[j] = true;
				}
			}
		}
		
		return presence;
	}
	
	
	
	public static void main(String[] args) {
		String[] dico = ChargerDictionnaire.chargerDictionnaire("res/dictionnaire1000en.txt");
		boolean[] presence = comparaisonDico(dico, lireMessage(new File("res/baseapp/ham/0.txt")));
		for(int i=0; i < dico.length; i++) {
			if(presence[i]) {
				System.out.println(dico[i]);
			}
		}
	}
	
}


