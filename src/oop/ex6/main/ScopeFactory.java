package oop.ex6.main;
import Structure.*;
import Parsing.BasicParsing;

import java.util.ArrayList;

/**
 * A factory class that creates scopes and add the the lines of each scope to it's respectable class.
 */
public class ScopeFactory {

    /**
     * @param data a list of strings, the content of the file
     * @return a scope hierarchy of the file
     * @throws Exception if there is a problem in the Sjava file
     */
    public static ClassScopeSingleton createClassScope(ArrayList<String> data) throws Exception{
        ClassScopeSingleton scope = ClassScopeSingleton.getInstance();
        for(int i = 0; i < data.size(); i++){
            String line = data.get(i);
            if(BasicParsing.startScope(line)){
                MethodScope innerscope = createMethodScope(trimList(data,i),scope);
                scope.addLine(new Line(line,innerscope));
                scope.addMethodDecleration(new MethodDeclaration(innerscope));
                i = i + innerscope.getScopeLines() - 1;
            }
            else{
                scope.addLine(new Line(line));
            }
        }
        return scope;
    }

    /**
     * @param data a list of strings, the content of the method
     * @param fatherScope the class scope
     * @return a scope hierarchy of the file
     * @throws Exception if there is a problem in the Sjava file
     */
    private static MethodScope createMethodScope(ArrayList<String> data, Scope fatherScope) throws Exception{
        MethodScope scope = new MethodScope(fatherScope);
        scope.addLine(new Line(data.get(0)));
        for(int i = 1; i < data.size(); i++){
            String line = data.get(i);
            if(BasicParsing.startScope(line)){
                Scope innerscope = createConditionScope(trimList(data,i),scope);
                scope.addLine(new Line(line,innerscope));
                i = i + innerscope.getScopeLines() - 1;
            }
            else if(BasicParsing.endScope(line)){
                scope.addLine(new Line(line));
                return scope;
            }
            else{
                scope.addLine(new Line(line));
            }
        }
        throw new Exception();
    }

    /**
     * @param data a list of strings, the content of the scope
     * @param fatherScope the scope in which the current scope is
     * @return a scope hierarchy of the file
     * @throws Exception if there is a problem in the Sjava file
     */
    private static Scope createConditionScope(ArrayList<String> data, Scope fatherScope) throws Exception{
        Scope scope = new Scope(fatherScope);
        scope.addLine(new Line(data.get(0)));
        for(int i = 1; i < data.size(); i++){
            String line = data.get(i);
            if(BasicParsing.startScope(line)){
                Scope innerscope = createConditionScope(trimList(data,i),scope);
                scope.addLine(new Line(line,innerscope));
                i = i + innerscope.getScopeLines() - 1;
            }
            else if(BasicParsing.endScope(line)){
                scope.addLine(new Line(line));
                return scope;
            }
            else{
                scope.addLine(new Line(line));
            }
        }
        throw new Exception();
    }

    /**
     * @param data a list of strings
     * @param index the index from which to take the data
     * @return the list withouts the data in indexes 0 - index
     */
    private static ArrayList<String> trimList(ArrayList<String> data, int index){
        ArrayList<String> newList = new ArrayList<>(data);
        for(int i = index - 1; i >= 0; i--){
            newList.remove(i);
        }
        return newList;
    }
}
