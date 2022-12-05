package nl.marksoelman.day5;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
    public void run(List<String> inputLines) {
        List<Deque<Character>> stacks = initializeDeques(inputLines.get(0).length() / 3);
        // Parse box lines
        inputLines.stream()
                .filter(line -> line.contains("["))
                .forEach(line -> this.parseBoxLine(line, stacks));
        // Parse move actions
        List<MoveAction> moveActions = inputLines.stream()
                .filter(line -> line.startsWith("move"))
                .map(this::parseMoveAction)
                .toList();
        // Execute moves
        moveActions.forEach(action -> this.executeMove(action, stacks));

        // Print top element
        stacks.stream()
                .filter(stack -> stack.size() > 0)
                .map(Deque::getFirst)
                .forEach(System.out::print);
    }

    private void executeMove(MoveAction action, List<Deque<Character>> stacks) {
                IntStream.range(0, action.times())
                .mapToObj(i -> stacks.get(action.fromStack()).removeFirst())
                .collect(Collectors.toCollection(LinkedList::new))
                        .descendingIterator()
                        .forEachRemaining(character -> stacks.get(action.toStack()).addFirst(character));
    }

    private ArrayList<Deque<Character>> initializeDeques(int size) {
        ArrayList<Deque<Character>> list = new ArrayList<>();
        IntStream.range(0, size).forEach(i -> list.add(new ArrayDeque<>()));
        return list;
    }

    private void parseBoxLine(String boxLine, List<Deque<Character>> stacks) {
        for(int idx = 1; idx < boxLine.length(); idx += 4) {
            char character = boxLine.charAt(idx);
            if(!Character.isAlphabetic(character)) {
                continue;
            }
            int stackIdx = (idx - 1) / 4;
            stacks.get(stackIdx).addLast(character);
        }
    }

    private MoveAction parseMoveAction(String moveLine) {
        Pattern pattern = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");
        Matcher matcher = pattern.matcher(moveLine);
        matcher.find();
        return new MoveAction(
                Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2)) - 1,
                Integer.parseInt(matcher.group(3)) -1
        );
    }
}

record MoveAction(int times, int fromStack, int toStack) {}
