package rs.ac.bg.etf.pp1;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandar Grkajac ga040202d@student.etf.rs, aleksa888@gmail.com
 * Date: 5/6/14
 * Time: 8:03 AM
 * To change this template use File | Settings | File Templates.
 */
public enum VarNode {
    GLOBAL_VAR(1),

    CLASS_VAR(2),

    METHOD_FORMAL_VAR(3),

    METHOD_LOCAL_VAR(4),

    GLOBAL_ARRAY_VAR(5),

    CLASS_ARRAY_VAR(6),

    METHOD_FORMAL_ARRAY_VAR(7),

    METHOD_LOCAL_ARRAY_VAR(8);

    private int var;

    private VarNode(int v) {
        var = v;
    }

    public int getVarNode() {
        return var;
    }

    public static VarNode varToArray(VarNode var){

        VarNode returnNode = var;

        switch (var){
            case GLOBAL_VAR:
                returnNode = GLOBAL_ARRAY_VAR;
                break;

            case CLASS_VAR:
                returnNode = CLASS_ARRAY_VAR;
                break;

            case METHOD_FORMAL_VAR:
                returnNode = METHOD_FORMAL_ARRAY_VAR;
                break;

            case METHOD_LOCAL_VAR:
                returnNode = METHOD_LOCAL_ARRAY_VAR;
                break;
        }
        return returnNode;
    }
}
