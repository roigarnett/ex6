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

    public String toString (){
        if (scope!=null)
            return("line: " + number + " content is: " + content + " defines scope: " + scope
                    .getLines().get(0).getNumber() + "-" + scope.getLines().get(scope.getLines().size()-1)
                    .getNumber());
        else
            return("line: " + number + " content is: " + content);
    }
}
