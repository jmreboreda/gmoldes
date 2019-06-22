package gmoldes.domain.consultation_contract.dto;

import gmoldes.ApplicationConstants;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConsultationContractDataTableDTO {

    private Integer variationTypeCode;
    private String variationTypeDescription;
    private LocalDate startDate;
    private LocalDate expectedEndDate;
    private LocalDate modificationDate;
    private LocalDate endingDate;
    private Duration weeklyWorkHours;

    public ConsultationContractDataTableDTO() {
    }

    public ConsultationContractDataTableDTO(Integer variationTypeCode,
                                            String variationTypeDescription,
                                            LocalDate startDate,
                                            LocalDate expectedEndDate,
                                            LocalDate modificationDate,
                                            LocalDate endingDate,
                                            Duration weeklyWorkHours) {
        this.variationTypeCode = variationTypeCode;
        this.variationTypeDescription = variationTypeDescription;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
        this.modificationDate = modificationDate;
        this.endingDate = endingDate;
        this.weeklyWorkHours = weeklyWorkHours;
    }

    public Integer getVariationTypeCode() {
        return variationTypeCode;
    }

    public void setVariationTypeCode(Integer variationTypeCode) {
        this.variationTypeCode = variationTypeCode;
    }

    public String getVariationTypeDescription() {
        return variationTypeDescription;
    }

    public void setVariationTypeDescription(String variationTypeDescription) {
        this.variationTypeDescription = variationTypeDescription;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(LocalDate expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public LocalDate getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDate modificationDate) {
        this.modificationDate = modificationDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }

    public Duration getWeeklyWorkHours() {
        return weeklyWorkHours;
    }

    public void setWeeklyWorkHours(Duration weeklyWorkHours) {
        this.weeklyWorkHours = weeklyWorkHours;
    }

    public String getStartDateToString(){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);

        return getStartDate().format(dateFormatter);
    }

    public String getEnglishStartDateToString(){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.ENGLISH_DATE_FORMAT);

        return getStartDate().format(dateFormatter);
    }

    public static ConsultationContractDataTableDTOBuilder create() {
        return new ConsultationContractDataTableDTOBuilder();
    }

    public static class ConsultationContractDataTableDTOBuilder {

        private Integer variationTypeCode;
        private String variationTypeDescription;
        private LocalDate startDate;
        private LocalDate expectedEndDate;
        private LocalDate modificationDate;
        private LocalDate endingDate;
        private Duration hoursWorkWeek;

        public ConsultationContractDataTableDTOBuilder withVariationTypeCode(Integer typesContractVariations) {
            this.variationTypeCode = typesContractVariations;
            return this;
        }

        public ConsultationContractDataTableDTOBuilder withVariationTypeDescription(String typesContractVariationDescription) {
            this.variationTypeDescription = typesContractVariationDescription;
            return this;
        }

        public ConsultationContractDataTableDTOBuilder withStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public ConsultationContractDataTableDTOBuilder withExpectedEndDate(LocalDate expectedEndDate) {
            this.expectedEndDate = expectedEndDate;
            return this;
        }

        public ConsultationContractDataTableDTOBuilder withModificationDate(LocalDate modificationDate) {
            this.modificationDate = modificationDate;
            return this;
        }

        public ConsultationContractDataTableDTOBuilder withEndingDate(LocalDate endingDate) {
            this.endingDate = endingDate;
            return this;
        }

        public ConsultationContractDataTableDTOBuilder withHoursWorkWeek(Duration hoursWorkWeek) {
            this.hoursWorkWeek = hoursWorkWeek;
            return this;
        }

        public ConsultationContractDataTableDTO build() {
            return new ConsultationContractDataTableDTO(this.variationTypeCode, this.variationTypeDescription, this.startDate, this.expectedEndDate,
                    this.modificationDate, this.endingDate, this.hoursWorkWeek);
        }
    }
}
