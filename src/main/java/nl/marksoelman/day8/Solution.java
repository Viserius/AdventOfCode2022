package nl.marksoelman.day8;

import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
    public void run(List<String> inputLines) {
        // Initialize 2d array
        int xLength = inputLines.get(0).length();
        int yLength = inputLines.size();
        int[][] treeHeights = new int[yLength][xLength];
        for(int y = 0; y < yLength; y++) {
            for(int x = 0; x < xLength; x++) {
                treeHeights[y][x] = Character.getNumericValue(inputLines.get(y).charAt(x));
            }
        }

        int maxScore = 0;
        for(int y = 0; y < yLength; y++) {
            for(int x = 0; x < xLength; x++) {
                int score = computeVisibility(treeHeights, treeHeights[y][x], x+1, xLength-1,  y, y, 1, 0) // left to right
                    * computeVisibility(treeHeights, treeHeights[y][x],x-1, 0,  y, y, -1, 0) // right to left
                    * computeVisibility(treeHeights, treeHeights[y][x], x, x,  y+1, yLength-1, 0, 1) // top to bottom
                    * computeVisibility(treeHeights, treeHeights[y][x], x, x, y-1, 0, 0, -1); // bottom to top
                if(score > maxScore) maxScore = score;
            }
        }

        // Compute solution
        System.out.println(maxScore);
    }

    private int computeVisibility(int[][] treeHeights, int maxHeight,
                                  int xFrom, int xTo,
                                  int yFrom, int yTo,
                                  int dx, int dy) {
        int amountVisible = 0;
        int x = xFrom, y = yFrom;
        while((dx > 0 && x <= xTo) || (dx < 0 && x >= xTo) || (dy > 0 && y <= yTo) || (dy < 0 && y >= yTo)) {
            int current = treeHeights[y][x];
            amountVisible++;
            if(current >= maxHeight) break;
            x += dx;
            y += dy;
            if(dx == 0 && dy == 0) break;
        }
        return amountVisible;
    }
}