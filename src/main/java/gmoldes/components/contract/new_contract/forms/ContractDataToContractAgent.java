package gmoldes.components.contract.new_contract.forms;

import gmoldes.components.contract.new_contract.components.WorkDaySchedule;
import gmoldes.utilities.Parameters;
import gmoldes.utilities.Utilities;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class ContractDataToContractAgent {

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

    public ContractDataToContractAgent(String notificationType, String officialContractNumber, String employerFullName, String employerQuoteAccountCode, LocalDate notificationDate,
                                       LocalTime notificationHour, String employeeFullName, String employeeNif, String employeeNASS,
                                       String employeeBirthDate, String employeeCivilState, String employeeNationality, String employeeFullAddress,
                                       String employeeMaxStudyLevel, Set<DayOfWeek> dayOfWeekSet, String contractTypeDescription,
                                       LocalDate startDate, LocalDate endDate, Duration durationDays, Set<WorkDaySchedule> schedule,
                                       String additionalData, String laborCategory) {

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
        this.laborCategory = laborCategory; }

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

    public static ContractDataToContractAgentBuilder create() {
        return new ContractDataToContractAgentBuilder();
    }

    public static class ContractDataToContractAgentBuilder {

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

        public ContractDataToContractAgentBuilder withNotificationType(String notificationType) {
            this.notificationType = notificationType;
            return this;
        }

        public ContractDataToContractAgentBuilder withOfficialContractNumber(String officialContractNumber) {
            this.officialContractNumber = officialContractNumber;
            return this;
        }

        public ContractDataToContractAgentBuilder withEmployerFullName(String employerFullName) {
            this.employerFullName = employerFullName;
            return this;
        }

        public ContractDataToContractAgentBuilder withEmployerQuoteAccountCode(String employerQuoteAccountCode) {
            this.employerQuoteAccountCode = employerQuoteAccountCode;
            return this;
        }

        public ContractDataToContractAgentBuilder withNotificationDate(LocalDate notificationDate) {
            this.notificationDate = notificationDate;
            return this;
        }

        public ContractDataToContractAgentBuilder withNotificationHour(LocalTime notificationHour) {
            this.notificationHour = notificationHour;
            return this;
        }

        public ContractDataToContractAgentBuilder withEmployeeFullName(String employeeFullName) {
            this.employeeFullName = employeeFullName;
            return this;
        }

        public ContractDataToContractAgentBuilder withEmployeeNif(String employeeNif) {
            this.employeeNif = employeeNif;
            return this;
        }

        public ContractDataToContractAgentBuilder withEmployeeNASS(String employeeNASS) {
            this.employeeNASS = employeeNASS;
            return this;
        }

        public ContractDataToContractAgentBuilder withEmployeeBirthDate(String employeeBirthDate) {
            this.employeeBirthDate = employeeBirthDate;
            return this;
        }

        public ContractDataToContractAgentBuilder withEmployeeCivilState(String employeeCivilState) {
            this.employeeCivilState = employeeCivilState;
            return this;
        }

        public ContractDataToContractAgentBuilder withEmployeeNationality(String employeeNationality) {
            this.employeeNationality = employeeNationality;
            return this;
        }

        public ContractDataToContractAgentBuilder withEmployeeFullAddress(String employeeFullAddress) {
            this.employeeFullAddress = employeeFullAddress;
            return this;
        }

        public ContractDataToContractAgentBuilder withEmployeeMaxStudyLevel(String employeeMaxStudyLevel) {
            this.employeeMaxStudyLevel = employeeMaxStudyLevel;
            return this;
        }

        public ContractDataToContractAgentBuilder withDayOfWeekSet(Set<DayOfWeek> dayOfWeekSet) {
            this.dayOfWeekSet = dayOfWeekSet;
            return this;
        }

        public ContractDataToContractAgentBuilder withContractTypeDescription(String contractTypeDescription) {
            this.contractTypeDescription = contractTypeDescription;
            return this;
        }

        public ContractDataToContractAgentBuilder withStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public ContractDataToContractAgentBuilder withEndDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public ContractDataToContractAgentBuilder withDurationDays(Duration durationDays) {
            this.durationDays = durationDays;
            return this;
        }

        public ContractDataToContractAgentBuilder withSchedule(Set<WorkDaySchedule> schedule) {
            this.schedule = schedule;
            return this;
        }

        public ContractDataToContractAgentBuilder withAdditionalData(String additionalData) {
            this.additionalData = additionalData;
            return this;
        }

        public ContractDataToContractAgentBuilder withLaborCategory(String laborCategory) {
            this.laborCategory = laborCategory;
            return this;
        }


        public ContractDataToContractAgent build() {
            return new ContractDataToContractAgent(this.notificationType, this.officialContractNumber,this.employerFullName, this.employerQuoteAccountCode,
                    this.notificationDate, this.notificationHour, this.employeeFullName, this.employeeNif, this.employeeNASS, this.employeeBirthDate,
                    this.employeeCivilState, this.employeeNationality, this.employeeFullAddress, this.employeeMaxStudyLevel, this.dayOfWeekSet,
                    this.contractTypeDescription, this.startDate, this.endDate, this.durationDays, this.schedule, this.additionalData,
                    this.laborCategory);
        }
    }
}
