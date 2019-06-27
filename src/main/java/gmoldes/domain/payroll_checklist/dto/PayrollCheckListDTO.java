package gmoldes.domain.payroll_checklist.dto;

import java.awt.datatransfer.Clipboard;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class PayrollCheckListDTO {

    private String employerFullName;
    private String workerFullName;
    private Month month;
    private Integer year;
    private String withVariationsInMonth;
    private String date;
    private Clipboard clipboard;

    public PayrollCheckListDTO() {

    }

    public PayrollCheckListDTO(String employerFullName, String workerFullName, String withVariationsInMonth, String date) {
        this.employerFullName = employerFullName;
        this.workerFullName = workerFullName;
        this.withVariationsInMonth = withVariationsInMonth;
        this.date = date;
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

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getWithVariationsInMonth() {
        return withVariationsInMonth;
    }

    public void setWithVariationsInMoutn(String withVariationsInMonth) {
        this.withVariationsInMonth = withVariationsInMonth;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Clipboard getClipboard() {
        return clipboard;
    }

    public void setClipboard(Clipboard clipboard) {
        this.clipboard = clipboard;
    }

    public String toString(){

        return getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) ;
    }
}
