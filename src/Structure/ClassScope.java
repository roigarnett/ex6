package Structure;

import java.util.ArrayList;

public class ClassScope extends Scope{

    private ArrayList<MethodDeclaration> methodDeclarations;

    public void addMethodDecleration(MethodDeclaration MethodDeclaration){
        this.methodDeclarations.add(MethodDeclaration);
    }
}
