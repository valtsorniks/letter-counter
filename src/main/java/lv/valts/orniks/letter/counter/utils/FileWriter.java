package lv.valts.orniks.letter.counter.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class FileWriter {


    public static boolean writeStringToFile(String text, File file){
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(text);
            writer.close();
            return true;
        } catch (IOException ex) {
            System.err.println("Could not print to file, " + ex.getMessage());
            return false;
        }
    }

    public static String getMapAsString(Map<Character, Integer> map){
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            stringBuilder.append(entry.getKey() + " - " + entry.getValue());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
