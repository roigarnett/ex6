package Structure;

public class Line {


    private int number;
    private String content;
    private Scope scope;

    public Line(int lineNumber, String content){
        this.number = lineNumber;
        this.content = content;
        this.scope = null;
    }

    public Line(int lineNumber, String content, Scope scope){
        this.number = lineNumber;
        this.content = content;
        this.scope = scope;
    }

    /**
     * @return the line's number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @return the line's content
     */
    public String getContent() {
        return content;
    }

    /**
     * @return the line's scope
     */
    public Scope getScope() {
        return scope;
    }

    /**
     * Set a new Scope to the line.
     * @param newScope the new scope
     */
    public void setScope (Scope newScope) {
        this.scope = newScope;
    }
}
