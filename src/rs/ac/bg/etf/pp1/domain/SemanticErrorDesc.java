package rs.ac.bg.etf.pp1.domain;

/**
 * Globalni enum za opis svih semantickih gresaka.
 * 
 * @author Aleksandar Grkajac
 *
 */
public enum SemanticErrorDesc {

	S2_00("Semanticka greska [S2-00] Nije pronadjena main funkcija"),
	S2_01("Semanticka greska [S2-01] Nije pronadjen tip 'replaceString' u tabeli simbola"),
	S2_02("Semanticka greska [S2-02] na liniji replaceString : Ime 'replaceString1' ne predstavlja tip"),
	S2_03("Semanticka greska [S2-03] na liniji replaceString : Tipovi nisu isti - Tip konstante nije int"),
	S2_04("Semanticka greska [S2-04] na liniji replaceString : Tipovi nisu isti - Tip konstante nije char"),
	S2_05("Semanticka greska [S2-05] na liniji replaceString : Tipovi nisu isti - Tip konstante nije bool"),
	S2_06("Semanticka greska [S2-06] na liniji replaceString : Tipovi nisu isti - Tip konstante nije string"),
	S2_07("Semanticka greska [S2-07] na liniji replaceString : [R76] Ime konstante 'replaceString1' je vec definisano"),
	S2_08("Semanticka greska [S2-08] na liniji replaceString : Tip promenljive nije definisan"),
	S2_09("Semanticka greska [S2-09] na liniji replaceString - Ime promeljive 'replaceString1' je vec definisano"),
	S2_10("Semanticka greska [S2-10] na liniji replaceString : [R84] Ime klase je vec definisano"),

	S2_11("Semanticka greska [S2-11] na liniji replaceString - nadklasa nije definisana"),
	S2_12("Semanticka greska [S2-12] na liniji replaceString - Nadklasa nije tip i nije tipa klase"),
	S2_13("Semanticka greska [S2-13] na liniji replaceString : metoda je vec deklarisana"),
	S2_14("Semanticka greska [S2-14] na liniji replaceString : main funkcija ne sme imati lokalne i formalne argumente"),
	S2_15("Semanticka greska [S2-15] na liniji replaceString : main funkcija mora biti tipa void"),
	S2_16("Semanticka greska [S2-16] - nije nadjen return izraz za metodu 'replaceString'"),
	S2_17("Semanticka greska [S2-17] - nadjen return izraz za metodu 'replaceString' koja je void"),
	S2_18("Semanticka greska [S2-18] na liniji replaceString - apstraktna metoda deklarisana van apstraktne klase na liniji"),
	S2_19(
			"Semanticka greska [S2-19] na liniji replaceString : 'replaceString1' sa leve strane izraza dodele vrednosti mora biti promenljiva, elem niza ili polje klase"),
	S2_20("Semanticka greska [S2-20] na liniji replaceString : promenljive sa leve i desne strane izraza dodele nisu kompatibilni"),

	S2_21(
			"Semanticka greska [S2-21] na liniji replaceString : 'replaceString1' u increment/decrement izrazu mora biti promenljiva, elem niza ili polje klase"),
	S2_22("Semanticka greska [S2-22] na liniji replaceString : increment/decrement izraz mora biti tipa int"),
	S2_23("Semanticka greska [S2-23] na liniji replaceString : povratni tip metode se ne slaze sa tipom return izraza"),
	S2_24("Semanticka greska [S2-24] na liniji replaceString : break iskaz se poziva van while petlje"),
	S2_25("Semanticka greska [S2-25] na liniji replaceString : 'replaceString1' mora oznacavati metodu un.klase ili glob. metodu"),
	S2_26("Semanticka greska [S2-26] na liniji replaceString : broj stvarnih i formalnih parametara se ne slaze"),
	S2_27(
			"Semanticka greska [S2-27] na liniji replaceString : 'replaceString1' u read izrazu mora biti promenljiva, elem niza ili polje klase"),
	S2_28("Semanticka greska [S2-28] na liniji replaceString : izraz u read f-ji mora biti int,char,bool ili string"),
	S2_29("Semanticka greska [S2-29] na liniji replaceString : izraz u print f-ji mora biti int,char,bool ili string"),
	S2_30("Semanticka greska [S2-30] na liniji replaceString : pri uslovnim proverama tipovi nisu kompatibilni"),

	S2_31(
			"Semanticka greska [S2-31] na liniji replaceString : pri uslovnim proverama tipovi klase ili niza mogu koristiti samo != i == operatore"),
	S2_32("Semanticka greska [S2-32] na liniji replaceString : iskaz mora biti tipa int"),
	S2_33("Semanticka greska [S2-33] na liniji replaceString : addop izrazi nisu kompatibilni"),
	S2_34("Semanticka greska [S2-34] na liniji replaceString : izrazi pri operaciji + moraju biti tipa int ili string"),
	S2_35("Semanticka greska [S2-35] na liniji replaceString : izrazi pri operaciji -, - moraju biti tipa int"),
	S2_36("Semanticka greska [S2-36] na liniji replaceString : izrazi pri operacijama *, / i % moraju biti tipa int"),
	S2_37("Semanticka greska [S2-37] na liniji replaceString :[R104] kreiranje niza tipom apstraktne klase"),
	S2_38("Semanticka greska [S2-38] na liniji replaceString : broj elemenata niza mora biti tipa int"),
	S2_39("Semanticka greska [S2-39] na liniji replaceString :[R104] kreiranje objekta tipom apstraktne klase"),
	S2_40("Semanticka greska [S2-40] na liniji replaceString : unut. klasa mora biti koriscenja za new iskaz"),

	S2_41("Semanticka greska [S2-41] na liniji replaceString : 'this' ne sme se koristiti u globalnom opsegu"),
	S2_42("Semanticka greska [S2-42] na liniji replaceString : 'replaceString1' nije deklarisano"),
	S2_43("Semanticka greska [S2-43] na liniji replaceString : index niza mora biti tipa int"),
	S2_44("Semanticka greska [S2-44] na liniji replaceString : nadjeni element mora biti tipa niz"),
	S2_45("Semanticka greska [S2-45] na liniji replaceString :  'replaceString1' mora biti unutrasnja klasa"),
	S2_46("Semanticka greska [S2-46] na liniji replaceString : 'replaceString1' nije deklarisano unutar klase"),
	S2_47(
			"Semanticka greska [S2-47] na liniji replaceString : stvarni parametar na poziciji replaceString1 nije kompatibilan pri dodeli sa formalnim parametrom metode"),
	S2_48("Semanticka greska [S2-48] na liniji replaceString : pri uslovnim proverama, izraz nije tipa bool");

	private final String errorCode;

	private SemanticErrorDesc(final String errorCode) {

		this.errorCode = errorCode;
	}

	@Override
	public String toString() {

		return errorCode;
	}

	public String print() {

		return toString();
	}

	public String print(String text) {

		return errorCode.replaceFirst("replaceString", text);
	}

	public String print(int line) {

		return errorCode.replaceFirst("replaceString", String.valueOf(line));
	}

	public String print(int line, int counter) {

		return print(line).replaceFirst("replaceString1", String.valueOf(counter));
	}

	public String print(int line, String text) {

		return print(line).replaceFirst("replaceString1", text);
	}
}
