package Parsing;

import Structure.MethodDeclaration;
import Structure.Scope;
import Structure.Variable;
import Structure.VariableTypes;

import java.util.ArrayList;

/**
 * this class contains static methods which checks if a line in the code is valid
 */
public class GeneralLineCheck {


    /**
     * checks the correctness of a line and if correct updates all the variables of the scope.
     * @param line a string representing the line
     * @throws Exception if there is somthing wrong with the line
     */
    static void checkRegularLine(String line, Scope scope) throws Exception{
        boolean isFinal;
        VariableTypes lineType = null;
        VariableTypes placementType = null;
        String name = null;

        String data = BasicParsing.removeWhiteSpaces(line);
        isFinal = BasicParsing.isFinal(data);
        if (isFinal){
            data = BasicParsing.removeFinal(data);
        }

        String[] lineSegments;
        lineType = isDeclaration(data);
        if (lineType!=null){
            data = BasicParsing.removeDecleration(data);
            lineSegments = BasicParsing.addSpace(data).split(",");
        }
        else {
            lineSegments = new String[1];
            lineSegments[0] = data;
        }

        //check each line segment and update the scope variables.
        for (String seg : lineSegments) {
            seg = BasicParsing.removeWhiteSpaces(seg);
            boolean isInitialized = isPlacement(isFinal, seg, lineType, scope);

            if(lineType != null){
                addVariable(isFinal, seg, lineType, scope, isInitialized);
            }
        }
    }


    /**
     * this method returns a variable type if
     * @param line a line to check
     * @return a variable type if the line is a declaration, and null if not
     * @throws Exception if there is a problem with the line
     */
    private static VariableTypes isDeclaration(String line) throws Exception{
        VariableTypes type = null;
        boolean isDeclare = BasicParsing.isDecleration(line);
        if(isDeclare){
            type = BasicParsing.declerationType(line);
        }
        return type;
    }

    /**
     * Checks if a given lin is a placement and if so updates the type of the placed value.
     * @param line the given line
     * @return true iff it is a good placement
     * @throws Exception if it is a wrong placement
     */
    private static boolean isPlacement(boolean isFinal, String line, VariableTypes lineType, Scope
            scope ) throws Exception{

        String name = BasicParsing.getName(line);
        if(lineType != null){
            checkName(name,scope);
        }

        boolean isPlacment = BasicParsing.isPlacement(line);

        if(isPlacment){
            VariableTypes placementType = extractPlacementType(line, scope);
            checkPlacementsTypes(isFinal, lineType, name, placementType, scope);
            return true;
        }
        else if(lineType == null){
            throw new Exception("untill whennnnnnnnnnn?");
        }

        return false;

    }

    /**
     * this method check if a variable name is valid in a certain scope
     * @param name the variable name
     * @param scope the current scope
     * @throws Exception if the variable name is valid in a certain scope
     */
    private static void checkName(String name, Scope scope) throws Exception{
        if(!BasicParsing.variableName(name)){
            throw new Exception("illegal variable name");
        }
        if(!scope.variableNameValid(name)){
            throw new Exception("variable name already exists");
        }
    }

    /**
     * gets a type of variable and a type of a placement and checks if the placemnt is possible. If so,
     * adds the variable to the scope
     * @param isFinal whether the variable is declared final
     * @param lineType the type of the declaration
     * @param name the name of the variable
     * @param placementType the type of the placement
     * @param scope the current scope
     * @throws Exception if the placement isn't possible
     */
    private static void checkPlacementsTypes(boolean isFinal, VariableTypes lineType, String
            name, VariableTypes placementType, Scope scope)  throws Exception {
        if(lineType != null){
            if(!VariableTypes.isPlacementPossible(lineType, placementType)){
                throw new Exception("variable type and placement type do not match");
            }
            else{
                scope.addVariable(new Variable(lineType, name, isFinal, true));
            }
        }
        else{
            Variable var = scope.getVariableFromName(name);
            VariableTypes type = var.getType();
            if(!VariableTypes.isPlacementPossible(type, placementType)){
                throw new Exception("variable type and placement type do not match");
            }
            else{
                var.initialize();
            }
        }
    }


    /**
     * extract placement type from expression
     * @param expression the expression
     * @param scope the scope of which this expression lives.
     * @return a type of variable if this expression defines such.
     * @throws Exception if placement is another variable and does not exist and if placement is not
     * recognized
     */
    private static VariableTypes extractPlacementType(String expression, Scope scope) throws Exception{
        expression = BasicParsing.removeWhiteSpaces(expression);
        VariableTypes placementType = BasicParsing.placmentType(expression);
        if (placementType == VariableTypes.OTHER_VAR) {
            String otherVarName = BasicParsing.getVariablePlacmentName(expression);
            Variable placementVar = scope.getVariableFromName(otherVarName);
            if (placementVar == null) {
                throw new Exception("placement variable does not exist");
            }
            if(!placementVar.isInitialized()){
                throw new Exception("placement variable is not initialized");
            }
            placementType = placementVar.getType();
        }
        if (placementType == null) {
            throw new Exception("bad placement of variable");
        }
        return placementType;

    }


    /**
     * Adds a new variable to the relevant scope if this is the line meaning.
     * @param isFinal A boolean indicator - indicates whether the new variable is final or not.
     * @param expression The expression from which to extract the variable declaration.
     * @param lineType The type of the new variable.
     * @param scope The relevant scope
     * @param isInitialized A boolean indicator - indicates whether the new variable is initialized or not.
     * @throws Exception if the line has no meaning and is incorrect.
     */
    private static void addVariable(boolean isFinal, String expression, VariableTypes lineType, Scope
            scope, boolean isInitialized) throws Exception {
        String name = BasicParsing.removeWhiteSpaces(expression);
        if (lineType==null){
            throw new Exception("there is no meaning for the line");
        }
        else {
            scope.addVariable(new Variable(lineType, name, isFinal, isInitialized));
        }
    }


    /**
     * this method checks if a method call is valid
     * @param data a string representing the method call
     * @param scope the current scope
     * @throws Exception if the call isn't valid
     */
    public static void checkMethodCall(String data, Scope scope) throws Exception{
        if(!BasicParsing.methodCall(data)){
            throw new Exception();
        }
        String name = BasicParsing.methodDeclerationName(data);
        ArrayList<Variable> originalVars = scope.getClassScope().getMethodFromName(name).getArgs();
        ArrayList<VariableTypes> calledVars = BasicParsing.methodCallVars(data);
        ArrayList<String> calledVarsNames = BasicParsing.methodCallNames(data);
        if(originalVars.size() != calledVars.size()){
            throw new Exception();
        }
        for(int i = 0; i < originalVars.size(); i++){
            Variable orgVar = originalVars.get(i);
            VariableTypes callType = calledVars.get(i);
            if(callType.equals(VariableTypes.OTHER_VAR)){
                Variable callVar = scope.getVariableFromName(calledVarsNames.get(i));
                if(callVar == null || !callVar.isInitialized()){
                    throw new Exception("called variable is not ininitializd");
                }
                callType = callVar.getType();
            }
            if(!VariableTypes.isPlacementPossible(orgVar.getType(),callType)){
                throw new Exception();
            }
        }
    }

    /**
     * this method checks if a condition declaration is valid
     * @param data a string representing the condition declaration
     * @param scope the current scope
     * @throws Exception if the declaration isn't valid
     */
    public static void checkConditionDecleration(String data, Scope scope) throws Exception{
        if(!BasicParsing.conditionCall(data)){
            throw new Exception();
        }
        ArrayList<String> booleanVariables = BasicParsing.conditionTerm(data);
        for(String varName : booleanVariables){
            Variable var = scope.getVariableFromName(varName);
            if(var == null || !var.isInitialized()){
                throw new Exception();
            }
            if(!VariableTypes.isPlacementPossible(VariableTypes.BOOLEAN,var.getType())){
                throw new Exception();
            }
        }
    }

    /**
     * this method checks if a method declaration is valid
     * @param data a string representing the method declaration
     * @return a new method declaration object (if the declaration is valid)
     * @throws Exception if the declaration isn't valid
     */
    public static MethodDeclaration checkMethodDecleration(String data) throws Exception{
        if(!BasicParsing.methodDecleration(data)){
            throw new Exception("bad method declaration format");
        }
        data = BasicParsing.methodDeclerationData(data);
        String name = BasicParsing.methodDeclerationName(data);
        ArrayList<Variable> vars = BasicParsing.methodDeclerationVars(data);
        return new MethodDeclaration(name,vars);
    }

}
