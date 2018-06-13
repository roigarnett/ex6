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
}
