package ex6.oop.main;
import Structure.ClassScope;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;

public class SJava {

    private ClassScope ClassScope = new ClassScope();

    public static void main(String [] args){
        try{
            File sourceFile = new File(args[0]);
            ArrayList<String> data = extractData(sourceFile);
            removeEmptyLines(data);
            data = basicParsing(data);
            for(String line : data){
                System.out.println(line);
            }
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

    private static ArrayList<String> basicParsing(ArrayList<String> data) throws Exception{
        ArrayList<String> parsedData = new ArrayList<String>();
        String pattern = "\\s*+//+\\.*";
        Pattern r = Pattern.compile(pattern);
        for(String line : data){
            Matcher m = r.matcher(line);
            if(m.find()){
                parsedData.add(line.split(pattern)[1]);
            }
            else{
                throw new Exception();
            }
        }
        return parsedData;
    }

    public ClassScope getClassScope(){
        return ClassScope;
    }

}
