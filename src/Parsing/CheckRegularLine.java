package Parsing;

import Structure.Scope;
import Structure.Variable;
import Structure.VariableTypes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    static boolean checkLine(String line, Scope scope) throws Exception{
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
            lineSegments = data.split(",");
        }
        else {
            lineSegments = new String[1];
            lineSegments[0] = data;
        }

        //check each line segment and update the scope variables.
        for (String seg : lineSegments) {
            seg = BasicParsing.removeWhiteSpaces(seg);
            if (!isLegalPlacement(isFinal, seg, lineType, scope)) {
                isOnlyName(isFinal, seg, lineType, scope);
            }
            else {
            }
        }
        return false;

    }


    private static VariableTypes isDeclaration(String line) throws Exception{
        VariableTypes type = null;
        boolean isDeclare = BasicParsing.isDecleration(line);
        if(isDeclare){
            type = BasicParsing.declerationType(line);
        }
        return type;
    }


    private static VariableTypes checkPlacement(String line) throws Exception{
        VariableTypes placementType = null;
        String IntPlacement = "^\\s*\\d*\\s*$";
        String StringPlacement = "^\\s*\\w*\\s*$";
        String BOOLEANPlacement = "^\\s*(true|false)\\s*$";
        String FLOATorDOUBLEPlacement = "^\\s*\\d{1,}\\.\\d{1,}\\s*$";
        String VariablePlacement = "^\\s*[a-zA-Z]\\S*\\s*$";

        String[] placements = {IntPlacement, StringPlacement, BOOLEANPlacement, FLOATorDOUBLEPlacement,
                VariablePlacement};

        for (String placement: placements){
            Pattern p = Pattern.compile(placement);
            Matcher m = p.matcher(line);
            if (m.find()){
                if(placement.equals(IntPlacement)){
                    placementType = VariableTypes.getType("int");
                }
                else if(placement.equals(StringPlacement)){
                    placementType = VariableTypes.getType("String");
                }
                else if(placement.equals(BOOLEANPlacement)){
                    placementType = VariableTypes.getType("boolean");
                }
                else if(placement.equals(FLOATorDOUBLEPlacement)){
                    placementType = VariableTypes.getType("floatOrDouble");
                }
                else if(placement.equals(VariablePlacement)){
                    placementType = VariableTypes.getType("otherVar");
                }
                break;
            }
        }
        return placementType;

    }


    private static boolean checkNameAndPlacementsTypes(boolean isFinal, VariableTypes lineType, String
            name, VariableTypes placementType, Scope scope)  throws Exception {

        Variable varInScope = scope.getVariableFromName(name);
        //no variable with such name in scope
        if(varInScope==null){
            if(lineType!=null){
                if(!VariableTypes.isPlacementPossible(lineType, placementType)){
                    throw new Exception("variable type and placement type do not match");
                }
                else{
                    scope.addVariable(new Variable(lineType, name, isFinal, true));
                    return true;
                }

            }
            else {
                throw new Exception("var does not exist!!");
            }
        }

        //there is another variable with this name in this scope.
        else {

            if(lineType!=null){
                throw new Exception("you cant declare a variable that's already been declared");
            }
            else if (!VariableTypes.isPlacementPossible(varInScope.getType(), placementType)){
                throw new Exception("variable type and placement type do not match");
            }
            else{
                varInScope.initialize();
            }
        }
        return true;
    }


    private static void isOnlyName(boolean isFinal, String expression, VariableTypes lineType, Scope
            scope) throws Exception {
        String name = BasicParsing.removeWhiteSpaces(expression);
        if (lineType==null){
            throw new Exception("there is no meaning for the line");
        }
        else {
            scope.addVariable(new Variable(lineType, name, isFinal));
        }
    }

    /**
     * Checks if a given lin is a placement and if so updates the type of the placed value.
     * @param line the given line
     * @return true iff it is a good placement
     * @throws Exception if it is a wrong placement
     */
    private static boolean isLegalPlacement(boolean isFinal, String line, VariableTypes lineType, Scope
            scope ) throws Exception{

        boolean isPlacment = BasicParsing.isPlacment(line);

        if(isPlacment){
            VariableTypes placementType = extractPlacementType(line, scope);
            String name = BasicParsing.getName(line);
            checkNameAndPlacementsTypes(isFinal, lineType, name, placementType, scope);
            return true;
        }

        return false;

    }

    /**
     * extracts the variable name from a given expression
     * @param expression
     * @param name
     * @return
     */
//    private static String extractVarName(String expression) throws Exception {
//        Pattern namePattern = Pattern.compile(VariableName);
//        Matcher nameMatch = namePattern.matcher(expression);
//        if(nameMatch.find()){
//            String name = expression.substring(nameMatch.start(), nameMatch.end());
//            return name;
//        }
//        throw new Exception("bad variable name");
//
//    }

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
            placementType = placementVar.getType();
        }
        if (placementType == null) {
            throw new Exception("bad placement of variable!!");
        }
        return placementType;

    }

}
