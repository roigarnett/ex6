package Structure;

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
    public Variable(VariableTypes type, String name, boolean isFinal, boolean initialized) {
        this.type = type;
        this.name = name;
        this.isFinal = isFinal;
        this.initialized = initialized;
    }

    /**
     * Creates a new uninitialized variable.
     * @param type
     * @param name
     * @param isFinal
     */

    public Variable(VariableTypes type, String name, boolean isFinal) {
        this.type = type;
        this.name = name;
        this.isFinal = isFinal;
    }

    /**
     * @return true iff the varible is initialized or not.
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

    @Override
    public String toString() {
        String varString = type.toString() + " " + name;
        if (isFinal){
            varString = "final " + varString;
        }
        if (isInitialized()) {
            varString = varString + "initialized";
        }
        return varString;
    }
}
