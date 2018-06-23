package Structure;

import java.util.*;

/**
 * A class representing a method scope which has certain variables and lines. It is different from Scope
 * class because we would want to change and create variables inside the method and not change them/create
 * them in the class scope.
 */
public class MethodScope extends Scope {

    /**a list of the scope's global variables*/
    private ArrayList<Variable> globalVariables;

    /**
     * creates a new empty method scope
     */
    public MethodScope(){
        super();
        this.globalVariables = new ArrayList<Variable>();
    }

    /**
     * creates a new scope with no variables and no lines
     * @param father the scope that contains this scope.
     */
    public MethodScope(Scope father){
        super(father);
        this.globalVariables = new ArrayList<Variable>();
    }

    @Override
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

    /**
     * @return the list of global variables
     */
    public ArrayList<Variable> getGlobalVariables(){
        return this.globalVariables;
    }

}
