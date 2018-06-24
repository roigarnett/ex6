package Structure;

/**
 * this class represents a line in a Sjava file
 */
public class Line {

    /**the content of the line*/
    private String content;

    /**the scope this line opens, null if the line doesn't open a scope*/
    private Scope scope;

    /**
     * creates a new line
     * @param content the content of the line
     */
    public Line(String content){
        this.content = content;
        this.scope = null;
    }

    /**
     * creates a new line with a scope that this line opens
     * @param content the content of the line
     * @param scope the scope this line opens
     */
    public Line(String content, Scope scope){
        this.content = content;
        this.scope = scope;
    }

    /**
     * @return the content of the line
     */
    public String getContent(){
        return this.content;
    }

    /**
     * @return the scope of the line
     */
    public Scope getScope(){
        return this.scope;
    }

    @Override
    public String toString(){
        return this.content;
    }
}
