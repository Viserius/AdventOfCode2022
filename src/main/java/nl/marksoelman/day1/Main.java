package nl.marksoelman.day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            File myObj = new File("input");
            Scanner myReader = new Scanner(myObj);
            List<String> inputLines = new ArrayList<>();
            while (myReader.hasNextLine()) {
                inputLines.add(myReader.nextLine());
            }
            new Solution().run(inputLines);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}