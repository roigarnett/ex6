package ex6.oop.main;
import Structure.ClassScope;
import Structure.Line;
import Structure.MethodScope;
import Structure.Scope;
import Parsing.regexes;

import java.util.ArrayList;
import java.util.Stack;

public class ScopeFactory {



//    public static void createScopes(ArrayList<String> data){
//        ArrayList<Scope> methodScopes = new ArrayList<Scope>();
//        Stack<Scope> openedScopes = new Stack<Scope>();
//        /*The current line.*/
//        Line currLine;
//        /*The scope of the SJava file.*/
//        ClassScope classScope;
//
//        classScope = new ClassScope();
//        for(int i=0; i<data.size(); i++) {
//            String line = data.get(i);
//            if (regexes.startNewScope(line)) {
//                if (openedScopes.isEmpty()) {
//                    openedScopes.push(new MethodScope(classScope));
//                }
//                else {
//                    openedScopes.push(new Scope(openedScopes.peek()));
//                }
//                currLine = new Line(line, openedScopes.peek());
//            }
//            else if(regexes.endScope(line)){
//                if(openedScopes.isEmpty()){
//                    System.out.println("there are no open scopes to close!!!");
//                    return;
//                }
//                Scope lastScope = openedScopes.pop();
//                if ( lastScope instanceof MethodScope){
//                    methodScopes.add(lastScope);
//                }
//            }
//
//            else {
//                if(openedScopes.isEmpty()){
//                    currLine = new Line(i, line, classScope);
//                    classScope.addLine(currLine);
//                }
//                else {
//                    currLine = new Line(i, line, openedScopes.peek());
//                    openedScopes.peek().addLine(currLine);
//                }
//            }
//        }
//
//        if (!openedScopes.isEmpty()){
//            System.out.println("not all scopes have been closed!!!");
//            return;
//        }
//        }


    public static Scope createClassScope(ArrayList<String> data) throws Exception{
        Scope scope = new ClassScope();
        for(int i = 0; i < data.size(); i++){
            String line = data.get(i);
            if(regexes.startNewScope(line)){
                Scope innerscope = createMethodScope(trimList(data,i),scope);
                scope.addLine(new Line(line,innerscope));
                i = i + innerscope.getLines().size() - 1;
            }
            else{
                scope.addLine(new Line(line));
            }
        }
        return scope;
    }

    private static Scope createMethodScope(ArrayList<String> data, Scope fatherScope) throws Exception{
        Scope scope = new MethodScope(fatherScope);
        scope.addLine(new Line(data.get(0)));
        for(int i = 1; i < data.size(); i++){
            String line = data.get(i);
            if(regexes.startNewScope(line)){
                Scope innerscope = createConditionScope(trimList(data,i),scope);
                scope.addLine(new Line(line,innerscope));
                i = i + innerscope.getLines().size() - 3;
            }
            else if(regexes.endScope(line)){
                scope.addLine(new Line(line));
                return scope;
            }
            else{
                scope.addLine(new Line(line));
            }
        }
        throw new Exception();
    }

    private static Scope createConditionScope(ArrayList<String> data, Scope fatherScope) throws Exception{
        Scope scope = new Scope(fatherScope);
        scope.addLine(new Line(data.get(0)));
        for(int i = 1; i < data.size(); i++){
            String line = data.get(i);
            if(regexes.startNewScope(line)){
                Scope innerscope = createConditionScope(trimList(data,i),scope);
                scope.addLine(new Line(line,innerscope));
                i = i + innerscope.getLines().size() - 3;
            }
            else if(regexes.endScope(line)){
                scope.addLine(new Line(line));
                return scope;
            }
            else{
                scope.addLine(new Line(line));
            }
        }
        throw new Exception();
    }

    private static ArrayList<String> trimList(ArrayList<String> data, int index){
        for(int i = index - 1; i >= 0; i--){
            data.remove(i);
        }
        return data;
    }
}
