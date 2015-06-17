package rs.ac.bg.etf.pp1;

/**
 * Globalni enum za opis svih semantickih gresaka.
 * 
 * @author Aleksandar Grkajac
 *
 */
public enum SemanticErrorDesc {
	
	S2_00("Semanticka greska [2S-00] Nije pronadjena main funkcija"),
	S2_01("Semanticka greska [2S-01] Nije pronadjen tip 'replaceString' u tabeli simbola"),
	S2_02("Semanticka greska [2S-02] na liniji replaceString : Ime 'replaceString1' ne predstavlja tip"),
	S2_03("Semanticka greska [2S-03] na liniji replaceString : Tipovi nisu isti - Tip konstante nije int"),
	S2_04("Semanticka greska [2S-04] na liniji replaceString : Tipovi nisu isti - Tip konstante nije char"),
	S2_05("Semanticka greska [2S-05] na liniji replaceString : Tipovi nisu isti - Tip konstante nije bool"),
	S2_06("Semanticka greska [2S-06] na liniji replaceString : Tipovi nisu isti - Tip konstante nije string"),
	S2_07("Semanticka greska [2S-07] na liniji replaceString : [R76] Ime konstante 'replaceString1' je vec definisano"),
	S2_08("Semanticka greska [2S-08] na liniji replaceString : Tip promenljive nije definisan"),
	S2_09("Semanticka greska [2S-09] na liniji replaceString - Ime promeljive 'replaceString1' je vec definisano"),
	S2_10("Semanticka greska [2S-10] na liniji replaceString : [R84] Ime klase je vec definisano"),

	S2_11("Semanticka greska [2S-11] na liniji replaceString - nadklasa nije definisana"),
	S2_12("Semanticka greska [2S-12] na liniji replaceString - Nadklasa nije tip i nije tipa klase"),
	S2_13("Semanticka greska [2S-13] na liniji replaceString : metoda je vec deklarisana."),
	S2_14("Semanticka greska [2S-14] na liniji replaceString : main funkcija ne sme imati lokalne i formalne argumente."),
	S2_15("Semanticka greska [2S-15] na liniji replaceString : main funkcija mora biti tipa void."),
	S2_16("Semanticka greska [2S-16] - nije nadjen return izraz za metodu 'replaceString'."),
	S2_17("Semanticka greska [2S-17] - nadjen return izraz za metodu 'replaceString' koja je void."),
	S2_18("Semanticka greska [2S-18] na liniji replaceString - apstraktna metoda deklarisana van apstraktne klase na liniji."),
	S2_19(
			"Semanticka greska [2S-19] na liniji replaceString : 'replaceString1' sa leve strane izraza dodele vrednosti mora biti promenljiva, elem niza ili polje klase."),
	S2_20("Semanticka greska [2S-20] na liniji replaceString : promenljive sa leve i desne strane izraza dodele nisu kompatibilni.");

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

	public String print(int line, String text) {
		return print(line).replaceFirst("replaceString1", text);
	}
}
