import java.util.*;

public class Scope {

    private ArrayList<Line> lines;
    private ArrayList<Variable> variables;
    private Scope father;

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
}
