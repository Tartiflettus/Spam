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
				if (line.length() > 3) {
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
