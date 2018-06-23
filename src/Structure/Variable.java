package Structure;

/**
 * A class describing a simple java variable.
 */
public class Variable {

    /**the variable type*/
    private VariableTypes type;

    /**the variable name*/
    private String name;

    /**whether the variable is final*/
    private boolean isFinal;

    /*a field indicating whether the variable has been initialized or not. */
    private boolean initialized;

    /**
     * Creates a new initialized variable.
     * @param type the variable type
     * @param name the variable name
     * @param isFinal whether the variable is final
     * @param initialized whether the variable is initialized
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
     * @param type the variable type
     * @param name the variable name
     * @param isFinal whether the variable is final
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
     * @return the variable's name
     */
    public String getName(){
        return this.name;
    }

    /**
     * @return the variable's type
     */
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

}
