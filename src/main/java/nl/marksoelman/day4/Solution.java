package nl.marksoelman.day4;

import java.util.List;

public class Solution {
    public void run(List<String> inputLines) {
        System.out.println(
                inputLines.stream()
                .map(this::parsePair)
                .filter(pair -> this.isRangeOverlapping(pair.first(), pair.second()))
                .count()
        );
    }

    private boolean isRangeOverlapping(Range first, Range second) {
        // First range starts before the second, so then the second range must start before the first ends
        // Otherwise, the second range starts before the second, and the first range must start before the second ends
        return (first.from() <= second.from() && second.from() <= first.to())
                || (second.from() < first.from() && first.from() <= second.to());
    }

    private Pair parsePair(String line) {
        return new Pair(
                this.parseRange(line.split(",")[0]),
                this.parseRange(line.split(",")[1])
        );
    }

    private Range parseRange(String range) {
        return new Range(
                Integer.parseInt(range.split("-")[0]),
                Integer.parseInt(range.split("-")[1])
        );
    }
}

record Range (int from, int to) {}
record Pair(Range first, Range second) {}