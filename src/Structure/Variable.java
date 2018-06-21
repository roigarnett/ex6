package Structure;

/**
 * A class describing a simple java variable.
 */
public class Variable {

    private VariableTypes type;
    private String name;
    private boolean isFinal;
    /*a field indicating whether the variable has been initialized or not. */
    private boolean initialized;

    /**
     * Creates a new initialized variable.
     * @param type
     * @param name
     * @param isFinal
     * @param initialized
     */
    public Variable(VariableTypes type, String name, boolean isFinal, boolean initialized) throws Exception{
        this.type = type;
        this.name = name;
        this.isFinal = isFinal;
        this.initialized = initialized;
        if(isFinal && !initialized){
            throw new Exception("Variable can't be initialized because it is final");
        }
    }

    /**
     * Creates a new uninitialized variable.
     * @param type
     * @param name
     * @param isFinal
     */
    public Variable(VariableTypes type, String name, boolean isFinal) throws Exception{
        this.type = type;
        this.name = name;
        this.isFinal = isFinal;
        if(isFinal){
            throw new Exception("Variable can't be initialized because it is final");
        }

    }

    /**
     * @return true iff the variable is initialized.
     */
    public boolean isInitialized() {
        return initialized;
    }

    /**
     * Initialize the variable.
     */
    public void initialize() {
        this.initialized = true;
    }

    public String getName() {
        return name;
    }


    public VariableTypes getType() {
        return type;
    }

    @Override
    public String toString() {
        String varString = type.toString() + " " + name;
        if (isFinal){
            varString = "final " + varString;
        }
        if (isInitialized()) {
            varString = varString + " initialized";
        }
        return varString;
    }
}
