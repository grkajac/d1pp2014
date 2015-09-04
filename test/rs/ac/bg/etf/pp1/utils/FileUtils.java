package rs.ac.bg.etf.pp1.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

public class FileUtils {

	public static Properties CONFIG;

	static {
		initConfigPropertie();
	}

	/**
	 * Inicijalizuje propertie na osnovu operativnog sistema.
	 */
	private static void initConfigPropertie() {

		CONFIG = new Properties();

		try {
			final String OS = System.getProperty("os.name").toLowerCase();

			String configFilePath = (OS.indexOf("ux") >= 0) ? "config" + File.separator + "config.unix.properties"
					: (OS.indexOf("win") >= 0) ? "config" + File.separator + "config.win.properties" : "null";

			CONFIG.load(new FileReader(new File(configFilePath)));

		} catch (FileNotFoundException e) {

			e.printStackTrace();
			System.exit(1);

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * @return {@link File} fajl sa log4j konfiguracijom.
	 */
	public static File getLog4JConfigFile() {

		return new File(CONFIG.getProperty("log4jConfig"));
	}

	/**
	 * @return {@link File} folder gde su smesteni mikrojava programi koji se testiraju.
	 */
	public static File getTestFolder() {

		return new File(CONFIG.getProperty("tests"));
	}

	/**
	 * Vraca fajl mikrojava programa koji se testira.
	 * 
	 * @param {@link String} _testFile - ime fajla
	 * @return {@link File}
	 * @throws IOException ako fajl ne postoji.
	 */
	public static File getTestFile(String _testFile) throws IOException {

		File testFile = new File(CONFIG.getProperty("tests") + File.separator + _testFile);

		if (!testFile.exists()) {
			throw new IOException("Nije nadjen test: " + _testFile);
		}

		return testFile;
	}

	/**
	 * @return {@link File} fajl u kome se nalazi trenutni obradjeni(parsirani) mikrojava program
	 */
	public static File getCurrentParsedTestFile() {

		return new File(CONFIG.getProperty("current_parsed_test"));
	}

	/**
	 * @return {@link File} folder gde su smesteni obradjeni(parsirani) mikrojava programi.
	 */
	public static File getParsedTestFolder() {

		return new File(CONFIG.getProperty("parsed_test_logs"));
	}

	public static File getParsedTestFile(String testFile) throws IOException {

		File parsedTestFile = new File(CONFIG.getProperty("parsed_test_logs") + File.separator + testFile);

		if (!parsedTestFile.exists()) {
			parsedTestFile.createNewFile();
		}

		return parsedTestFile;
	}

	/**
	 * @return {@link File} folder gde su smesteni mikrojava programi kojima je generisan kod.
	 */
	public static File getGeneratedCodeFolder() {

		return new File(CONFIG.getProperty("generated_code"));
	}

	/**
	 * Kreira fajl u kome ce se smestiti generisan kod mikrojava programa.
	 * 
	 * @param {@link String} testFile - ime fajla
	 * @return {@link File}
	 */
	public static File createGeneratedCodeFile(String testFile) throws IOException {

		return new File(CONFIG.getProperty("generated_code") + File.separator + testFile);
	}

	/**
	 * Vraca fajl sa generisanim kodom za zadati mikrojava program.
	 * 
	 * @param {@link String} testFile - ime fajla
	 * @return {@link File}
	 * @throws IOException ako fajl ne postoji.
	 */
	public static File getGeneratedCodeFile(String testFile) throws IOException {

		File generatedTestFile = new File(CONFIG.getProperty("generated_code") + File.separator + testFile);

		if (!generatedTestFile.exists()) {
			throw new IOException("Nije nadjen fajl sa generisanim java mikro kodom");
		}

		return generatedTestFile;
	}

	/**
	 * @return {@link File} u kome se nalazi konfiguracija za ocekivane rezultate JUnit testa.
	 */
	public static File getExpectedTestResultConfigFile() {

		return new File(CONFIG.getProperty("expected_test_results_file_path"));
	}

	/**
	 * Kopira sadrzaj trenutno parsiranog mikrojava programa u odvojeni fajl.
	 * 
	 * @param currentTestLog
	 * @throws IOException
	 */
	public static void copyTempLogToCurrentTestLog(String currentTestLog) throws IOException {

		Path tempTestLogPath = getCurrentParsedTestFile().toPath();
		Path currentTestLogPath = getParsedTestFile(currentTestLog).toPath();

		Files.copy(tempTestLogPath, currentTestLogPath, StandardCopyOption.REPLACE_EXISTING);
	}
}
