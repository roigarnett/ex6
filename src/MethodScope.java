import java.util.*;

public class MethodScope extends Scope {

    private ArrayList<Variable> globalVariables;

    public MethodScope(Scope father){
        super(father);
        this.globalVariables = new ArrayList<Variable>();
    }

    public ArrayList<Variable> getGlobalVariables(){
        return this.globalVariables;
    }

}
