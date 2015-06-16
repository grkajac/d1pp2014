package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;

/**
 * Class generates actual results of tests.
 *
 * User: Aleksandar Grkajac ga040202d@student.etf.rs, aleksa888@gmail.com Date: 7/23/14 Time: 2:42 PM
 */
public class ActualTestResults {

	public static final String TEST_PATH = "test/kojini_testovi";
	public static final String CURRENT_PARSED_TEST_PATH = "logs/testResult.log";
	public static final String PARSED_TESTS_SINGLE_LOG_PATH = "logs-test";
	public static final String GENERATED_CODE_TEST_PATH = "logs-generatedcode";

	private static List<File> testFiles = new ArrayList<>();
	private static Map<String, TestResult> results = new HashMap<>();

	private static TestResult currentTest;
	private static Pattern errorTagPattern = Pattern.compile("\\[(\\w{2}-\\d{2})\\]( na liniji (\\d+))?|(test\\d{2,3}\\.mj)");

	static {

		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());

		Log4JUtils.instance().prepareLogTestResultFile(Logger.getRootLogger());
	}

	public static Map<String, TestResult> getResults() {

		readTestFiles(new File(TEST_PATH));

		parseResults();

		return results;
	}

	private static void readTestFiles(final File folder) {

		for (final File fileEntry : folder.listFiles()) {

			if (!fileEntry.isDirectory()) {

				testFiles.add(fileEntry);
			}
		}
	}

	/**
	 * Create results that are parsed from actual test files.
	 *
	 * For example, we have written test, (test01.mj).
	 *
	 * That test is loaded and analized by compiler. Compiler output is stored in result file. That result file is analyzed, via regex, if
	 * it contains any syntax, semantic or counting error.
	 */
	private static void parseResults() {

		// Clearing logs for all tests, each test contains single log for itself.
		clearAllTestSingleLog(new File(PARSED_TESTS_SINGLE_LOG_PATH));

		// Clearing same thing for test that is used for generating code.
		clearAllTestSingleLog(new File(GENERATED_CODE_TEST_PATH));

		for (File testFile : testFiles) {

			clearLogFile(new File(CURRENT_PARSED_TEST_PATH));

			startTest(testFile);

			parseCurrentTest();
		}

	}

	/**
	 * Clears log file where compiler output is stored.
	 * 
	 * @param logFile
	 */
	private static void clearLogFile(final File logFile) {

		PrintWriter writer = null;

		try {

			writer = new PrintWriter(logFile);
			writer.print("");

		} catch (FileNotFoundException e) {

			System.out.println("Nije nadjen fajl prilikom ciscenja konteksta!!!");
			e.printStackTrace();

		} finally {

			writer.close();
		}
	}

	private static void clearAllTestSingleLog(final File testLogsFolder) {

		for (final File fileEntry : testLogsFolder.listFiles()) {

			if (!fileEntry.isDirectory()) {

				clearLogFile(fileEntry);
			}
		}
	}

	@SuppressWarnings("unused")
	private static void startTest(File testFile) {

		Reader br = null;

		Logger log = Logger.getLogger(ActualTestResults.class);

		currentTest = new TestResult();

		try {

			log.info("\n======================================================\n");
			log.info("Compiling source file: " + testFile.getAbsolutePath());

			br = new BufferedReader(new FileReader(testFile));
			Yylex lexer = new Yylex(br);

			TabUtils.resetAll();

			MJParser p = new MJParser(lexer);
			Symbol s = p.parse(); // pocetak parsiranja

			// Level II

			Brojanje testCounts = currentTest.getBrojanje();

			log.info("Broj deklaracija globalnih promenljivih prostog tipa = " + p.globalPrimitiveVarsCount);
			testCounts.setGlob_promenljivih(p.globalPrimitiveVarsCount);

			log.info("Broj deklaracija globalnih nizova = " + p.globalArraysCount);
			testCounts.setGlob_nizova(p.globalArraysCount);

			log.info("Broj definicija funkcija u glavnom programu = " + p.globalFunctionsCount);
			testCounts.setFunkcija(p.globalFunctionsCount);

			log.info("Broj definicija unutrašnjih klasa = " + p.innerClassesCount);
			testCounts.setUn_klasa(p.innerClassesCount);

			log.info("Broj blokova naredbi = " + p.statementsCount);
			testCounts.setBlokova_naredbi(p.statementsCount);

			log.info("Broj poziva funkcija u telu metode main = " + p.functionCallsInMainCount);
			testCounts.setPoziva_funkcija(p.functionCallsInMainCount);

			log.info("Broj naredbi instanciranja objekata = " + p.objectCreationCount);
			testCounts.setInstanciranja(p.objectCreationCount);

			log.info("Broj definicija metoda unutrašnjih klasa = " + p.innerClassMethodsCount);
			testCounts.setMetoda(p.innerClassMethodsCount);

			log.info("Broj deklaracija polja unutrašnjih klasa = " + p.innerClassAttributesCount);
			testCounts.setAtributa(p.innerClassAttributesCount);

			log.info("Broj izvodjenja klasa = " + p.extendingClassesCount);
			testCounts.setIzvodjenja(p.extendingClassesCount);

			// Level III

//            Tab.dump();

			if (!p.errorDetected) {

				log.info("Parsiranje USPESNO zavrseno :)");

				if (testFile.getName().matches("test\\d{3}\\.mj")) {

					File codeGenFile = new File(GENERATED_CODE_TEST_PATH + "/" + testFile.getName() + ".obj");

					Code.write(new FileOutputStream(codeGenFile));
				}

			} else {

				log.error("Parsiranje ima GRESKE :(");
			}
			log.info("\n======================================================\n");

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			if (br != null) {

				try {

					br.close();

				} catch (IOException e1) {

					System.out.println("GrammarError u testu");
				}
			}
		}

	}

	/**
	 * Parse processed test result from file.
	 *
	 * Each line from file is analized and if line contains error then error is putted into specific list based on error type.
	 *
	 * For example if line contains syntax error it goes to syntax error list, same thing is for semantic and counting errors.
	 */
	private static void parseCurrentTest() {

		boolean isParsingOk = true;
		Scanner scanner = null;

		try {

			scanner = new Scanner(new File(CURRENT_PARSED_TEST_PATH));

			String line;
			Matcher matcher = null;

			while (scanner.hasNextLine()) {

				line = scanner.nextLine();

				matcher = errorTagPattern.matcher(line);

				if (matcher.find()) {

					setTestName(matcher.group(4));

					addGrammarError(line, matcher.group(1), matcher.group(3));
				}
			}

			Files.copy(new File(CURRENT_PARSED_TEST_PATH).toPath(), new File(PARSED_TESTS_SINGLE_LOG_PATH + "/" + currentTest.getName()
					+ ".log").toPath(), StandardCopyOption.REPLACE_EXISTING);

		} catch (Exception e) {

			e.printStackTrace();
			isParsingOk = false;

		} finally {

			if (scanner != null) {

				try {

					scanner.close();

				} catch (Exception e1) {

					System.out.println("GrammarError u citanju " + CURRENT_PARSED_TEST_PATH);
				}
			}
		}

		if (isParsingOk) {

			results.put(currentTest.getName(), currentTest);
		}
	}

	/**
	 * Sets name for current test.
	 *
	 * @param testName
	 */
	private static void setTestName(String testName) {

		if (testName != null) {

			currentTest.setName(testName);
		}
	}

	/**
	 * Adds grammar error if pattern is matched for processed line.
	 *
	 * @param line
	 * @param errorDescription
	 * @param errorLine
	 */
	private static void addGrammarError(String line, String errorDescription, String errorLine) {

		Integer errLine = (errorLine != null) ? Integer.valueOf(errorLine) : 0;

		if (line.contains("Sintaksna greska")) {

			currentTest.addSyntaxError(new GrammarError(errorDescription, errLine));
		}

		if (line.contains("Semanticka greska")) {

			currentTest.addSemanticError(new GrammarError(errorDescription, errLine));
		}
	}

	public static void main(String[] args) {

		// for testing purpose
		getResults();
	}
}
