package gmoldes.domain.client_invoice_checklist;

import gmoldes.ApplicationMainController;
import gmoldes.domain.client.dto.ClientDTOOk;
import gmoldes.domain.client_invoice_checklist.dto.ClientInvoiceCheckListDTO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.text.Collator;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class ClientInvoiceCheckList {

    private String clientGMFullName;
    private String  sg21Code;
    private Month month;
    private Integer year;
    private Clipboard clipboard;

    public ClientInvoiceCheckList() {

        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    }

    public ClientInvoiceCheckList(String clientGMFullName, String sg21Code) {
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

    @Override
    public String toString(){

        return getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    public void loadClipboard(List<ClientInvoiceCheckListDTO> clientInvoiceCheckListDTOList){

        String clipboardData = "";

        for(ClientInvoiceCheckListDTO clientInvoiceCheckListDTO : clientInvoiceCheckListDTOList){
            clipboardData = clipboardData + clientInvoiceCheckListDTO.getClientGMFullName() + ";" + clientInvoiceCheckListDTO.getSg21Code() + "\n";
        }

        StringSelection ss = new StringSelection(clipboardData);
        clipboard.setContents(ss, ss);
    }

    public List<ClientInvoiceCheckListDTO> retrieveAllClientGMWithInvoiceInForceInPeriod(Month month, Integer year){

        List<ClientInvoiceCheckListDTO> clientInvoiceCheckListDTOList = new ArrayList<>();
        ApplicationMainController applicationMainController = new ApplicationMainController();

        Integer yearReceived = year;
        Integer monthReceived = month.getValue();
        Integer dayReceived = 1;

        Calendar calendar = Calendar.getInstance();
        calendar.set(yearReceived, monthReceived - 1, dayReceived);

        LocalDate periodInitialDate = LocalDate.of(yearReceived, monthReceived, dayReceived);
        LocalDate periodFinalDate =  LocalDate.of(yearReceived, monthReceived, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        List<ClientDTOOk> clientDTOList = applicationMainController.findAllClientGMWithInvoiceInForceInPeriod(periodInitialDate, periodFinalDate);
        for(ClientDTOOk clientDTO : clientDTOList){
            String clientFullName = clientDTO.toString();
            String clientSg21Code = clientDTO.getSg21Code();

            ClientInvoiceCheckListDTO clientInvoiceCheckListDTO = new ClientInvoiceCheckListDTO(clientFullName, clientSg21Code);
            clientInvoiceCheckListDTOList.add(clientInvoiceCheckListDTO);
        }

        Collator primaryCollator = Collator.getInstance(new Locale("es","ES"));
        primaryCollator.setStrength(Collator.PRIMARY);

        return clientInvoiceCheckListDTOList
                .stream()
                .sorted(Comparator.comparing(ClientInvoiceCheckListDTO::getClientGMFullName, primaryCollator)).collect(Collectors.toList());
    }

    private List<ContractNewVersionDTO> findAllContractInForceInPeriod(Month month, Integer year){

        Integer monthReceived = month.getValue();
        Integer dayReceived = 15;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthReceived - 1, dayReceived);

        Integer firstDayOfMonth = calendar.getMinimum(Calendar.DAY_OF_MONTH);
        LocalDate initialDate = LocalDate.of(year, monthReceived, firstDayOfMonth);
        LocalDate finalDate =  LocalDate.of(year, monthReceived, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        ApplicationMainController applicationMainController = new ApplicationMainController();

        return applicationMainController.findAllContractInForceInPeriod(initialDate, finalDate);

    }
}
