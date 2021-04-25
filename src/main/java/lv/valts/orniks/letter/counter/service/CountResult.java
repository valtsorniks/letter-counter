package lv.valts.orniks.letter.counter.service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CountResult {

    private final Map<Character, Integer> resultMap;
    private LinkedHashMap<Character,Integer> orderedMap;
    private Integer total;

    public CountResult(Map<Character, Integer> map, Integer total){
        this.resultMap = map;
        this.orderedMap = new LinkedHashMap<>(map);
        this.total = total;
    }

    public static Comparator<Map.Entry<Character, Integer>> getOrderedByKeyComparator(boolean reverseOrder){
        //Sort as strings, so that case letters are one after another.
        Comparator<Map.Entry<Character, Integer>> comparator = Map.Entry.comparingByKey(Comparator.comparing(Object::toString,String.CASE_INSENSITIVE_ORDER));
        if (reverseOrder){
            comparator = comparator.reversed();
        }
        return comparator;
    }

    public static Comparator<Map.Entry<Character, Integer>> getOrderedByValueComparator(boolean reverseOrder){
        Comparator<Map.Entry<Character, Integer>> comparator = Map.Entry.comparingByValue();
        if (reverseOrder){
            comparator = comparator.reversed();
        }
        return comparator;
    }

    //Orders by comparator and updates orderedMap, so that the original map stays unchanged
    public void setOrdered(Comparator<Map.Entry<Character, Integer>> comparator, boolean ignoreEmpty, boolean combineCases){
        Stream<Map.Entry<Character, Integer>> stream = resultMap.entrySet().stream();

        if (ignoreEmpty){
            stream = stream.filter(e -> e.getValue() > 0);
        }


        //collect and then sort
        if (combineCases){
            LinkedHashMap<Character, Integer> groupedMap = stream.collect(Collectors.groupingBy(entry -> Character.toUpperCase(entry.getKey()), LinkedHashMap::new, Collectors.summingInt(Map.Entry::getValue)));
            orderedMap = groupedMap.entrySet().stream().sorted(comparator).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(e1, e2) -> e1, LinkedHashMap::new));
        }
        //sort and then collect
        else {
            stream = stream.sorted(comparator);
            orderedMap = stream.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(e1, e2) -> e1, LinkedHashMap::new));
        }

    }

    public Map<Character, Integer> getOriginalMap() {
        return resultMap;
    }

    public LinkedHashMap<Character, Integer> getMap() {
        return orderedMap;
    }

    public Integer getTotal() {
        return total;
    }
}
