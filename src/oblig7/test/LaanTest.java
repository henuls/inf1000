package oblig7.test;

import oblig7.DVDAdministrasjon;
import oblig7.Person;

/*
 * "Integrasjonstest"
 * 
 * Tester at antall eid, laant og utlaant er korrekt i etterkant av laan og retur
 */
public class LaanTest {
	private DVDAdministrasjon admin = new DVDAdministrasjon();

	public static void main(String[] args) throws Exception {
		LaanTest test = new LaanTest();
		test.laan_og_retur();
	}

	public void laan_og_retur() throws Exception {
		final String navn1 = "Henrik";
		final String navn2 = "Øystein";
		final String navn3 = "Torbjørn";

		final String skyfall = "skyfall";
		final String moonraker = "moonraker";
		final String goldfinger = "goldfinger";
		final String thunderball = "thunderball";

		// Lagrer tre testpersoner
		admin.lagNyPerson(navn1);
		admin.lagNyPerson(navn2);
		admin.lagNyPerson(navn3);

		// Kjøper fire DVD-er. Én testpersoner eier to, de andre eier én
		admin.kjop(navn1, skyfall);
		admin.kjop(navn1, moonraker);
		admin.kjop(navn2, goldfinger);
		admin.kjop(navn3, thunderball);

		// Validerer at antalle eid, laant og utlaant for hver av testpersonene
		validerEidLaantOgUtlaant(navn1, 2, 0, 0);
		validerEidLaantOgUtlaant(navn2, 1, 0, 0);
		validerEidLaantOgUtlaant(navn3, 1, 0, 0);
		
		// Utføre to lån
		admin.laan(navn1, navn2, goldfinger);
		
		// Valider at antallet er endre i etterkant av laan
		validerEidLaantOgUtlaant(navn1, 2, 1, 0);
		validerEidLaantOgUtlaant(navn2, 1, 0, 1);
		
		// Forsoker aa returnere DVD-tittel som ikke finnes
		admin.returnerDVD(navn1, "tittel som ikke finnes");

		// Antall skal ikke endre seg
		validerEidLaantOgUtlaant(navn1, 2, 1, 0);
		validerEidLaantOgUtlaant(navn2, 1, 0, 1);
		
		// Gjennomfore vellykket retur
		admin.returnerDVD(navn1, goldfinger);
		
		// Validere at ingen DVD-er er laant eller utlaant
		validerEidLaantOgUtlaant(navn1, 2, 0, 0);
		validerEidLaantOgUtlaant(navn2, 1, 0, 0);
		
		System.out.println("Testen er kjørt vellykket!");
	}

	public void validerEidLaantOgUtlaant(String navn, int eid, int laant, int utlaant) throws Exception {
		Person person = admin.hentPerson(navn);

		if (person == null) {
			throw new Exception("Personen " + navn + "finnes ikke");
		}

		int faktiskEid = person.hentEideDVDer().size();

		if (faktiskEid != eid) {
			throw new Exception("Antall eide DVD-er for " + navn + " er " + faktiskEid + ", forventet " + eid);
		}

		int faktiskLaant = person.hentLaanteDVDer().size();

		if (faktiskLaant != laant) {
			throw new Exception("Antall laante DVD-er for " + navn + " er " + faktiskLaant + ", forventet " + laant);
		}
		
		int faktiskUtlaant = person.hentUtlaanteDVDer().size();

		if (faktiskUtlaant != utlaant) {
			throw new Exception("Antall utlaante DVD-er for " + navn + " er " + faktiskUtlaant + ", forventet " + utlaant);
		}
	}
}
