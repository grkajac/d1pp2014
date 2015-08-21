package rs.ac.bg.etf.pp1.util;

import rs.etf.pp1.mj.runtime.Code;

public class CodeUtils {

	public static void resetCodeData() {

		Code.buf = new byte[8192]; // prostor za smestanje prevedenog programskog koda
		Code.pc = 0; // tekuca adresa za smestanje prevedene instrukcije
		Code.mainPc = -1; // adresa main rutine
		Code.dataSize = 0; // velicina oblasti globalnih podataka
		Code.greska = false; // flag da li je prijavljena neka greske
	}
}
