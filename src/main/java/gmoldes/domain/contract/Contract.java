package gmoldes.domain.contract;

import gmoldes.ApplicationConstants;
import gmoldes.components.contract.ContractConstants;
import gmoldes.components.contract.new_contract.components.WorkDaySchedule;
import gmoldes.domain.contract_type.ContractType;
import gmoldes.domain.employee.Employee;
import gmoldes.domain.employer.Employer;
import gmoldes.domain.types_contract_variations.TypesContractVariations;
import gmoldes.utilities.Utilities;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class Contract {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);

    private Employer employer;
    private Employee employee;
    private ContractType contractType;
    private Integer gmContractNumber;
    private TypesContractVariations variationType;
    private LocalDate startDate;
    private LocalDate expectedEndDate;
    private LocalDate modificationDate;
    private LocalDate endingDate;
    private Set<WorkDaySchedule> weeklyWorkSchedule;
    private String laborCategory;
    private String quoteAccountCode;
    private String identificationContractNumberINEM;
    private String publicNotes;
    private String privateNotes;

    public Contract() {
    }

    public Contract(Employer employer,
                    Employee employee,
                    ContractType contractType,
                    Integer gmContractNumber,
                    TypesContractVariations variationType,
                    LocalDate startDate,
                    LocalDate expectedEndDate,
                    LocalDate modificationDate,
                    LocalDate endingDate,
                    Set<WorkDaySchedule> weeklyWorkSchedule,
                    String laborCategory,
                    String quoteAccountCode,
                    String identificationContractNumberINEM,
                    String publicNotes,
                    String privateNotes) {

        this.employer = employer;
        this.employee = employee;
        this.contractType = contractType;
        this.gmContractNumber = gmContractNumber;
        this.variationType = variationType;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
        this.modificationDate = modificationDate;
        this.endingDate = endingDate;
        this.weeklyWorkSchedule = weeklyWorkSchedule;
        this.laborCategory = laborCategory;
        this.quoteAccountCode = quoteAccountCode;
        this.identificationContractNumberINEM = identificationContractNumberINEM;
        this.publicNotes = publicNotes;
        this.privateNotes = privateNotes;
    }

    public Employer getEmployer() {
        return employer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setGmContractNumber(Integer gmContractNumber) {
        this.gmContractNumber = gmContractNumber;
    }

    public Integer getGmContractNumber() {
        return gmContractNumber;
    }

    public TypesContractVariations getVariationType() {
        return variationType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getExpectedEndDate() {
        return expectedEndDate;
    }

    public LocalDate getModificationDate() {
        return modificationDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public Set<WorkDaySchedule> getWeeklyWorkSchedule() {
        return weeklyWorkSchedule;
    }

    public String getLaborCategory() {
        return laborCategory;
    }

    public String getQuoteAccountCode() {
        return quoteAccountCode;
    }

    public String getIdentificationContractNumberINEM() {
        return identificationContractNumberINEM;
    }

    public String getPublicNotes() {
        return publicNotes;
    }

    public String getPrivateNotes() {
        return privateNotes;
    }

    public String getEmployerAlphabeticalName (){

        return getEmployer().getAlphabeticalOrderName();
    }

    public String getEmployeeAlphabeticalName(){

        return getEmployee().toString();
    }

    public String getStartDateFormatted(){

        return getStartDate().format(formatter);
    }

    public String getExpectedEndDateFormatted(){

        return getExpectedEndDate().format(formatter);
    }

    public String getModificationDateFormatted(){

        return getModificationDate().format(formatter);
    }

    public String getEndingDateFormatted(){

        return getEndingDate().format(formatter);
    }

    public Integer getContractCode(){

        return getContractType().getContractCode();
    }

    public String detailedNameOfContract(){

        String contractDuration = "";
        if(getContractType().getIsDeterminedDuration()){
            contractDuration = ContractConstants.DETERMINED_DURATION_TEXT;
        }

        if(getContractType().getIsTemporal()){
            contractDuration = ContractConstants.TEMPORAL_DURATION_TEXT;

        }
        if(getContractType().getIsUndefined()){
            contractDuration = ContractConstants.UNDEFINED_DURATION_TEXT;
        }

        String partialFullTimeString = getPartialOrFullWorkDayString();
        if(isPartialWorkDay()){
            String weeklyWorkHours = Utilities.converterDurationToTimeString(getWeeklyWorkHours());
            partialFullTimeString = partialFullTimeString.concat(" [").concat(weeklyWorkHours).concat(" " + ContractConstants.HOURS_WORK_WEEK_TEXT.toLowerCase()).concat("]");
        }

        return getContractType().getColloquial().concat(", ").concat(contractDuration).concat(", ").concat(partialFullTimeString);
    }

    public Duration getWeeklyWorkHours(){

        Duration weeklyWorkDuration = null;
        Set<WorkDaySchedule> schedule = getWeeklyWorkSchedule();
        for(WorkDaySchedule workDaySchedule : schedule){
            if(workDaySchedule.getDurationHours() != null){
                weeklyWorkDuration = weeklyWorkDuration.plus(workDaySchedule.getDurationHours());
            }
        }

        return weeklyWorkDuration;
    }

    public Boolean isPartialWorkDay(){

        if(getContractType().getIsPartialTime()){

            return true;
        }

        return false;
    }

    public Boolean isFullWorkDay(){

        if(getContractType().getIsFullTime()){

            return true;
        }

        return false;
    }

    public String getPartialOrFullWorkDayString(){

        if(getContractType().getIsFullTime()){

            return ContractConstants.FULL_WORKDAY;
        }

        return ContractConstants.PARTIAL_WORKDAY;
    }

    public Set<DayOfWeek> getDaysOfWeekToWork(){

        Set<DayOfWeek> dayOfWeekSet = new HashSet<>();

        for(WorkDaySchedule workDaySchedule : getWeeklyWorkSchedule()){
            if(workDaySchedule.getDayOfWeek().equals("lunes")){
                dayOfWeekSet.add(DayOfWeek.MONDAY);
            }

            if(workDaySchedule.getDayOfWeek().equals("martes")){
                dayOfWeekSet.add(DayOfWeek.TUESDAY);
            }

            if(workDaySchedule.getDayOfWeek().equals("miércoles")){
                dayOfWeekSet.add(DayOfWeek.WEDNESDAY);
            }

            if(workDaySchedule.getDayOfWeek().equals("jueves")){
                dayOfWeekSet.add(DayOfWeek.THURSDAY);
            }

            if(workDaySchedule.getDayOfWeek().equals("viernes")){
                dayOfWeekSet.add(DayOfWeek.FRIDAY);
            }

            if(workDaySchedule.getDayOfWeek().equals("sábado")){
                dayOfWeekSet.add(DayOfWeek.SATURDAY);
            }

            if(workDaySchedule.getDayOfWeek().equals("domingo")){
                dayOfWeekSet.add(DayOfWeek.SUNDAY);
            }
        }

        return dayOfWeekSet;
    }

    public static ContractBuilder create() {
        return new ContractBuilder();
    }

    public static class ContractBuilder {

        private Employer employer;
        private Employee employee;
        private ContractType contractType;
        private Integer gmContractNumber;
        private TypesContractVariations variationType;
        private LocalDate startDate;
        private LocalDate expectedEndDate;
        private LocalDate modificationDate;
        private LocalDate endingDate;
        private Set<WorkDaySchedule> weeklyWorkSchedule;
        private String laborCategory;
        private String quoteAccountCode;
        private String identificationContractNumberINEM;
        private String publicNotes;
        private String privateNotes;

        public ContractBuilder withEmployer(Employer employer) {
            this.employer = employer;
            return this;
        }

        public ContractBuilder withEmployee(Employee employee) {
            this.employee = employee;
            return this;
        }

        public ContractBuilder withContractType(ContractType contractType) {
            this.contractType = contractType;
            return this;
        }


        public ContractBuilder withGMContractNumber(Integer gmContractNumber) {
            this.gmContractNumber = gmContractNumber;
            return this;
        }

        public ContractBuilder withVariationType(TypesContractVariations variationType) {
            this.variationType = variationType;
            return this;
        }

        public ContractBuilder withStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public ContractBuilder withExpectedEndDate(LocalDate expectedEndDate) {
            this.expectedEndDate = expectedEndDate;
            return this;
        }

        public ContractBuilder withModificationDate(LocalDate modificationDate) {
            this.modificationDate = modificationDate;
            return this;
        }

        public ContractBuilder withEndingDate(LocalDate endingDate) {
            this.endingDate = endingDate;
            return this;
        }

        public ContractBuilder withWeeklyWorkSchedule(Set<WorkDaySchedule> weeklyWorkSchedule) {
            this.weeklyWorkSchedule = weeklyWorkSchedule;
            return this;
        }

        public ContractBuilder withLaborCategory(String laborCategory) {
            this.laborCategory = laborCategory;
            return this;
        }

        public ContractBuilder withQuoteAccountCode(String quoteAccountCode) {
            this.quoteAccountCode = quoteAccountCode;
            return this;
        }

        public ContractBuilder withIdentificationContractNumberINEM(String identificationContractNumberINEM) {
            this.identificationContractNumberINEM = identificationContractNumberINEM;
            return this;
        }

        public ContractBuilder withPublicNotes(String publicNotes) {
            this.publicNotes = publicNotes;
            return this;
        }

        public ContractBuilder withPrivateNotes(String privateNotes) {
            this.privateNotes = privateNotes;
            return this;
        }

        public Contract build() {
            return new Contract(this.employer, this.employee, this.contractType, this.gmContractNumber, this.variationType, this.startDate, this.expectedEndDate, this.modificationDate,
            this.endingDate, this.weeklyWorkSchedule, this.laborCategory, this.quoteAccountCode, this.identificationContractNumberINEM, this.publicNotes, this.privateNotes);
        }
    }
}
