package oop.ex6.main;
import Structure.*;
import Parsing.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.io.File;

/**
 * The "main" class of the program. extracts data, removes lines which are not interesting (comments and
 * empty lines) and runs the whole program.
 */
public class Sjavac {

    /**
     * this is the main method which runs the program
     * @param args the program arguments
     */
    public static void main(String [] args){
        try{
            if(args.length != 1){
                throw new IOException("The system cannot find the path specified");
            }
            File sourceFile = new File(args[0]);
            ArrayList<String> data = extractData(sourceFile);
            data = removeEmptyLines(data);
            data = removeComments(data);
            ClassScopeSingleton scope = ScopeFactory.createClassScope(data);
            CheckScopes.checkClassScope(scope);
            System.out.println("0");
        }
        catch (IOException exception){
            System.out.println("2");
            System.err.println(exception.getMessage());
        }
        catch (Exception exception){
            System.out.println("1");
            System.err.println(exception.getMessage());
        }

    }

    /**
     * @param file the Sjava file
     * @return a list of Strings representing the text from the file
     * @throws java.io.IOException if there is any IO problem
     */
    private static ArrayList<String> extractData(File file) throws IOException{
        ArrayList<String> data = new ArrayList<String>();
        FileReader fileReader = new FileReader(file);
        BufferedReader br = new BufferedReader(fileReader);
        String line = br.readLine();
        while (line != null) {
            String currentLine = line;
            data.add(currentLine);
            line = br.readLine();
        }
        return data;
    }

    /**
     * @param data a list of strings, the content of the file
     * @return the list without empty lines
     */
    private static ArrayList<String> removeEmptyLines(ArrayList<String> data){
        ArrayList<String> newData = new ArrayList<String>();
        for(String line : data){
            if(!BasicParsing.emptyLine(line)){
                newData.add(line);
            }
        }
        return newData;
    }

    /**
     * @param data a list of strings, the content of the file
     * @return the list without comment lines
     */
    private static ArrayList<String> removeComments(ArrayList<String> data){
        ArrayList<String> newData = new ArrayList<String>();
        for(String line : data){
            if(!BasicParsing.commentLine(line)){
                newData.add(line);
            }
        }
        return newData;
    }


}
