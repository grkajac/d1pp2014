package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.ac.bg.etf.pp1.util.TabUtils;
import rs.ac.bg.etf.pp1.utils.FileUtils;
import rs.etf.pp1.mj.runtime.Code;

public class MJParserTest {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}

	public static void main(String[] args) throws Exception {

		Logger log = Logger.getLogger(MJParserTest.class);

		Reader br = null;
		try {

			System.out.println("Example 01 ");
			System.out.println("Example: 01 09 ");
			System.out.print("Unesi broj test(ova)? :  ");

			String input = new Scanner(System.in).nextLine();
			String[] numberOfFile = input.split(" ");

			for (int i = 0; i < numberOfFile.length; i++) {

				File sourceCode = new File("test/kojini_testovi/test" + numberOfFile[i] + ".mj");

				log.info("\n======================================================\n");
				log.info("Compiling source file: " + sourceCode.getAbsolutePath());

				br = new BufferedReader(new FileReader(sourceCode));
				Yylex lexer = new Yylex(br);

				MJParser p = new MJParser(lexer);
				@SuppressWarnings("unused")
				Symbol s = p.parse(); // pocetak parsiranja

				// Level II
				log.info("\n\n===================== COUNTING =========================\n");

				log.info("Broj deklaracija globalnih promenljivih znakovnog tipa = " + p.globalCharVarCount);
				log.info("Broj deklaracija globalnih nizova = " + p.globalArraysCount);
				log.info("Broj definicija funkcija u glavnom programu = " + p.globalFunctionsCount);
				log.info("Broj definicija unutrašnjih klasa = " + p.innerClassesCount);
				log.info("Broj blokova naredbi = " + p.statementsCount);
				log.info("Broj poziva funkcija u telu metode main = " + p.functionCallsInMainCount);
				log.info("Broj naredbi instanciranja objekata = " + p.objectCreationCount);
				log.info("Broj definicija metoda unutrašnjih klasa = " + p.innerClassMethodsCount);
				log.info("Broj deklaracija polja unutrašnjih klasa = " + p.innerClassAttributesCount);
				log.info("Broj izvodjenja klasa = " + p.extendingClassesCount);

				// Level III
				TabUtils.dump(log);

				if (!p.errorDetected) {
					log.info("Parsiranje USPESNO zavrseno :)");
				} else {
					log.error("Parsiranje ima GRESKE :(");
				}

				if (sourceCode.getName().matches("test\\d{3}\\.mj") && !Code.greska) {

					File codeGenFile = FileUtils.createGeneratedCodeFile(sourceCode.getName() + ".obj");

					Code.write(new FileOutputStream(codeGenFile));

					log.info("Generisanje .mj.obj fajla USPESNO zavrseno :)");
				}

				if (Code.greska) {
					log.info("Generisanje .mj.obj fajla NEUSPESNO zavrseno :(");
				}

				log.info("\n==================== " + sourceCode.getAbsolutePath() + " ==================================");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e1) {
					log.error(e1.getMessage(), e1);
				}
		}
	}
}
