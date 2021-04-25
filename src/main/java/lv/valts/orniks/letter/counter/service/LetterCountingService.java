package lv.valts.orniks.letter.counter.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class LetterCountingService {

    public static final String ENG_ALPHABET = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";

    private String alphabet;

    public LetterCountingService(String alphabet) {
        this.alphabet = alphabet;
    }

    public CountResult countFile(File file) throws IOException {

        LineIterator it = FileUtils.lineIterator(file, "UTF-8");
        Map<Character, Integer> letterMap = getMapPopulatedWithLetters();
        Integer totalValidLetters = 0;

        try {
            while (it.hasNext()) {
                String line = it.nextLine();
                char[] chars = line.toCharArray();
                for (char character : chars) {
                    Integer count = letterMap.get(character);
                    //We have all acceptable chars in map, ignore nulls
                    if (count != null) {
                        letterMap.put(character, ++count);
                        totalValidLetters++;
                    }
                }
            }
        } finally {
            it.close();
        }

        return new CountResult(letterMap, totalValidLetters);
    }

    public Map<Character, Integer> getMapPopulatedWithLetters() {
        LinkedHashMap<Character, Integer> alphabetMap = new LinkedHashMap<>();
        char[] characters = alphabet.toCharArray();
        for (char character : characters) {
            alphabetMap.put(character, 0);
        }
        return alphabetMap;
    }


}
