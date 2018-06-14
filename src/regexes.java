import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regexes {

    static String basicLineCheck = "^\\s*+//+\\.*";
    static String endLineSemicolon = ";$";
    static String INT = "^\\s*+int+\\s{1,}+\\w*";
    static String STRING = "^\\s*+String+\\s{1,}+\\w*";
    static String BOOLEAN = "^\\s*+boolean+\\s{1,}+\\w*";
    static String FLOAT = "^\\s*+float+\\s{1,}+\\w*";
    static String DOUBLE = "^\\s*+double+\\s{1,}+\\w*";
    static String Return = "^\\s*+return+\\s*$";
    static String Placement = "^\\s*+=+\\s*+\\S*+\\s*$";
    static String IntPlacement = "^\\s*+=+\\s*+\\d*+\\s*$";
    static String StringPlacement = "^\\s*+=+\\s*+[a-zA-Z]+\\S*+\\s*$";
    static String BOOLEANPlacement = "^\\s*+=+\\s*+(true|false)+\\s*$";
    static String FLOATorDOUBLEPlacement = "^\\s*+=+\\s*+\\d{1,}+\\.+\\d{1,}+\\s*$";
    static String MethodCall = "^\\s+void+\\s{1,}+[a-zA-Z]+\\S*+\\s*";
    static String ARGUMENTSCALL = "^\\s*+(int|String|boolean|float|double+\\s{1,}+[a-zA-Z],)^+\\S*+\\s*";


    public static void main (String[] args ) {
        Pattern r = Pattern.compile(ARGUMENTSCALL);
        String testString = " int a, ";
        Matcher m = r.matcher(testString);
        if(m.find()){
            System.out.println("sucseed");
        }
        else{
            System.out.println("failed");
        }

    }
}
