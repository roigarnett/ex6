package ex6.oop.main;
import Structure.ClassScope;
import Structure.Line;
import Structure.MethodScope;
import Structure.Scope;
import ParsingData.regexes;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Stack;

public class ScopeFactory {

    private ArrayList<Scope> methodScopes = new ArrayList<Scope>();
    private Stack<Scope> openedScopes = new Stack<Scope>();
    /*The current line.*/
    private Line currLine;
    /*The scope of the SJava file.*/
    private ClassScope classScope;

    public void createScopes(ArrayList<String> data){
        classScope = new ClassScope();
        for (int i=0; i<data.size(); i++) {
            String line = data.get(i);

            if (regexes.startNewScope(line)){

                //there is already an open method scope.
                if (!openedScopes.isEmpty()){
                    Scope lastScope = openedScopes.peek();
                    openedScopes.push(new Scope(openedScopes.peek()));
                    currLine = new Line(i, line, openedScopes.peek());
                    lastScope.addLine(currLine);
                }
                else {
                    openedScopes.push(new MethodScope(classScope));
                    currLine = new Line(i, line, openedScopes.peek());
                    classScope.addLine(currLine);
                }
                openedScopes.peek().addLine(currLine);
            }

            else if(regexes.endScope(line)){
                if(openedScopes.isEmpty()){
                    System.out.println("there are no open scopes to close!!!");
                    return;
                }
                currLine = new Line(i, line, openedScopes.peek());
                openedScopes.peek().addLine(currLine);
                Scope lastScope = openedScopes.pop();
                if ( lastScope instanceof MethodScope){
                    methodScopes.add(lastScope);
                }
            }

            else {
                if(openedScopes.isEmpty()){
                    currLine = new Line(i, line, classScope);
                    classScope.addLine(currLine);
                }
                else {
                    currLine = new Line(i, line, openedScopes.peek());
                    openedScopes.peek().addLine(currLine);
                }
            }
        }

        if (!openedScopes.isEmpty()){
            System.out.println("not all scopes have been closed!!!");
            return;
        }
        Collections.reverse(methodScopes);
    }
}
