package oblig6;

public class Ord {
	private final String tekst;
	private int antall;
	
	public Ord(String tekst) {
		this.tekst = tekst;
		antall = 1;
	}
	
	@Override
	public String toString() {
		return tekst;
	}
	
	public String hentTekst() {
		return tekst;
	}
	
	public int hentAntall() {
		return antall;
	}
	
	public void oekAntall() {
		antall++;
	}
	
	public int hentLengde() {
		return tekst.length();
	}
	
	public int plassIDokument() {
		return antall * hentLengde();
	}
}
