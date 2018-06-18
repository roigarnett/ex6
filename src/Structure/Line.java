package Structure;

public class Line {

    private String content;
    private Scope scope;

    public Line(String content){
        this.content = content;
        this.scope = null;
    }

    public Line(String content, Scope scope){
        this.content = content;
        this.scope = scope;
    }

    public String getContent(){
        return this.content;
    }

    public Scope getScope(){
        return this.scope;
    }

    public String toString(){
        return this.content;
    }
}
