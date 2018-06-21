package ex6.oop.main;
import Structure.*;
import Parsing.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;

public class SJava {

    public static void main(String [] args){
        try{
            File sourceFile = new File(args[0]);
            ArrayList<String> data = extractData(sourceFile);
            removeEmptyLines(data);
            removeComments(data);
            ClassScope scope = ScopeFactory.createClassScope(data);
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

    private static ArrayList<String> extractData(File file) throws java.io.IOException{
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

    private static void removeEmptyLines(ArrayList<String> data){
        for(String line : data){
            if(line.equals("")){
                data.remove(line);
            }
        }
    }

    private static void removeComments(ArrayList<String> data) throws Exception{
        for(String line : data){
            if(BasicParsing.commentLine(line)){
                data.remove(line);
            }
        }
    }


}
