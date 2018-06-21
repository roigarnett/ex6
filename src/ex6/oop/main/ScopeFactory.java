package ex6.oop.main;
import Structure.*;
import Parsing.BasicParsing;

import java.util.ArrayList;
import java.util.Stack;

public class ScopeFactory {


    public static ClassScope createClassScope(ArrayList<String> data) throws Exception{
        ClassScope scope = new ClassScope();
        for(int i = 0; i < data.size(); i++){
            String line = data.get(i);
            if(BasicParsing.startScope(line)){
                MethodScope innerscope = createMethodScope(trimList(data,i),scope);
                scope.addLine(new Line(line,innerscope));
                scope.addMethodDecleration(new MethodDeclaration(innerscope));
                i = i + innerscope.getLines().size() - 1;
            }
            else{
                scope.addLine(new Line(line));
            }
        }
        return scope;
    }

    private static MethodScope createMethodScope(ArrayList<String> data, Scope fatherScope) throws Exception{
        MethodScope scope = new MethodScope(fatherScope);
        scope.addLine(new Line(data.get(0)));
        for(int i = 1; i < data.size(); i++){
            String line = data.get(i);
            if(BasicParsing.startScope(line)){
                Scope innerscope = createConditionScope(trimList(data,i),scope);
                scope.addLine(new Line(line,innerscope));
                i = i + innerscope.getLines().size() - 2;
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

    private static Scope createConditionScope(ArrayList<String> data, Scope fatherScope) throws Exception{
        Scope scope = new Scope(fatherScope);
        scope.addLine(new Line(data.get(0)));
        for(int i = 1; i < data.size(); i++){
            String line = data.get(i);
            if(BasicParsing.startScope(line)){
                Scope innerscope = createConditionScope(trimList(data,i),scope);
                scope.addLine(new Line(line,innerscope));
                i = i + innerscope.getLines().size() - 2;
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

    private static ArrayList<String> trimList(ArrayList<String> data, int index){
        for(int i = index - 1; i >= 0; i--){
            data.remove(i);
        }
        return data;
    }
}
