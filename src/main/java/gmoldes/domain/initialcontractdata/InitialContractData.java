package gmoldes.domain.initialcontractdata;

public class InitialContractData {

    private Integer clientGMId;
    private Integer workerId;
    private String quoteAccountCode;
    private String laborCategory ;
    private String weeklyWorkHours;
    private String daysOfWeekToWork;
    private String fullPartialWorkday;
    private String identificationContractNumberINEM;

    public InitialContractData() {
    }

    public InitialContractData(Integer clientGMId, Integer workerId, String quoteAccountCode, String laborCategory, String weeklyWorkHours,
                               String daysOfWeekToWork, String fullPartialWorkday, String identificationContractNumberINEM) {
        this.clientGMId = clientGMId;
        this.workerId = workerId;
        this.quoteAccountCode = quoteAccountCode;
        this.laborCategory = laborCategory;
        this.weeklyWorkHours = weeklyWorkHours;
        this.daysOfWeekToWork = daysOfWeekToWork;
        this.fullPartialWorkday = fullPartialWorkday;
        this.identificationContractNumberINEM = identificationContractNumberINEM;
    }

    public Integer getClientGMId() {
        return clientGMId;
    }

    public void setClientGMId(Integer clientGMId) {
        this.clientGMId = clientGMId;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public String getQuoteAccountCode() {
        return quoteAccountCode;
    }

    public void setQuoteAccountCode(String quoteAccountCode) {
        this.quoteAccountCode = quoteAccountCode;
    }

    public String getLaborCategory() {
        return laborCategory;
    }

    public void setLaborCategory(String laborCategory) {
        this.laborCategory = laborCategory;
    }

    public String getWeeklyWorkHours() {
        return weeklyWorkHours;
    }

    public void setWeeklyWorkHours(String weeklyWorkHours) {
        this.weeklyWorkHours = weeklyWorkHours;
    }

    public String getDaysOfWeekToWork() {
        return daysOfWeekToWork;
    }

    public void setDaysOfWeekToWork(String daysOfWeekToWork) {
        this.daysOfWeekToWork = daysOfWeekToWork;
    }

    public String getFullPartialWorkday() {
        return fullPartialWorkday;
    }

    public void setFullPartialWorkday(String fullPartialWorkday) {
        this.fullPartialWorkday = fullPartialWorkday;
    }

    public String getIdentificationContractNumberINEM() {
        return identificationContractNumberINEM;
    }

    public void setIdentificationContractNumberINEM(String identificationContractNumberINEM) {
        this.identificationContractNumberINEM = identificationContractNumberINEM;
    }
}
