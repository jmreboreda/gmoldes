package gmoldes.domain.dto;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class ContractScheduleDayDTO {

    private final SimpleStringProperty dayOfWeek;
    private final SimpleObjectProperty<LocalDate> date;
    private final SimpleObjectProperty<LocalTime> amFrom;
    private final SimpleObjectProperty<LocalTime> amTo;
    private final SimpleObjectProperty<LocalTime> pmFrom;
    private final SimpleObjectProperty<LocalTime> pmTo;
    private final SimpleObjectProperty<Duration> totalDayHours;

    public ContractScheduleDayDTO(String dayOfWeek, LocalDate date, LocalTime amFrom, LocalTime amTo, LocalTime pmFrom, LocalTime pmTo, Duration totalDayHours) {
        this.dayOfWeek = new SimpleStringProperty(dayOfWeek);
        this.date = new SimpleObjectProperty<>(date);
        this.amFrom = new SimpleObjectProperty<>(amFrom);
        this.amTo = new SimpleObjectProperty<>(amTo);
        this.pmFrom = new SimpleObjectProperty<>(pmFrom);
        this.pmTo = new SimpleObjectProperty<>(pmTo);
        this.totalDayHours = new SimpleObjectProperty<>(totalDayHours);
    }

    public String getDayOfWeek() {
        return dayOfWeek.get();
    }

    public SimpleStringProperty dayOfWeekProperty() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek.set(dayOfWeek);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public SimpleObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public LocalTime getAmFrom() {
        return amFrom.get();
    }

    public SimpleObjectProperty<LocalTime> amFromProperty() {
        return amFrom;
    }

    public void setAmFrom(LocalTime amFrom) {
        this.amFrom.set(amFrom);
    }

    public LocalTime getAmTo() {
        return amTo.get();
    }

    public SimpleObjectProperty<LocalTime> amToProperty() {
        return amTo;
    }

    public void setAmTo(LocalTime amTo) {
        this.amTo.set(amTo);
    }

    public LocalTime getPmFrom() {
        return pmFrom.get();
    }

    public SimpleObjectProperty<LocalTime> pmFromProperty() {
        return pmFrom;
    }

    public void setPmFrom(LocalTime pmFrom) {
        this.pmFrom.set(pmFrom);
    }

    public LocalTime getPmTo() {
        return pmTo.get();
    }

    public SimpleObjectProperty<LocalTime> pmToProperty() {
        return pmTo;
    }

    public void setPmTo(LocalTime pmTo) {
        this.pmTo.set(pmTo);
    }

    public Duration getTotalDayHours() {
        return totalDayHours.get();
    }

    public SimpleObjectProperty<Duration> totalDayHoursProperty() {
        return totalDayHours;
    }

    public void setTotalDayHours(Duration totalDayHours) {
        this.totalDayHours.set(totalDayHours);
    }

    public static ContractScheduleDayDTO.ContractScheduleDTOBuilder create() {
        return new ContractScheduleDayDTO.ContractScheduleDTOBuilder();
    }

    public static class ContractScheduleDTOBuilder {

        private String dayOfWeek;
        private LocalDate date;
        private LocalTime amFrom;
        private LocalTime amTo;
        private LocalTime pmFrom;
        private LocalTime pmTo;
        private Duration totalDayHours;

        public ContractScheduleDayDTO.ContractScheduleDTOBuilder withDayOfWeek(String dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
            return this;
        }

        public ContractScheduleDayDTO.ContractScheduleDTOBuilder withDate(LocalDate date) {
            this.dayOfWeek = dayOfWeek;
            return this;
        }

        public ContractScheduleDayDTO.ContractScheduleDTOBuilder withAmFrom(LocalTime amFrom) {
            this.amFrom = amFrom;
            return this;
        }

        public ContractScheduleDayDTO.ContractScheduleDTOBuilder withAmTo(LocalTime amTo) {
            this.amTo = amTo;
            return this;
        }

        public ContractScheduleDayDTO.ContractScheduleDTOBuilder withPmFrom(LocalTime pmFrom) {
            this.pmFrom = pmFrom;
            return this;
        }

        public ContractScheduleDayDTO.ContractScheduleDTOBuilder withPmTo(LocalTime pmTo) {
            this.pmTo = pmTo;
            return this;
        }

        public ContractScheduleDayDTO.ContractScheduleDTOBuilder withTotalDayHours(Duration totalDayHours) {
            this.totalDayHours = totalDayHours;
            return this;
        }

        public ContractScheduleDayDTO build() {
            return new ContractScheduleDayDTO(this.dayOfWeek, this.date, this.amFrom, this.amTo, this.pmFrom,
                    this.pmTo, this.totalDayHours);
        }
    }
}
