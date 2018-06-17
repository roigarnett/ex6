package Structure;

import java.util.ArrayList;

public class MethodDeclaration {

    /*The method's name.*/
    private String name;

    /*The Variables the method gets as arguments.*/
    private ArrayList<Variable> methodVariables;;

    public MethodDeclaration(String name, ArrayList<Variable> args) {
        this.name = name;
        this.methodVariables = args;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Variable> getMethodVariables() {
        return methodVariables;
    }

    @Override
    public String toString() {
        StringBuilder declareString = new StringBuilder(name);
        for(Variable var: methodVariables) {
            declareString.append(" " + var.toString());
        }
        return declareString.toString();
    }
}
