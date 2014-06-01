package rs.ac.bg.etf.pp1;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandar Grkajac ga040202d@student.etf.rs, aleksa888@gmail.com
 * Date: 5/1/14
 * Time: 11:35 PM
 * To change this template use File | Settings | File Templates.
 */

import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;

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

    public static Obj currentDesignatorObj = Tab.noObj;

    public static boolean inWhile = false;

    public static void insertParam(){

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
    }
}
