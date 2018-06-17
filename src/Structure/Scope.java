package Structure;

import java.util.*;

public class Scope {

    private ArrayList<Line> lines;
    private ArrayList<Variable> variables;
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

    public void addVariable(Variable variable){
        this.variables.add(variable);
    }

    public ArrayList<Line> getLines(){
        return this.lines;
    }

    public ArrayList<Variable> getVariables(){
        return this.variables;
    }

    /**
     * @return the scope's lines (for debugging).
     */
    public String toString(){
        String scopeString = "Scope lines are: ";
        for (Line line: lines){
            scopeString += line.getNumber();
        }
        return scopeString;
    }

    /**
     * prints the scope variables (for debugging).
     */
    public void printScopeVariables() {
        System.out.print("Scope vars are: ");
        for (int i=0; i<variables.size(); i++){
            System.out.print(variables.get(i).toString());
        }
    }
}
