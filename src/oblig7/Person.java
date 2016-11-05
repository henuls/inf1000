package oblig7;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Person {
	private final String navn;
	private final Map<String, DVD> eideDVDer = new HashMap<>();
	private final Map<String, DVD> laanteDVDer = new HashMap<>();
	private final Map<String, DVD> utlaanteDVDer = new HashMap<>();

	public Person(String navn) {
		this.navn = navn;
	}

	public String getNavn() {
		return navn;
	}

	public DVD leggTilDVD(String tittel) {
		DVD dvd = new DVD(tittel, this);

		eideDVDer.put(tittel, dvd);

		return dvd;
	}

	public void laanDVD(DVD dvd) {
		laanteDVDer.put(dvd.getTittel(), dvd);
	}

	public DVD laanUtDVD(String tittel, Person laaner) {
		/*
		 * Mulig å låne ut kun dersom: 1) personen eier DVDen 2) DVDen er ikke
		 * allerede utlånt
		 */
		if (eierDVD(tittel) && !erUtlaant(tittel)) {
			DVD dvd = eideDVDer.get(tittel);

			utlaanteDVDer.put(dvd.getTittel(), dvd);
			
			dvd.setLaaner(laaner);
			
			return dvd;
		}

		return null;
	}

	public void mottaUtlaantDVD(DVD dvd) {
		utlaanteDVDer.remove(dvd.getTittel());
	}
	
	public void returnerLaantDVD(DVD dvd) {
		laanteDVDer.remove(dvd.getTittel());
	}

	public boolean eierDVD(String tittel) {
		return eideDVDer.get(tittel) != null;
	}

	public boolean erUtlaant(String tittel) {
		return utlaanteDVDer.get(tittel) != null;
	}
	
	public DVD hentLaantDVD(String tittel) {
		return laanteDVDer.get(tittel);
	}
	
	public Collection<DVD> hentEideDVDer() {
		return eideDVDer.values();
	}
	
	public Collection<DVD> hentUtlaanteDVDer() {
		return utlaanteDVDer.values();
	}
	
	public Collection<DVD> hentLaanteDVDer() {
		return laanteDVDer.values();
	}
	
	@Override
	public String toString() {
		return navn;
	}
}
