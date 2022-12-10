package nl.marksoelman.day10;

import java.util.List;

public class Solution {
    public void run(List<String> inputLines) {
        // Parse input
        List<Instruction> instructions = inputLines.stream()
                .map(this::parseInstruction)
                .toList();

        // Execute program
        Program program = new Program();
        instructions.forEach(program::execute);
    }
    private Instruction parseInstruction(String instruction) {
        if(instruction.contains("noop"))
            return new Noop();
        return new Addx(Integer.parseInt(instruction.split("addx ")[1]));
    }
}

class Program {
    int cycle = 1;
    int x = 1;

    public void nextCycle() {
        this.printPixel();
        this.cycle++;
    }

    private void printPixel() {
        int position = (cycle -1) % 40;
        if(x >= position-1 && x <= position+1) {
            System.out.print("#");
        } else {
            System.out.print(".");
        }
        if(position == 39) System.out.println();
    }

    public void execute(Instruction instruction) {
        instruction.execute(this);
    }
}

interface Instruction {
    void execute(Program program);
}

class Noop implements Instruction {
    @Override
    public void execute(Program program) {
        program.nextCycle();
    }
}

class Addx implements Instruction {
    int increment;
    public Addx(int val) {
        this.increment = val;
    }

    @Override
    public void execute(Program program) {
        program.nextCycle();
        program.nextCycle();
        program.x += increment;
    }
}