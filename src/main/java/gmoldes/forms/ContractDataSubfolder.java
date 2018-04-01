package gmoldes.forms;

import gmoldes.utilities.Parameters;
import gmoldes.utilities.Utilities;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class ContractDataSubfolder {

    private String notificationType;
    private String officialContractNumber;
    private String employerFullName;
    private String employerQuoteAccountCode;
    private LocalDate notificationDate;
    private LocalTime notificationHour;
    private String employeeFullName;
    private String employeeNif;
    private String employeeNASS;
    private String employeeBirthDate;
    private String employeeCivilState;
    private String employeeNationality;
    private String employeeFullAddress;
    private String employeeMaxStudyLevel;
    private Set<DayOfWeek> dayOfWeekSet;
    private String contractTypeDescription;
    private LocalDate startDate;
    private LocalDate endDate;
    private Duration durationDays;
    private Set<WorkDaySchedule> schedule;
    private String additionalData;
    private String laborCategory;
    private String gmContractNumber;

    public ContractDataSubfolder(String notificationType, String officialContractNumber, String employerFullName, String employerQuoteAccountCode, LocalDate notificationDate,
                                 LocalTime notificationHour, String employeeFullName, String employeeNif, String employeeNASS,
                                 String employeeBirthDate, String employeeCivilState, String employeeNationality, String employeeFullAddress,
                                 String employeeMaxStudyLevel, Set<DayOfWeek> dayOfWeekSet, String contractTypeDescription,
                                 LocalDate startDate, LocalDate endDate, Duration durationDays, Set<WorkDaySchedule> schedule,
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

    public LocalDate getNotificationDate() {
        return notificationDate;
    }

    public LocalTime getNotificationHour() {
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

    public String getContractTypeDescription() {
        return contractTypeDescription;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Duration getDurationDays() {
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


        return Utilities.replaceWithUnderscore(employerFullName)
                + "_" +
                Utilities.replaceWithUnderscore(Parameters.NEW_CONTRACT_TEXT.toLowerCase())
                + "_" +
                startDate.format(dateFormatter)
                + "_" +
                Utilities.replaceWithUnderscore(employeeFullName);
    }

    public static ContractDataSubfolderBuilder create() {
        return new ContractDataSubfolderBuilder();
    }

    public static class ContractDataSubfolderBuilder {

        private String notificationType;
        private String officialContractNumber;
        private String employerFullName;
        private String employerQuoteAccountCode;
        private LocalDate notificationDate;
        private LocalTime notificationHour;
        private String employeeFullName;
        private String employeeNif;
        private String employeeNASS;
        private String employeeBirthDate;
        private String employeeCivilState;
        private String employeeNationality;
        private String employeeFullAddress;
        private String employeeMaxStudyLevel;
        private Set<DayOfWeek> dayOfWeekSet;
        private String contractTypeDescription;
        private LocalDate startDate;
        private LocalDate endDate;
        private Duration durationDays;
        private Set<WorkDaySchedule> schedule;
        private String additionalData;
        private String laborCategory;
        private String gmContractNumber;

        public ContractDataSubfolderBuilder withNotificationType(String notificationType) {
            this.notificationType = notificationType;
            return this;
        }

        public ContractDataSubfolderBuilder withOfficialContractNumber(String officialContractNumber) {
            this.officialContractNumber = officialContractNumber;
            return this;
        }

        public ContractDataSubfolderBuilder withEmployerFullName(String employerFullName) {
            this.employerFullName = employerFullName;
            return this;
        }

        public ContractDataSubfolderBuilder withEmployerQuoteAccountCode(String employerQuoteAccountCode) {
            this.employerQuoteAccountCode = employerQuoteAccountCode;
            return this;
        }

        public ContractDataSubfolderBuilder withNotificationDate(LocalDate notificationDate) {
            this.notificationDate = notificationDate;
            return this;
        }

        public ContractDataSubfolderBuilder withNotificationHour(LocalTime notificationHour) {
            this.notificationHour = notificationHour;
            return this;
        }

        public ContractDataSubfolderBuilder withEmployeeFullName(String employeeFullName) {
            this.employeeFullName = employeeFullName;
            return this;
        }

        public ContractDataSubfolderBuilder withEmployeeNif(String employeeNif) {
            this.employeeNif = employeeNif;
            return this;
        }

        public ContractDataSubfolderBuilder withEmployeeNASS(String employeeNASS) {
            this.employeeNASS = employeeNASS;
            return this;
        }

        public ContractDataSubfolderBuilder withEmployeeBirthDate(String employeeBirthDate) {
            this.employeeBirthDate = employeeBirthDate;
            return this;
        }

        public ContractDataSubfolderBuilder withEmployeeCivilState(String employeeCivilState) {
            this.employeeCivilState = employeeCivilState;
            return this;
        }

        public ContractDataSubfolderBuilder withEmployeeNationality(String employeeNationality) {
            this.employeeNationality = employeeNationality;
            return this;
        }

        public ContractDataSubfolderBuilder withEmployeeFullAddress(String employeeFullAddress) {
            this.employeeFullAddress = employeeFullAddress;
            return this;
        }

        public ContractDataSubfolderBuilder withEmployeeMaxStudyLevel(String employeeMaxStudyLevel) {
            this.employeeMaxStudyLevel = employeeMaxStudyLevel;
            return this;
        }

        public ContractDataSubfolderBuilder withDayOfWeekSet(Set<DayOfWeek> dayOfWeekSet) {
            this.dayOfWeekSet = dayOfWeekSet;
            return this;
        }

        public ContractDataSubfolderBuilder withContractTypeDescription(String contractTypeDescription) {
            this.contractTypeDescription = contractTypeDescription;
            return this;
        }

        public ContractDataSubfolderBuilder withStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public ContractDataSubfolderBuilder withEndDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public ContractDataSubfolderBuilder withDurationDays(Duration durationDays) {
            this.durationDays = durationDays;
            return this;
        }

        public ContractDataSubfolderBuilder withSchedule(Set<WorkDaySchedule> schedule) {
            this.schedule = schedule;
            return this;
        }

        public ContractDataSubfolderBuilder withAdditionalData(String additionalData) {
            this.additionalData = additionalData;
            return this;
        }

        public ContractDataSubfolderBuilder withLaborCategory(String laborCategory) {
            this.laborCategory = laborCategory;
            return this;
        }

        public ContractDataSubfolderBuilder withGmContractNumber(String gmContractNumber) {
            this.gmContractNumber = gmContractNumber;
            return this;
        }


        public ContractDataSubfolder build() {
            return new ContractDataSubfolder(this.notificationType, this.officialContractNumber,this.employerFullName, this.employerQuoteAccountCode,
                    this.notificationDate, this.notificationHour, this.employeeFullName, this.employeeNif, this.employeeNASS, this.employeeBirthDate,
                    this.employeeCivilState, this.employeeNationality, this.employeeFullAddress, this.employeeMaxStudyLevel, this.dayOfWeekSet,
                    this.contractTypeDescription, this.startDate, this.endDate, this.durationDays, this.schedule, this.additionalData,
                    this.laborCategory, this.gmContractNumber);
        }
    }
}
