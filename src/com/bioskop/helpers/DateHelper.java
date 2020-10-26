package com.bioskop.helpers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DateHelper{
        private String date;
        private LocalDate localDate;
        private String displayDate;
        private LocalDateTime localDateTime;
        private LocalTime localTime;
        private String displayTime;

        public DateHelper(LocalDate ld){
            localDate = ld;
            date = localDate.toString();
            displayDate = localDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
        }

        public DateHelper(LocalDateTime ldt)
        {
            localDateTime = ldt;
            localDate = localDateTime.toLocalDate();
            localTime = localDateTime.toLocalTime();
            date = localDate.toString();
            displayDate = localDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
            displayTime = localTime.toString();
        }

    public String getDate() {
        return date;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public String getDisplayDate() {
        return displayDate;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public String getDisplayTime() {
        return displayTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateHelper that = (DateHelper) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(localDate, that.localDate) &&
                Objects.equals(displayDate, that.displayDate) &&
                Objects.equals(localDateTime, that.localDateTime) &&
                Objects.equals(localTime, that.localTime) &&
                Objects.equals(displayTime, that.displayTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, localDate, displayDate, localDateTime, localTime, displayTime);
    }

}
