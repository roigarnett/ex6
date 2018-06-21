package Structure;

/**
 * Types of variables in simple java.
 */
public enum VariableTypes {
    INT, STRING, BOOLEAN, DOUBLE, FLOAT, ERROR, OTHER_VAR, FLOAT_OR_DOUBLE;


    public static VariableTypes getType(String data){
        if(data.equals("int")){
            return INT;
        }
        else if(data.equals("String")){
            return STRING;
        }
        else if(data.equals("float")){
            return FLOAT;
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
        else if(data.equals("floatOrDouble")){
            return FLOAT_OR_DOUBLE;
        }
        else{
            return ERROR;
        }
    }

    public static boolean isVariablesTypeMatch (Variable var1, Variable var2){
        return var1.getType() == var2.getType();
    }

    public static boolean isStringMatchVariableType(String str, Variable var){
        return getType(str) == var.getType();
    }
}
