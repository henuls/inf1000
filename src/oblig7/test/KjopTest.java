package oblig7.test;

import oblig7.DVDAdministrasjon;
import oblig7.Person;

/*
 * "Integrasjonstest"
 * 
 * Tester at kjøp fungerer ved å telle opp antall eid etterkant av kjøp
 */
public class KjopTest {
	private DVDAdministrasjon admin = new DVDAdministrasjon();

	public static void main(String[] args) throws Exception {
		LaanTest test = new LaanTest();
		test.laan_og_retur();
	}

	public void kjop() throws Exception {
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

		// Validerer at antalle eidfor hver av testpersonene
		validerEid(navn1, 2);
		validerEid(navn2, 1);
		validerEid(navn3, 1);

		System.out.println("Testen er kjørt vellykket!");
	}

	public void validerEid(String navn, int eid) throws Exception {
		Person person = admin.hentPerson(navn);

		if (person == null) {
			throw new Exception("Personen " + navn + "finnes ikke");
		}

		int faktiskEid = person.hentEideDVDer().size();

		if (faktiskEid != eid) {
			throw new Exception("Antall eide DVD-er for " + navn + " er " + faktiskEid + ", forventet " + eid);
		}
	}
}
