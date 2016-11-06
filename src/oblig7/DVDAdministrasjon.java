package oblig7;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Denne klassen koordinerer og haandterer grunnleggende administrative oppgaver
 * som aa registrere ny person, kjop av DVD-er, laan og retur. I tillegg har
 * klassen metoder for å gå oversikt over status på eid, laant og utlaant
 * DVD-er.
 *
 */
public class DVDAdministrasjon {
	public final Map<String, Person> navneliste = new HashMap<>();

	// Oppretter DVD-arkiv fra fil.
	public void lesArkivFraFil(String filnavn) throws Exception {
		Scanner fil = new Scanner(new File(filnavn));

		/*
		 * Leser filen som en tilstandsmaskin med følgende gyldige tilstander:
		 * 
		 * State 0: Leser navn, har ikke passert første bindestrek
		 * State 1: Leser ett navn i forkant av DVD-liste
		 * State 2: Leser DVD-titler
		 * State 3: Har lest lånt DVD-tittel. Neste linje inneholder låner.
		 */
		
		int state = 0;

		Person eier = null;
		String laantTittel = null;
		
		while (fil.hasNextLine()) {
			String linje = fil.nextLine();

			switch (state) {
			case 0:
				// Skilletegn indikerer at opplisting av personer som skal registreres er ferdig. State endres til 1.
				if ("-".equals(linje)) {
					state = 1;
				} else {
					lagNyPerson(linje);
				}
				
				break;

			case 1:
				// Henter opp eier slik at vi for de neste linjene kan legge DVD-er på denne eieren.
				eier = navneliste.get(linje);
				
				// Flytter umiddelbart til state 2
				state = 2;
				break;

			case 2:
				/* 
				 * Skilletegn indikerer at opplisting av eide/utlaante DVD-er for en person er ferdig.
				 * Flytter til state 1 slik at programmet er klart for ny person.
				 */
				if ("-".equals(linje)) {
					state = 1;
					break;
				}
				
				String tittel = linje;
				
				// Stjerne indikerer at DVD-en er utlaant.
				if (tittel.startsWith("*")) {
					// Fjerner stjernen fra tittelen.
					tittel = tittel.substring(1);
					laantTittel = tittel;
					
					// Flytter til state 3 for å kunne registrere hvem som laaner DVD-en
					state = 3;
				}
				
				// Uansett om DVD-en er kun eiet, eller baade eiet og utlaant, skal DVD-en legges til personen.
				eier.leggTilDVD(tittel);
				
				break;
			
			
			case 3:
				// Registrere laanet
				laan(linje, eier.getNavn(), laantTittel);
				
				// Returnerer til state 2 for å kunne registrere nye titler på personen
				state = 2;
				laantTittel = null;
				
				break;
			}
		}

		fil.close();
	}

	public boolean finnesPerson(String navn) {
		return navneliste.get(navn) != null;
	}
	
	public Person hentPerson(String navn) {
		return navneliste.get(navn);
	}
	
	public void lagNyPerson(String navn) {
		navneliste.put(navn, new Person(navn));
	}

	public void kjop(String kjoper, String tittel) {
		Person person = navneliste.get(kjoper);

		// Validerer at kjoper faktisk finnes
		if (person == null) {
			System.out.println("Personen " + kjoper + " finnes ikke");
			return;
		}
		
		// Validerer at kjoperen ikke allerede eier DVD-en
		if (person.eierDVD(tittel)) {
			System.out.println(kjoper + " eier allerede " + tittel);
			return;
		}
		
		person.leggTilDVD(tittel);
	}

	public void laan(String laaner, String utlaaner, String tittel) {
		// Kan ikke låne ut til seg selv
		if (laaner.equals(utlaaner)) {
			return;
		}

		Person objLaaner = navneliste.get(laaner);
		Person objUtlaaner = navneliste.get(utlaaner);

		// Både låner og utlåner må finnes
		if (objLaaner == null || objUtlaaner == null) {
			return;
		}

		DVD dvd = objUtlaaner.laanUtDVD(tittel, objLaaner);

		// Betingelser for utlån håndtert i Person.laanUtDVD.
		if (dvd != null) {
			objLaaner.laanDVD(dvd);
		} else {
			System.out.println("Det finnes ingen DVD med tittel " + tittel + " som eies av " + utlaaner + ".");
		}
	}
	
	public void returnerDVD(String navn, String tittel) {
		Person person = navneliste.get(navn);
		
		// Validerer at laaner faktisk finnes
		if (person == null) {
			System.out.println("Person med navn " + navn + " finnes ikke");
			return;
		}
		
		DVD laantDVD = person.hentLaantDVD(tittel);
		 
		// Kontrollerer at laaner faktisk har laant DVD-en
		if (laantDVD == null) {
			System.out.println(navn + " har ikke laant " + tittel);
			return;
		}
		
		Person eier = laantDVD.getEier();
		
		// Returnerer DVD fra laaner
		person.returnerLaantDVD(laantDVD);
		
		// Lar eier motta DVD
		eier.mottaUtlaantDVD(laantDVD);
		
		// Markerer på DVD-en at ingen laaner DVD-en lenger
		laantDVD.setLaaner(null);
	}
	
	public void visPerson(String navn) {
		if ("*".equals(navn)) {
			for (Person person : navneliste.values()) {
				visPerson(person);
			}
			
			return;
		}
		
		Person person = navneliste.get(navn);
		
		if (person == null) {
			System.out.println("Finnes ingen person med navn " + navn);
			return;
		}
		
		visPerson(person);	
	}
	
	private void visPerson(Person person) {
		String navn = person.getNavn();
		
		System.out.println("Person: " + navn);
		
		// Skrive ut eide DVD-er
		Collection<DVD> eide = person.hentEideDVDer();
		
		if (eide.isEmpty()) {
			System.out.println(navn + " eier ingen DVD-er");
		} else {
			System.out.println("DVD-er " + navn + " eier:");
			
			for (DVD dvd : eide) {
				System.out.println(dvd.getTittel());
			}
		}
		
		// Utlånte DVD-er
		Collection<DVD> utlaant = person.hentUtlaanteDVDer();
		
		if (utlaant.isEmpty()) {
			System.out.println(navn + " låner ikke bort noen DVD-er");
		} else {
			System.out.println("DVD-er " + navn + " låner ut:");
			
			for (DVD dvd : utlaant) {
				System.out.println(dvd.getTittel() + " til " + dvd.getLaaner().getNavn());
			}
		}
		
		// Utlånte DVD-er
		Collection<DVD> laant = person.hentLaanteDVDer();
		
		if (laant.isEmpty()) {
			System.out.println(navn + " låner ikke noen DVD-er");
		} else {
			System.out.println("DVD-er " + navn + " laaner:");
			
			for (DVD dvd : laant) {
				System.out.println(dvd.getTittel() + " fra " + dvd.getEier().getNavn());
			}
		}
	}
	
	public void visOversikt() {
		for (Person person : navneliste.values()) {
			Collection<DVD> eier = person.hentEideDVDer();
			Collection<DVD> utlaant = person.hentUtlaanteDVDer();
			Collection<DVD> laant = person.hentLaanteDVDer();
			
			System.out.println("Person: " + person.getNavn());
			System.out.println("Eier: " + eier.size());
			System.out.println("Laant: " + laant.size());
			System.out.println("Utlaant: " + utlaant.size());			
		}
	}
}
