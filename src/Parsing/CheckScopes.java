package Parsing;
import Structure.*;

import java.util.ArrayList;

public class CheckScopes {

    public static void checkClassScope(ClassScope scope) throws Exception{
        int methodDeclarationNum = 0;
        for(Line line : scope.getLines()){
            String data = line.getContent();
            if(line.getScope() != null){
                MethodDeclaration md = scope.getMethodDeclarations().get(methodDeclarationNum);
                md.update(checkMethodDecleration(data));
                methodDeclarationNum += 1;
            }
            else{
                data = BasicParsing.checkSemicolon(data);
                CheckRegularLine.checkLine(data,scope);
            }
        }
        for(MethodDeclaration md : scope.getMethodDeclarations()){
                checkMethodScope(md.getScope());
        }
    }

    private static void checkMethodScope(MethodScope scope) throws Exception{
        scope.getGlobalVariables().addAll(scope.getFather().getVariables());
        MethodDeclaration md = checkMethodDecleration(scope.getLines().get(0).getContent());
        for(Variable var : (md.getArgs())){
            scope.addVariable(var);
        }
        int scopeLength = scope.getLines().size();
        for(int i = 1; i < scopeLength - 2; i ++){
            Line line = scope.getLines().get(i);
            String data = line.getContent();
            if(line.getScope() != null){
                CheckConditionDecleration.check(data,scope);
                checkConditionScope(line.getScope());
            }
            else{
                data = BasicParsing.checkSemicolon(data);
                if(BasicParsing.methodCall(data)){
                    checkMethodCall.check(data,scope);
                }
                else if(!BasicParsing.returnCall(data)){
                    CheckRegularLine.checkLine(data, scope);
                }
            }
        }
        String methodReturn = BasicParsing.checkSemicolon(scope.getLines().get(scopeLength - 2).getContent());
        if(!BasicParsing.returnCall(methodReturn)){
            throw new Exception("bad return statement");
        }
        String methodClose = scope.getLines().get(scopeLength - 1).getContent();
        if(!BasicParsing.endScope(methodClose)){
            throw new Exception();
        }
    }

    private static void checkConditionScope(Scope scope) throws Exception{
        scope.getVariables().addAll(scope.getFather().getVariables());
        ArrayList<String> startVars =
                checkConditionDecleration(scope.getLines().get(0).getContent());
        int scopeLength = scope.getLines().size();
        for(int i = 1; i < scopeLength - 1; i ++){
            Line line = scope.getLines().get(i);
            String data = line.getContent();
            if(line.getScope() != null){
                ArrayList<String> vars = checkConditionDecleration(data);
                checkConditionScope(line.getScope());
            }
            else{
                data = BasicParsing.checkSemicolon(data);
                if(BasicParsing.methodCall(data)){
                    checkMethodCall.check(data,scope);
                }
                else if(!BasicParsing.returnCall(data)){
                    CheckRegularLine.checkLine(data, scope);
                }
            }
        }
        String methodClose = scope.getLines().get(scopeLength - 1).getContent();
        if(!BasicParsing.endScope(methodClose)){
            throw new Exception();
        }
    }

    private static MethodDeclaration checkMethodDecleration(String data) throws Exception{
        if(!BasicParsing.methodDecleration(data)){
            throw new Exception();
        }
        data = BasicParsing.methodDeclerationData(data);
        String name = BasicParsing.methodDeclerationName(data);
        ArrayList<Variable> vars = BasicParsing.methodDeclerationVars(data);
        return new MethodDeclaration(name,vars);
    }

    private static ArrayList<String> checkConditionDecleration(String data) throws Exception{
        if(!BasicParsing.conditionCall(data)){
            throw new Exception();
        }
        return BasicParsing.conditionTerm(data);
    }



}
