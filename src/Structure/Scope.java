package Structure;

import java.util.*;

/**
 * A class representing a scope which has certain variables and lines.
 */
public class Scope {

    /**a list of strings representing the lines inside the scope*/
    private ArrayList<Line> lines;

    /**a list of the scope's variables*/
    private ArrayList<Variable> variables;

    /*The scope that contains this scope. null if no scope contains it.*/
    private Scope father;

    /**
     * creates a new scope with no variables, no father and no lines
     */
    public Scope(){
        this.lines = new ArrayList<Line>();
        this.variables = new ArrayList<Variable>();
        this.father = null;
    }

    /**
     * creates a new scope with no variables and no lines
     * @param father the scope that contains this scope.
     */
    public Scope(Scope father){
        this.lines = new ArrayList<Line>();
        this.variables = new ArrayList<Variable>();
        this.father = father;
    }

    /**
     * adds a line to the scope's line list
     * @param line the line to add
     */
    public void addLine(Line line){
        this.lines.add(line);
    }

    /**
     * adds a variable to the scope's variable list
     * @param variable the variable to add
     * @throws Exception if there is already a variable with that name in the scope
     */
    public void addVariable(Variable variable) throws Exception{
        if(!variableNameValid(variable.getName())){
            throw new Exception("var name already exist");
        }
        this.variables.add(variable);
    }

    /**
     * @return the scope's line list
     */
    public ArrayList<Line> getLines(){
        return this.lines;
    }

    /**
     * @return the scope's variables list
     */
    public ArrayList<Variable> getVariables(){
        return this.variables;
    }

    /**
     * @return the scope's father scope
     */
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

    /**
     * @param name the wanted variable name
     * @return the variable (global or not) with that name, null if there is no such variable
     */
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

    /**
     * @return the class class scope which contians this scope
     */
    public ClassScope getClassScope(){
        Scope currentScope = this;
        Scope father = currentScope.getFather();
        while(father != null) {
            currentScope = father;
            father = currentScope.getFather();
        }
        return (ClassScope)currentScope;
    }

    /**
     * @return the number of the lines inside the scope (including inner scopes)
     */
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

    /**
     * @param name some variable name
     * @return true if it is legal to create a new variable with that name in the scope
     */
    public boolean variableNameValid(String name){
        for(Variable var : this.variables){
            if(var.getName().equals(name)){
                return false;
            }
        }
        return true;
    }


}
