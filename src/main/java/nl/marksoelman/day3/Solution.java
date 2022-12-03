package nl.marksoelman.day3;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
    public void run(List<String> inputLines) {
        int totalPrioritySum = 0;
        for(int group = 0; group < inputLines.size() / 3; group++) {
            String first = inputLines.get(group * 3);
            String second = inputLines.get(group * 3 + 1);
            String third = inputLines.get(group * 3 + 2);
            var firstChars = createCharSet(first);
            var secondChars = createCharSet(second);
            var thirdChars = createCharSet(third);

            // Get characters
            totalPrioritySum += computeIntersection(firstChars, secondChars, thirdChars).stream()
                    .mapToInt(this::toPriority)
                    .sum();
        }

        System.out.println(totalPrioritySum);
    }

    private Set<Character> createCharSet(String input) {
        return IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toSet());
    }

    @SafeVarargs
    private Set<Character> computeIntersection(Set<Character>... set) {
        Set<Character> result = new HashSet<>(set[0]);
        Arrays.stream(set).forEach(result::retainAll);
        return result;
    }

    private int toPriority(Character character) {
        // uppercase
        if(character.toString().equals(character.toString().toUpperCase())) {
            return character - 'A' + 27;
        }
        return character - 'a' + 1;
    }
}
