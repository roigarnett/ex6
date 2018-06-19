package Structure;

import java.util.ArrayList;

public class MethodDecleration {

    private String name;
    private ArrayList<Variable> args;

    public MethodDecleration(String name, ArrayList<Variable> args) {
        this.name = name;
        this.args = args;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Variable> getArgs() {
        return args;
    }
}
