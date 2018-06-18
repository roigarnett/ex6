package ex6.oop.main;
import Structure.Scope;
import Parsing.*;

import java.io.BufferedReader;
import java.io.FileReader;
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
            Scope scope = ScopeFactory.createClassScope(data);
            System.out.println(scope);
        }
        catch (Exception exception){
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

    private static ArrayList<String> removeComments(ArrayList<String> data) throws Exception{
        ArrayList<String> parsedData = new ArrayList<String>();
        for(String line : data){
            if(!regexes.commentLine(line)){
                parsedData.add(line);
            }
        }
        return parsedData;
    }


}
