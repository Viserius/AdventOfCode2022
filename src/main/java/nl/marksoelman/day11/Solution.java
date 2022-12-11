package nl.marksoelman.day11;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Solution {
    public void run(List<String> inputLines) {
        // Parsing
        List<Monkey> monkeys = parseMonkeys(inputLines);

        // Ensure that the number does not grow too big
        long multipleOfAllDivisors = monkeys.stream()
                .map(monkey -> monkey.test)
                .map(test -> (DivisibleTest) test)
                .map(DivisibleTest::getDivisor)
                .reduce((a, b) -> a * b)
                .orElseThrow();

        // Execute
        IntStream.range(0, 10_000).forEach(i -> this.executeRound(multipleOfAllDivisors, monkeys));

        // Solution
        System.out.println(
                monkeys.stream()
                        .sorted(Comparator.comparing(Monkey::getInspectedItems).reversed())
                        .limit(2)
                        .map(monkey -> monkey.inspectedItems)
                        .mapToLong(i -> i)
                        .reduce((a, b) -> a * b)
                        .orElseThrow()
        );
    }

    private void executeRound(long multipleOfAllDivisors, List<Monkey> monkeys) {
        IntStream.range(0, monkeys.size())
                .forEach(idx -> monkeys.get(idx).inspectItems(monkeys, multipleOfAllDivisors));
    }

    private List<Monkey> parseMonkeys(List<String> inputLines) {
        List<Monkey> monkeyList = new ArrayList<>();
        String input = "";
        Iterator<String> it = inputLines.iterator();
        while (it.hasNext()) {
            input = it.next();
            if (input.length() <= 1) continue;
            if (input.startsWith("Monkey")) continue;
            List<Long> items = this.parseItems(input);
            Operation operation = this.parseOperation(it.next());
            Test test = this.parseTest(it, it.next());
            Monkey newMonkey = new Monkey(items, operation, test);
            monkeyList.add(newMonkey);
        }
        return monkeyList;
    }

    private List<Long> parseItems(String input) {
        input = input.replaceFirst(" *Starting items: ", "");
        return Arrays.stream(input.split(","))
                .map(str -> str.replace(" ", ""))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    private Test parseTest(Iterator<String> it, String input) {
        Test test;
        if (input.contains("divisible")) {
            test = new DivisibleTest(Integer.parseInt(input.split("by ")[1]));
        } else {
            throw new IllegalStateException();
        }
        test.actionTrue = this.parseThrowAction(it.next());
        test.actionFalse = this.parseThrowAction(it.next());
        return test;
    }

    private ThrowAction parseThrowAction(String input) {
        return new ThrowAction(Integer.parseInt(input.split("throw to monkey ")[1]));
    }

    private Operation parseOperation(String input) {
        Pattern pattern = Pattern.compile(" *Operation: new = (old|\\d+) (\\+|\\*) (old|\\d+)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.find()) throw new IllegalStateException();
        return switch (matcher.group(2)) {
            case "+" -> new Plus(
                    (matcher.group(1).equals("old")) ? -1 : Integer.parseInt(matcher.group(1)),
                    (matcher.group(3).equals("old")) ? -1 : Integer.parseInt(matcher.group(3))
            );
            case "*" -> new Multiply(
                    (matcher.group(1).equals("old")) ? -1 : Integer.parseInt(matcher.group(1)),
                    (matcher.group(3).equals("old")) ? -1 : Integer.parseInt(matcher.group(3))
            );
            default -> throw new IllegalStateException();
        };
    }
}
