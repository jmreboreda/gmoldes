package gmoldes.forms;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class WorkDay {

    private DayOfWeek dayOfWeek;
    private LocalDate date;
    private LocalTime amFrom;
    private LocalTime amTo;
    private LocalTime pmFrom;
    private LocalTime pmTo;
    private Duration durationHours;

    public WorkDay(DayOfWeek dayOfWeek, LocalDate date, LocalTime amFrom, LocalTime amTo, LocalTime pmFrom, LocalTime pmTo, Duration durationHours) {
        this.dayOfWeek = dayOfWeek;
        this.date = date;
        this.amFrom = amFrom;
        this.amTo = amTo;
        this.pmFrom = pmFrom;
        this.pmTo = pmTo;
        this.durationHours = durationHours;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getAmFrom() {
        return amFrom;
    }

    public LocalTime getAmTo() {
        return amTo;
    }

    public LocalTime getPmFrom() {
        return pmFrom;
    }

    public LocalTime getPmTo() {
        return pmTo;
    }

    public Duration getDurationHours() {
        return durationHours;
    }

    public static WorkDay.WorkDayBuilder create() {
        return new WorkDay.WorkDayBuilder();
    }

    public static class WorkDayBuilder {

        private DayOfWeek dayOfWeek;
        private LocalDate date;
        private LocalTime amFrom;
        private LocalTime amTo;
        private LocalTime pmFrom;
        private LocalTime pmTo;
        private Duration durationHours;

        public WorkDay.WorkDayBuilder withDayOfWeek(DayOfWeek dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
            return this;
        }

        public WorkDay.WorkDayBuilder withDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public WorkDay.WorkDayBuilder withAmFrom(LocalTime amFrom) {
            this.amFrom = amFrom;
            return this;
        }

        public WorkDay.WorkDayBuilder withAmTo(LocalTime amTo) {
            this.amTo = amTo;
            return this;
        }

        public WorkDay.WorkDayBuilder withPmFrom(LocalTime pmFrom) {
            this.pmFrom = pmFrom;
            return this;
        }

        public WorkDay.WorkDayBuilder withPmTo(LocalTime pmTo) {
            this.pmTo = pmTo;
            return this;
        }

        public WorkDay.WorkDayBuilder withDurationHours(Duration durationHours) {
            this.durationHours = durationHours;
            return this;
        }

        public WorkDay build() {
            return new WorkDay(this.dayOfWeek, this.date, this.amFrom, this.amTo, this.pmFrom, this.pmTo, this.durationHours);
        }
    }
}
