package nl.marksoelman.day2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {
    public void run(List<String> inputLines) {
        // Parse input into rounds
        List<RoundStrategy> rounds = inputLines.stream()
                .map(line -> new RoundStrategy(line.charAt(0), line.charAt(2)))
                .toList();

        // Execute rounds
        int result = rounds.stream()
                .map(RoundStrategy::withPlayerMove)
                .map(RoundStrategy::computeScore)
                .reduce(Integer::sum)
                .orElseThrow();

        // Solution
        System.out.println(result);
    }
}

class RoundStrategy {
    // The element to the right wins from the element of the left
    private static final List<Move> MOVE_ORDER = List.of(Move.ROCK, Move.PAPER, Move.SCISSORS);
    private final Move opponent;

    private final Goal goal;
    private Move you;

    public RoundStrategy(char opponent, char goal) {
        this.opponent = parseEncryptedMove(opponent);
        this.goal = parseEncryptedGoal(goal);
    }

    public static RoundStrategy withPlayerMove(RoundStrategy roundStrategy) {
        roundStrategy.you = switch (roundStrategy.goal) {
            case WIN -> MOVE_ORDER.get((MOVE_ORDER.indexOf(roundStrategy.opponent) + 1) % 3);
            case DRAW -> roundStrategy.opponent;
            case LOSE -> MOVE_ORDER.get(Math.floorMod(MOVE_ORDER.indexOf(roundStrategy.opponent) - 1, 3));
        };
        return roundStrategy;
    }

    public int computeScore() {
        return getShapeScore(you) + getPlayScore(opponent, you);
    }

    private Goal parseEncryptedGoal(char goal) {
        return switch(goal) {
            case 'X' -> Goal.LOSE;
            case 'Y' -> Goal.DRAW;
            case 'Z' -> Goal.WIN;
            default -> throw new IllegalStateException();
        };
    }

    private static Move parseEncryptedMove(char move) {
        return switch(move) {
            case 'A', 'X' -> Move.ROCK;
            case 'B', 'Y' -> Move.PAPER;
            case 'C', 'Z' -> Move.SCISSORS;
            default -> throw new IllegalStateException();
        };
    }

    private static int getShapeScore(Move shape) {
        return switch (shape) {
            case ROCK -> 1;
            case PAPER -> 2;
            case SCISSORS -> 3;
        };
    }

    private static int getPlayScore(Move opponent, Move you) {
        // Win condition
        if(MOVE_ORDER.indexOf(you) == (MOVE_ORDER.indexOf(opponent) + 1) % 3) {
            return 6;
        }
        // Draw
        if(you == opponent) {
            return 3;
        }
        // Lose
        return 0;
    }
}

enum Move {
    ROCK, PAPER, SCISSORS
}

enum Goal {
    LOSE, DRAW, WIN
}