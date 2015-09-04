package rs.ac.bg.etf.pp1.util;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.structure.HashTableDataStructure;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;
import rs.etf.pp1.symboltable.visitors.SymbolTableVisitor;

/**
 * Created with IntelliJ IDEA. 
 * User: Aleksandar Grkajac ga040202d@student.etf.rs, aleksa888@gmail.com 
 * Date: 5/1/14 Time: 11:35 PM
 */

public class TabUtils {

	public final static Struct boolType = new Struct(Struct.Bool);
	public final static Struct stringType = new Struct(Struct.Array, new Struct(Struct.Char));

	public static boolean hasMainFunc = false;

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

	public static boolean inWhile = false;

	// public static boolean hasAbstractMethod = false;

	public static void resetAll() {

		resetClassFlags();

		resetMethodsFlags();

		hasMainFunc = false;

		conditionError = false;

		currentVarType = Tab.noType;

		// Broj stvarnih parametara
		actualParams = new ArrayList<Struct>();
		actualParamCount = 0;

		inWhile = false;

		// hasAbstractMethod = false;

	}

	public static void resetClassFlags() {

		currentClassObj = Tab.noObj;
	}

	public static void resetMethodsFlags() {

		isVoid = false;
		returnFound = false;
		formalParamCount = 0;
		formalParamPosition = 0;
		currentMethodObj = Tab.noObj;
		isMethodExist = false;
	}

	public static String printObj(Obj obj) {

		DumpSymbolTableVisitor visitor = new DumpSymbolTableVisitor();
		visitor.visitObjNode(obj);

		return visitor.getOutput();
	}

	/**
	 * TODO Pored osnovne funkcionalnosti, treba da se proverava i deo
	 * "Ako je dst referenca na osnovnu klasu, a src referenca na izvedenu klasu"
	 */
	public static boolean assignableTo(Struct dest, Struct src) {

		boolean result = src.assignableTo(dest)
				|| (dest.getKind() == Struct.Array && dest.getElemType().getKind() == Struct.Char && src.getElemType() == stringType)
				|| (equalsClassRefs(dest, src));

		return result;
	}

	/**
	 * Poredi da li je dest dodeljiv src po tipu klasa, dest osnovna, src izvedene klase iz osnovne. Metoda prolazi kroz listu dest clanova
	 * osnovne klase i gleda da li postoje u src listi izvedenih clanovima.
	 */
	public static boolean equalsClassRefs(Struct dest, Struct src) {

		if (dest.getKind() != Struct.Class || src.getKind() != Struct.Class) {
			return false;
		}

		Collection<Obj> destMembers = dest.getMembers();
		SymbolDataStructure srcMembers = src.getMembersTable();

		if (srcMembers.numSymbols() < destMembers.size()) {
			return false;
		}

		for (Obj obj : destMembers) {
			if (srcMembers.searchKey(obj.getName()) == null) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Trazi lokalnu promenljivu sa zadatim imenom u metodi.
	 * 
	 * @param {@link Obj} method - metoda u kojoj se trazi promenljiva (lokalna ili parametar)
	 * @param varName - ime promenljive
	 * @return vraca trazenu promenljivu, u suprotnom vraca Tab.noObj.
	 */
	public static Obj findVarInMethod(Obj method, String varName) {

		if (method == null || Tab.noObj.equals(method) || varName == null || varName == "") {
			return Tab.noObj;
		}

		for (Obj obj : method.getLocalSymbols()) {
			if (obj.getName().equals(varName)) {
				return obj;
			}
		}
		return Tab.noObj;
	}

	/**
	 * Trazi Obj atributa ili metode za zadati Obj klase.
	 * 
	 * @param {@link Obj} classObj - obj klase u kojoj se trazi clan klase
	 * @param memberName - ime clana
	 * @return vraca {@link Obj} clan ako ga nadje, u suprotnom vraca Tab.noObj.
	 */
	public static Obj findMemberInClass(Obj classObj, String memberName) {

		if (classObj.getType().getKind() != Struct.Class) {
			return Tab.noObj;
		}

		Collection<Obj> members = classObj.getType().getMembers();

		for (Obj member : members) {
			if (member.getName().equals(memberName)) {
				return member;
			}
		}

		return Tab.noObj;
	}

	private static boolean parentClassExists() {

		if (Tab.noObj.equals(currentClassObj)) {
			return false;
		}

		if (currentClassObj.getType() == null) {
			return false;
		}

		if (currentClassObj.getType().getElemType() == null) {
			return false;
		}

		if (currentClassObj.getType().getElemType().getMembers() == null) {
			return false;
		}

		return true;
	}

	public static Collection<Obj> getParentMembers() {

		if (!parentClassExists()) {
			return new ArrayList<Obj>();
		}

		return currentClassObj.getType().getElemType().getMembers();
	}

	/**
	 * Proverava da li ime metode postoji u nadklasi.
	 * 
	 * @param methodName
	 * @return true ako postoji, u suprotnom vraca false.
	 */
	public static boolean checkIfMethodNameExistsInParentClass(String methodName) {

		if (!parentClassExists()) {
			return false;
		}

		Collection<Obj> parentClassMembers = getParentMembers();

		for (Obj member : parentClassMembers) {
			if (Obj.Meth == member.getKind() && member.getName().equals(methodName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Kopira clanove (atribute ili metode) roditeljske klase u trenutni otvoreni opseg.
	 */
	public static void copyParentClassMembers(int kind) {

		if (!parentClassExists()) {
			return;
		}

		Collection<Obj> parentClassMembers = getParentMembers();

		for (Obj member : parentClassMembers) {
			if (kind == member.getKind()) {
				Tab.currentScope().addToLocals(member);
			}
		}
	}

	/**
	 * Metoda prolazi kroz sve metode klase i popravlja referencu na osnovnu klasu za prvi argument metoda "this".
	 */
	public static void repairThisParameter() {

		Collection<Obj> classMembers = Tab.currentScope().values();
		Struct repairedType = currentClassObj.getType();

		SymbolDataStructure currentLocals = null;

		for (Obj methodObj : classMembers) {

			if (Obj.Meth == methodObj.getKind()) {

				// za svaku metodu, koju obradjujemo, kreiramo novu listu za parametre metode, locale lista.
				currentLocals = new HashTableDataStructure();

				// za svaki argument metode, ako je this onda ga popravljamo i smestamo u listu, ako nije onda ga samo smestimo u listu.
				for (Obj parametar : methodObj.getLocalSymbols()) {
					if (parametar.getName().equals("this")) {

						Obj repairedThis = new Obj(parametar.getKind(), parametar.getName(), repairedType, parametar.getAdr(),
								parametar.getLevel());
						repairedThis.setFpPos(parametar.getFpPos());
						currentLocals.insertKey(repairedThis);
					} else {
						currentLocals.insertKey(parametar);
					}
				}
				// setujemo novu listu, prepravljenu listu parametara za metodu.
				methodObj.setLocals(currentLocals);
			}
		}
	}

	public static void dump(Logger log) {

		log.info("\n\n=====================SYMBOL TABLE DUMP=========================\n");

		SymbolTableVisitor stv = new DumpSymbolTableVisitor();

		for (Scope s = Tab.currentScope; s != null; s = s.getOuter()) {
			s.accept(stv);
		}

		log.info(stv.getOutput());
	}
}
