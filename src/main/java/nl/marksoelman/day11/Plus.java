package nl.marksoelman.day11;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Plus implements Operation {
    int first;
    int second;

    @Override
    public long apply(long item) {
        long firstScoped = (first == -1) ? item : first;
        long secondScoped = (second == -1) ? item : second;
        return firstScoped + secondScoped;
    }
}
