package Parsing;
import Structure.*;

import java.util.ArrayList;

public class checkMethodCall {

    public static void check(String data, Scope scope) throws Exception{
        if(!BasicParsing.methodCall(data)){
            throw new Exception();
        }
        String name = BasicParsing.MethodDeclerationName(data);
        ArrayList<Variable> originalVars = scope.getClassScope().getMethodFromName(name).getArgs();
        ArrayList<Variable> calledVars = BasicParsing.MethodDeclerationVars(data);
        if(originalVars.size() != calledVars.size()){
            throw new Exception();
        }
        for(int i = 0; i < originalVars.size(); i++){
            Variable orgVar = originalVars.get(i);
            Variable callVar = calledVars.get(i);
            if(!VariableTypes.isPlacementPossible(orgVar.getType(),callVar.getType())){
                throw new Exception();
            }
            Variable var = scope.getVariableFromName(callVar.getName());
            if(var == null){
                throw new Exception();
            }
        }
    }
}
