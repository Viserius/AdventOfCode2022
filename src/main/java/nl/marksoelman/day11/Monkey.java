package nl.marksoelman.day11;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Monkey {
    List<Long> items;
    Operation operation;
    Test test;

    int inspectedItems = 0;

    public Monkey(List<Long> items, Operation operation, Test test) {
        this.items = items;
        this.operation = operation;
        this.test = test;
    }

    public void inspectItems(List<Monkey> monkeys, long divisorProduct) {
        List<Long> items = this.items;
        this.items = new ArrayList<>(); // remove all
        items.forEach(item -> {
            item = operation.apply(item);
            item = item % divisorProduct;
            if (test.test(item)) {
                test.actionTrue.perform(item, monkeys);
            } else {
                test.actionFalse.perform(item, monkeys);
            }
            inspectedItems++;
        });
    }
}
