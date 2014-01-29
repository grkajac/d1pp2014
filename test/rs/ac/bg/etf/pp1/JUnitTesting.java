package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * Created with IntelliJ IDEA.
 * User: Aleksandar Grkajac ga040202d@student.etf.rs
 * Date: 1/21/14
 * Time: 11:06 PM
 * To change this template use File | Settings | File Templates.
 * visit http://examples.javacodegeeks.com/core-java/xml/java-xml-parser-tutorial/
 */
public class JUnitTesting {

    private Reader br;
    private File sourceCode;

    private Yylex lexer;
    private MJParser parser;

    private static File[] testFiles = new File[10];
    private static List<TestInfo> testsConfiguration;

    @BeforeClass
    public static void setUp() throws ParserConfigurationException, SAXException, IOException {

        // init testFiles
        for(int i = 0; i <= 9; i++){
            if(i == 9) {
              testFiles[i] = new File("test/kojini_testovi/test10.mj");
            } else {
                testFiles[i] = new File("test/kojini_testovi/test0" + (i+1) +".mj");
            }
        }

        // init testsConfiguration
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(new File("testConfig.xml"));

        testsConfiguration = new ArrayList<TestInfo>();
        NodeList nodeList = document.getDocumentElement().getChildNodes();

        String name;
        int globalNonRefVarCount;
        int globalArrayCount;
        int funcDefInProgramCount;
        int innerClassDefCount;
        int blocksCount;
        int funcCallsInMainCount;
        int instantiationCallsCount;
        int innerClassMethodDefCount;
        int innerClassAttrDefCount;
        int innerClassExtendsCount;


        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element elem = (Element) node;

                name = node.getAttributes().getNamedItem("name").getNodeValue();

                globalNonRefVarCount = Integer.parseInt(
                        elem.getElementsByTagName("deklaracija_globalnih_promenljivih_prostog_tipa")
                                .item(0).getChildNodes().item(0).getNodeValue().trim()
                );
                globalArrayCount = Integer.parseInt(
                        elem.getElementsByTagName("deklaracija_globalnih_nizova")
                                .item(0).getChildNodes().item(0).getNodeValue().trim()
                );
                funcDefInProgramCount = Integer.parseInt(
                        elem.getElementsByTagName("definicija_funkcija_u_glavnom_programu")
                                .item(0).getChildNodes().item(0).getNodeValue().trim()
                );
                innerClassDefCount = Integer.parseInt(
                        elem.getElementsByTagName("definicija_unutrašnjih_klasa")
                                .item(0).getChildNodes().item(0).getNodeValue().trim()
                );
                blocksCount = Integer.parseInt(
                        elem.getElementsByTagName("blokova_naredbi")
                                .item(0).getChildNodes().item(0).getNodeValue().trim()
                );
                funcCallsInMainCount = Integer.parseInt(
                        elem.getElementsByTagName("poziva_funkcija_u_telu_metode_main")
                                .item(0).getChildNodes().item(0).getNodeValue().trim()
                );
                instantiationCallsCount = Integer.parseInt(
                        elem.getElementsByTagName("naredbi_instanciranja_objekata")
                                .item(0).getChildNodes().item(0).getNodeValue().trim()
                );
                innerClassMethodDefCount = Integer.parseInt(
                        elem.getElementsByTagName("definicija_metoda_unutrašnjih_klasa")
                                .item(0).getChildNodes().item(0).getNodeValue().trim()
                );
                innerClassAttrDefCount = Integer.parseInt(
                        elem.getElementsByTagName("deklaracija_polja_unutrašnjih_klasa")
                                .item(0).getChildNodes().item(0).getNodeValue().trim()
                );
                innerClassExtendsCount = Integer.parseInt(
                        elem.getElementsByTagName("izvodjenja_klasa")
                                .item(0).getChildNodes().item(0).getNodeValue().trim()
                );

                testsConfiguration.add(new TestInfo(
                        name,
                        globalNonRefVarCount,
                        globalArrayCount,
                        funcDefInProgramCount,
                        innerClassDefCount,
                        blocksCount,
                        funcCallsInMainCount,
                        instantiationCallsCount,
                        innerClassMethodDefCount,
                        innerClassAttrDefCount,
                        innerClassExtendsCount)
                );
            }
        }

    }

    protected MJParser setEnviroment(int testNumber) {
        sourceCode = testFiles[testNumber];

        try {
            br = new BufferedReader(new FileReader(sourceCode));
            lexer = new Yylex(br);
            parser = new MJParser(lexer);

            Symbol s = parser.parse();  //pocetak parsiranja

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (br != null) try { br.close(); } catch (IOException e1) { System.out.println("Greska u testu"); }
        }

        return parser;
    }

    protected void testing (MJParser parser, int testNumber){
        TestInfo expected = testsConfiguration.get(testNumber);

        assertEquals("Test broj: " + (testNumber + 1) + " | Broj deklaracija globalnih promenljivih prostog tipa = ",
                expected.getDeklaracija_globalnih_promenljivih_prostog_tipa() ,
                parser.globalPrimitiveVarsCount);

        assertEquals("Test broj: " + (testNumber + 1) + " | Broj deklaracija globalnih nizova =  ",
                expected.getDeklaracija_globalnih_nizova() ,
                parser.globalArraysCount);

        assertEquals("Test broj: " + (testNumber + 1) + " | Broj definicija funkcija u glavnom programu =  ",
                expected.getDefinicija_funkcija_u_glavnom_programu() ,
                parser.globalFunctionsCount);

        assertEquals("Test broj: " + (testNumber + 1) + " | Broj definicija unutrašnjih klasa = ",
                expected.getDefinicija_unutrašnjih_klasa() ,
                parser.innerClassesCount);

        assertEquals("Test broj: " + (testNumber + 1) + " | Broj blokova naredbi = ",
                expected.getBlokova_naredbi() ,
                parser.statementsCount);

        assertEquals("Test broj: " + (testNumber + 1) + " | Broj poziva funkcija u telu metode main = ",
                expected.getPoziva_funkcija_u_telu_metode_main() ,
                parser.functionCallsInMainCount);

        assertEquals("Test broj: " + (testNumber + 1) + " | Broj naredbi instanciranja objekata = ",
                expected.getNaredbi_instanciranja_objekata() ,
                parser.objectCreationCount);

        assertEquals("Test broj: " + (testNumber + 1) + " | Broj definicija metoda unutrašnjih klasa = ",
                expected.getDefinicija_metoda_unutrašnjih_klasa() ,
                parser.innerClassMethodsCount);

        assertEquals("Test broj: " + (testNumber + 1) + " | Broj deklaracija polja unutrašnjih klasa = ",
                expected.getDeklaracija_polja_unutrašnjih_klasa() ,
                parser.innerClassAttributesCount);

        assertEquals("Test broj: " + (testNumber + 1) + " | Broj izvodjenja klasa = ",
                expected.getIzvodjenja_klasa() ,
                parser.extendingClassesCount);

//        assertFalse("Test isn't passed", parser.errorDetected);
    }

    @Test
    public void test1() throws Exception {
        this.testing(this.setEnviroment(0), 0);
    }

    @Test
    public void test2() throws Exception {
        this.testing(this.setEnviroment(1), 1);
    }

    @Test
    public void test3() throws Exception {
        this.testing(this.setEnviroment(2), 2);
    }
    @Test
    public void test4() throws Exception {
        this.testing(this.setEnviroment(3), 3);
    }

    @Test
    public void test5() throws Exception {
        this.testing(this.setEnviroment(4), 4);
    }
    @Test
    public void test6() throws Exception {
        this.testing(this.setEnviroment(5), 5);
    }
    @Test
    public void test7() throws Exception {
        this.testing(this.setEnviroment(6), 6);
    }
    @Test
    public void test8() throws Exception {
        this.testing(this.setEnviroment(7), 7);
    }
    @Test
    public void test9() throws Exception {
        this.testing(this.setEnviroment(8), 8);
    }
    @Test
    public void test10() throws Exception {
        this.testing(this.setEnviroment(9), 9);
    }

    @org.junit.After
    public void tearDown() throws Exception {
        br = null;
        sourceCode = null;
        lexer = null;
        parser = null;
    }
}
