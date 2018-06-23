package Structure;

import java.util.ArrayList;

/**
 * A class representing a method declaration.
 */
public class MethodDeclaration {

    /**the name of the method*/
    private String name;

    /**the variables the method gets*/
    private ArrayList<Variable> args;

    /**the scope of the method*/
    private MethodScope scope;

    /**
     * creates a new method decleration
     * @param scope the method's scope
     */
    public MethodDeclaration(MethodScope scope){
        this.scope = scope;
        this.args = new ArrayList<Variable>();
    }

    /**
     * creates a new method deceleration with no scope
     * @param name the method's name
     * @param args the method's variables
     */
    public MethodDeclaration(String name, ArrayList<Variable> args) {
        this.name = name;
        this.args = args;
        this.scope = null;
    }

    /**
     * updates the deceleration name and args
     * @param md the deceleration to update according to
     */
    public void update(MethodDeclaration md){
        setName(md.getName());
        setArgs(md.getArgs());
    }

    /**
     * @return the method's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the method's variable list
     */
    public ArrayList<Variable> getArgs() {
        return args;
    }

    /**
     * sets the method's name
     * @param name the new name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * sets the method's variables
     * @param args the new variables
     */
    public void setArgs(ArrayList<Variable> args){
        this.args.addAll(args);
    }

    /**
     * @return the method's scope
     */
    public MethodScope getScope(){
        return this.scope;
    }
}
