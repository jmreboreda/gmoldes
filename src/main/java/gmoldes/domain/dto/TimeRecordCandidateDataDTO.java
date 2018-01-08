package gmoldes.domain.dto;

public class TimeRecordCandidateDataDTO {

    private String employeeFullName;
    private String employeeNif;
    private String quoteAccountCode;
    private String workDayType;
    private String hoursByWeek;
    private String contractType;
    private String dateFrom;
    private String dateTo;

    public TimeRecordCandidateDataDTO(String employeeFullName,
                                      String employeeNif,
                                      String quoteAccountCode,
                                      String workDayType,
                                      String hoursByWeek,
                                      String contractType,
                                      String dateFrom,
                                      String dateTo) {

        this.employeeFullName = employeeFullName;
        this.employeeNif = employeeNif;
        this.quoteAccountCode = quoteAccountCode;
        this.workDayType = workDayType;
        this.hoursByWeek = hoursByWeek;
        this.contractType = contractType;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public String getEmployeeFullName() {
        return employeeFullName;
    }

    public void setEmployeeFullName(String employeeFullName) {
        this.employeeFullName = employeeFullName;
    }

    public String getEmployeeNif() {
        return employeeNif;
    }

    public void setEmployeeNif(String employeeNif) {
        this.employeeNif = employeeNif;
    }

    public String getQuoteAccountCode() {
        return quoteAccountCode;
    }

    public void setQuoteAccountCode(String quoteAccountCode) {
        this.quoteAccountCode = quoteAccountCode;
    }

    public String getWorkDayType() {
        return workDayType;
    }

    public void setWorkDayType(String workDayType) {
        this.workDayType = workDayType;
    }

    public String getHoursByWeek() {
        return hoursByWeek;
    }

    public void setHoursByWeek(String hoursByWeek) {
        this.hoursByWeek = hoursByWeek;
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
}
