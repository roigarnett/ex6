package Structure;

import java.util.ArrayList;

public class ClassScope extends Scope{

    private ArrayList<MethodDeclaration> methodDeclarations;

    public ClassScope(){
        super();
        methodDeclarations = new ArrayList<MethodDeclaration>();
    }

    public void addMethodDecleration(MethodDeclaration MethodDeclaration){
        this.methodDeclarations.add(MethodDeclaration);
    }

    public ArrayList<MethodDeclaration> getMethodDeclarations(){
        return this.methodDeclarations;
    }

    public MethodDeclaration getMethodFromName(String name){
        for (MethodDeclaration md : this.methodDeclarations) {
            if (md.getName().equals(name)) {
                return md;
            }
        }
        return null;
    }
}
