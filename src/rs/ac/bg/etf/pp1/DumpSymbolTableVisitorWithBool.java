package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandar Grkajac ga040202d@student.etf.rs, aleksa888@gmail.com
 * Date: 6/22/14
 * Time: 8:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class DumpSymbolTableVisitorWithBool extends DumpSymbolTableVisitor {

    @Override
    public void visitStructNode(Struct structToVisit) {
        switch (structToVisit.getKind()) {
            case Struct.None:
                output.append("notype");
                break;
            case Struct.Int:
                output.append("int");
                break;
            case Struct.Char:
                output.append("char");
                break;
            case Struct.Array:
                output.append("Arr of ");

                switch (structToVisit.getElemType().getKind()) {
                    case Struct.None:
                        output.append("notype");
                        break;
                    case Struct.Int:
                        output.append("int");
                        break;
                    case Struct.Char:
                        output.append("char");
                        break;
                    case Struct.Class:
                        output.append("Class");
                        break;
                }
                break;
            case Struct.Class:
                output.append("Class [");
                for (Obj obj : structToVisit.getMembers()) {
                    obj.accept(this);
                }
                output.append("]");
                break;
            case Struct.Bool:
                output.append("bool");
                break;
        }

    }
}
