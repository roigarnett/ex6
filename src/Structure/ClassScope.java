package Structure;

import java.util.ArrayList;

/**
 * The scope of the global variables and the file itself.
 */
public class ClassScope extends Scope{

    /**a list of methods declared in the class*/
    private ArrayList<MethodDeclaration> methodDeclarations;

    /**
     * creates a new class scope
     */
    public ClassScope(){
        super();
        methodDeclarations = new ArrayList<MethodDeclaration>();
    }

    /**
     * adds a method declaration to the scope
     * @param MethodDeclaration the method declaration
     */
    public void addMethodDecleration(MethodDeclaration MethodDeclaration){
        this.methodDeclarations.add(MethodDeclaration);
    }

    /**
     * @return the list of method declarations
     */
    public ArrayList<MethodDeclaration> getMethodDeclarations(){
        return this.methodDeclarations;
    }

    /**
     * @param name a method's name
     * @return the method declaration with that name, null if there is no such method
     */
    public MethodDeclaration getMethodFromName(String name){
        for (MethodDeclaration md : this.methodDeclarations) {
            if (md.getName().equals(name)) {
                return md;
            }
        }
        return null;
    }

    /**
     * @param name a method name
     * @return true iff there is no other method with this name
     */
    public boolean methodNameValid(String name){
        for(MethodDeclaration md : methodDeclarations){
            if(md.getName() != null &&md.getName().equals(name)){
                return false;
            }
        }
        return true;
    }
}
