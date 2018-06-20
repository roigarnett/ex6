package Parsing;
import Structure.*;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regexes {


    private static Pattern endLineSemicolon = Pattern.compile(";\\s*$");
    private static Pattern OpenBracketLine = Pattern.compile("\\{\\s*$");
    private static Pattern CloseBracketLine = Pattern.compile("^\\s*\\}\\s*$");

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

    private static Pattern MethodDecleration = Pattern.compile("^\\s*void\\s{1,}[a-zA-Z]\\S*\\s*\\(.*\\)\\s*\\{\\s*$");
    private static Pattern Return = Pattern.compile("^\\s*return\\s*$");
    private static Pattern WHILE = Pattern.compile("^\\s*while\\s*\\(.*\\)\\s*\\{\\s*$");
    private static Pattern IF = Pattern.compile("^\\s*if\\s*\\(.*\\)\\s*\\{\\s*$");

    private static Pattern BasicBooleanExpression = Pattern.compile("(true|false)");
    private static Pattern BasicCompareExpression = Pattern.compile("(==|<|>|<=|>=)");
    private static Pattern BasicVariableName = Pattern.compile("^[a-zA-Z]\\S*");

    private static Pattern CommentLine = Pattern.compile("^\\s*//");
    private static Pattern MethodCall = Pattern.compile("[a-zA-Z]\\S*\\s{0,1}\\(.*\\)$");

    static String ORCondition = ".*||.*";
    static String ANDCondition = ".*&&.*";

    /**
     * @param dataWithBrackets a string containing brackets
     * @return the characters inside the brackets
     */
    private static String dataInBrackets(String dataWithBrackets){
        String[] splitted = dataWithBrackets.split("\\(");
        String[] data = (splitted[1]).split("\\)");
        return data[0];
    }

    /**
     * @param dataWithSpaces a string
     * @return the string without whitespaces at the beggining and end
     */
    private static String removeOuterWhiteSpaces(String dataWithSpaces){
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




    private static boolean genericPatternMatcher(String data,Pattern regex){
        Matcher m = regex.matcher(data);
        return m.find();
    }



    public static boolean startScope(String data){
        return genericPatternMatcher(data,OpenBracketLine);
    }

    public static boolean endScope(String data){
        return genericPatternMatcher(data,CloseBracketLine);
    }

    public static boolean MethodDecleration(String data){
        return genericPatternMatcher(data,MethodDecleration);
    }

    public static boolean conditionCall(String data){
        return (genericPatternMatcher(data, WHILE) || genericPatternMatcher(data, IF));
    }

    public static boolean commentLine(String data){
        return genericPatternMatcher(data,CommentLine);
    }

    public static boolean methodCall(String data){
        data = removeWhiteSpaces(data);
        return genericPatternMatcher(data,MethodCall) && (!startScope(data));
    }

    public static boolean returnCall(String data){
        return genericPatternMatcher(data,Return);
    }

    public static boolean semicolon(String data){
        return genericPatternMatcher(data,endLineSemicolon);
    }



    public static String MethodDeclerationName(String data){
        String[] splitted = removeWhiteSpaces(data).split(" ");
        String name = splitted[0];
        if(name.contains("(")){
            return (name.split("\\("))[0];
        }
        return name;
    }

    public static ArrayList<Variable> MethodDeclerationVars(String data) throws Exception{
        String[] splitted = dataInBrackets(data).split(",");
        ArrayList<Variable> vars = new ArrayList<Variable>();
        for(int i = 0; i < splitted.length; i++){
            String[] variableData = splitted[i].split(" ");
            boolean isFinal = false;
            if(variableData.length > 3){
                throw new Exception();
            }
            if(variableData.length == 3 && !variableData[0].equals("final")){
                throw new Exception();
            }
            if(variableData.length == 1){
                throw new Exception();
            }
            if(variableData.length == 3){
                isFinal = true;
            }
            String typeName = variableData[variableData.length - 2];
            String varName = variableData[variableData.length - 1];
            VariableTypes type = VariableTypes.getType(typeName);
            Variable var = new Variable(type,varName,isFinal,true);
            vars.add(var);
        }
        return vars;
    }

    public static ArrayList<String> conditionTerm(String data){
        ArrayList<String> vars = new ArrayList<String>();
        data = dataInBrackets(data);
        String[] spllited = data.split("(\\|\\||&&)");
        for(String term : spllited){
            vars.addAll(conditionTermHelp(term));
        }
        return vars;
    }

    public static ArrayList<String> conditionTermHelp(String data){
        ArrayList<String> vars = new ArrayList<String>();
        data = removeWhiteSpaces(data);
        if(genericPatternMatcher(data,BasicBooleanExpression)){
            return vars;
        }
        String[] splitted = data.split("(==|<|>|<=|>=)");
        for(String name : splitted){
            if(genericPatternMatcher(removeWhiteSpaces(name),BasicVariableName)){
                vars.add(removeWhiteSpaces(name));
            }
        }
        return vars;
    }




    public static void main (String[] args ) {
        ArrayList<String> al = conditionTerm("while(a == 5 ||   4 - 1 < dwdg  && true)") ;
        for(String s : al){
            System.out.println(s);
        }
    }
}
