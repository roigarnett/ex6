package Parsing;
import Structure.*;

import java.util.ArrayList;

public class CheckConditionDecleration {

    public static void check(String data, Scope scope) throws Exception{
        if(!BasicParsing.conditionCall(data)){
            throw new Exception();
        }
        ArrayList<String> booleanVariables = BasicParsing.conditionTerm(data);
        for(String varName : booleanVariables){
            Variable var = scope.getVariableFromName(varName);
            if(var == null){
                throw new Exception();
            }
            if(!VariableTypes.isPlacementPossible(VariableTypes.BOOLEAN,var.getType())){
                throw new Exception();
            }
        }
    }
}
