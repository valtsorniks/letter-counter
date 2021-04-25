package lv.valts.orniks.letter.counter.service;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LetterCountingServiceTest {

    private static LetterCountingService letterCountingService = new LetterCountingService(LetterCountingService.ENG_ALPHABET);

    @Test
    void countFile() {
        try {
            URL resource = getClass().getResource("/CountingTest.txt");
            File file = new File(resource.getFile());
            assertNotNull(file,"Could not find test file");

            CountResult countResult = letterCountingService.countFile(file);
            Map<Character, Integer> testMap = letterCountingService.getMapPopulatedWithLetters();
            testMap.put('A',1);
            testMap.put('a',8);
            testMap.put('d',2);
            testMap.put('e',4);
            testMap.put('f',18);
            testMap.put('i',2);
            testMap.put('j',2);
            testMap.put('k',3);
            testMap.put('o',1);
            testMap.put('p',1);
            testMap.put('q',3);
            testMap.put('r',2);
            testMap.put('s',6);
            testMap.put('u',3);
            testMap.put('v',2);
            testMap.put('w',5);
            testMap.put('x',1);
            testMap.put('z',1);

            assertEquals(testMap,countResult.getOriginalMap(),"Input map test failed");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}