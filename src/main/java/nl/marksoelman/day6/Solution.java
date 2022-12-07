package nl.marksoelman.day6;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Solution in time complexity O(n) and space complexity O(n)
 */
public class Solution {

    private static final int FRAME_LENGTH = 14;
    public void run(List<String> inputLines) {
        // Prepare input
        String input = inputLines.get(0);
        int currentUnique = 0;
        Map<Character, Integer> characterCountInFrame = new HashMap<>();

        // Process each character as a rolling frame
        for(int i = 0; i < input.length(); i++) {
            // Add new character that comes into frame
            Character in = input.charAt(i);
            int incrementValue = characterCountInFrame.compute(in, (character, count) -> (count == null) ? 1 : count + 1);
            if(incrementValue == 1) currentUnique++;

            // Remove out-of-frame
            if(i < FRAME_LENGTH) continue;
            Character out = input.charAt(i - FRAME_LENGTH);
            int decrementValue = characterCountInFrame.compute(out, (character, count) -> count - 1 );
            if(decrementValue == 0) currentUnique--;

            // Check solution
            if(currentUnique == FRAME_LENGTH) {
                System.out.println(i+1);
                return;
            }
        }
    }
}
