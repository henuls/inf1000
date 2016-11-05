package oblig7;

import java.util.Scanner;

public class DVDArkiv {
	private DVDAdministrasjon admin = new DVDAdministrasjon();
	private Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		DVDArkiv arkiv = new DVDArkiv();
		
		arkiv.hovedMeny();
	}

	private void hovedMeny() {
		System.out.println("MENY FOR DVD-ADMINISTRASJON");

		System.out.println("1: Ny person.");
		System.out.println("2: Kjop.");
		System.out.println("3: Laan.");
		System.out.println("4: Vis.");
		System.out.println("5: Oversikt.");
		System.out.println("6: Retur.");
		System.out.println("7: Avslutt.");
		
		String strValg = scanner.nextLine();

		int valg = Integer.parseInt(strValg);
		
		switch (valg) {
			case 1:
				nyPersonMeny();
				break;
				
			case 2:
				kjopMeny();
				break;
				
			case 3:
				laanMeny();
				break;
				
			case 4:
				visMeny();
				break;
				
			case 5:
				oversikt();
				break;
				
			case 6:
				returMeny();
				break;
				
			case 7:
				System.exit(1);
		}
	}
	
	private void nyPersonMeny() {
		System.out.println("\nHva heter den nye personen?");
		String navn = scanner.nextLine();
		admin.lagNyPerson(navn);
		hoppTilHovedMeny();
	}
	
	private void kjopMeny() {
		System.out.println("\nHvem har kjøpt DVD-en?");
		String navn = scanner.nextLine();
		
		if (!validerNavn(navn)) {
			return;
		}
		
		System.out.println("Hva er tittelen paa DVD-en?");
		String tittel = scanner.nextLine();
		
		admin.kjop(navn, tittel);
		
		hoppTilHovedMeny();
	}
	
	private void laanMeny() {
		System.out.println("\nHvem vil laane DVD-en?");
		String laaner = scanner.nextLine();
		
		if (!validerNavn(laaner)) {
			return;
		}
		
		System.out.println("Hvem skal DVD-en laanes fra?");
		String utlaaner = scanner.nextLine();
		
		if (!validerNavn(utlaaner)) {
			return;
		}
		
		System.out.println("Hva er tittelen på DVD-en?");
		String tittel = scanner.nextLine();
		
		admin.laan(laaner, utlaaner, tittel);
		
		hoppTilHovedMeny();
	}
	
	private void visMeny() {
		System.out.println("\nHvilken person vil du se? (* for alle)");
		String navn = scanner.nextLine();
		admin.visPerson(navn);
		
		hoppTilHovedMeny();
	}
	
	private void returMeny() {
		System.out.println("\nHvem skal returnere DVD?");
		String navn = scanner.nextLine();
		
		if (!validerNavn(navn)) {
			return;
		}
		
		System.out.println("\nHvilken DVD skal returneres?");
		String tittel = scanner.nextLine();
		
		admin.returnerDVD(navn, tittel);
		
		hoppTilHovedMeny();
	}
	
	private void oversikt() {
		System.out.println();
		
		admin.visOversikt();
		
		hoppTilHovedMeny();
	}
	
	private boolean validerNavn(String navn) {
		if (!admin.finnesPerson(navn)) {
			System.out.println("Det er ingen personer som heter " + navn + ".");
			hoppTilHovedMeny();
			
			return false;
		}
		
		return true;
	}
	
	private void hoppTilHovedMeny() {
		System.out.println();
		hovedMeny();
	}
}
