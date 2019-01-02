package gmoldes.domain.contractjsondata;

public class ContractDayScheduleJsonData {

    private String dayOfWeek;
    private String date;
    private String amFrom;
    private String amTo;
    private String pmFrom;
    private String pmTo;
    private Double durationHours;

    public ContractDayScheduleJsonData(String dayOfWeek, String date, String amFrom, String amTo, String pmFrom, String pmTo, Double durationHours) {
        this.dayOfWeek = dayOfWeek;
        this.date = date;
        this.amFrom = amFrom;
        this.amTo = amTo;
        this.pmFrom = pmFrom;
        this.pmTo = pmTo;
        this.durationHours = durationHours;
    }

    public ContractDayScheduleJsonData() {
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

    public Double getDurationHours() {
        return durationHours;
    }

    public static ContractDayScheduleJsonData.WorkDayBuilder create() {
        return new ContractDayScheduleJsonData.WorkDayBuilder();
    }

    public static class WorkDayBuilder {

        private String dayOfWeek;
        private String date;
        private String amFrom;
        private String amTo;
        private String pmFrom;
        private String pmTo;
        private Double durationHours;

        public ContractDayScheduleJsonData.WorkDayBuilder withDayOfWeek(String dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
            return this;
        }

        public ContractDayScheduleJsonData.WorkDayBuilder withDate(String date) {
            this.date = date;
            return this;
        }

        public ContractDayScheduleJsonData.WorkDayBuilder withAmFrom(String amFrom) {
            this.amFrom = amFrom;
            return this;
        }

        public ContractDayScheduleJsonData.WorkDayBuilder withAmTo(String amTo) {
            this.amTo = amTo;
            return this;
        }

        public ContractDayScheduleJsonData.WorkDayBuilder withPmFrom(String pmFrom) {
            this.pmFrom = pmFrom;
            return this;
        }

        public ContractDayScheduleJsonData.WorkDayBuilder withPmTo(String pmTo) {
            this.pmTo = pmTo;
            return this;
        }

        public ContractDayScheduleJsonData.WorkDayBuilder withDurationHours(Double durationHours) {
            this.durationHours = durationHours;
            return this;
        }

        public ContractDayScheduleJsonData build() {
            return new ContractDayScheduleJsonData(this.dayOfWeek, this.date, this.amFrom, this.amTo, this.pmFrom, this.pmTo, this.durationHours);
        }
    }
}
