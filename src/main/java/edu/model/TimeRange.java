package edu.model;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@AllArgsConstructor
@With
@Getter
public class TimeRange {

    private final LocalDateTime begin;

    private final LocalDateTime end;

    public TimeRange(LocalDate begin, LocalDate end) {
        this.begin = begin.atStartOfDay();
        this.end = end.atStartOfDay();
    }

    public boolean isAscending() {
        return begin.isBefore(end);
    }
}
