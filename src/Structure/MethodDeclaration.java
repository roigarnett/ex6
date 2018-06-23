package Structure;

import java.util.ArrayList;

/**
 * A class representing a method declaration.
 */
public class MethodDeclaration {

    private String name;
    private ArrayList<Variable> args;
    private MethodScope scope;

    public MethodDeclaration(MethodScope scope){
        this.scope = scope;
        this.args = new ArrayList<Variable>();
    }

    public MethodDeclaration(String name, ArrayList<Variable> args) {
        this.name = name;
        this.args = args;
        this.scope = null;
    }

    public void update(MethodDeclaration md){
        setName(md.getName());
        setArgs(md.getArgs());
    }

    public String getName() {
        return name;
    }

    public ArrayList<Variable> getArgs() {
        return args;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setArgs(ArrayList<Variable> args){
        this.args.addAll(args);
    }

    public MethodScope getScope(){
        return this.scope;
    }
}
