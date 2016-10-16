package oblig6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Oblig6 {
	public static void main(String[] args) throws Exception {
		Ordliste liste = new Ordliste();
		liste.lesBok("scarlet.txt");
		
		Ord[] topp5 = liste.vanligste5();
		
		System.out.println("Topp 5 forekomst: " + Arrays.toString(topp5));
		
		List<String> kontrollOrd = new ArrayList<>();
		
		kontrollOrd.add("watson");
		kontrollOrd.add("indeed");
		kontrollOrd.add("astonishment");
		kontrollOrd.add("elementary");
		
		for (String ord : kontrollOrd) {
			Ord kontroll = liste.finnOrd(ord);
			System.out.println("Antall forekomster av " + kontroll + ": " + kontroll.hentAntall());
		}
		
		Ord kortest = liste.finnKorteste();
		Ord lengst = liste.finnLengste();
		Ord tarMestPlass = liste.tarMestPlassIDokument();
		
		System.out.println("Kortest: " + kortest);
		System.out.println("Lengst : " + lengst);
		System.out.println("Plass  : " + tarMestPlass);
	}
}
