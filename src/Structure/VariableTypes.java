package Structure;

/**
 * Types of variables in simple java.
 */
public enum VariableTypes {
    INT, STRING, BOOLEAN, DOUBLE, FLOAT, ERROR;


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
        else{
            return ERROR;
        }
    }
}
