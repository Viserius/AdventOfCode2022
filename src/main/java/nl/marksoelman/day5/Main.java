package nl.marksoelman.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        new Solution().run(Files.readAllLines(Path.of("./input")));
    }
}