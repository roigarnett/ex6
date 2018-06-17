package Structure;

import java.util.*;

public class MethodScope extends Scope {

    /*The variables the method has.*/
    private ArrayList<Variable> methodScopeVariables;


    /**
     * Creates a new method scope
     * @param father the scope of which the method scope will be nested in.
     */
    public MethodScope(Scope father){
        super(father);
        methodScopeVariables = new ArrayList<Variable>();
    }

    /**
     * @return returns the variables of the method.
     */
    public ArrayList<Variable> getVariables() {
        return methodScopeVariables;
    }

    /**
     * Adds a new variable to the method scope.
     * @param newVariable the new variable to add.
     */
    public void addVariable(Variable newVariable) {
        methodScopeVariables.add(newVariable);
    }

    /**
     * Updates all the method variables by the arguments given in the declaration to the method and the class
     * scope's variables.
     * @param methodDeclaration the call to the method.
     */
    public void updateVariables(MethodDeclaration methodDeclaration){
        methodScopeVariables = new ArrayList<Variable>(father.getVariables());
        methodScopeVariables.addAll(methodDeclaration.getMethodVariables());

    }


}
