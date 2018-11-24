package gmoldes.domain.payroll_checklist.dto;

public class PayrollCheckListDTO {

    private String employerFullName;
    private String workerFullName;

    public PayrollCheckListDTO() {
    }

    public PayrollCheckListDTO(String employerFullName, String workerFullName) {
        this.employerFullName = employerFullName;
        this.workerFullName = workerFullName;
    }

    public String getEmployerFullName() {
        return employerFullName;
    }

    public void setEmployerFullName(String employerFullName) {
        this.employerFullName = employerFullName;
    }

    public String getWorkerFullName() {
        return workerFullName;
    }

    public void setWorkerFullName(String workerFullName) {
        this.workerFullName = workerFullName;
    }
}
