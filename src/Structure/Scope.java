package Structure;

import java.util.*;

/**
 * A class representing a scope which has certain variables and lines.
 */
public class Scope {

    private ArrayList<Line> lines;
    private ArrayList<Variable> variables;
    /*The scope that contains this scope. null if no scope contains it.*/
    Scope father;

    public Scope(){
        this.lines = new ArrayList<Line>();
        this.variables = new ArrayList<Variable>();
        this.father = null;
    }

    public Scope(Scope father){
        this.lines = new ArrayList<Line>();
        this.variables = new ArrayList<Variable>();
        this.father = father;
    }

    public void addLine(Line line){
        this.lines.add(line);
    }

    public void addVariable(Variable variable) throws Exception{
        if(getVariableFromName(variable.getName()) != null){
            throw new Exception("var name already exist");
        }
        this.variables.add(variable);
    }

    public ArrayList<Line> getLines(){
        return this.lines;
    }

    public ArrayList<Variable> getVariables(){
        return this.variables;
    }

    public Scope getFather(){
        return this.father;
    }

    /**
     * @return the scope's lines (for debugging).
     */
    public String toString(){
        String scopeString = "Scope lines are:   ";
        for (Line line : lines){
            if(line.getScope() != null){
                scopeString += " < ";
                scopeString += line.getScope().toString();
                scopeString += " > ";
            }
            else{
                scopeString += line.toString();
            }
        }
        return scopeString;
    }

    public Variable getVariableFromName(String name){
        Scope currentScope = this;
        while(currentScope != null) {
            for (Variable var : currentScope.getVariables()) {
                if (var.getName().equals(name)) {
                    return var;
                }
            }
            currentScope = currentScope.getFather();
        }
        return null;
    }

    /**
     * prints the scope variables (for debugging).
     */
    public void printScopeVariables() {
        System.out.print("Scope vars are: ");
        for (int i=0; i<getVariables().size(); i++){
            System.out.print(getVariables().get(i).toString());
        }
    }

    public ClassScope getClassScope(){
        Scope currentScope = this;
        Scope father = currentScope.getFather();
        while(father != null) {
            currentScope = father;
            father = currentScope.getFather();
        }
        return (ClassScope)currentScope;
    }

    public int getScopeLines(){
        int number = 0;
        for(Line line : this.lines){
            number += 1;
            if(line.getScope() != null){
                number += line.getScope().getScopeLines() - 1;
            }
        }
        return number;
    }
}
