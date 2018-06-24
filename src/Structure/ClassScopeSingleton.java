package Structure;

import java.util.ArrayList;

/**
 * The scope of the global variables and the file itself.
 */
public class ClassScopeSingleton extends Scope{

    /*The instance of the class' scope.*/
    private static ClassScopeSingleton classScope = null;
    /**a list of methods declared in the class*/
    private ArrayList<MethodDeclaration> methodDeclarations;

    /**
     * creates a new class scope
     */
    private ClassScopeSingleton(){
        super();
        methodDeclarations = new ArrayList<MethodDeclaration>();
    }

    /**
     * @return the single class' scope instance (or creates a new one and returns it).
     */
    public static ClassScopeSingleton getInstance() {
        if (classScope == null)
             classScope = new ClassScopeSingleton();
        return classScope;
    }

    public static void disposeClass(){
        classScope = null;
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
