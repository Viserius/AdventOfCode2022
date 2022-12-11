package nl.marksoelman.day11;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ThrowAction {
    int targetMonkey;

    public void perform(long item, List<Monkey> monkeys) {
        monkeys.get(targetMonkey).items.add(item);
    }
}
