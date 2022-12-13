package nl.marksoelman.day13;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
    public void run(List<String> inputLines) {
        Comparator<Element> compare = new Comparator<>() {
            @Override
            public int compare(Element first, Element second) {
                // Case 1, both integers
                if (first.value != null && second.value != null) {
                    return first.value.compareTo(second.value);
                }
                // Case 2, both lists
                if (first.children != null && second.children != null) {
                    for (int i = 0; i < Math.max(first.children.size(), second.children.size()); i++) {
                        // first runs out first
                        if (i == first.children.size()) return -1;
                        // right runs out first
                        if (i == second.children.size()) return 1;
                        Element childFirst = first.children.get(i);
                        Element childSecond = second.children.get(i);
                        int comparedResult = compare(childFirst, childSecond);
                        if (comparedResult != 0) return comparedResult;
                    }
                    return 0;
                }
                // Case 3, exactly one argument is a value and not a list
                Element valueAsList = new Element();
                valueAsList.children = (first.value != null) ? List.of(first) : List.of(second);
                return (first.value != null) ? compare(valueAsList, second) : compare(first, valueAsList);
            }
        };
        List<Element> inputs = new ArrayList<>();
        inputLines.forEach(input -> {
            if (input.length() > 0)
                inputs.add(parseInput(input));
        });
        inputs.add(parseInput("[[2]]"));
        inputs.add(parseInput("[[6]]"));
        inputs.sort(compare);
        System.out.println(IntStream.range(0, inputs.size())
                .filter(i -> inputs.get(i).toString().equals("[[2]]") || inputs.get(i).toString().equals("[[6]]"))
                .reduce((a, b) -> (a + 1) * (b + 1)).orElseThrow());
    }

    private Element parseInput(String input) {
        Element element = new Element();
        if (input.charAt(0) == '[') {
            // We are processing a list!
            // That means that the current element is equal to a list containing child elements,
            //  of which each element may be their own list of elements or a value.
            String subList = input.substring(1, input.length() - 1);
            element.children = parseList(subList);
        } else {
            element.value = Integer.parseInt(input);
        }
        return element;
    }

    private List<Element> parseList(String input) {
        List<Element> elements = new ArrayList<>();
        int nestedLevel = 0;
        int startNestingIdx = -1;
        for (int i = 0; i < input.length(); i++) {

            // When starting a list, keep track of the depth to know when we are out again
            if (input.charAt(i) == '[') {
                // Keep track of where the list starts
                if (nestedLevel == 0) startNestingIdx = i + 1;
                nestedLevel++;
                continue;
            }

            // When closing a list, decrement the level of nesting
            if (input.charAt(i) == ']') {
                nestedLevel--;
                // If the list is completely closed on this level, we should register this as a child element
                if (nestedLevel == 0) {
                    // List is closed. we can process this.
                    Element childList = new Element();
                    childList.children = parseList(input.substring(startNestingIdx, i));
                    startNestingIdx = -1;
                    elements.add(childList);
                }
                continue;
            }

            // Do not process the contents of a child list
            if (nestedLevel > 0) continue;
            if (input.charAt(i) == ',') continue;

            // When we do not encounter a list (open/close), we should parse a number
            int endOfNumber = input.substring(i).indexOf(',');
            if (endOfNumber == -1) endOfNumber = input.substring(i).length();
            String numberString = input.substring(i).substring(0, endOfNumber);
            int numberValue = Integer.parseInt(numberString);
            Element numberElement = new Element();
            numberElement.value = numberValue;
            elements.add(numberElement);
            i = i + endOfNumber; // Skip to the end of the number
        }
        return elements;
    }
}

class Element {
    Integer value = null;
    List<Element> children = null;

    @Override
    public String toString() {
        if (this.value != null) return String.valueOf(value);
        return "[" + children.stream().map(Element::toString).collect(Collectors.joining(",")) + "]";
    }
}