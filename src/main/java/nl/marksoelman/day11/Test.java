package nl.marksoelman.day11;

import lombok.Data;

@Data
public abstract class Test {
    ThrowAction actionTrue;
    ThrowAction actionFalse;

    public abstract boolean test(long item);
}
