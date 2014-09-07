package rs.ac.bg.etf.pp1;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * Created with IntelliJ IDEA.
 * User: Aleksandar Grkajac ga040202d@student.etf.rs, aleksa888@gmail.com
 * Date: 1/21/14
 * Time: 11:06 PM
 * To change this template use File | Settings | File Templates.
 * visit
 */
@RunWith(Parameterized.class)
public class GrammarAnalysisTest {

    private static List<TestInfo> testsConfiguration;

    private static Map<String, TestInfo> actualTestsResults;

    private String testName;

    private String testIndex;

    // For this feature JUnit 4.11 > is needed
    @Parameters(name = "testing {0}")
    public static Collection<String[]> data() {

        readTestConfigurations();

        readActualTestResults();

        return getData();
    }

    private static Collection<String[]> getData() {

        int testCount = testsConfiguration.size();

        String[][] data = new String[testCount][2];

        for (int i = 0; i < testCount; i++) {
            for (int j = 0; j < 2; j++) {
                if (j == 0) {

                    data[i][j] = testsConfiguration.get(i).getName();
                } else {

                    data[i][j] = String.valueOf(i);
                }
            }
        }

        return Arrays.asList(data);
    }

    public GrammarAnalysisTest(String testName, String testIndex) {

        this.testName = testName;

        this.testIndex = testIndex;
    }

    protected static void readTestConfigurations() {

        ObjectMapper mapper = new ObjectMapper();

        try {

            // read from file, and convert to the user object
            testsConfiguration = mapper.readValue(new File("testConfig.json"), new TypeReference<List<TestInfo>>() {
            });

        } catch (JsonGenerationException | JsonMappingException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

    protected static void readActualTestResults() {

        actualTestsResults = ActualTestResults.getResults();
    }


    protected void isEqual(TestInfo actualTest, TestInfo expectedTest) {


        assertEquals("Imena se ne slazu!",
                expectedTest.getName(),
                actualTest.getName());

        isEqual(actualTest.getBrojanje(), expectedTest.getBrojanje());

        isEqual(actualTest.getSyntaxErrorList(), expectedTest.getSyntaxErrorList(), "Sintaksna");

        isEqual(actualTest.getSemanticErrorList(), expectedTest.getSemanticErrorList(), "Semanticka");
    }

    protected void isEqual(Brojanje actual, Brojanje expected) {

        assertEquals("Broj deklaracija globalnih promenljivih prostog tipa = ",
                expected.getGlob_promenljivih(),
                actual.getGlob_promenljivih());

        assertEquals("Broj deklaracija globalnih nizova = ",
                expected.getGlob_nizova(),
                actual.getGlob_nizova());

        assertEquals("Broj definicija funkcija u glavnom programu = ",
                expected.getFunkcija(),
                actual.getFunkcija());

        assertEquals("Broj definicija unutrašnjih klasa = ",
                expected.getUn_klasa(),
                actual.getUn_klasa());

        assertEquals("Broj blokova naredbi = ",
                expected.getBlokova_naredbi(),
                actual.getBlokova_naredbi());

        assertEquals("Broj poziva funkcija u telu metode main = ",
                expected.getPoziva_funkcija(),
                actual.getPoziva_funkcija());

        assertEquals("Broj naredbi instanciranja objekata = ",
                expected.getInstanciranja(),
                actual.getInstanciranja());

        assertEquals("Broj definicija metoda unutrašnjih klasa = ",
                expected.getMetoda(),
                actual.getMetoda());

        assertEquals("Broj deklaracija polja unutrašnjih klasa = ",
                expected.getAtributa(),
                actual.getAtributa());

        assertEquals("Broj izvodjenja klasa = ",
                expected.getIzvodjenja(),
                actual.getIzvodjenja());
    }

    private void isEqual(List<GrammarError> actualSyntaxErrorList, List<GrammarError> expectedSyntaxErrorList, String message) {

        if (actualSyntaxErrorList.size() != expectedSyntaxErrorList.size()) {

            assertEquals("Ne slaze se broj gresaka! " + message, expectedSyntaxErrorList.size(), actualSyntaxErrorList.size());

            return;
        }

        Iterator<GrammarError> expectedIterator = expectedSyntaxErrorList.iterator();
        Iterator<GrammarError> actualIterator = actualSyntaxErrorList.iterator();

        GrammarError actualGrammarError = null;
        GrammarError expectedGrammarError = null;
        String errorMessage = null;

        while (actualIterator.hasNext() && expectedIterator.hasNext()) {

            actualGrammarError = actualIterator.next();

            expectedGrammarError = expectedIterator.next();

            errorMessage = message + " greska nije ista sa ocekivanom! [ "
                    + actualGrammarError.getDescription() + " != " + expectedGrammarError.getDescription() + ", "
                    + actualGrammarError.getLine() + " != " + expectedGrammarError.getLine() + " ]";

            assertTrue(errorMessage, actualGrammarError.isEqual(expectedGrammarError));
        }
    }


    @Test
    public void testGrammar() {

        TestInfo expectedTest = testsConfiguration.get(Integer.valueOf(testIndex));

        TestInfo actualTest = actualTestsResults.get(testName);

        if (actualTest == null) {

            fail("Nije nadjen aktuelni test sa imenom " + expectedTest.getName());

        } else {

            isEqual(actualTest, expectedTest);
        }
    }
}
