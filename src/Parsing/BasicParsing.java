package Parsing;
import Structure.*;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasicParsing {


    private static Pattern ENDLINESEMICOLON = Pattern.compile(";\\s*$");
    private static Pattern OPENBRACKETLINE = Pattern.compile("\\{\\s*$");
    private static Pattern CLOSEBRACKETLINE = Pattern.compile("^\\s*\\}\\s*$");

    private static Pattern VARIABLENAME1 = Pattern.compile("^\\s*[a-zA-Z0-9_]{1,}\\s*$");
    private static Pattern VARIABLENAME2 = Pattern.compile("[a-zA-Z0-9]");

    private static Pattern METHODDECLERATION = Pattern.compile("^\\s*void\\s{1,}[a-zA-Z]\\S*\\s*\\(.*\\)\\s*\\{\\s*$");
    private static Pattern RETURN = Pattern.compile("^\\s*RETURN\\s*$");
    private static Pattern WHILE = Pattern.compile("^\\s*while\\s*\\(.*\\)\\s*\\{\\s*$");
    private static Pattern IF = Pattern.compile("^\\s*if\\s*\\(.*\\)\\s*\\{\\s*$");

    private static Pattern BASICBOOLEANEXPRESSION = Pattern.compile("(true|false)");
    private static Pattern BASICINTEXPRESSION = Pattern.compile("^\\s*-{0,1}\\d{1,}\\s*");
    private static Pattern BASICDOUBLEEXPRESSION = Pattern.compile("^\\s*-{0,1}\\d{1,}(\\.{0,1}\\d{1,}|)\\s*");
    private static Pattern BASICSTRINGEXPRESSION = Pattern.compile("^\\s*\".*\"\\s*$");
    private static Pattern BASICCAHREXPRESSION = Pattern.compile("^\\s*\'.{0,1}\'\\s*$");
    private static Pattern BASICVARIABLENAME = Pattern.compile("^[a-zA-Z]\\S*");

    private static Pattern COMMENTLINE = Pattern.compile("^//");
    private static Pattern BLANKLINE = Pattern.compile("^\\s*$");
    private static Pattern METHODCALL = Pattern.compile("[a-zA-Z]\\S*\\s{0,1}\\(.*\\)$");


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


    /**
     * @param data a string we want to check
     * @param regex a regex to match
     * @return true iff the regex metches the data
     */
    private static boolean genericPatternMatcher(String data,Pattern regex){
        Matcher m = regex.matcher(data);
        return m.find();
    }


    /**
     * @param data a string we want to check
     * @return true iff the string starts a new scope
     */
    public static boolean startScope(String data){
        return genericPatternMatcher(data,OPENBRACKETLINE);
    }

    /**
     * @param data a string we want to check
     * @return true iff the string ends a scope
     */
    public static boolean endScope(String data){
        return genericPatternMatcher(data,CLOSEBRACKETLINE);
    }

    /**
     * @param data a string we want to check
     * @return true iff the string is a decleration on a new method
     */
    public static boolean METHODDECLERATION(String data){
        return genericPatternMatcher(data,METHODDECLERATION);
    }

    /**
     * @param data a string we want to check
     * @return true iff the string is a while or an if line
     */
    public static boolean conditionCall(String data){
        return (genericPatternMatcher(data, WHILE) || genericPatternMatcher(data, IF));
    }

    /**
     * @param data a string we want to check
     * @return true iff the line is a comment line
     */
    public static boolean COMMENTLINE(String data){
        return genericPatternMatcher(data,COMMENTLINE);
    }

    /**
     * @param data a string we want to check
     * @return true iff the string is call for a method
     */
    public static boolean METHODCALL(String data){
        data = removeWhiteSpaces(data);
        return genericPatternMatcher(data,METHODCALL) && (!startScope(data));
    }

    /**
     * @param data a string we want to check
     * @return true iff the string is a RETURN call
     */
    public static boolean RETURNCall(String data){
        return genericPatternMatcher(data,RETURN);
    }

    /**
     * @param data a string we want to check
     * @return true iff the string can represent a variable name
     */
    public static boolean variableName(String data){
        return genericPatternMatcher(data,VARIABLENAME1) && genericPatternMatcher(data,VARIABLENAME2);
    }

    public static boolean emptyLine(String data){
        return genericPatternMatcher(data,BLANKLINE);
    }


    /**
     * @param data a string representing a method decletration
     * @return the name of the method
     * @throws Exception if the method name is invalid
     */
    public static String METHODDECLERATIONName(String data) throws Exception{
        String[] splitted = removeWhiteSpaces(data).split(" ");
        String name = splitted[0];
        if(name.contains("(")){
            return (name.split("\\("))[0];
        }
        name = removeWhiteSpaces(name);
        if(!variableName(name)){
            throw new Exception("An invalid method name");
        }
        return name;
    }

    /**
     * @param data string representing a method decleration
     * @return a list of variables the method gets
     * @throws Exception if one of the variable declerations is invalid
     */
    public static ArrayList<Variable> METHODDECLERATIONVars(String data) throws Exception{
        ArrayList<Variable> vars = new ArrayList<Variable>();
        data = removeWhiteSpaces(dataInBrackets(data));
        if(data.equals("")){
            return vars;
        }
        String[] splitted = data.split(",");
        for(int i = 0; i < splitted.length; i++){
            String[] variableData = removeWhiteSpaces(splitted[i]).split(" ");
            boolean isFinal = false;
            if(variableData.length > 3){
                throw new Exception("An error occurod while processing the method's variables");
            }
            if(variableData.length == 3 && !variableData[0].equals("final")){
                throw new Exception("An error occurod while processing the method's variables");
            }
            if(variableData.length == 1){
                throw new Exception();
            }
            if(variableData.length == 3){
                isFinal = true;
            }
            String typeName = variableData[variableData.length - 2];
            String varName = variableData[variableData.length - 1];
            if(!variableName(varName)){
                throw new Exception("A bad variable name - " +
                        varName + " can't be a variable name");
            }
            VariableTypes type = VariableTypes.getType(typeName);
            Variable var = new Variable(type,varName,isFinal,true);
            vars.add(var);
        }
        return vars;
    }

    /**
     * @param data a string representing an if \ while line
     * @return a list of variabel names which need to be declared boolean variables
     * @throws Exception if the condition term is invalid
     */
    public static ArrayList<String> conditionTerm(String data)  throws Exception{
        ArrayList<String> vars = new ArrayList<String>();
        data = dataInBrackets(data);
        String[] spllited = data.split("(\\|\\||&&)");
        for(String term : spllited){
            vars.addAll(conditionTermHelp(term));
        }
        return vars;
    }

    /**
     * @param data a string representing a basic condition term
     * @return a variable name, if the string is a variable name
     * @throws Exception if the string isn't a variable name and isn't a boolean conditon
     */
    private static ArrayList<String> conditionTermHelp(String data) throws Exception{
        ArrayList<String> vars = new ArrayList<String>();
        data = removeWhiteSpaces(data);
        if(genericPatternMatcher(data,BASICBOOLEANEXPRESSION) ||
                genericPatternMatcher(data,BASICDOUBLEEXPRESSION)){
            return vars;
        }
        if(variableName(data)){
            vars.add(data);
            return vars;
        }
        throw new Exception("A while or if loop was given a non - boolean parameter. " +
                data + " is not a boolean condition");
    }

    public static String checkSemicolon(String data) throws Exception{
        if(!genericPatternMatcher(data,ENDLINESEMICOLON)){
            throw new Exception("No semicolon at the end of the line");
        }
        data = data.split(";")[0];
        return data;
    }



    public static void main (String[] args ) throws Exception{
        String str = "A___";
        System.out.println(variableName(str));
    }
}
