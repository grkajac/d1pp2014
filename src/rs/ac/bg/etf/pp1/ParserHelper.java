package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;

import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class ParserHelper {

	// counting declarations vars
	private int globalPrimitiveVarsCount = 0;
	private int globalArraysCount = 0;

	// counting definitions vars
	private int globalFunctionsCount = 0;
	private int innerClassesCount = 0;

	//
	private int statementsCount = 0;
	private int functionCallsInMainCount = 0;
	private int objectCreationCount = 0;

	// inner classes vars
	private int innerClassAttributesCount = 0;
	private int innerClassMethodsCount = 0;
	private int extendingClassesCount = 0;

	public java_cup.runtime.lr_parser parser;
	public Logger log;

	// error detection var
	boolean errorDetected = false;


	public ParserHelper(MJParser mjParser, Logger logger) {

		parser = mjParser;
		log = logger;
	}

	// GETTERS
	public int getGlobalPrimitiveVarsCount() {

		return globalPrimitiveVarsCount;
	}

	public int getGlobalArraysCount() {

		return globalArraysCount;
	}

	public int getGlobalFunctionsCount() {

		return globalFunctionsCount;
	}

	public int getInnerClassesCount() {

		return innerClassesCount;
	}

	public int getStatementsCount() {

		return statementsCount;
	}

	public int getFunctionCallsInMainCount() {

		return functionCallsInMainCount;
	}

	public int getObjectCreationCount() {

		return objectCreationCount;
	}

	public int getInnerClassAttributesCount() {

		return innerClassAttributesCount;
	}

	public int getInnerClassMethodsCount() {

		return innerClassMethodsCount;
	}

	public int getExtendingClassesCount() {

		return extendingClassesCount;
	}

	// slede redefinisani metodi za prijavu gresaka radi izmene teksta poruke

	public void report_fatal_error(String message, Object info) throws java.lang.Exception {

		parser.done_parsing();
		report_error(message, info);
	}

	public void syntax_error(Symbol cur_token) {

		// report_error("\nSintaksna greska", cur_token);
	}

	public void unrecovered_syntax_error(Symbol cur_token) throws java.lang.Exception {

		report_fatal_error("Fatalna greska, parsiranje se ne moze nastaviti", cur_token);
	}

	public void report_error(String message, Object info) {

		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		if (info instanceof Symbol)
			msg.append(" na liniji ").append(((Symbol) info).left);
		log.error(msg.toString());
	}

	public void report_info(String message, Object info) {

		StringBuilder msg = new StringBuilder(message);
		if (info instanceof Symbol)
			msg.append(" na liniji ").append(((Symbol) info).left);
		log.info(msg.toString());
	}

	/*
	 * util functions
	 */

	public Obj constantProcessing(String id, int idleft, Struct type, ConstNode node) {

		Obj konstanta = Tab.find(id);

		if (!Tab.noObj.equals(konstanta)) {
			report_error("Semanticka greska [2S-07] na liniji " + idleft + " : [R76] Ime konstante '" + konstanta.getName()
					+ "' je vec definisano", null);
			return konstanta;
		}

		switch (node) {
			case NUMBER:

				if (!Tab.intType.equals(type)) {
					report_error("Semanticka greska [2S-03] na liniji " + idleft + " : Tipovi nisu isti - Tip konstante nije int", null);
					break;
				}

				konstanta = Tab.insert(Obj.Con, id, type);
				report_info("Nadjena konstanta na liniji " + idleft + " | " + TabUtils.printObj(konstanta), null);
				break;

			case CHAR:

				if (Tab.charType.equals(type)) {

					konstanta = Tab.insert(Obj.Con, id, type);
					report_info("Nadjena konstanta na liniji " + idleft + " | " + TabUtils.printObj(konstanta), null);
				} else {
					report_error("Semanticka greska [2S-04] na liniji " + idleft + " : Tipovi nisu isti - Tip konstante nije char", null);
				}
				break;

			case BOOL:

				if (TabUtils.boolType.equals(type)) {

					konstanta = Tab.insert(Obj.Con, id, type);
					report_info("Nadjena konstanta na liniji " + idleft + " | " + TabUtils.printObj(konstanta), null);
				} else {
					report_error("Semanticka greska [2S-05] na liniji " + idleft + " : Tipovi nisu isti - Tip konstante nije bool", null);
				}
				break;

			case STRING:

				if (TabUtils.stringType.equals(type)) {

					konstanta = Tab.insert(Obj.Con, id, type);
					report_info("Nadjena konstanta na liniji " + idleft + " | " + TabUtils.printObj(konstanta), null);
				} else {
					report_error("Semanticka greska [2S-06] na liniji " + idleft + " : Tipovi nisu isti - Tip konstante nije string", null);
				}
				break;
		}

		return konstanta;
	}

	public void varProcessing(String id, int idleft, Struct type, VarNode node) {

		if (Tab.noType.equals(type)) {
			report_error("Semanticka greska [2S-08] na liniji " + idleft + " : Tip promenljive nije definisan", null);
			return;
		}

		Obj promenljiva = Tab.currentScope().findSymbol(id);

		if (promenljiva == null) {
			promenljiva = Tab.noObj;
		}

		if (Tab.noObj.equals(promenljiva)) {

			Obj prom;

			switch (node) {
				case GLOBAL_VAR:

					prom = Tab.insert(Obj.Var, id, type);

					globalPrimitiveVarsCount++;

					report_info("Nadjena globalna promenljiva na liniji " + idleft + " | " + TabUtils.printObj(prom), null);
					break;

				case CLASS_VAR:

					prom = Tab.insert(Obj.Fld, id, type);

					if (!"this".equals(id)) {

						innerClassAttributesCount++;

						report_info("Nadjeno polje unutrasnje klase na liniji " + idleft + " | " + TabUtils.printObj(prom), null);
					}
					break;

				case METHOD_LOCAL_VAR:

					prom = Tab.insert(Obj.Var, id, type);
					report_info("Nadjena lokalna promenljiva na liniji " + idleft + " | " + TabUtils.printObj(prom), null);
					break;

				case METHOD_FORMAL_VAR:

					prom = Tab.insert(Obj.Var, id, type);
					setFormalParam(prom);
					TabUtils.formalParamCount++;
					break;

				case GLOBAL_ARRAY_VAR:

					prom = Tab.insert(Obj.Var, id, new Struct(Struct.Array, type));
					globalArraysCount++;
					report_info("Nadjena globalna promenljiva na liniji " + idleft + " | " + TabUtils.printObj(prom), null);
					break;

				case CLASS_ARRAY_VAR:

					prom = Tab.insert(Obj.Fld, id, new Struct(Struct.Array, type));

					if (!"this".equals(id)) {

						innerClassAttributesCount++;

						report_info("Nadjeno polje unutrasnje klase na liniji " + idleft + " | " + TabUtils.printObj(prom), null);
					}
					break;

				case METHOD_LOCAL_ARRAY_VAR:

					prom = Tab.insert(Obj.Var, id, new Struct(Struct.Array, type));
					report_info("Nadjena lokalna promenljiva na liniji " + idleft + " | " + TabUtils.printObj(prom), null);
					break;

				case METHOD_FORMAL_ARRAY_VAR:

					prom = Tab.insert(Obj.Var, id, new Struct(Struct.Array, type));
					setFormalParam(prom);
					TabUtils.formalParamCount++;
					break;
			}
		} else {
			report_error("Semanticka greska [2S-09] na liniji " + idleft + " - Ime promeljive '" + promenljiva.getName()
					+ "' je vec definisano", null);
		}
	}

	public void setFormalParam(Obj formParam) {

		formParam.setFpPos(TabUtils.formalParamPosition++);
	}

	// counting declarations vars
	public void incGlobalPrimitiveVarsCount() {

		globalPrimitiveVarsCount++;
	};

	public void incGlobalArraysCount() {

		globalArraysCount++;
	}

	// counting definitions vars
	public void incGlobalFunctionsCount() {

		globalFunctionsCount++;
	}

	public void incInnerClassesCount() {

		innerClassesCount++;
	};

	public void incStatementsCount() {

		statementsCount++;
	}

	public void incFunctionCallsInMainCount() {

		functionCallsInMainCount++;
	}

	public void incObjectCreationCount() {

		objectCreationCount++;
	}

	public void incInnerClassAttributesCount() {

		innerClassAttributesCount++;
	}

	public void incInnerClassMethodsCount() {

		innerClassMethodsCount++;
	}

	public void incExtendingClassesCount() {

		extendingClassesCount++;
	};

}
