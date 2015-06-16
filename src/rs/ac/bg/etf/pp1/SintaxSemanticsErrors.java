package rs.ac.bg.etf.pp1;

/**
 * Globalni enum za opis svih gresaka, sintaksnih i semanickih. Cilj je da imamo opise gresaka na jednom mestu.
 * 
 * @author Aleksandar Grkajac
 *
 */
public enum SintaxSemanticsErrors {
	
	// Sintaksne greske
	S1_00("Sintaksna greska [1S-00] na liniji replaceString : prilikom definicije glob. konstante, izvrsen oporavak do ; "),
	S1_01("Sintaksna greska [1S-01] na liniji replaceString : prilikom definicije glob. konstante, izvrsen oporavak do , "),
	S1_02("Sintaksna greska [1S-02] na liniji replaceString : prilikom definicije glob. prom., izvrsen oporavak do ;"),
	S1_03("Sintaksna greska [1S-03] na liniji replaceString : prilikom definicije glob. prom., izvrsen oporavak do ,"),
	S1_04("Sintaksna greska [1S-04] na liniji replaceString : prilikom prosirenja nadklase, izvrsen oporavak do {"),
	S1_05("Sintaksna greska [1S-05] na liniji replaceString : prilikom definicije atributa klase, izvrsen oporavak do }"),
	S1_06("Sintaksna greska [1S-06] na liniji replaceString : prilikom definicije atributa klase, izvrsen oporavak do ;"),
	S1_07("Sintaksna greska [1S-07] na liniji replaceString : prilikom definicije lokalnih prom. metode, izvrsen oporavak do ;"),
	S1_08("Sintaksna greska [1S-08] na liniji replaceString : prilikom deklaracije lokalnih prom., izvrsen oporavak do {"),
	S1_09("Sintaksna greska [1S-09] na liniji replaceString : prilikom deklaracije formalnih prom, izvrsen oporavak do ,"),
	S1_10("Sintaksna greska [1S-10] na liniji replaceString : prilikom deklaracije formalnih prom, izvrsen oporavak do )"),
	S1_11("Sintaksna greska [1S-11] na liniji replaceString : logickog izraza unutar if ili while konstrukcije, izvrsen oporavak do prvog znaka )"),
	S1_12("Sintaksna greska [1S-12] na liniji replaceString : prilikom dodele vrednosti, izvrsen oporavak do ;"),
	S1_13("Sintaksna greska [1S-13] na liniji replaceString : prilikom poziva funkcije, izvrsen oporavak do ;"),
	S1_14("Sintaksna greska [1S-14] na liniji replaceString : u listi parametara pri pozivu f-je, izvrsen oporavak do )"),
	S1_15("Sintaksna greska [1S-15] na liniji replaceString : izraza za index. niza, izvrsen oporavak do ]"),
	S2_00("");

	
	
	private final String errorCode;

	/**
	 * @param text
	 */
	private SintaxSemanticsErrors(final String errorCode) {

		this.errorCode = errorCode;
	}

	@Override
	public String toString() {

		return errorCode;
	}

	public String printError(int line) {

		return errorCode.replaceFirst("replaceString", String.valueOf(line));
	}
}
