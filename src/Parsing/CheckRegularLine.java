package Parsing;

import Structure.Scope;
import Structure.Variable;
import Structure.VariableTypes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckRegularLine {

    static final String semicolonEnd = "(;+\\s*)$";
    static final String startFinal = "^\\s*final\\s";
    static final String declarationString = "^(int|String|boolean|double|float)\\s";
    static final String fullPlacement = "^[a-zA-Z]\\S*\\s*=\\s*\\S*\\s*$";
    static final String VariableNameWithPlacement = "^[a-zA-Z_][a-zA-Z_0-9]*\\s*=";
    static final String VariableName = "^[a-zA-Z_][a-zA-Z_0-9]*";
    static final String OnlyVariableName = "^[a-zA-Z_][a-zA-Z_0-9]*$";


    public static void main (String[] args ) throws Exception{
        Scope scope = new Scope();
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
        boolean isInitialized = false;

        int b;
        String data = regexes.removeWhiteSpaces(line);
        isFinal = startsWithFinal(data);
        if (isFinal){
            data = data.split(startFinal)[1];
        }

        String[] lineSegments;
        lineType = isDeclaration(data);
        if (lineType!=null){
            data = data.split(declarationString)[1];
            lineSegments = data.split(",");
        }
        else {
            lineSegments = new String[1];
            lineSegments[0] = data;
        }

        //check each line segment and update the scope variables.
        for (String seg: lineSegments) {
            seg = regexes.removeWhiteSpaces(seg);
            System.out.println(seg);
            if (!isLegalPlacement(isFinal, seg, lineType, scope)) {
                if (!isOnlyName(isFinal, seg, lineType, scope)) {
                    throw new Exception("not a placement or a name!!!");
                }
                else{
                    System.out.println(" declared a new var and updated scope vars " );
                }
            }
            else {
                System.out.println(" placed a new var and updated scope vars " );
            }

        }
        return false;

    }


    private static boolean startsWithFinal(String line) {

        Pattern p = Pattern.compile(startFinal);
        Matcher m = p.matcher(line);
        return m.find();
    }

    private static VariableTypes isDeclaration(String line) {
        VariableTypes type = null;
        Pattern p = Pattern.compile(declarationString);
        Matcher m = p.matcher(line);
        String typeString;
        boolean isDeclare = m.find();
        if(isDeclare){
            typeString = line.substring(m.start(), m.end()-1);
            type = VariableTypes.getType(typeString);
        }
        return type;
    }




    private static VariableTypes checkPlacement(String line) {
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

    private static boolean isOnlyName(boolean isFinal, String expression, VariableTypes lineType, Scope
            scope) throws Exception {
        String name;
        Pattern p = Pattern.compile(OnlyVariableName);
        Matcher m = p.matcher(expression);
        boolean isName = m.find();
        if (isName) {
            name = expression;
        }
        else {
            throw new Exception("not a valid name for var");
        }
        if (lineType==null){
            throw new Exception("there is no meaning for the line");
        }
        else {
            scope.addVariable(new Variable(lineType, name, isFinal));
        }
        return isName;
    }

    /**
     * Checks if a given lin is a placement and if so updates the type of the placed value.
     * @param line the given line
     * @return true iff it is a good placement
     * @throws Exception if it is a wrong placement
     */
    private static boolean isLegalPlacement(boolean isFinal, String line, VariableTypes lineType, Scope
            scope ) throws Exception{

        Pattern p = Pattern.compile(fullPlacement);
        Matcher m = p.matcher(line);

        if(m.find()){
            String name = null;
            VariableTypes placementType = extractPlacementType(line, scope);
            name = extractVarName(line, name);
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
    private static String extractVarName(String expression, String name) {
        Pattern namePattern = Pattern.compile(VariableName);
        Matcher nameMatch = namePattern.matcher(expression);
        if(nameMatch.find()){
            name = expression.substring(nameMatch.start(), nameMatch.end());
        }
        else {
            System.out.println("what the f*ck");
        }
        return name;
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
        String onlyPlacement;
        VariableTypes placementType = null;
        Pattern nameAndEqualsPattern = Pattern.compile(VariableNameWithPlacement);
        Matcher nameAndEqualsMatch = nameAndEqualsPattern.matcher(expression);
        if (nameAndEqualsMatch.find()) {
            onlyPlacement = expression.split(VariableNameWithPlacement)[1];
            onlyPlacement = regexes.removeWhiteSpaces(onlyPlacement);
            placementType = checkPlacement(onlyPlacement);
            if (placementType == VariableTypes.OTHER_VAR) {
                Variable placementVar = scope.getVariableFromName(onlyPlacement);
                if (placementVar == null) {
                    throw new Exception("placement variable does not exist");
                }
                placementType = placementVar.getType();
            }
            System.out.println(onlyPlacement);
            if (placementType == null) {
                throw new Exception("bad placement of variable!!");
            }
        } else {
            System.out.println("what the f*ck");
        }

        return placementType;
    }

}
