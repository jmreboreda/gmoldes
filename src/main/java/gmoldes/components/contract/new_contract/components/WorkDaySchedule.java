package gmoldes.components.contract.new_contract.components;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class WorkDaySchedule {

    private String dayOfWeek;
    private LocalDate date;
    private LocalTime amFrom;
    private LocalTime amTo;
    private LocalTime pmFrom;
    private LocalTime pmTo;
    private Duration durationHours;

    public WorkDaySchedule(String dayOfWeek, LocalDate date, LocalTime amFrom, LocalTime amTo, LocalTime pmFrom, LocalTime pmTo, Duration durationHours) {
        this.dayOfWeek = dayOfWeek;
        this.date = date;
        this.amFrom = amFrom;
        this.amTo = amTo;
        this.pmFrom = pmFrom;
        this.pmTo = pmTo;
        this.durationHours = durationHours;
    }

    public String getDayOfWeek() {
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

    public static WorkDaySchedule.WorkDayBuilder create() {
        return new WorkDaySchedule.WorkDayBuilder();
    }

    public static class WorkDayBuilder {

        private String dayOfWeek;
        private LocalDate date;
        private LocalTime amFrom;
        private LocalTime amTo;
        private LocalTime pmFrom;
        private LocalTime pmTo;
        private Duration durationHours;

        public WorkDaySchedule.WorkDayBuilder withDayOfWeek(String dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
            return this;
        }

        public WorkDaySchedule.WorkDayBuilder withDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public WorkDaySchedule.WorkDayBuilder withAmFrom(LocalTime amFrom) {
            this.amFrom = amFrom;
            return this;
        }

        public WorkDaySchedule.WorkDayBuilder withAmTo(LocalTime amTo) {
            this.amTo = amTo;
            return this;
        }

        public WorkDaySchedule.WorkDayBuilder withPmFrom(LocalTime pmFrom) {
            this.pmFrom = pmFrom;
            return this;
        }

        public WorkDaySchedule.WorkDayBuilder withPmTo(LocalTime pmTo) {
            this.pmTo = pmTo;
            return this;
        }

        public WorkDaySchedule.WorkDayBuilder withDurationHours(Duration durationHours) {
            this.durationHours = durationHours;
            return this;
        }

        public WorkDaySchedule build() {
            return new WorkDaySchedule(this.dayOfWeek, this.date, this.amFrom, this.amTo, this.pmFrom, this.pmTo, this.durationHours);
        }
    }
}
