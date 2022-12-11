package nl.marksoelman.day11;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DivisibleTest extends Test {
    int divisor;

    @Override
    public String toString() {
        return "DivisibleTest{" +
                "divisor=" + divisor +
                ", actionTrue=" + actionTrue +
                ", actionFalse=" + actionFalse +
                '}';
    }

    @Override
    public boolean test(long item) {
        return item % divisor == 0;
    }
}