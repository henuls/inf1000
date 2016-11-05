package oblig7;

public class Oblig7 {
	public static void main(String[] args) throws Exception {
		DVDAdministrasjon admin = new DVDAdministrasjon();
		admin.lesArkivFraFil("dvdarkiv.txt");
		
		admin.visPerson("*");
		// admin.visOversikt();
		
		admin.returnerDVD("Kari", "The music man");
		
		System.out.println();
		
		admin.visPerson("*");
	}
}
