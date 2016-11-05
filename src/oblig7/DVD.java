package oblig7;

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
	
	public void returnerEtterLaan() {
		eier.mottaUtlaantDVD(this);
		laaner.returnerLaantDVD(this);
		
		laaner = null;
	}
}
