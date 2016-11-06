package oblig7;

/**
 * Denne klassen representerer en DVD. Holder i tillegg til DVD-tittel rede på
 * hvem som eier og hvem som evt. laaner DVD-en. Tittel og eier kan ikke endres
 * etter at DVD-objektet er opprettet.
 *
 */
public class DVD {
	private final String tittel;
	private final Person eier;
	private Person laaner;

	public DVD(String tittel, Person eier) {
		this.tittel = tittel;
		this.eier = eier;
	}

	public String getTittel() {
		return tittel;
	}

	public Person getEier() {
		return eier;
	}

	public Person getLaaner() {
		return laaner;
	}

	public void setLaaner(Person laaner) {
		this.laaner = laaner;
	}
}
