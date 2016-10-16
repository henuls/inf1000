package oblig6;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Ordliste {
	private List<Ord> ordliste = new ArrayList<>();

	private Ord lengst = null;
	private Ord kortest = null;
	private Ord tarMestPlass = null;

	public void lesBok(String filnavn) throws Exception {
		Scanner fil = new Scanner(new File(filnavn));

		while (fil.hasNextLine()) {
			String linje = fil.nextLine();

			leggTilOrd(linje);
		}

		fil.close();
	}

	public void leggTilOrd(String ord) {
		if (null == ord) {
			return;
		}

		Ord objOrd = finnOrd(ord);

		// Ordet ligger allerede i ordlista, skal kun øke antall forekomster
		if (objOrd != null) {
			objOrd.oekAntall();
		}
		// Order ligger ikke i ordlista. Må legge ordet til i ordlista.
		else {
			objOrd = new Ord(ord);
			ordliste.add(objOrd);
		}

		// På dette tidspunktet er objOrd aldri null.

		if (kortest == null || objOrd.hentLengde() < kortest.hentLengde()) {
			kortest = objOrd;
		}

		if (lengst == null || objOrd.hentLengde() > lengst.hentLengde()) {
			lengst = objOrd;
		}

		if (tarMestPlass == null || objOrd.plassIDokument() > tarMestPlass.plassIDokument()) {
			tarMestPlass = objOrd;
		}
	}

	public Ord finnOrd(String tekst) {
		if (null == tekst) {
			return null;
		}

		for (Ord ord : ordliste) {
			if (tekst.equalsIgnoreCase(ord.hentTekst())) {
				return ord;
			}
		}

		return null;
	}

	public int antallOrd() {
		return ordliste.size();
	}

	public int antallForekomster(String tekst) {
		Ord ord = finnOrd(tekst);

		if (ord == null) {
			return 0;
		}

		return ord.hentAntall();
	}

	public Ord[] vanligste5() {
		// Sorterer ordene i ordlista på bakgrunn av antall forekomster. Høyest antall først.
		Collections.sort(ordliste, new Comparator<Ord>() {
			@Override
			public int compare(Ord ord1, Ord ord2) {
				if (ord1.hentAntall() > ord2.hentAntall()) {
					return -1;
				} else if (ord1.hentAntall() < ord2.hentAntall()) {
					return 1;
				} else {
					return 0;
				}
			}
		});

	// Setter størrelsen på returnert array. Oppad begrenset til fem.
	int antall = 5;

	if(ordliste.size()<antall)

	{
		antall = ordliste.size();
	}

	Ord[] topp5 = new Ord[antall];

	// Fyller topp5-array med ord
	for(
	int i = 0;i<antall;i++)

	{
		topp5[i] = ordliste.get(i);
	}

	return topp5;

	}

	public Ord finnLengste() {
		return lengst;
	}

	public Ord finnKorteste() {
		return kortest;
	}

	public Ord tarMestPlassIDokument() {
		return tarMestPlass;
	}

}
