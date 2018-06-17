package Structure;

import java.util.ArrayList;

public class ClassScope extends Scope{

    private ArrayList<MethodDeclaration> methodDeclarations;

    public ClassScope() {
        super();
        methodDeclarations = new ArrayList<MethodDeclaration>();
    }

    /**
     * Add a new method call.
     * @param methodDeclaration the new method call.
     */
    public void addMethodDeclaration(MethodDeclaration methodDeclaration){
        if(methodDeclarations.contains(methodDeclaration)){
            System.out.println("method already exist");
        }
        else {
            methodDeclarations.add(methodDeclaration);
        }
    }

    @Override
    public String toString() {
        StringBuilder SJavaString = new StringBuilder(super.toString() + " methods declared are: ");
        for (MethodDeclaration m: methodDeclarations) {
            SJavaString.append(" " + m.toString());
        }
        return SJavaString.toString();
    }
}
