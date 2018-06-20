package Parsing;
import Structure.*;

import java.util.ArrayList;

public class CheckScopes {

    public static void checkClassScope(ClassScope scope) throws Exception{
        for(Line line : scope.getLines()){
            if(line.getScope() != null){
                scope.addMethodDecleration(checkMethodDecleration(line.getContent()));
            }
            else{
                System.out.println("a");
            }
        }
    }

    public static void checkMethodScope(MethodScope scope) throws Exception{
        scope.getGlobalVariables().addAll(scope.getFather().getVariables());
        MethodDeclaration md = checkMethodDecleration(scope.getLines().get(0).getContent());
        scope.getVariables().addAll(md.getArgs());
        for(int i = 1; i < scope.getLines().size() - 2; i ++){
            Line line = scope.getLines().get(i);
            if(line.getScope() != null){
                ArrayList<String> vars = checkConditionDecleration(line.getContent());
            }
            else if(regexes.methodCall(line.getContent())){

            }
            else{
                System.out.println("a");
            }
        }
    }

    private static void checkConditionScope(Scope scope) throws Exception{
        scope.getVariables().addAll(scope.getFather().getVariables());
        ArrayList<String> startVars =
                checkConditionDecleration(scope.getLines().get(0).getContent());
        for(int i = 1; i < scope.getLines().size() - 2; i ++){
            Line line = scope.getLines().get(i);
            if(line.getScope() != null){
                ArrayList<String> vars = checkConditionDecleration(line.getContent());
            }
            else if(regexes.methodCall(line.getContent())){

            }
            else{
                System.out.println("a");
            }
        }
    }

    private static MethodDeclaration checkMethodDecleration(String data) throws Exception{
        if(!regexes.MethodDecleration(data)){
            throw new Exception();
        }
        String name = regexes.MethodDeclerationName(data);
        ArrayList<Variable> vars = regexes.MethodDeclerationVars(data);
        return new MethodDeclaration(name,vars);
    }

    private static ArrayList<String> checkConditionDecleration(String data) throws Exception{
        if(!regexes.conditionCall(data)){
            throw new Exception();
        }
        return regexes.conditionTerm(data);
    }


}
