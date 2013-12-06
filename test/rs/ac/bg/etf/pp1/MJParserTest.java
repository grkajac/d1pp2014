package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.symboltable.Tab;

import java.io.*;

public class MJParserTest {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}

	public static void main(String[] args) throws Exception {

		Logger log = Logger.getLogger(MJParserTest.class);

		Reader br = null;
		try {
			File sourceCode = new File("test/programOK.mj");
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());

			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);

			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse();  //pocetak parsiranja

			// Level II
	        log.info("Broj deklaracija globalnih promenljivih prostog tipa = " + p.globalPrimitiveVarsCount);
	        log.info("Broj deklaracija globalnih nizova = " + p.globalArraysCount);
	        log.info("Broj definicija funkcija u glavnom programu = " + p.functionsInMainCount);
	        log.info("Broj definicija unutrašnjih klasa = " + p.innerClassesCount);
	        log.info("Broj blokova naredbi = " + p.statementsCount);
	        log.info("Broj poziva funkcija u telu metode main = " + p.functionCallsInMainCount);
	        log.info("Broj naredbi instanciranja objekata = " + p.objectCreationCount);
	        log.info("Broj definicija metoda unutrašnjih klasa = " + p.innerClassMethodsCount);
	        log.info("Broj deklaracija polja unutrašnjih klasa = " + p.innerClassAttributesCount);
	        log.info("Broj izvodjenja klasa = " + p.extendingClassesCount);

			// Level III

	        // Tab.dump();

	        if (!p.errorDetected) {
	        	log.info("Parsiranje USPESNO zavrseno :)");
	        }
	        else {
	        	log.error("Parsiranje ima GRESKE :(");
	        }

		}
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}

	}


}
