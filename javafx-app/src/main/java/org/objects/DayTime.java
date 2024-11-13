package org.objects;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class DayTime {
    private DayOfWeek dayOfWeek;
    private LocalTime time;

    public DayTime(DayOfWeek dayOfWeek, LocalTime time) {
        this.dayOfWeek = dayOfWeek;
        this.time = time;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return dayOfWeek + " " + time;
    }
}
