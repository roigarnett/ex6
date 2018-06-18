package Parsing;
import Structure.VariableTypes;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regexes {


    static String endLineSemicolon = ";\\s*$";
    static String endLineBrackets = "\\{\\s*$";
    static String CloseBracketLine = "^\\s*\\}\\s*$";

    static String Type = "^(int|String|boolean|double|float)$";
    static String INT = "^\\s*int\\s{1,}[a-zA-Z]\\S*";
    static String STRING = "^\\s*String\\s{1,}[a-zA-Z]\\S*";
    static String BOOLEAN = "^\\s*boolean\\s{1,}[a-zA-Z]\\S*";
    static String FLOAT = "^\\s*float\\s{1,}[a-zA-Z]\\S*";
    static String DOUBLE = "^\\s*double\\s{1,}[a-zA-Z]\\S*";
    static String SecondDecleration = "^\\s*,\\s*[a-zA-Z]\\S*";

    static String Placement = "^\\s*=\\s*\\S*\\s*$";
    static String IntPlacement = "^\\s*=\\s*\\d*\\s*$";
    static String StringPlacement = "^\\s*=\\s*\\w*\\s*$";
    static String BOOLEANPlacement = "^\\s*=\\s*(true|false)\\s*$";
    static String FLOATorDOUBLEPlacement = "^\\s*=\\s*\\d{1,}\\.\\d{1,}\\s*$";
    static String VariablePlacement = "^\\s*=\\s*[a-zA-Z]\\S*\\s*$";
    static String ARGUMENTSCALL = "^\\s*(int|String|boolean|float|double\\s{1,}+[a-zA-Z],)^\\S*\\s*";

    static String MethodDecleration = "^\\s*void\\s{1,}[a-zA-Z]\\S*\\s*\\(.*\\)\\s*\\{\\s*$";
    static String Return = "^\\s*return\\s*$";
    static String WHILE = "^\\s*while\\s*\\(.*\\)\\s*\\{\\s*$";
    static String IF = "^\\s*if\\s*\\(.*\\)\\s*\\{\\s*$";

    static String CommentLine = "^\\s*//";

    static String ORCondition = ".*||.*";
    static String ANDCondition = ".*&&.*";

    /**
     * @param dataWithBrackets a string containing brackets
     * @return the characters inside the brackets
     */
    public static String dataInBrackets(String dataWithBrackets){
        String[] splitted = dataWithBrackets.split("\\(");
        String[] data = (splitted[1]).split("\\)");
        return data[0];
    }

    /**
     * @param dataWithSpaces a string
     * @return the string without whitespaces at the beggining and end
     */
    public static String removeOuterWhiteSpaces(String dataWithSpaces){
        String startRegex = "\\S{1,}.*$";
        Pattern startPattern = Pattern.compile(startRegex);
        Matcher startMathcher = startPattern.matcher(dataWithSpaces);
        if(!startMathcher.find()){
            return "";
        }
        String noLeftSpace = startMathcher.group(0);

        String endRegex = "^.*\\S{1,}";
        Pattern endpattrn = Pattern.compile(endRegex);
        Matcher endMatcher = endpattrn.matcher(noLeftSpace);
        if(!endMatcher.find()){
            return "";
        }
        String noSpaces = endMatcher.group(0);

        return noSpaces;
    }

    /**
     * @param dataWithSpaces a string with whitespaces
     * @return the string without whitespaces
     */
    public static String removeWhiteSpaces(String dataWithSpaces){
        String[] splitted = dataWithSpaces.split(" ");
        String resultString = "";
        for(String word : splitted){
            if(!word.equals("")){
                resultString += " " + word;
            }
        }
        return removeOuterWhiteSpaces(resultString);
    }

    public static boolean startNewScope(String data){
        Pattern r = Pattern.compile(endLineBrackets);
        Matcher m = r.matcher(data);
        return m.find();
    }

    public static boolean endScope(String data){
        Pattern r = Pattern.compile(CloseBracketLine);
        Matcher m = r.matcher(data);
        return m.find();
    }

    public static boolean MethodDecleration(String data){
        Pattern r = Pattern.compile(MethodDecleration);
        Matcher m = r.matcher(data);
        return m.find();
    }

    public static boolean conditionCall(String data){
        return genericPatternMatcher(data, WHILE) || genericPatternMatcher(data, IF);
    }

    public static boolean commentLine(String data){
        Pattern r = Pattern.compile(CommentLine);
        Matcher m = r.matcher(data);
        return m.find();
    }

    public static boolean genericPatternMatcher(String data,String regex){
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(data);
        return m.find();
    }

    public static String MethodDeclerationName(String data){
        String[] splitted = removeWhiteSpaces(data).split(" ");
        String name = splitted[0];
        if(name.contains("(")){
            return (name.split("\\("))[0];
        }
        return name;
    }

    public static ArrayList<VariableTypes> MethodDeclerationTypes(String data){
        String[] splitted = dataInBrackets(data).split(",");
        ArrayList<VariableTypes> types = new ArrayList<VariableTypes>();
        for(int i = 0; i < splitted.length; i++){
            String typeName = splitted[i].split(" ")[0];
            types.add(VariableTypes.getType(typeName));
        }
        return types;
    }

    public static boolean isDecleration(String data){
        data = removeWhiteSpaces(data);
        String[] splitted = data.split(" ");
        return genericPatternMatcher(splitted[0],Type)
                || genericPatternMatcher(splitted[1],Type);
    }

    public static boolean isPlacement(String data){
        return genericPatternMatcher(data,"=");
    }

    public static boolean isMultiply(String data){
        return genericPatternMatcher(data,",");
    }

    public static void main (String[] args ) {
        Pattern r = Pattern.compile(VariablePlacement);
        String testString = "  =   a346";
        Matcher m = r.matcher(testString);
        if(m.find()){
            System.out.println("sucseed");
        }
        else{
            System.out.println("failed");
        }
        String test = "     aa   aa aa     aa    ";
        System.out.println(removeWhiteSpaces(test));

    }
}
