import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

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
	 * @throws FileNotFoundException 
	 */
	public static String[] lireMessage(File f) throws FileNotFoundException {
		Scanner sc = new Scanner(f);
		
		List<String> reponse = new LinkedList<>();
		
		String mot = null;
		//lire chaque ligne jusqu'à la fin du fichier
		while(sc.hasNext("[A-Za-z]+")) {
			mot = sc.next("[A-Za-z]+");
			mot = mot.trim(); //supprimer les espaces en début et fin de mot
			mot = mot.toLowerCase(); //mettre en minuscules le mot potentiellement en majuscule
			reponse.add(mot);
		}
		
		sc.close();
		return reponse.toArray(STR_ARRAY);
	}
	
	
	
	public static void main(String[] args) {
		try {
			String[] msg = lireMessage(new File("res/baseapp/ham/0.txt"));
			for(String s : msg) {
				System.out.println(s);
			}
		}
		catch(IOException e) {
			System.err.println("Impossible de lire le fichier");
			e.printStackTrace(System.err);
			System.exit(-1);
		}
	}
	
}


