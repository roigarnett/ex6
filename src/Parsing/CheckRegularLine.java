package Parsing;

import Structure.Scope;
import Structure.Variable;
import Structure.VariableTypes;

public class CheckRegularLine {


    public static void main (String[] args ) throws Exception{

        Scope scope = new Scope();
        checkLine(" return ", scope);
        checkLine("  final String  dsc    = true, dsjsd = sdius", scope);
        checkLine("int jsd = 212, dsjc = 2617, hoenc", scope);
        checkLine("String skdsd = ", scope);
        scope.printScopeVariables();

    }


    /**
     * checks the correctness of a line and if correct updates all the variables of the scope.
     * @param line
     * @return
     * @throws Exception
     */
    static void checkLine(String line, Scope scope) throws Exception{
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
        checkName(name,lineType,scope);

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

    private static void checkName(String name, VariableTypes lineType, Scope scope) throws Exception{
        if(lineType != null){
            if(!BasicParsing.variableName(name)){
                throw new Exception("illegal variable name");
            }
            if(!scope.variableNameValid(name)){
                throw new Exception("variable name already exists");
            }
        }
        else{

        }

    }

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
     * extarct placement type from expression
     * @param expression
     * @param scope
     * @return
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

}
