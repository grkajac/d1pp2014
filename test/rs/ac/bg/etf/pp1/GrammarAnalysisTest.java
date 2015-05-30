package rs.ac.bg.etf.pp1;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


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

	/**
	 * Naziv fajla u kome su rezultati testova koje ocekujemo da su ispravni. Sadrzi listu parametara za svaki test u json formatu.
	 */
	private static final String EXPECTED_TEST_RESULTS_FILE_PATH = "testConfig.json";

	/**
	 * Lista ocekivanih rezultata testova.
	 */
	private static List<TestResult> expectedTestResultList;

	/**
	 * Lista stvarnih rezultata testova.
	 */
	private static Map<String, TestResult> actualTestsResults;

    private String singleTestName;

    private String singleTestIndex;

	public GrammarAnalysisTest(String testName, String testIndex) {

		this.singleTestName = testName;
		this.singleTestIndex = testIndex;
	}

    // For this feature JUnit 4.11 > is needed
    @Parameters(name = "testing {0}")
    public static Collection<String[]> data() {

        readExpectedTestResults();

        readActualTestResults();

        return getData();
    }

	// formatter:off
	/**
	 * Metoda puni objekat {@link #expectedTestResultList} sa podacima iz testConfig.json fajla. 
	 * U fajlu testConfig.json se nalazi lista ocekivanih rezultata za svaki pokrenuti test.
	 */
	// formatter:on
    protected static void readExpectedTestResults() {

        ObjectMapper mapper = new ObjectMapper();

        try {

            // read from file, and convert to the user object
			expectedTestResultList = mapper.readValue(new File(EXPECTED_TEST_RESULTS_FILE_PATH), new TypeReference<List<TestResult>>() {
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

	private static Collection<String[]> getData() {

		int testCount = expectedTestResultList.size();

		String[][] data = new String[testCount][2];

		for (int i = 0; i < testCount; i++) {
			for (int j = 0; j < 2; j++) {
				if (j == 0) {

					data[i][j] = expectedTestResultList.get(i).getName();
				} else {

					data[i][j] = String.valueOf(i);
				}
			}
		}
		return Arrays.asList(data);
	}

    @Test
    public void testGrammar() {

		TestResult expectedTest = expectedTestResultList.get(Integer.valueOf(singleTestIndex));

		TestResult actualTest = actualTestsResults.get(singleTestName);

        if (actualTest == null) {

            fail("Nije nadjen aktuelni test sa imenom " + expectedTest.getName());

        } else {

			TestComparingUtils.isEqual(actualTest, expectedTest);
        }
    }
}
