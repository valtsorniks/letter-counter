package lv.valts.orniks.letter.counter;

import javafx.application.Application;
import lv.valts.orniks.letter.counter.service.LetterCountingService;
import lv.valts.orniks.letter.counter.ui.GUI;
import lv.valts.orniks.letter.counter.utils.FileWriter;
import org.apache.commons.cli.*;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

public class Main {

    public static void main(String args[]) throws Exception {

        Options options = new Options();
        options.addOption("i", "input", true, "Input file location");
        options.addOption("c", "characters", true, "Characters to find");
        options.addOption("o", "output", true, "Output file location");
        options.addOption("h","help",false,"Print help");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        }
        catch (Exception e){
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        if (cmd.hasOption("h")){
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "java -jar letter-counter.jar [options]", options );
            System.exit(0);
        }


        if (cmd.getOptions().length == 0) {
            Application.launch(GUI.class);
        } else {
            File file = getFile(cmd);
            if (file == null) {
                System.exit(-1);
            }

            LetterCountingService countingService = new LetterCountingService(LetterCountingService.ENG_ALPHABET);
            Map<Character, Integer> map = countingService.countFile(file).getMap();


            if (cmd.hasOption("c")) {
                String characters = cmd.getOptionValue("c");
                TreeMap<Character, Integer> filteredChars = new TreeMap<>();
                char[] charArray = characters.toCharArray();
                for (char character : charArray) {
                    filteredChars.put(character, map.getOrDefault(character, 0));
                }
                map = filteredChars;

            }

            if (cmd.hasOption("o")) {
                String outputLocation = cmd.getOptionValue("o");
                File outputFile = new File(outputLocation);
                if (!outputFile.exists()) {
                    outputFile.getParentFile().mkdirs();
                }
                FileWriter.writeStringToFile(FileWriter.getMapAsString(map), outputFile);
            }
            else {
                printMap(map);
            }
        }


    }

    private static File getFile(CommandLine cmd) {
        if (cmd.hasOption("i")) {
            String fileLocation = cmd.getOptionValue("i");
            if (fileLocation != null && !fileLocation.isEmpty()) {
                File file = new File(fileLocation);
                if (file.exists() && !file.isDirectory()) {
                    return file;
                }
            }
        }
        else {
            System.err.println("Please provide a input file. Use -i [file location]");
        }
        return null;
    }

    private static void printMap(Map<Character, Integer> map) {

        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }


}
