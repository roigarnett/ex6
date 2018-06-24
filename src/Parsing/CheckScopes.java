package Parsing;
import Structure.*;

import java.util.ArrayList;

/**
 * A service class used for managing each scope checking independently.
 */
public class CheckScopes {

    /**
     * checks the correctness of the class scope and updates the relevant variables in the scope.
     * @param scope the class scope.
     * @throws Exception if we found a line in the class scope which is illegal.
     */
    public static void checkClassScope(ClassScopeSingleton scope) throws Exception{
        int methodDeclarationNum = 0;
        for(Line line : scope.getLines()){
            String data = line.getContent();
            if(line.getScope() != null){
                MethodDeclaration md = scope.getMethodDeclarations().get(methodDeclarationNum);
                MethodDeclaration newMd = GeneralLineCheck.checkMethodDecleration(data);
                if(!scope.methodNameValid(newMd.getName())){
                    throw new Exception("two methods with the same name detected");
                }
                md.update(newMd);
                methodDeclarationNum += 1;
            }
            else{
                data = BasicParsing.checkSemicolon(data);
                GeneralLineCheck.checkRegularLine(data,scope);
            }
        }
        for(MethodDeclaration md : scope.getMethodDeclarations()){
                checkMethodScope(md.getScope());
        }
    }

    /**
     * checks the correctness of the class scope and updates the relevant variables in the relevant scope.
     * @param scope the method scope.
     * @throws Exception if we found a line in the method scope which is illegal.
     */
    private static void checkMethodScope(MethodScope scope) throws Exception{
        ArrayList<Variable> globalVars = scope.getFather().getVariables();
        for(Variable var : globalVars){
            scope.getGlobalVariables().add(new Variable(var));
        }
        MethodDeclaration md = GeneralLineCheck.checkMethodDecleration(scope.getLines().get(0).getContent());
        for(Variable var : (md.getArgs())){
            scope.addVariable(var);
        }
        int scopeLength = scope.getLines().size();
        for(int i = 1; i < scopeLength - 2; i ++){
            Line line = scope.getLines().get(i);
            String data = line.getContent();
            if(line.getScope() != null){
                GeneralLineCheck.checkConditionDecleration(data,scope);
                checkConditionScope(line.getScope());
            }
            else{
                data = BasicParsing.checkSemicolon(data);
                if(BasicParsing.methodCall(data)){
                    GeneralLineCheck.checkMethodCall(data,scope);
                }
                else if(!BasicParsing.returnCall(data)){
                    GeneralLineCheck.checkRegularLine(data, scope);
                }
            }
        }
        String methodReturn = BasicParsing.checkSemicolon(scope.getLines().get(scopeLength - 2).getContent());
        if(!BasicParsing.returnCall(methodReturn)){
            throw new Exception("bad return statement");
        }
        String methodClose = scope.getLines().get(scopeLength - 1).getContent();
        if(!BasicParsing.endScope(methodClose)){
            throw new Exception("scope must end with a closing semicolon");
        }
    }

    /**
     * checks the correctness of the condition scope and updates the relevant variables in the scope.
     * @param scope the condition scope.
     * @throws Exception if we found a line in the condition scope which is illegal.
     */
    private static void checkConditionScope(Scope scope) throws Exception{
        scope.getVariables().addAll(scope.getFather().getVariables());
        int scopeLength = scope.getLines().size();
        for(int i = 1; i < scopeLength - 1; i ++){
            Line line = scope.getLines().get(i);
            String data = line.getContent();
            if(line.getScope() != null){
                checkConditionScope(line.getScope());
            }
            else{
                data = BasicParsing.checkSemicolon(data);
                if(BasicParsing.methodCall(data)){
                    GeneralLineCheck.checkMethodCall(data,scope);
                }
                else if(!BasicParsing.returnCall(data)){
                    GeneralLineCheck.checkRegularLine(data, scope);
                }
            }
        }
        String methodClose = scope.getLines().get(scopeLength - 1).getContent();
        if(!BasicParsing.endScope(methodClose)){
            throw new Exception("scope must end with a closing semicolon");
        }
    }

}
