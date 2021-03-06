package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;
import org.apache.log4j.Logger;
import rs.etf.pp1.symboltable.Tab;

import java.io.*;
import java.util.Scanner;

/**
* Created with IntelliJ IDEA.
* User: Aleksandar Grkajac ga040202d@student.etf.rs  , aleksa888@gmail.com
* Date: 1/21/14
* Time: 8:27 PM
* To change this template use File | Settings | File Templates.
*/
public class SingleTesting implements Testing {

    @Override
    public void testing(Logger log) {

        Reader br = null;
        try {

            System.out.println("Example 01 ");
            System.out.println("Example: 01 09 ");
            System.out.println("Example all ");
            System.out.print("Unesi broj test/testova:  ");

            boolean all = false;
            String[] numberOfFile = null;
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();

            if(input.contains("all")){
                all = true;
            }else{
                numberOfFile = input.split(" ");
            }

            if(all){
                Testing test = new CompleteTesting();
                test.testing(log);
            }else {
                for(int i = 0; i < numberOfFile.length; i++){

                    File sourceCode = new File("test/kojini_testovi/test" + numberOfFile[i] + ".mj");

                    log.info("\n======================================================\n");
                    log.info("Compiling source file: " + sourceCode.getAbsolutePath());

                    br = new BufferedReader(new FileReader(sourceCode));
                    Yylex lexer = new Yylex(br);

                    MJParser p = new MJParser(lexer);
                    Symbol s = p.parse();  //pocetak parsiranja

                    // Level II
                    log.info("Broj deklaracija globalnih promenljivih prostog tipa = " + p.globalPrimitiveVarsCount);
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

                    Tab.dump();

                    if (!p.errorDetected) {
                        log.info("Parsiranje USPESNO zavrseno :)");
                    }
                    else {
                        log.error("Parsiranje ima GRESKE :(");
                    }
                    log.info("\n==================== " + sourceCode.getAbsolutePath() + " ==================================");
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
        }
    }

}
