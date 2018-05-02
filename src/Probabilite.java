
/**
 * Classe utilitaire permettant de sauvegarder les probabilités qu'un message soit un spam ou un ham
 * @author Victor
 *
 */
public class Probabilite {
	
	/**
	 * Se comporte comme un structure en C
	 */
	public double spam;
	public double ham;

	public Probabilite() {
	}
	
	public Probabilite(double spam, double ham) {
		this.spam = spam;
		this.ham = ham;
	}

}
