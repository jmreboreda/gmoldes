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

public class ContractDataToContractsAgent {

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

    public ContractDataToContractsAgent(String notificationType, String officialContractNumber, String employerFullName, String employerQuoteAccountCode, LocalDate notificationDate,
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

        LocalDate dateInFileName = getStartDate() != null ? getStartDate() : getEndDate();

        return Utilities.replaceWithUnderscore(employerFullName)
                + "_" +
                Utilities.replaceWithUnderscore(Parameters.NEW_CONTRACT_TEXT.toLowerCase())
                + "_" +
                dateInFileName.format(dateFormatter)
                + "_" +
                Utilities.replaceWithUnderscore(employeeFullName);
    }

    public static NewContractDataToContractAgentBuilder create() {
        return new NewContractDataToContractAgentBuilder();
    }

    public static class NewContractDataToContractAgentBuilder {

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

        public NewContractDataToContractAgentBuilder withNotificationType(String notificationType) {
            this.notificationType = notificationType;
            return this;
        }

        public NewContractDataToContractAgentBuilder withOfficialContractNumber(String officialContractNumber) {
            this.officialContractNumber = officialContractNumber;
            return this;
        }

        public NewContractDataToContractAgentBuilder withEmployerFullName(String employerFullName) {
            this.employerFullName = employerFullName;
            return this;
        }

        public NewContractDataToContractAgentBuilder withEmployerQuoteAccountCode(String employerQuoteAccountCode) {
            this.employerQuoteAccountCode = employerQuoteAccountCode;
            return this;
        }

        public NewContractDataToContractAgentBuilder withNotificationDate(LocalDate notificationDate) {
            this.notificationDate = notificationDate;
            return this;
        }

        public NewContractDataToContractAgentBuilder withNotificationHour(LocalTime notificationHour) {
            this.notificationHour = notificationHour;
            return this;
        }

        public NewContractDataToContractAgentBuilder withEmployeeFullName(String employeeFullName) {
            this.employeeFullName = employeeFullName;
            return this;
        }

        public NewContractDataToContractAgentBuilder withEmployeeNif(String employeeNif) {
            this.employeeNif = employeeNif;
            return this;
        }

        public NewContractDataToContractAgentBuilder withEmployeeNASS(String employeeNASS) {
            this.employeeNASS = employeeNASS;
            return this;
        }

        public NewContractDataToContractAgentBuilder withEmployeeBirthDate(String employeeBirthDate) {
            this.employeeBirthDate = employeeBirthDate;
            return this;
        }

        public NewContractDataToContractAgentBuilder withEmployeeCivilState(String employeeCivilState) {
            this.employeeCivilState = employeeCivilState;
            return this;
        }

        public NewContractDataToContractAgentBuilder withEmployeeNationality(String employeeNationality) {
            this.employeeNationality = employeeNationality;
            return this;
        }

        public NewContractDataToContractAgentBuilder withEmployeeFullAddress(String employeeFullAddress) {
            this.employeeFullAddress = employeeFullAddress;
            return this;
        }

        public NewContractDataToContractAgentBuilder withEmployeeMaxStudyLevel(String employeeMaxStudyLevel) {
            this.employeeMaxStudyLevel = employeeMaxStudyLevel;
            return this;
        }

        public NewContractDataToContractAgentBuilder withDayOfWeekSet(Set<DayOfWeek> dayOfWeekSet) {
            this.dayOfWeekSet = dayOfWeekSet;
            return this;
        }

        public NewContractDataToContractAgentBuilder withContractTypeDescription(String contractTypeDescription) {
            this.contractTypeDescription = contractTypeDescription;
            return this;
        }

        public NewContractDataToContractAgentBuilder withStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public NewContractDataToContractAgentBuilder withEndDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public NewContractDataToContractAgentBuilder withDurationDays(Duration durationDays) {
            this.durationDays = durationDays;
            return this;
        }

        public NewContractDataToContractAgentBuilder withSchedule(Set<WorkDaySchedule> schedule) {
            this.schedule = schedule;
            return this;
        }

        public NewContractDataToContractAgentBuilder withAdditionalData(String additionalData) {
            this.additionalData = additionalData;
            return this;
        }

        public NewContractDataToContractAgentBuilder withLaborCategory(String laborCategory) {
            this.laborCategory = laborCategory;
            return this;
        }


        public ContractDataToContractsAgent build() {
            return new ContractDataToContractsAgent(this.notificationType, this.officialContractNumber,this.employerFullName, this.employerQuoteAccountCode,
                    this.notificationDate, this.notificationHour, this.employeeFullName, this.employeeNif, this.employeeNASS, this.employeeBirthDate,
                    this.employeeCivilState, this.employeeNationality, this.employeeFullAddress, this.employeeMaxStudyLevel, this.dayOfWeekSet,
                    this.contractTypeDescription, this.startDate, this.endDate, this.durationDays, this.schedule, this.additionalData,
                    this.laborCategory);
        }
    }
}
