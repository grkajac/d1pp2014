package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{
    // Buffer za stringove
    StringBuffer stBuffer = new StringBuffer();

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}

%cup
%line
%column

%xstate COMMENT
%xstate STRING

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }

"break"     {return new_symbol(sym.BREAK, yytext());}
"class"     {return new_symbol(sym.CLASS, yytext());}
"else"      {return new_symbol(sym.ELSE, yytext());}
"final"     {return new_symbol(sym.FINAL, yytext());}
"if"        {return new_symbol(sym.IF, yytext());}
"new"       {return new_symbol(sym.NEW, yytext());}
"print"     {return new_symbol(sym.PRINT, yytext());}
"read"      {return new_symbol(sym.READ, yytext());}
"return"    {return new_symbol(sym.RETURN, yytext());}
"void" 	    {return new_symbol(sym.VOID, yytext());}
"while"     {return new_symbol(sym.WHILE, yytext());}
"extends"   {return new_symbol(sym.EXTENDS, yytext());}

"program"   { return new_symbol(sym.PROG, yytext()); }
"const"     { return new_symbol(sym.CONST, yytext()); }


"+"	{return new_symbol(sym.PLUS, yytext());}
"-"	{return new_symbol(sym.MINUS, yytext());}
"*"	{return new_symbol(sym.TIMES, yytext());}
"/"	{return new_symbol(sym.DIV, yytext());}
"%"	{return new_symbol(sym.MOD, yytext());}

"=="    {return new_symbol(sym.ISEQUAL, yytext());}
"!="    {return new_symbol(sym.ISNOTEQUAL, yytext());}
">"	    {return new_symbol(sym.ISGREATER, yytext());}
">="	{return new_symbol(sym.ISGREATEREQ, yytext());}
"<"	    {return new_symbol(sym.ISLESS, yytext());}
"<="	{return new_symbol(sym.ISLESSEQ, yytext());}

"="	    {return new_symbol(sym.EQUAL, yytext());}
"++"    {return new_symbol(sym.INCREMENT, yytext());}
"--"	{return new_symbol(sym.DECREMENT, yytext()); }


";"	{return new_symbol(sym.SEMI, yytext());}
","	{return new_symbol(sym.COMMA, yytext());}
"."	{return new_symbol(sym.DOT, yytext());}
"("	{return new_symbol(sym.LPAREN, yytext());}
")"	{return new_symbol(sym.RPAREN, yytext());}
"["	{return new_symbol(sym.LSQUARE, yytext());}
"]"	{return new_symbol(sym.RSQUARE, yytext());}
"{"	{return new_symbol(sym.LBRACE, yytext());}
"}"	{return new_symbol(sym.RBRACE, yytext());}

"//" 		     { yybegin(COMMENT); }
<COMMENT> .      { yybegin(COMMENT); }
<COMMENT> "\r\n" { yybegin(YYINITIAL); }


\" { stBuffer.setLength(0); yybegin(STRING); }
<STRING> {
    \" {    yybegin(YYINITIAL);
            return new_symbol(sym.STRING_LITERAL, stBuffer.toString());
       }
    [^\n\r\"\\]+ { stBuffer.append( yytext() ); }
    \\t          { stBuffer.append('\t'); }
    \\n          { stBuffer.append('\n'); }

    \\r          { stBuffer.append('\r'); }
    \\\"         { stBuffer.append('\"'); }
    \\           { stBuffer.append('\\'); }
}

[0-9]+  { return new_symbol(sym.NUMBER, new Integer (yytext())); }
(true | false)  { return new_symbol(sym.BOOLVAL, new Boolean (yytext())); }
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{return new_symbol (sym.IDENT, yytext()); }
"'"[\040-\176]"'" {return new_symbol (sym.CHARCONST, new Character (yytext().charAt(1)));}
. { System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1)); }






