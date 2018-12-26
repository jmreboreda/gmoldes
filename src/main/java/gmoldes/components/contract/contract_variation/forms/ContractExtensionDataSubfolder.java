package gmoldes.components.contract.contract_variation.forms;

import gmoldes.components.contract.new_contract.components.WorkDaySchedule;
import gmoldes.utilities.Utilities;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class ContractExtensionDataSubfolder {

    private String notificationType;
    private String officialContractNumber;
    private String employerFullName;
    private String employerQuoteAccountCode;
    private String notificationDate;
    private String notificationHour;
    private String employeeFullName;
    private String employeeNif;
    private String employeeNASS;
    private String employeeBirthDate;
    private String employeeCivilState;
    private String employeeNationality;
    private String employeeFullAddress;
    private String employeeMaxStudyLevel;
    private Set<DayOfWeek> dayOfWeekSet;
    private String hoursWorkWeek;
    private String contractTypeDescription;
    private String startDate;
    private String endDate;
    private String durationDays;
    private Set<WorkDaySchedule> schedule;
    private String additionalData;
    private String laborCategory;
    private String gmContractNumber;

    public ContractExtensionDataSubfolder(String notificationType, String officialContractNumber, String employerFullName, String employerQuoteAccountCode, String notificationDate,
                                          String notificationHour, String employeeFullName, String employeeNif, String employeeNASS,
                                          String employeeBirthDate, String employeeCivilState, String employeeNationality, String employeeFullAddress,
                                          String employeeMaxStudyLevel, Set<DayOfWeek> dayOfWeekSet, String hoursWorkWeek, String contractTypeDescription,
                                          String startDate, String endDate, String durationDays, Set<WorkDaySchedule> schedule,
                                          String additionalData, String laborCategory, String gmContractNumber) {

        this.notificationType = notificationType;
        this.officialContractNumber = officialContractNumber;
        this.employerFullName = employerFullName;
        this.employerQuoteAccountCode = employerQuoteAccountCode;
        this.notificationDate = notificationDate;
        this.notificationHour = notificationHour;
        this.employeeFullName = employeeFullName;
        this.employeeNif = employeeNif;
        this.employeeNASS = employeeNASS;
        this.employeeBirthDate = employeeBirthDate;
        this.employeeCivilState = employeeCivilState;
        this.employeeNationality = employeeNationality;
        this.employeeFullAddress = employeeFullAddress;
        this.employeeMaxStudyLevel = employeeMaxStudyLevel;
        this.dayOfWeekSet = dayOfWeekSet;
        this.hoursWorkWeek = hoursWorkWeek;
        this.contractTypeDescription = contractTypeDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.durationDays = durationDays;
        this.schedule = schedule;
        this.additionalData = additionalData;
        this.laborCategory = laborCategory;
        this.gmContractNumber = gmContractNumber;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public String getOfficialContractNumber() {
        return officialContractNumber;
    }

    public String getEmployerFullName() {
        return employerFullName;
    }

    public String getEmployerQuoteAccountCode() {
        return employerQuoteAccountCode;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public String getNotificationHour() {
        return notificationHour;
    }

    public String getEmployeeFullName() {
        return employeeFullName;
    }

    public String getEmployeeNif() {
        return employeeNif;
    }

    public String getEmployeeNASS() {
        return employeeNASS;
    }

    public String getEmployeeBirthDate() {
        return employeeBirthDate;
    }

    public String getEmployeeCivilState() {
        return employeeCivilState;
    }

    public String getEmployeeNationality() {
        return employeeNationality;
    }

    public String getEmployeeFullAddress() {
        return employeeFullAddress;
    }

    public String getEmployeeMaxStudyLevel() {
        return employeeMaxStudyLevel;
    }

    public Set<DayOfWeek> getDayOfWeekSet() {
        return dayOfWeekSet;
    }

    public String getHoursWorkWeek() {
        return hoursWorkWeek;
    }

    public String getContractTypeDescription() {
        return contractTypeDescription;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getDurationDays() {
        return durationDays;
    }

    public Set<WorkDaySchedule> getSchedule() {
        return schedule;
    }

    public String getAdditionalData() {
        return additionalData;
    }

    public String getLaborCategory() {
        return laborCategory;
    }

    public String getGmContractNumber() {
        return gmContractNumber;
    }

    public String toFileName(){

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd_MM_yyyy");

        String fileNameDate = startDate != null ? startDate : endDate;

        return Utilities.replaceWithUnderscore(employerFullName)
                + "_" +
//                Utilities.replaceWithUnderscore(Parameters.NEW_CONTRACT_TEXT.toLowerCase())
                Utilities.replaceWithUnderscore(getNotificationType().toLowerCase())
                + "_" +
                fileNameDate
                + "_" +
                Utilities.replaceWithUnderscore(employeeFullName);
    }

    public static ContractExtensionDataSubfolderBuilder create() {
        return new ContractExtensionDataSubfolderBuilder();
    }

    public static class ContractExtensionDataSubfolderBuilder {

        private String notificationType;
        private String officialContractNumber;
        private String employerFullName;
        private String employerQuoteAccountCode;
        private String notificationDate;
        private String notificationHour;
        private String employeeFullName;
        private String employeeNif;
        private String employeeNASS;
        private String employeeBirthDate;
        private String employeeCivilState;
        private String employeeNationality;
        private String employeeFullAddress;
        private String employeeMaxStudyLevel;
        private Set<DayOfWeek> dayOfWeekSet;
        private String hoursWorkWeek;
        private String contractTypeDescription;
        private String startDate;
        private String endDate;
        private String durationDays;
        private Set<WorkDaySchedule> schedule;
        private String additionalData;
        private String laborCategory;
        private String gmContractNumber;

        public ContractExtensionDataSubfolderBuilder withNotificationType(String notificationType) {
            this.notificationType = notificationType;
            return this;
        }

        public ContractExtensionDataSubfolderBuilder withOfficialContractNumber(String officialContractNumber) {
            this.officialContractNumber = officialContractNumber;
            return this;
        }

        public ContractExtensionDataSubfolderBuilder withEmployerFullName(String employerFullName) {
            this.employerFullName = employerFullName;
            return this;
        }

        public ContractExtensionDataSubfolderBuilder withEmployerQuoteAccountCode(String employerQuoteAccountCode) {
            this.employerQuoteAccountCode = employerQuoteAccountCode;
            return this;
        }

        public ContractExtensionDataSubfolderBuilder withNotificationDate(String notificationDate) {
            this.notificationDate = notificationDate;
            return this;
        }

        public ContractExtensionDataSubfolderBuilder withNotificationHour(String notificationHour) {
            this.notificationHour = notificationHour;
            return this;
        }

        public ContractExtensionDataSubfolderBuilder withEmployeeFullName(String employeeFullName) {
            this.employeeFullName = employeeFullName;
            return this;
        }

        public ContractExtensionDataSubfolderBuilder withEmployeeNif(String employeeNif) {
            this.employeeNif = employeeNif;
            return this;
        }

        public ContractExtensionDataSubfolderBuilder withEmployeeNASS(String employeeNASS) {
            this.employeeNASS = employeeNASS;
            return this;
        }

        public ContractExtensionDataSubfolderBuilder withEmployeeBirthDate(String employeeBirthDate) {
            this.employeeBirthDate = employeeBirthDate;
            return this;
        }

        public ContractExtensionDataSubfolderBuilder withEmployeeCivilState(String employeeCivilState) {
            this.employeeCivilState = employeeCivilState;
            return this;
        }

        public ContractExtensionDataSubfolderBuilder withEmployeeNationality(String employeeNationality) {
            this.employeeNationality = employeeNationality;
            return this;
        }

        public ContractExtensionDataSubfolderBuilder withEmployeeFullAddress(String employeeFullAddress) {
            this.employeeFullAddress = employeeFullAddress;
            return this;
        }

        public ContractExtensionDataSubfolderBuilder withEmployeeMaxStudyLevel(String employeeMaxStudyLevel) {
            this.employeeMaxStudyLevel = employeeMaxStudyLevel;
            return this;
        }

        public ContractExtensionDataSubfolderBuilder withDayOfWeekSet(Set<DayOfWeek> dayOfWeekSet) {
            this.dayOfWeekSet = dayOfWeekSet;
            return this;
        }

        public ContractExtensionDataSubfolderBuilder withHoursWorkWeek(String hoursWorkWeek) {
            this.hoursWorkWeek = hoursWorkWeek;
            return this;
        }

        public ContractExtensionDataSubfolderBuilder withContractTypeDescription(String contractTypeDescription) {
            this.contractTypeDescription = contractTypeDescription;
            return this;
        }

        public ContractExtensionDataSubfolderBuilder withStartDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        public ContractExtensionDataSubfolderBuilder withEndDate(String endDate) {
            this.endDate = endDate;
            return this;
        }

        public ContractExtensionDataSubfolderBuilder withDurationDays(String durationDays) {
            this.durationDays = durationDays;
            return this;
        }

        public ContractExtensionDataSubfolderBuilder withSchedule(Set<WorkDaySchedule> schedule) {
            this.schedule = schedule;
            return this;
        }

        public ContractExtensionDataSubfolderBuilder withAdditionalData(String additionalData) {
            this.additionalData = additionalData;
            return this;
        }

        public ContractExtensionDataSubfolderBuilder withLaborCategory(String laborCategory) {
            this.laborCategory = laborCategory;
            return this;
        }

        public ContractExtensionDataSubfolderBuilder withGmContractNumber(String gmContractNumber) {
            this.gmContractNumber = gmContractNumber;
            return this;
        }


        public ContractExtensionDataSubfolder build() {
            return new ContractExtensionDataSubfolder(this.notificationType, this.officialContractNumber,this.employerFullName, this.employerQuoteAccountCode,
                    this.notificationDate, this.notificationHour, this.employeeFullName, this.employeeNif, this.employeeNASS, this.employeeBirthDate,
                    this.employeeCivilState, this.employeeNationality, this.employeeFullAddress, this.employeeMaxStudyLevel, this.dayOfWeekSet,
                    this.hoursWorkWeek, this.contractTypeDescription, this.startDate, this.endDate, this.durationDays, this.schedule, this.additionalData,
                    this.laborCategory, this.gmContractNumber);
        }
    }
}
