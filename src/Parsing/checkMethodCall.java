package Parsing;
import Structure.*;

import java.util.ArrayList;

public class checkMethodCall {

    public static void check(String data, Scope scope) throws Exception{
        if(!BasicParsing.methodCall(data)){
            throw new Exception();
        }
        String name = BasicParsing.methodDeclerationName(data);
        ArrayList<Variable> originalVars = scope.getClassScope().getMethodFromName(name).getArgs();
        ArrayList<VariableTypes> calledVars = BasicParsing.methodCallVars(data);
        ArrayList<String> calledVarsNames = BasicParsing.methodCallNames(data);
        if(originalVars.size() != calledVars.size()){
            throw new Exception();
        }
        for(int i = 0; i < originalVars.size(); i++){
            Variable orgVar = originalVars.get(i);
            VariableTypes callType = calledVars.get(i);
            if(callType.equals(VariableTypes.OTHER_VAR)){
                Variable callVar = scope.getVariableFromName(calledVarsNames.get(i));
                if(callVar == null || !callVar.isInitialized()){
                    throw new Exception("called variable is not ininitializd");
                }
                callType = callVar.getType();
            }
            if(!VariableTypes.isPlacementPossible(orgVar.getType(),callType)){
                throw new Exception();
            }
        }
    }
}
