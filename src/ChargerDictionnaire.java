import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ChargerDictionnaire {
	private ArrayList<String> dictionnaire;

	public ChargerDictionnaire() {
		dictionnaire = new ArrayList<>();
	}
	
	/**
	 * Permet de charger le dictionnaire texte
	 * @param texte
	 */
	public String[] chargerDictionnaire(String texte) {
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
		ChargerDictionnaire cd = new ChargerDictionnaire();
		String[] dict = cd.chargerDictionnaire("res/dictionnaire1000en.txt");
		for (String s : dict)
			System.out.println(s);
	}
}
