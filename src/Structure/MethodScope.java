package Structure;

import java.util.*;

/**
 * A class representing a method scope which has certain variables and lines. It is different from Scope
 * class because we would want to change and create variables inside the method and not change them/create
 * them in the class scope.
 */
public class MethodScope extends Scope {

    private ArrayList<Variable> globalVariables;

    public MethodScope(){
        super();
        this.globalVariables = new ArrayList<Variable>();
    }

    public MethodScope(Scope father){
        super(father);
        this.globalVariables = new ArrayList<Variable>();
    }

    public Variable getVariableFromName(String name){
        for (Variable var : this.getVariables()) {
            if (var.getName().equals(name)) {
                return var;
            }
        }
        for (Variable var : this.globalVariables) {
            if (var.getName().equals(name)) {
                return var;
            }
        }
        return null;
    }

    public ArrayList<Variable> getGlobalVariables(){
        return this.globalVariables;
    }

}
