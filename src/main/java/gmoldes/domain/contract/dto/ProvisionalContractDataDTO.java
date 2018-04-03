package gmoldes.domain.contract.dto;

import java.time.DayOfWeek;
import java.util.Set;

public class ProvisionalContractDataDTO {

    private String employerFullName;
    private String employeeFullName;
    private String quoteAccountCode;
    private String contractType;
    private String dateFrom;
    private String dateTo;
    private String durationDays;
    private String workDayType;
    private String numberHoursPerWeek;
    private Set<DayOfWeek> daysWeekToWork;
    private String laborCategory;
    private String status;

    public ProvisionalContractDataDTO(String employerFullName, String employeeFullName, String quoteAccountCode, String contractType,
                                      String dateFrom, String dateTo, String durationDays, String workDayType,
                                      String numberHoursPerWeek, Set<DayOfWeek> daysWeekToWork, String laborCategory, String status) {
        this.employerFullName = employerFullName;
        this.employeeFullName = employeeFullName;
        this.quoteAccountCode = quoteAccountCode;
        this.contractType = contractType;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.durationDays = durationDays;
        this.workDayType = workDayType;
        this.numberHoursPerWeek = numberHoursPerWeek;
        this.daysWeekToWork = daysWeekToWork;
        this.laborCategory = laborCategory;
        this.status = status;
    }

    public String getEmployerFullName() {
        return employerFullName;
    }

    public void setEmployerFullName(String employerFullName) {
        this.employerFullName = employerFullName;
    }

    public String getEmployeeFullName() {
        return employeeFullName;
    }

    public void setEmployeeFullName(String employeeFullName) {
        this.employeeFullName = employeeFullName;
    }

    public String getQuoteAccountCode() {
        return quoteAccountCode;
    }

    public void setQuoteAccountCode(String quoteAccountCode) {
        this.quoteAccountCode = quoteAccountCode;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(String durationDays) {
        this.durationDays = durationDays;
    }

    public String getWorkDayType() {
        return workDayType;
    }

    public void setWorkDayType(String workDayType) {
        this.workDayType = workDayType;
    }

    public String getNumberHoursPerWeek() {
        return numberHoursPerWeek;
    }

    public void setNumberHoursPerWeek(String numberHoursPerWeek) {
        this.numberHoursPerWeek = numberHoursPerWeek;
    }

    public Set<DayOfWeek> getDaysWeekToWork() {
        return daysWeekToWork;
    }

    public void setDaysWeekToWork(Set<DayOfWeek> daysWeekToWork) {
        this.daysWeekToWork = daysWeekToWork;
    }

    public String getLaborCategory() {
        return laborCategory;
    }

    public void setLaborCategory(String laborCategory) {
        this.laborCategory = laborCategory;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static ProvisionalContractDataDTOBuilder create() {
        return new ProvisionalContractDataDTOBuilder();
    }

    public static class ProvisionalContractDataDTOBuilder {

        private String employerFullName;
        private String employeeFullName;
        private String quoteAccountCode;
        private String contractType;
        private String dateFrom;
        private String dateTo;
        private String durationDays;
        private String workDayType;
        private String numberHoursPerWeek;
        private Set<DayOfWeek> daysWeekToWork;
        private String laborCategory;
        private String status;

        public ProvisionalContractDataDTOBuilder withEmployerFullName (String employerFullName) {
            this.employerFullName = employerFullName;
            return this;
        }

        public ProvisionalContractDataDTOBuilder withEmployeeFullName(String employeeFullName) {
            this.employeeFullName = employeeFullName;
            return this;
        }

        public ProvisionalContractDataDTOBuilder withQuoteAccountCode(String quoteAccountCode) {
            this.quoteAccountCode = quoteAccountCode;
            return this;
        }

        public ProvisionalContractDataDTOBuilder withContractType(String contractType) {
            this.contractType = contractType;
            return this;
        }

        public ProvisionalContractDataDTOBuilder withDateFrom(String dateFrom) {
            this.dateFrom = dateFrom;
            return this;
        }

        public ProvisionalContractDataDTOBuilder withDateTo(String dateTo) {
            this.dateTo = dateTo;
            return this;
        }

        public ProvisionalContractDataDTOBuilder withDurationDays(String durationDays) {
            this.durationDays = durationDays;
            return this;
        }

        public ProvisionalContractDataDTOBuilder withWorkDayType(String workDayType) {
            this.workDayType = workDayType;
            return this;
        }

        public ProvisionalContractDataDTOBuilder withNumberHoursPerWeek(String numberHoursPerWeek) {
            this.numberHoursPerWeek = numberHoursPerWeek;
            return this;
        }

        public ProvisionalContractDataDTOBuilder withDaysWeekToWork(Set<DayOfWeek> daysWeekToWork) {
            this.daysWeekToWork = daysWeekToWork;
            return this;
        }

        public ProvisionalContractDataDTOBuilder withLaborCategory(String laborCategory) {
            this.laborCategory = laborCategory;
            return this;
        }

        public ProvisionalContractDataDTOBuilder withStatus(String status) {
            this.status = status;
            return this;
        }

        public ProvisionalContractDataDTO build() {
            return new ProvisionalContractDataDTO(this.employerFullName, this.employeeFullName, this.quoteAccountCode, this.contractType, this.dateFrom,
            this.dateTo, this.durationDays, this.workDayType, this.numberHoursPerWeek, this.daysWeekToWork, this.laborCategory, this.status);
        }
    }
}
