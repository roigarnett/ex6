package Parsing;

import Structure.VariableTypes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckRegularLine {

    static final String semicolonEnd = "(;+\\s*)$";
    static final String startFinal = "^\\s*final\\s";
    static final String declarationString = "^(int|String|boolean|double|float)\\s";
    static final String fullPlacement = "^[a-zA-Z]\\S*\\s*=\\s*\\S*\\s*$";
    static final String VariableNameWithPlacement = "^[a-zA-Z_][a-zA-Z_0-9]*\\s*=";
    static final String OnlyVariableName = "^[a-zA-Z_][a-zA-Z_0-9]*$";



    /**
     * checks the correctness of a line and if correct saves: name of the variable and it"s type, and the
     * placement type if it has been placed.
     * @param line
     * @return
     * @throws Exception
     */
    static boolean checkLine(String line) throws Exception{
        boolean isFinal;
        VariableTypes lineType = null;
        VariableTypes placementType = null;
        String name = null;
        boolean isInitialized = false;


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
        for (String seg: lineSegments) {

            seg = regexes.removeWhiteSpaces(seg);


            System.out.println(seg);
            if (!isPlacement(seg)) {
                if (!isOnlyName(seg)) {
                    throw new Exception("not a placement or a name!!!");
                }
                else{
                    System.out.println("declaring of var " );
                }
            }
            else {
                System.out.println(" placement a new var " );
            }

        }



        return false;

    }

    public static void main (String[] args ) throws Exception{
        checkLine(" final String  dsc    = true, reas ,fsdf = 32233, djdjd = 327832, fidjfd==dsjf, " +
                "fjfd=fdks" +
                ".sdj" +
                " ");

    }

    public static boolean endsWithSemicolon(String line) throws Exception {

        Pattern p = Pattern.compile(semicolonEnd);
        Matcher m = p.matcher(line);
        if (m.find()){
            return true;
        }
        else {
            throw new Exception("line does not end with ;");
        }
    }

    public static boolean startsWithFinal(String line) {

        Pattern p = Pattern.compile(startFinal);
        Matcher m = p.matcher(line);
        return m.find();
    }

    public static VariableTypes isDeclaration(String line) {
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

    /**
     * Checks if a given lin is a placement and if so updates the type of the placed value.
     * @param line the given line
     * @return true iff it is a good placement
     * @throws Exception if it is a wrong placement
     */
    private static boolean isPlacement(String line) throws Exception{

        Pattern p = Pattern.compile(fullPlacement);
        Matcher m = p.matcher(line);

        if(m.find()){

            Pattern namePattern = Pattern.compile(VariableNameWithPlacement);
            Matcher nameMatch = namePattern.matcher(line);

            if(nameMatch.find()){
                name = line.substring(nameMatch.start(), nameMatch.end());
            }
            else {
                System.out.println("what the f*ck");
            }
            String onlyPlacement = line.split(VariableNameWithPlacement)[1];
            System.out.println(onlyPlacement);
            if(checkPlacement(onlyPlacement)!=null){
                throw new Exception("bad placement of variable!!");
            }


            return true;
        }
        return false;
    }

    private static VariableTypes checkPlacement(String line) {
        VariableTypes placementType = null;
        boolean isFound = false;
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
                isFound = true;
                break;
            }
        }
        return placementType;

    }

    private static boolean isOnlyName(String line) {
        Pattern p = Pattern.compile(OnlyVariableName);
        Matcher m = p.matcher(line);
        boolean isName = m.find();
        if (isName) {
            name = line;
        }
        return isName;
    }

//    public String toString(){
//        return type.toString() + " " + name + " " + " initialized with " + placementType + " final? " +
//                isFinal;
//    }
}




//    public static boolean findStringInLine (String line, String strToFind, String errorString) throws
//            Exception{
//        Pattern p = Pattern.compile(strToFind);
//        Matcher m = p.matcher(line);
//        if (m.find()){
//            return true;
//        }
//        else {
//            throw new Exception(errorString);
//        }
//    }
