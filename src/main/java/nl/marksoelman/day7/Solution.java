package nl.marksoelman.day7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {

    private static final long MAX_SPACE = 70000000;
    private static final long MINIMUM_FREE_REQUIRED = 30000000;
    public void run(List<String> inputLines) {
        Node tree = this.parseInput(inputLines);

        List<Node> dirsWithFileSizeUnderUpperBound = new ArrayList<>();
        this.addAllDirectoriesToList(dirsWithFileSizeUnderUpperBound, tree);
        long minimumSpaceNeeded = (MAX_SPACE - tree.fileSize - MINIMUM_FREE_REQUIRED) * -1;
        System.out.println(
                dirsWithFileSizeUnderUpperBound.stream()
                        .mapToLong(dir -> dir.fileSize)
                        .sorted()
                        .filter(size -> size >= minimumSpaceNeeded)
                        .findFirst()
                        .orElseThrow()
        );
    }

    private void addAllDirectoriesToList(List<Node> dirsWithFileSizeUnderUpperBound, Node current) {
        if(current.children.size() != 0) {
            dirsWithFileSizeUnderUpperBound.add(current);
        }
        current.children.values().forEach(child -> this.addAllDirectoriesToList(dirsWithFileSizeUnderUpperBound, child));
    }

    private Node parseInput(List<String> inputLines) {
        Node root = new Node(null);
        Node current = root;
        for(String inputLine : inputLines) {
            if(inputLine.startsWith("$ cd")) {
                current = executeDir(inputLine, current, root);
            }
            else if (inputLine.startsWith("$ ls")) {
                continue; // nothing to do on this line
            } else {
                // Processing files/dirs
                this.addDataToNode(current, inputLine);
            }
        }
        return root;
    }

    private void addDataToNode(Node current, String inputLine) {
        String[] listOutput = inputLine.split(" ");
        if(listOutput[0].equals("dir")) {
            String dirName = listOutput[1];
            current.children.putIfAbsent(dirName, new Node(current));
        } else {
            // Create file
            String fileName = listOutput[1];
            long fileSize = Long.parseLong(listOutput[0]);
            Node newFile = new Node(current);
            if(current.children.putIfAbsent(fileName, newFile) == null) {
                newFile.addFileSizeRecursiveToParent(fileSize);
            }
        }
    }

    private Node executeDir(String traverseLine, Node current, Node root) {
        String traverseTo = traverseLine.substring(5);
        if(traverseTo.equals("/")) {
            return root;
        }
        if(traverseTo.equals("..")) {
            return current.parent;
        }
        return current.children.get(traverseTo);
    }

    class Node {
        Node parent;
        Map<String, Node> children = new HashMap<>();
        long fileSize = 0;

        public Node(Node parent) {
            this.parent = parent;
        }

        public void addFileSizeRecursiveToParent(long fileSize) {
            this.fileSize += fileSize;
            if(parent != null) {
                this.parent.addFileSizeRecursiveToParent(fileSize);
            }
        }
    }
}
