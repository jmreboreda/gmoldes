package gmoldes.domain.check.dto;

public class IDCControlDTO {

    private String variationDescription;
    private String workerFullName;
    private String clientGMFullName;
    private String dateTo;
    private Integer days;

    public IDCControlDTO() {
    }

    public String getVariationDescription() {
        return variationDescription;
    }

    public void setVariationDescription(String variationDescription) {
        this.variationDescription = variationDescription;
    }

    public String getWorkerFullName() {
        return workerFullName;
    }

    public void setWorkerFullName(String workerFullName) {
        this.workerFullName = workerFullName;
    }

    public String getClientGMFullName() {
        return clientGMFullName;
    }

    public void setClientGMFullName(String clientGMFullName) {
        this.clientGMFullName = clientGMFullName;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
