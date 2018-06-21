package Structure;

/**
 * Types of variables in simple java.
 */
public enum VariableTypes {
    INT, STRING, BOOLEAN, DOUBLE, CHAR, ERROR, OTHER_VAR;


    public static VariableTypes getType(String data) throws Exception{
        if(data.equals("int")){
            return INT;
        }
        else if(data.equals("String")){
            return STRING;
        }
        else if(data.equals("char")){
            return CHAR;
        }
        else if(data.equals("boolean")){
            return BOOLEAN;
        }
        else if(data.equals("double")){
            return DOUBLE;
        }
        else if(data.equals("otherVar")){
            return OTHER_VAR;
        }
        else{
            throw new Exception("Unrecognized variable type at method decleration");
        }
    }

    public static boolean isVariablesTypeMatch (Variable var1, Variable var2){
        return var1.getType() == var2.getType();
    }

    public static boolean isStringMatchVariableType(String str, Variable var) throws Exception{
        return getType(str) == var.getType();
    }

    public static boolean isPlacementPossible(VariableTypes nameType, VariableTypes placementType){
        return (nameType==placementType || (nameType==INT && placementType==DOUBLE) || (nameType==STRING &&
                placementType==CHAR) || (nameType==BOOLEAN && placementType==DOUBLE)
                || (nameType==BOOLEAN && placementType==INT));
    }
}
