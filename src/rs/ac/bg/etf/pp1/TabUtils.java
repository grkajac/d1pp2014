package rs.ac.bg.etf.pp1;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandar Grkajac ga040202d@student.etf.rs, aleksa888@gmail.com
 * Date: 5/1/14
 * Time: 11:35 PM
 * To change this template use File | Settings | File Templates.
 */

import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.symboltable.factory.SymbolTableFactory;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;

import java.util.*;

public class TabUtils {

    public final static Struct boolType = new Struct(Struct.Bool);
    public final static Struct stringType = new Struct(Struct.Array, new Struct(Struct.Char));

    public static boolean hasMainFunc = false;

    public static boolean isSingleName = false;

    public static boolean conditionError = false;

    public static Obj currentMethodObj = Tab.noObj;
    public static Obj currentClassObj = Tab.noObj;
    public static Struct currentVarType = Tab.noType;

    public static boolean returnFound = false;
    public static boolean isVoid = false;
    public static boolean isMethodExist = false;

    // Broj formalnih argumenata
    public static int formalParamCount = 0;

    // Redni broj formalnog parametra u metodi
    public static int formalParamPosition = 0;

    // Broj stvarnih parametara
    public static ArrayList<Struct> actualParams = new ArrayList<Struct>();
    public static int actualParamCount = 0;

    public static Obj currentDesignatorObj = Tab.noObj;

    public static boolean inWhile = false;

    // abstract classes
    public static Map<String, Obj> abstractClasses = new HashMap<String, Obj>();
    public static Map<String, String> abstractMethods = new HashMap<String, String>();
//    public static Map<String, Obj> extendedClasses = new HashMap<String, Obj>();
    public static String currentTypeNameUses;
    public static boolean hasAbstractMethod = false;

    public static void resetAll() {

          hasMainFunc = false;

          isSingleName = false;

          conditionError = false;

          currentMethodObj = Tab.noObj;
          currentClassObj = Tab.noObj;
          currentVarType = Tab.noType;

          returnFound = false;
          isVoid = false;
          isMethodExist = false;

        // Broj formalnih argumenata
          formalParamCount = 0;

        // Redni broj formalnog parametra u metodi
          formalParamPosition = 0;

        // Broj stvarnih parametara
          actualParams = new ArrayList<Struct>();
          actualParamCount = 0;

          currentDesignatorObj = Tab.noObj;

          inWhile = false;

        // abstract classes
          abstractClasses = new HashMap<String, Obj>();
          abstractMethods = new HashMap<String, String>();
    //    extendedClasses = new HashMap<String, Obj>();
          currentTypeNameUses = null;
          hasAbstractMethod = false;
    }

    public static boolean isAbstractClass(){

        return abstractClasses.containsKey(currentTypeNameUses);
    }

    public static String printObj(Obj obj){

        DumpSymbolTableVisitor visitor = new DumpSymbolTableVisitor();
        visitor.visitObjNode(obj);

        return visitor.getOutput();
    }

    public static void resetMethodsFlags(){
        isVoid = false;
        returnFound = false;
        formalParamCount = 0;
        formalParamPosition = 0;
        currentMethodObj = Tab.noObj;
        isMethodExist = false;
    }

    /**
     *  Pored osnovne funkcionalnosti, treba da se proverava i deo
     *  "Ako je dst referenca na osnovnu klasu, a src referenca na izvedenu klasu"
     */
    public static boolean assignableTo(Struct dest, Struct src){
        boolean result = src.assignableTo(dest)
                ||
                (dest.getKind() == Struct.Array && dest.getElemType().getKind() == Struct.Char && src.getElemType() == stringType)
                ||
                (equalsClassRefs(dest, src));

        return result;
    }

    /**
     * Poredi da li je dest dodeljiv src po tipu klasa,
     * dest osnovna, src izvedene klase iz osnovne.
     */
    public static boolean equalsClassRefs(Struct dest, Struct src) {

        boolean result = false;

        Obj srcSuperClassObj = src.getMembersTable().searchKey("super");

        while (srcSuperClassObj != null) {

            if(dest.equals(srcSuperClassObj.getType())){
                result = true;
            }

            srcSuperClassObj = srcSuperClassObj.getType().getMembersTable().searchKey("super");
        }

        return result;
    }

    /**
     * Metoda trazi polje u klasi ili klasama roditelja za
     * klasu koja je u procesu definicije.
     * @param fieldName
     * @return
     */
    public static Obj findVarFromClassMethod(String fieldName) {

        // trazi u lokalnom opsegu
        Obj finded = findInCurrentScope(fieldName);

        if(Tab.noObj.equals(finded)) {

            // ako nema u lokalno opsegu, trazi u klasi koja se trenutno
            // obradjuje i njenoj nadklasi
            finded =  findInCurrentClassAndSuperClasses(fieldName);

        } else {

            return finded;

        }

        // ukoliko je nema ni tamo, trazi u globalnom opsegu
        return (Tab.noObj.equals(finded)) ?  Tab.find(fieldName) : finded;
    }

    /**
     * Podrazumeva se da smo u klasom opsegu.
     * Nalazi Obj u klasi koja je u procesu definicije.
     *
     * @param fieldName ime polja koje se trazi.
     * @return
     */
    public static Obj findInCurrentClassAndSuperClasses(String fieldName) {

        Obj findedObj = Tab.currentScope.getOuter().findSymbol(fieldName);

        Obj superClassObj = Tab.currentScope.getOuter().findSymbol("super");

        superClassObj = (superClassObj == null) ? Tab.noObj : superClassObj;

        // Onda se trazi u roditeljskim klasama rekurzivno
        return (findedObj == null) ? findInSuperClasses(fieldName, superClassObj) : findedObj;
    }

    /**
     * Nalazi Obj u nadklasama za zadati objekat.
     *
     * @param fieldName ime polja.
     * @param classObj obj klase za koju se trazi polje u nadklasama.
     * @return
     */
    public static Obj findInSuperClasses(String fieldName, Obj classObj) {

        Obj findedObj = null;

        String currentClassName;
        Obj currentSuperClassObj = classObj;//getSuperClass(classObj.getName());

        while(!Tab.noObj.equals(currentSuperClassObj)) {

            findedObj = currentSuperClassObj.getType().getMembersTable().searchKey(fieldName);

            if(findedObj != null) {
                break;
            }

            currentSuperClassObj = getSuperClass(currentSuperClassObj);
        }

        return (findedObj == null) ? Tab.noObj : findedObj;
    }

    /**
     * Metoda trazi polje u klasi ili klasama roditelja za klasu koja je vec definisana.
     * @param fieldName
     * @return
     */
    public static Obj findFieldInClassAndSuperClasses(String fieldName, Obj currClass) {

        // Pretraga u klasi koja je data kao argument metode
        Obj findedObj = currClass.getType().getMembersTable().searchKey(fieldName);

        Obj superClassObj = currClass.getType().getMembersTable().searchKey("super");

        superClassObj = (superClassObj == null) ? Tab.noObj : superClassObj;

        // Onda se trazi u roditeljskim klasama rekurzivno
        return (findedObj == null) ? findInSuperClasses(fieldName, superClassObj) : findedObj;
    }

    /**
     * Nalazi Obj u trenutnom opsegu.
     *
     * @param fieldName ime polja koje se trazi.
     * @return
     */
    public static Obj findInCurrentScope(String fieldName) {

        Obj findedObj = Tab.currentScope.findSymbol(fieldName);

        return (findedObj == null) ? Tab.noObj : findedObj;
    }

    /**
     * Nalazi Obj roditeljske klase za izvedenu ako postoji,
     * ako ne postoji vraca Tab.noObj
     *
     * @param classObj ime osnovne klase.
     * @return
     */
    public static Obj getSuperClass(Obj classObj) {

        Obj findedObj = classObj.getType().getMembersTable().searchKey("super");//extendedClasses.get(className);

        if(findedObj == null) {
            findedObj = Tab.noObj;
        }

        return findedObj;
    }

    /**
     * Postoje klase koje su:
     *
     *  1. Definisane
     *  2. Koje su u procesu definicije.
     *
     *  1. Kod definisanih klasa trazenje polja se svodi na trazenje polja u njenom opsegu i
     *     opsegu njenih nadklase koje su u extendedClasses mapi.
     *
     *  2. Kod klasa koje su u procesu definicije, polje koje trazimo nije prebaceno u opseg klase
     *     (to se radi na kraju obrade , Tab.chainLocalSymbols(classObj.getType())) vec treba traziti po opsezima.
     *     Trenutnom opsegu, opsegu iznad, opsegu nadklasa i na kraju globalnom opsegu.
     */
}
