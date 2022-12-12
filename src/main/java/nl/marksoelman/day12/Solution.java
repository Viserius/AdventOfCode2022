package nl.marksoelman.day12;

import lombok.ToString;

import java.util.*;

public class Solution {
    private static void parseGraph(List<String> inputLines,
                                   Map<Position, Node> positions,
                                   Queue<Node> toVisit) {
        for (int y = 0; y < inputLines.size(); y++) {
            for (int x = 0; x < inputLines.get(0).length(); x++) {
                Position position = new Position(x, y);
                Node node = new Node(position, inputLines.get(y).charAt(x));
                positions.put(position, node);
                if (node.height == 'S' || node.height == 'a') {
                    node.height = 'a';
                    node.isDestination = true;
                } else if (node.height == 'E') {
                    node.stepNumber = 0;
                    node.height = 'z';
                    toVisit.add(node);
                }
            }
        }
    }

    public void run(List<String> inputLines) {
        Map<Position, Node> positionToNode = new HashMap<>();
        Queue<Node> toVisit = new PriorityQueue<>(Comparator.comparing(p -> p.stepNumber));
        parseGraph(inputLines, positionToNode, toVisit);

        while (toVisit.size() > 0) {
            Node current = toVisit.remove();
            current.visited = true;
            if (current.isDestination) {
                System.out.println("Target can be reached in " + current.stepNumber + " steps.");
                break;
            }
            current.getNeighbourPositions().stream()
                    .filter(positionToNode::containsKey)
                    .map(positionToNode::get)
                    .filter(neighbour -> neighbour.height + 1 >= current.height)
                    .filter(neighbour -> !neighbour.visited)
                    .filter(neighbour -> neighbour.stepNumber > current.stepNumber + 1)
                    .forEach(neighbour -> this.prepareVisit(toVisit, neighbour, current.stepNumber + 1));
        }
    }

    private void prepareVisit(Queue<Node> toVisit, Node neighbour, int stepNumber) {
        neighbour.stepNumber = stepNumber;
        toVisit.add(neighbour);
    }
}

record Position(int x, int y) {
}

@ToString
class Node {
    Position position;
    int stepNumber = Integer.MAX_VALUE;
    char height;
    boolean visited = false;
    boolean isDestination = false;

    public Node(Position position, char height) {
        this.position = position;
        this.height = height;
    }

    public Set<Position> getNeighbourPositions() {
        return Set.of(
                new Position(position.x() + 1, position.y()),
                new Position(position.x() - 1, position.y()),
                new Position(position.x(), position.y() + 1),
                new Position(position.x(), position.y() - 1)
        );
    }
}