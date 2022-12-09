package nl.marksoelman.day9;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
    public void run(List<String> inputLines) {
        // Parse input
        List<Instruction> instructions = inputLines.stream()
                .flatMap(line -> IntStream.range(0, Integer.parseInt(line.split(" ")[1])).mapToObj(i -> line.split(" ")[0]))
                .map(instruction -> new Instruction(instruction.charAt(0)))
                .toList();

        List<Position> rope = createRope();

        Set<String> tailVisited = new HashSet<>(List.of("0,0"));
        instructions.forEach(instruction -> updateRope(rope, instruction, tailVisited));

        System.out.println(tailVisited.size());
    }
    private void updateRope(List<Position> rope, Instruction instruction, Set<String> tailVisited) {
        // Update each rope segment as a pair of (head, tail)
        for(int idx = 0; idx < rope.size() -1 ; idx++) {
            if(idx == 0) {
                // Move the head by instruction
                rope.get(0).moveByInstruction(instruction);
            }
            // Update the tail by motion dynamics
            updatePosition(rope.get(idx), rope.get(idx+1));
        }
        tailVisited.add(rope.get(rope.size()-1).x + "," + rope.get(rope.size()-1).y);
    }

    private void updatePosition(Position newHead, Position tail) {
        int dx = newHead.x - tail.x;
        int dy = newHead.y - tail.y;
        if(dx == 0 || dy == 0) {
            // only in 1 direction
            if(Math.abs(dx) == 2) {
                // Difference in x is too big, so close the gap
                tail.x = (dx > 0) ? tail.x+1 : tail.x-1;
            } else if(Math.abs(dy) == 2) {
                // difference in y is too big, so close the gap
                tail.y = (dy > 0) ? tail.y+1 : tail.y-1;
            }
        } else if(!tail.isAdjacentTo(newHead)) {
            // We need to update both directions
            tail.x = (dx > 0) ? tail.x+1 : tail.x-1;
            tail.y = (dy > 0) ? tail.y+1 : tail.y-1;
        }
    }
    private static List<Position> createRope() {
        List<Position> rope = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            rope.add(new Position());
        }
        return rope;
    }
}
record Instruction(char move){}
class Position {
    int x, y;
    public Position() {
        this.x = 0;
        this.y = 0;
    }

    public void moveByInstruction(Instruction instruction) {
        switch (instruction.move()) {
            case 'U' -> this.y++;
            case 'R' -> this.x++;
            case 'D' -> this.y--;
            case 'L' -> this.x--;
        }
    }

    public boolean isAdjacentTo(Position newHead) {
        return Math.abs(newHead.x - this.x) <= 1 && Math.abs(newHead.y - this.y) <= 1;
    }
}