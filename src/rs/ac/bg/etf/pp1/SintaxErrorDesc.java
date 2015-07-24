package rs.ac.bg.etf.pp1;

/**
 * Globalni enum za opis svih sintaksnih gresaka.
 * 
 * @author Aleksandar Grkajac
 *
 */
public enum SintaxErrorDesc {

	S1_00("Sintaksna greska [S1-00] na liniji replaceString : prilikom definicije glob. konstante, izvrsen oporavak do ; "),
	S1_01("Sintaksna greska [S1-01] na liniji replaceString : prilikom definicije glob. konstante, izvrsen oporavak do , "),
	S1_02("Sintaksna greska [S1-02] na liniji replaceString : prilikom definicije glob. prom., izvrsen oporavak do ;"),
	S1_03("Sintaksna greska [S1-03] na liniji replaceString : prilikom definicije glob. prom., izvrsen oporavak do ,"),
	S1_04("Sintaksna greska [S1-04] na liniji replaceString : prilikom prosirenja nadklase, izvrsen oporavak do {"),
	S1_05("Sintaksna greska [S1-05] na liniji replaceString : prilikom definicije atributa klase, izvrsen oporavak do }"),
	S1_06("Sintaksna greska [S1-06] na liniji replaceString : prilikom definicije atributa klase, izvrsen oporavak do ;"),
	S1_07("Sintaksna greska [S1-07] na liniji replaceString : prilikom definicije lokalnih prom. metode, izvrsen oporavak do ;"),
	S1_08("Sintaksna greska [S1-08] na liniji replaceString : prilikom deklaracije lokalnih prom., izvrsen oporavak do {"),
	S1_09("Sintaksna greska [S1-09] na liniji replaceString : prilikom deklaracije formalnih prom, izvrsen oporavak do ,"),
	S1_10("Sintaksna greska [S1-10] na liniji replaceString : prilikom deklaracije formalnih prom, izvrsen oporavak do )"),
	S1_11(
			"Sintaksna greska [S1-11] na liniji replaceString : logickog izraza unutar if ili while konstrukcije, izvrsen oporavak do prvog znaka )"),
	S1_12("Sintaksna greska [S1-12] na liniji replaceString : prilikom dodele vrednosti, izvrsen oporavak do ;"),
	S1_13("Sintaksna greska [S1-13] na liniji replaceString : prilikom poziva funkcije, izvrsen oporavak do ;"),
	S1_14("Sintaksna greska [S1-14] na liniji replaceString : u listi parametara pri pozivu f-je, izvrsen oporavak do )"),
	S1_15("Sintaksna greska [S1-15] na liniji replaceString : izraza za index. niza, izvrsen oporavak do ]");

	private final String errorCode;

	private SintaxErrorDesc(final String errorCode) {

		this.errorCode = errorCode;
	}

	@Override
	public String toString() {

		return errorCode;
	}

	public String print(int line) {

		return errorCode.replaceFirst("replaceString", String.valueOf(line));
	}

}
