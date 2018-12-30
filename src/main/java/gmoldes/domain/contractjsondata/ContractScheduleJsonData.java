package gmoldes.domain.contractjsondata;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class ContractScheduleJsonData {

    private String dayOfWeek;
    private String date;
    private String amFrom;
    private String amTo;
    private String pmFrom;
    private String pmTo;
    private Long durationHours;

    public ContractScheduleJsonData(String dayOfWeek, String date, String amFrom, String amTo, String pmFrom, String pmTo, Long durationHours) {
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

    public String getDate() {
        return date;
    }

    public String getAmFrom() {
        return amFrom;
    }

    public String getAmTo() {
        return amTo;
    }

    public String getPmFrom() {
        return pmFrom;
    }

    public String getPmTo() {
        return pmTo;
    }

    public Long getDurationHours() {
        return durationHours;
    }

    public static ContractScheduleJsonData.WorkDayBuilder create() {
        return new ContractScheduleJsonData.WorkDayBuilder();
    }

    public static class WorkDayBuilder {

        private String dayOfWeek;
        private String date;
        private String amFrom;
        private String amTo;
        private String pmFrom;
        private String pmTo;
        private Long durationHours;

        public ContractScheduleJsonData.WorkDayBuilder withDayOfWeek(String dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
            return this;
        }

        public ContractScheduleJsonData.WorkDayBuilder withDate(String date) {
            this.date = date;
            return this;
        }

        public ContractScheduleJsonData.WorkDayBuilder withAmFrom(String amFrom) {
            this.amFrom = amFrom;
            return this;
        }

        public ContractScheduleJsonData.WorkDayBuilder withAmTo(String amTo) {
            this.amTo = amTo;
            return this;
        }

        public ContractScheduleJsonData.WorkDayBuilder withPmFrom(String pmFrom) {
            this.pmFrom = pmFrom;
            return this;
        }

        public ContractScheduleJsonData.WorkDayBuilder withPmTo(String pmTo) {
            this.pmTo = pmTo;
            return this;
        }

        public ContractScheduleJsonData.WorkDayBuilder withDurationHours(Long durationHours) {
            this.durationHours = durationHours;
            return this;
        }

        public ContractScheduleJsonData build() {
            return new ContractScheduleJsonData(this.dayOfWeek, this.date, this.amFrom, this.amTo, this.pmFrom, this.pmTo, this.durationHours);
        }
    }
}
