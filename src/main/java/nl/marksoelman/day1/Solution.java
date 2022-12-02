package nl.marksoelman.day1;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    public void run(List<String> inputLines) {
        List<Long> elfCalories = new ArrayList<>(List.of(0L));
        for(String input : inputLines) {
            if(input.length() == 0) {
                elfCalories.add(0L); // prepare next item in list to 0
                continue;
            }

            elfCalories.set(elfCalories.size()-1, elfCalories.get(elfCalories.size()-1) + Long.parseLong(input));
        }

        Long solution = elfCalories.stream().sorted((a, b) -> (int) (b-a)).limit(3).reduce(0L, Long::sum);
        System.out.println(solution);
    }

}
