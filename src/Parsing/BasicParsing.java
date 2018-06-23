package Parsing;
import Structure.*;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that responsible for simple checking and parsing of line parts.
 */
public class BasicParsing {

    //patterns regarding the folding of line: brackets, semicolon and curled brackets.
    private static Pattern EMPTY_BRACKETS = Pattern.compile("^.*\\(\\s*\\).*$");
    private static Pattern ENDLINE_SEMICOLON = Pattern.compile(";\\s*$");
    private static Pattern OPEN_BRACKET_LINE = Pattern.compile("\\{\\s*$");
    private static Pattern CLOSE_BRACKET_LINE = Pattern.compile("^\\s*\\}\\s*$");

    //patterns regarding use and declaration of a variable name.
    private static Pattern VARIABLENAME1 = Pattern.compile("^\\s*[a-zA-Z][a-zA-Z0-9_]*\\s*$");
    private static Pattern VARIABLENAME2 = Pattern.compile("^\\s*[_][a-zA-Z0-9_]{1,}\\s*$");
    private static Pattern FINAL = Pattern.compile("^\\s*final\\s");
    private static Pattern DECLERATION = Pattern.compile("^(int|String|boolean|double|char)\\s");

    //patterns regarding condition, method declarations and calls skeleton.
    private static Pattern METHODDECLERATION = Pattern.compile("^\\s*void\\s{1,}[a-zA-Z]\\S*\\s*\\(.*\\)\\s*\\{\\s*$");
    private static Pattern WHILE = Pattern.compile("^\\s*while\\s*\\(.*\\)\\s*\\{\\s*$");
    private static Pattern IF = Pattern.compile("^\\s*if\\s*\\(.*\\)\\s*\\{\\s*$");
    private static Pattern METHOD_CALL = Pattern.compile("[a-zA-Z]\\S*\\s{0,1}\\(.*\\)$");

    //pattern that checks a return statement.
    private static Pattern RETURN = Pattern.compile("^\\s*return\\s*$");

    //patterns regarding the correctness of a placement.
    private static Pattern BASIC_BOOLEAN_EXPRESSION =
            Pattern.compile("(^\\s*true\\s*$|^\\s*false\\s*$)");
    private static Pattern BASIC_INT_EXPRESSION =
            Pattern.compile("(^|\\s)\\s*-{0,1}\\d{1,}\\s*($|\\s)");
    private static Pattern BASIC_DOUBLE_EXPRESSION =
            Pattern.compile("(^|\\s)\\s*-{0,1}\\d{1,}\\.\\d{1,}\\s*($|\\s)");
    private static Pattern BASIC_STRING_EXPRESSION =
            Pattern.compile("(^|\\s)\\s*\".*\"\\s*($|\\s)");
    private static Pattern BASIC_CHAR_EXPRESSION =
            Pattern.compile("(^|\\s)\\s*\'.{0,1}\'\\s*($|\\s)");

    // patterns regarding strings we would want to ignore from.
    private static Pattern SPACE_IN_MIDDLE = Pattern.compile("\\S\\s{1,}\\S");
    private static Pattern COMMENT_LINE = Pattern.compile("^//");
    private static Pattern BLANK_LINE = Pattern.compile("^\\s*$");


    /**
     * @param dataWithBrackets a string containing brackets
     * @return the characters inside the brackets
     * @throws Exception if there to many brackets in one line.
     */
    private static String dataInBrackets(String dataWithBrackets) throws Exception{
        if (genericPatternMatcher(dataWithBrackets, EMPTY_BRACKETS)){
            return "";
        }
        String[] splitted = dataWithBrackets.split("\\(");
        if(splitted.length > 2){
            throw new Exception("too many brackets");
        }
        String[] data = (splitted[1]).split("\\)");
        if(data.length > 2){
            throw new Exception("too many brackets");
        }
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
            if(!genericPatternMatcher(word,BLANK_LINE)){
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
     * @return true if the string can represent a boolean value
     */
    private static boolean basicBooleanExpression(String data){
        return genericPatternMatcher(data, BASIC_BOOLEAN_EXPRESSION) &&
                !genericPatternMatcher(data, SPACE_IN_MIDDLE);
    }

    /**
     * @param data a string we want to check
     * @return true if the string can represent a int value
     */
    private static boolean basicIntExpression(String data){
        return genericPatternMatcher(data, BASIC_INT_EXPRESSION) &&
                !genericPatternMatcher(data, SPACE_IN_MIDDLE);
    }

    /**
     * @param data a string we want to check
     * @return true if the string can represent an souble value
     */
    private static boolean basicDoubleExpression(String data){
        return genericPatternMatcher(data, BASIC_DOUBLE_EXPRESSION) &&
                !genericPatternMatcher(data, SPACE_IN_MIDDLE);
    }

    /**
     * @param data a string we want to check
     * @return true if the string can represent a string value
     */
    private static boolean basicStringExpression(String data){
        return genericPatternMatcher(data, BASIC_STRING_EXPRESSION);
    }

    /**
     * @param data a string we want to check
     * @return true if the string can represent a char value
     */
    private static boolean basicCharExpression(String data){
        return genericPatternMatcher(data, BASIC_CHAR_EXPRESSION) &&
                !genericPatternMatcher(data, SPACE_IN_MIDDLE);
    }


    /**
     * @param data a string we want to check
     * @return true iff the string starts a new scope
     */
    public static boolean startScope(String data){
        return genericPatternMatcher(data, OPEN_BRACKET_LINE);
    }

    /**
     * @param data a string we want to check
     * @return true iff the string ends a scope
     */
    public static boolean endScope(String data){
        return genericPatternMatcher(data, CLOSE_BRACKET_LINE);
    }

    /**
     * @param data a string we want to check
     * @return true iff the string is a decleration on a new method
     */
    public static boolean methodDecleration(String data){
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
    public static boolean commentLine(String data){
        return genericPatternMatcher(data, COMMENT_LINE);
    }

    /**
     * @param data a string we want to check
     * @return true iff the string is call for a method
     */
    public static boolean methodCall(String data){
        data = removeWhiteSpaces(data);
        return genericPatternMatcher(data, METHOD_CALL) && (!startScope(data));
    }

    /**
     * @param data a string we want to check
     * @return true iff the string is a RETURN call
     */
    public static boolean returnCall(String data){
        return genericPatternMatcher(data,RETURN);
    }

    /**
     * @param data a string we want to check
     * @return true iff the string can represent a variable name
     */
    public static boolean variableName(String data){
        return genericPatternMatcher(data,VARIABLENAME1) || genericPatternMatcher(data,VARIABLENAME2);
    }

    /**
     * @param data a string we want to check
     * @return true iff the string is empty
     */
    public static boolean emptyLine(String data){
        return genericPatternMatcher(data, BLANK_LINE);
    }

    /**
     * @param data a string we want to check
     * @return true iff the string starts with the word final
     */
    public static boolean isFinal(String data){
        return genericPatternMatcher(data,FINAL);
    }

    /**
     * @param data a string we want to check.
     * @return true iff the data is a declaration of a new variable.
     */
    public static boolean isDecleration(String data){
        return genericPatternMatcher(data,DECLERATION);

    }

    /**
     * @param data a string we want to check.
     * @return true iff the string is a placement of new variable.
     */
    public static boolean isPlacement(String data){
        return data.contains("=");
    }




    /**
     * @param data a string representing a method decletration
     * @return the name of the method
     * @throws Exception if the method name is invalid
     */
    public static String methodDeclerationName(String data) throws Exception{
        String[] splitted = removeWhiteSpaces(data).split("\\(");
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
    public static ArrayList<Variable> methodDeclerationVars(String data) throws Exception{
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
        if(genericPatternMatcher(data, BASIC_BOOLEAN_EXPRESSION) ||
                genericPatternMatcher(data, BASIC_DOUBLE_EXPRESSION) ||
                genericPatternMatcher(data, BASIC_INT_EXPRESSION)){
            return vars;
        }
        if(variableName(data)){
            vars.add(data);
            return vars;
        }
        throw new Exception("A while or if loop was given a non - boolean parameter. " +
                data + " is not a boolean condition");
    }

    /**
     * @param data a string representing a basic SJave line
     * @return the string without a semicolon at the end
     * @throws Exception if there is no semicolon at the end of the string
     */
    public static String checkSemicolon(String data) throws Exception{
        if(!genericPatternMatcher(data, ENDLINE_SEMICOLON)){
            throw new Exception("No semicolon at the end of the line");
        }
        String[] splitted = removeWhiteSpaces(data).split(";");
        if(splitted.length > 1){
            throw new Exception("can't have more than one semicolon");
        }
        return splitted[0];
    }

    
    public static VariableTypes placmentType(String data) throws Exception{
        data = removeWhiteSpaces(data);
        String[] splitted = data.split("=");
        if(!variableName(removeWhiteSpaces(splitted[0]))){
            throw new Exception("variable name is not valid");
        }
        if(splitted.length != 2){
            throw new Exception("could not process placment");
        }
        return getType(removeWhiteSpaces(splitted[1]));
    }

    private static VariableTypes getType(String data){
        data = removeWhiteSpaces(data);
        if(basicIntExpression(data)){
            return VariableTypes.INT;
        }
        if(basicCharExpression(data)){
            return VariableTypes.CHAR;
        }
        if(basicBooleanExpression(data)){
            return VariableTypes.BOOLEAN;
        }
        if(basicStringExpression(data)){
            return VariableTypes.STRING;
        }
        if(basicDoubleExpression(data)){
            return VariableTypes.DOUBLE;
        }
        return VariableTypes.OTHER_VAR;
    }

    public static String getVariablePlacmentName(String data) throws Exception{
        data = removeWhiteSpaces(data);
        String[] splitted = data.split("=");
        if(splitted.length != 2){
            throw new Exception("could not process placment");
        }
        return removeWhiteSpaces(splitted[1]);
    }

    public static String removeFinal(String data) throws Exception{
        if(!isFinal(data)){
            throw new Exception("there is not final in this line");
        }
        data = data.split("final")[1];
        return removeWhiteSpaces(data);
    }

    public static String removeDecleration(String data) throws Exception{
        data = data.split(DECLERATION.pattern())[1];
        return removeWhiteSpaces(data);
    }

    public static String getName(String data) throws Exception{
        data = removeWhiteSpaces(data);
        String nameString = null;
        if(isPlacement(data)){
            nameString = removeWhiteSpaces(data.split("=")[0]);
        }
        else{
            nameString = data;
        }
        if(!variableName(nameString)){
            throw new Exception("bad variable name");
        }
        return nameString;
    }

    public static VariableTypes declerationType(String data) throws Exception{
        if(!isDecleration(data)){
            throw new Exception("not a decleration type");
        }
        data = data.split(" ")[0];
        return VariableTypes.getType(removeWhiteSpaces(data));
    }

    public static String methodDeclerationData(String data) throws Exception{
        if(!methodDecleration(data)){
            throw new Exception("bad method decleration");
        }
        return removeWhiteSpaces(data.split("void")[1]);
    }

    public static ArrayList<VariableTypes> methodCallVars(String data) throws Exception{
        ArrayList<VariableTypes> types = new ArrayList<VariableTypes>();
        data = dataInBrackets(data);
        if(data.equals("")){
            return types;
        }
        String[] splitted = data.split(",");
        for(int i = 0; i < splitted.length; i++){
            String var = removeWhiteSpaces(splitted[i]);
            types.add(getType(var));
        }
        return types;
    }

    public static ArrayList<String> methodCallNames(String data) throws Exception{
        ArrayList<String> names = new ArrayList<String>();
        data = dataInBrackets(data);
        if(data.equals("")){
            return names;
        }
        String[] splitted = data.split(",");
        for(int i = 0; i < splitted.length; i++){
            String var = removeWhiteSpaces(splitted[i]);
            names.add(var);
        }
        return names;
    }

    public static String addSpace(String data){
        return data + " ";
    }

}
