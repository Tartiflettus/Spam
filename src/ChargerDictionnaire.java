import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ChargerDictionnaire {

	public ChargerDictionnaire() {
	
	}
	
	/**
	 * Permet de charger le dictionnaire texte
	 * @param texte
	 * @return 
	 */
	public static String[] chargerDictionnaire(String texte) {
		ArrayList<String> dictionnaire = new ArrayList<>();
		BufferedReader br = null;
		String[] dict = null;
		try {
			br = new BufferedReader(new FileReader(texte));
			String line;
			while ((line = br.readLine()) != null) {
				// Ne prend pas en compte les mots de moins de 3 lettres
				if (line.length() > 3) {
					// Met les mots en minuscule
					line = line.toLowerCase();
					dictionnaire.add(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null)
				try {
					br.close();
					dict = new String[dictionnaire.size()];
					// Transforme de liste à tableau
					dict = dictionnaire.toArray(dict);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return dict;
	}
	
	public static void main(String[] args) {
		String[] dict = chargerDictionnaire("res/dictionnaire1000en.txt");
		for (String s : dict)
			System.out.println(s);
	}
}
