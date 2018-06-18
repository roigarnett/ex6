package Structure;

import java.util.ArrayList;

public class ClassScope extends Scope{

    private ArrayList<MethodDecleration> MethodDeclerations;

    public void addMethodDecleration(MethodDecleration MethodDecleration){
        this.MethodDeclerations.add(MethodDecleration);
    }
}
