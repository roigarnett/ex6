package Structure;

import java.util.ArrayList;

public class MethodDecleration {

    private String name;
    private ArrayList<VariableTypes> args;

    public MethodDecleration(String name, ArrayList<VariableTypes> args) {
        this.name = name;
        this.args = args;
    }

    public String getName() {
        return name;
    }

    public ArrayList<VariableTypes> getArgs() {
        return args;
    }
}
