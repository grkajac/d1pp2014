package rs.ac.bg.etf.pp1.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;

import rs.ac.bg.etf.pp1.dto.Counting;
import rs.ac.bg.etf.pp1.dto.GrammarError;
import rs.ac.bg.etf.pp1.dto.TestResult;

public class TestComparingUtils {

	public static void isEqual(TestResult actualTest, TestResult expectedTest) {

		assertEquals("Imena se ne slazu!", expectedTest.getName(), actualTest.getName());

		isEqual(actualTest.getCounting(), expectedTest.getCounting());

		isEqual(actualTest.getSyntaxErrorList(), expectedTest.getSyntaxErrorList(), "Sintaksna");

		isEqual(actualTest.getSemanticErrorList(), expectedTest.getSemanticErrorList(), "Semanticka");
	}

	public static void isEqual(Counting actual, Counting expected) {

		assertEquals("Broj deklaracija globalnih promenljivih znakovnog tipa = ", expected.getGlob_promenljivih(),
				actual.getGlob_promenljivih());

		assertEquals("Broj deklaracija globalnih nizova = ", expected.getGlob_nizova(), actual.getGlob_nizova());

		assertEquals("Broj definicija funkcija u glavnom programu = ", expected.getFunkcija(), actual.getFunkcija());

		assertEquals("Broj definicija unutrašnjih klasa = ", expected.getUn_klasa(), actual.getUn_klasa());

		assertEquals("Broj blokova naredbi = ", expected.getBlokova_naredbi(), actual.getBlokova_naredbi());

		assertEquals("Broj poziva funkcija u telu metode main = ", expected.getPoziva_funkcija(), actual.getPoziva_funkcija());

		assertEquals("Broj naredbi instanciranja objekata = ", expected.getInstanciranja(), actual.getInstanciranja());

		assertEquals("Broj definicija metoda unutrašnjih klasa = ", expected.getMetoda(), actual.getMetoda());

		assertEquals("Broj deklaracija polja unutrašnjih klasa = ", expected.getAtributa(), actual.getAtributa());

		assertEquals("Broj izvodjenja klasa = ", expected.getIzvodjenja(), actual.getIzvodjenja());
	}

	public static void isEqual(List<GrammarError> actualSyntaxErrorList, List<GrammarError> expectedSyntaxErrorList, String message) {

		if (actualSyntaxErrorList.size() != expectedSyntaxErrorList.size()) {

			assertEquals("Ne slaze se broj gresaka! " + message, expectedSyntaxErrorList.size(), actualSyntaxErrorList.size());

			return;
		}
		
		Iterator<GrammarError> expectedIterator = expectedSyntaxErrorList.iterator();
		Iterator<GrammarError> actualIterator = actualSyntaxErrorList.iterator();

		while (actualIterator.hasNext() && expectedIterator.hasNext()) {

			GrammarError actualGrammarError = actualIterator.next();
			GrammarError expectedGrammarError = expectedIterator.next();

			String errorMessage = message + " greska nije ista sa ocekivanom! [ " + actualGrammarError.getDescription() + " != "
					+ expectedGrammarError.getDescription() + ", " + actualGrammarError.getLine() + " != " + expectedGrammarError.getLine()
					+ " ]";

			assertTrue(errorMessage, actualGrammarError.isEqual(expectedGrammarError));
		}
	}
}
