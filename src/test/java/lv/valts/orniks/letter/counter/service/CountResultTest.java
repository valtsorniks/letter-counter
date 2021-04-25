package lv.valts.orniks.letter.counter.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CountResultTest {

    private static LetterCountingService letterCountingService = new LetterCountingService(LetterCountingService.ENG_ALPHABET);
    private static LinkedHashMap<Character,Integer> inputMap = new LinkedHashMap<>();
    private static Integer total = 21;
    private static CountResult countResult;

    @BeforeAll
    static void populateMap(){

        inputMap.putAll(letterCountingService.getMapPopulatedWithLetters());

        inputMap.put('A',1);
        inputMap.put('c',4);
        inputMap.put('e',5);
        inputMap.put('a',6);
        inputMap.put('x',2);
        inputMap.put('W',3);

        countResult = new CountResult(inputMap,total);
    }

    @Test
    void testNotOrdered(){
        assertEquals(inputMap,countResult.getOriginalMap());
    }
    @Test
    void testEmptyCharRemoving(){
        LinkedHashMap<Character,Integer> expectedMap = new LinkedHashMap<>();

        expectedMap.put('A',1);
        expectedMap.put('a',6);
        expectedMap.put('c',4);
        expectedMap.put('e',5);
        expectedMap.put('W',3);
        expectedMap.put('x',2);

        countResult.setOrdered(CountResult.getOrderedByKeyComparator(false),true,false);
        assertTrue(testLinkedHashMap(expectedMap,countResult.getMap()),"Empty character removing failed");

    }

    @Test
    void testReverseOrder(){
        LinkedHashMap<Character,Integer> expectedMap = new LinkedHashMap<>();

        expectedMap.put('x',2);
        expectedMap.put('W',3);
        expectedMap.put('e',5);
        expectedMap.put('c',4);
        expectedMap.put('A',1);
        expectedMap.put('a',6);

        countResult.setOrdered(CountResult.getOrderedByKeyComparator(true),true,false);
        assertTrue(testLinkedHashMap(expectedMap,countResult.getMap()),"Empty character removing failed");
    }


    @Test
    void testGrouping(){
        LinkedHashMap<Character,Integer> expectedMap = new LinkedHashMap<>();
        expectedMap.put('A',7);
        expectedMap.put('C',4);
        expectedMap.put('E',5);
        expectedMap.put('W',3);
        expectedMap.put('X',2);

        countResult.setOrdered(CountResult.getOrderedByKeyComparator(false),true,true);
        assertTrue(testLinkedHashMap(expectedMap,countResult.getMap()),"Case grouping failed");
    }


    private boolean testLinkedHashMap(LinkedHashMap<Character,Integer> left,LinkedHashMap<Character,Integer> right){

        Iterator<Map.Entry<Character, Integer>> leftIterator = left.entrySet().iterator();
        Iterator<Map.Entry<Character, Integer>> rightIterator = right.entrySet().iterator();

        while(leftIterator.hasNext() && rightIterator.hasNext()){
            Map.Entry<Character, Integer> leftEntry = leftIterator.next();
            Map.Entry<Character, Integer> rightEntry = rightIterator.next();

            if (!leftEntry.equals(rightEntry)){
                return false;
            }
        }

        return !leftIterator.hasNext() && !rightIterator.hasNext();
    }
}