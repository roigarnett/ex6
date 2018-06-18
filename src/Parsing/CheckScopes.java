package Parsing;
import Structure.*;

import java.util.ArrayList;

public class CheckScopes {

    public static void checkClassScope(ClassScope scope){
        for(Line line : scope.getLines()){
            String data = line.getContent();
            if(regexes.MethodDecleration(data)){
                String name = regexes.MethodDeclerationName(data);
                ArrayList<VariableTypes> types = regexes.MethodDeclerationTypes(data);
                scope.addMethodDecleration(new MethodDecleration(name,types));
            }
            if(regexes.isDecleration(data)){
                if(regexes.isPlacement(data)){

                }
            }
            else{

            }
        }
    }


}
