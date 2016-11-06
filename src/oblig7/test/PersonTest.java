package oblig7.test;

import oblig7.DVD;
import oblig7.Person;

/*
 * "Enhetstest"
 * 
 * Tester at grunnleggende operasjoner på en person er korrekt
 */
public class PersonTest {
	public static void main(String[] args) throws Exception {
		PersonTest test = new PersonTest();
		test.legg_til_dvd();
		test.laan_ut_dvd();
	}
	
	// Tester at legg til DVD fungerer
	private void legg_til_dvd() throws Exception {
		Person person = new Person("Ola");
		
		// Legger til to DVD-er på personer
		person.leggTilDVD("Skyfall");
		person.leggTilDVD("Moonraker");
		
		// Validerer at personen nå eier DVD-ene som ble lagt til
		validerEier(person, "Skyfall");
		validerEier(person, "Moonraker");
		
		System.out.println("Test legg_til_dvd() vellykket!");		
	}
	
	// Tester at det utlaan av DVD fungerer
	private void laan_ut_dvd() throws Exception {
		// Oppretter to personer, eier og laaner
		Person eier = new Person("eier");
		Person laaner = new Person("laaner");
		
		// Legger til én film på eier
		eier.leggTilDVD("Moonraker");
		
		// Forsøker aa laane en DVD eier ikke eier
		DVD dvd = eier.laanUtDVD("finnes ikke", laaner);
		
		// Forventer null i retur, alt annet tolkes som at testen har feilet
		if (dvd != null) {
			throw new Exception("Forsok på laan av tittel som ikke finnes burde ikke bli gjennomfort");
		}
		
		// Gjennomforer utlaan på DVD som eier faktisk eier
		dvd = eier.laanUtDVD("Moonraker", laaner);
		
		// Forventer at DVD-instans returneres
		if (dvd == null) {
			throw new Exception("Klarte ikke gjennomfore utlaan av DVD");
		}
		
		if (!"Moonraker".equals(dvd.getTittel())) {
			throw new Exception("Forventet at laant tittel var Moonraker, " + dvd.getTittel() + " faktisk tittel");
		}
		
		System.out.println("Test laan_ut_dvd() vellykket!");
	}
	
	private void validerEier(Person person, String tittel) throws Exception {
		if (!person.eierDVD(tittel)) {
			throw new Exception("Person " + person.getNavn() + " eier ikke " + tittel);
		}
	}
}
