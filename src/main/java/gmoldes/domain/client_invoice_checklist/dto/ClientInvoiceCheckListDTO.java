package gmoldes.domain.client_invoice_checklist.dto;

import java.awt.datatransfer.Clipboard;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class ClientInvoiceCheckListDTO {

    private String clientGMFullName;
    private String sg21Code;
    private Month month;
    private Integer year;
    private Clipboard clipboard;

    public ClientInvoiceCheckListDTO() {

    }

    public ClientInvoiceCheckListDTO(String clientGMFullName, String sg21Code) {
        this.clientGMFullName = clientGMFullName;
        this.sg21Code = sg21Code;
    }

    public String getClientGMFullName() {
        return clientGMFullName;
    }

    public void setClientGMFullName(String clientGMFullName) {
        this.clientGMFullName = clientGMFullName;
    }

    public String getSg21Code() {
        return sg21Code;
    }

    public void setSg21Code(String sg21Code) {
        this.sg21Code = sg21Code;
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
