package edu.model;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@AllArgsConstructor
@With
@Getter
public class TimeRange {

    private final LocalDateTime current;

    private final LocalDateTime end;

    public TimeRange(LocalDate begin, LocalDate end) {
        this.current = begin.atStartOfDay();
        this.end = end.atStartOfDay();
    }

    public boolean isAscending() {
        return current.isBefore(end);
    }
}
