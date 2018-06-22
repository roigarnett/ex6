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
            throw new Exception("Variable can't be initialized with no value because it is final");
        }

    }

    public Variable (Variable var){
        this.type = var.type;
        this.name = var.name;
        this.isFinal = var.isFinal;
        this.initialized = var.initialized;
    }

    /**
     * @return true iff the variable is initialized.
     */
    public boolean isInitialized() {
        return initialized;
    }

    /**
     * initialize the variable if possible (=the variable is not final).
     * @throws Exception if the variable is final.
     */
    public void initialize() throws Exception {
        if(isFinal && this.initialized){
            throw new Exception("can't change a final Variable");
        }
        this.initialized = true;

    }

    public String getName(){
        return this.name;
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
        return varString + " ";
    }
}
