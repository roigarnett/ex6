package Structure;

/**
 * Types of variables in simple java.
 */
public enum VariableTypes {
    INT, STRING, BOOLEAN, DOUBLE, CHAR, OTHER_VAR;

    /**
     * @param data a string representing a variable type, or an other variable name
     * @return the matching VariableType
     * @throws Exception if the string doesn't represent any variable type
     */
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
            throw new Exception("Unrecognized variable type");
        }
    }

    /**
     * @param nameType a variable type (1)
     * @param placementType a variable type (2)
     * @return true iff type (2) can be assignd to type (1)
     */
    public static boolean isPlacementPossible(VariableTypes nameType, VariableTypes placementType){
        return (nameType==placementType || (nameType==DOUBLE && placementType==INT)
                || (nameType==BOOLEAN && placementType==DOUBLE)
                || (nameType==BOOLEAN && placementType==INT));
    }
}
