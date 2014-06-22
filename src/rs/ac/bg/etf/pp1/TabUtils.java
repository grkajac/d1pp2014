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
    public static Struct currentMethodType = Tab.noType;
    public static Struct returnType = Tab.noType;

    public static boolean returnFound = false;
    public static boolean isVoid = false;

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
    public static Map<String, String> extendedClasses = new HashMap<String, String>();
    public static String currentTypeNameUses;
    public static boolean hasAbstractMethod = false;

    public static void insertParam(){

    }

    public static boolean isAbstractClass(){

        return abstractClasses.containsKey(currentTypeNameUses);
    }

    public static String printObj(Obj obj){

        DumpSymbolTableVisitorWithBool visitor = new DumpSymbolTableVisitorWithBool();
        visitor.visitObjNode(obj);

        return visitor.getOutput();
    }

    public static void resetMethodsFlags(){
        isVoid = false;
        returnFound = false;
        formalParamCount = 0;
        formalParamPosition = 0;
        currentMethodObj = Tab.noObj;
    }

    /**
     *  Pored osnovne funkcionalnosti, treba da se proverava i deo
     *  "Ako je dst referenca na osnovnu klasu, a src referenca na izvedenu klasu"
     */
    public static boolean assignableTo(Struct dest, Struct src){
        boolean result = src.assignableTo(dest)
                ||
                (equalsCompleteHash(dest.getMembersTable(), src.getMembersTable()))
                ||
                (dest.getKind() == Struct.Array && dest.getElemType().getKind() == Struct.Char && src.getElemType() == stringType);

        return result;
    }

    /**
     * Poredi da li je src hes tabela sadrzi sve elemente iz dest hes tabele.
     * Metoda je korisna kada proveravamo da li izvedena klasa sadrzi sve clanove iz osnovne klase.
     */
    public static boolean equalsCompleteHash(SymbolDataStructure dest, SymbolDataStructure src) {

        if (dest == null || src == null)
            return false;

        if (dest.numSymbols() < src.numSymbols()) {
            Collection<Obj> destObj = dest.symbols(), srcObj = src.symbols();
            Iterator<Obj> itdest = destObj.iterator(), itsrc = srcObj.iterator();

            while (itdest.hasNext()) {
                if (!itdest.next().equals(itsrc.next()))
                    return false;
            }
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Postavlja clanove osnovne klase na pocetak niza clanova izvedene klase
     *
     * @param extended   Obj izvedene klase
     * @param base       Obj osnovne klase
     */
    public static void copyBaseClassMembersToExtendedClass(Obj extended, Obj base){

            // setLocals je namerno implementirano, zato sto na pocetku svi ovi elementi
            // treba da se nadju u trenutnom opsegu da bi se omogucila pretraga trenutnog opsega koji
            // je u ovom slucaju klasa

            // kada se zavrsi sa obradom osnovne klase, svi lokalni elementi ce se prebaciti u
            // Struct.memeber-e


            extended.setLocals(base.getType().getMembersTable());
    }

    /**
     * Potrebno uraditi
     *
     * 1. Poredjenje clanova osnovne i super klase, tako sto treba proveriti da li
     *    osnovna klasa sadrzi sve clanove super klase.
     *
     * 2. Presipanje clanova super klase u osnovnu klasu
     */
}
