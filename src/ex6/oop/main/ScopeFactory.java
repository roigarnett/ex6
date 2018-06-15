package ex6.oop.main;
import Structure.ClassScope;
import Structure.MethodScope;
import Structure.Scope;
import Structure.Line;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScopeFactory {

    public static Scope createClassScope(ArrayList<String> data){
        Scope scope = new ClassScope();
        String pattern = "(\\.*)+[{]+(\\.*)";
        Pattern r = Pattern.compile(pattern);
        for(String line : data){
            Matcher m = r.matcher(line);
            if(m.find()){

            }
            else{
                scope.addLine(new Line(line));
            }
        }
        return scope;
    }

    public static Scope createMethodScope(ArrayList<String> data){
        Scope scope = new MethodScope();
        String pattern = "(\\.*)+[{]+(\\.*)";
        Pattern r = Pattern.compile(pattern);
        for(String line : data){
            Matcher m = r.matcher(line);
            if(m.find()){

            }
            else{
                scope.addLine(new Line(line));
            }
        }
        return scope;
    }

}
