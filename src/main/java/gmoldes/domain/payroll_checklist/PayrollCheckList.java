package gmoldes.domain.payroll_checklist;

import gmoldes.ApplicationConstants;
import gmoldes.domain.client.ClientService;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.ContractService;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.payroll_checklist.dto.PayrollCheckListDTO;
import gmoldes.domain.person.PersonService;
import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.types_contract_variations.TypesContractVariationsService;
import javafx.scene.input.KeyCode;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PayrollCheckList {

    private String employerFullName;
    private String  workerFullName;
    private String withVariationsInMonth;
    private String date;
    private Month month;
    private Integer year;
    private Clipboard clipboard;

    public PayrollCheckList() {

        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    }

    public String getEmployerFullNameList() {
        return employerFullName;
    }

    public void setEmployerFullNameList(String employerFullNameList) {
        this.employerFullName = employerFullNameList;
    }

    public String getWorkerFullNameList() {
        return workerFullName;
    }

    public void setWorkerFullNameList(String workerFullNameList) {
        this.workerFullName = workerFullNameList;
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

    public void setWithVariationsInMonth(String withVariationsInMonth) {
        this.withVariationsInMonth = withVariationsInMonth;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString(){

        return getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    public void loadClipboard(List<PayrollCheckListDTO> payrollCheckListDTOList){

        String clipboardData = "";

        for(PayrollCheckListDTO payrollCheckListDTO : payrollCheckListDTOList){
            clipboardData = clipboardData + payrollCheckListDTO.getEmployerFullName() + ";" +
                    payrollCheckListDTO.getWorkerFullName() + ";" +
                    payrollCheckListDTO.getWithVariationsInMonth() + ";" +
                    payrollCheckListDTO.getDate() + "\n";
        }

        StringSelection ss = new StringSelection(clipboardData);
        clipboard.setContents(ss, ss);
    }

    public List<PayrollCheckListDTO> retrieveAllContractInForceInPeriod(Month month, Integer year){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);

        List<PayrollCheckListDTO> payrollCheckListDTOList = new ArrayList<>();
        List<ContractNewVersionDTO> contractNewVersionDTOList = findAllContractInForceInPeriod(month, year);
        for(ContractNewVersionDTO contractNewVersionDTO : contractNewVersionDTOList){
            ClientService clientService = ClientService.ClientServiceFactory.getInstance();
            TypesContractVariationsService typesContractVariationsService = TypesContractVariationsService.TypesContractVariationServiceFactory.getInstance();
            ClientDTO employerDTO = clientService.findClientById(contractNewVersionDTO.getContractJsonData().getClientGMId());
            String employerName = employerDTO.toString();
            PersonService personService = PersonService.PersonServiceFactory.getInstance();
            PersonDTO workerDTO = personService.findPersonById(contractNewVersionDTO.getContractJsonData().getWorkerId());
            String workerName = workerDTO.toString();
            String date = "";

            String variation = "";
            if(contractNewVersionDTO.getStartDate().getMonth().getValue() == month.getValue() &&
                    contractNewVersionDTO.getStartDate().getYear() == year){

                variation = typesContractVariationsService.findTypesContractVariationsById(contractNewVersionDTO.getVariationType())
                        .getVariation_description().length() >= 21 ?
                        typesContractVariationsService.findTypesContractVariationsById(contractNewVersionDTO
                                .getVariationType())
                                .getVariation_description().substring(0, 21) :
                        typesContractVariationsService.findTypesContractVariationsById(contractNewVersionDTO
                                .getVariationType())
                                .getVariation_description();

                date = contractNewVersionDTO.getStartDate() != null ? contractNewVersionDTO.getStartDate().format(dateFormatter) : " ";
            }

            PayrollCheckListDTO payrollCheckListDTO = new PayrollCheckListDTO(employerName, workerName, variation, date);
            payrollCheckListDTOList.add(payrollCheckListDTO);
        }

        return payrollCheckListDTOList;
    }

    private List<ContractNewVersionDTO> findAllContractInForceInPeriod(Month month, Integer year){

        Integer monthReceived = month.getValue();
        Integer dayReceived = 15;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthReceived - 1, dayReceived);

        Integer firstDayOfMonth = calendar.getMinimum(Calendar.DAY_OF_MONTH);
        LocalDate initialDate = LocalDate.of(year, monthReceived, firstDayOfMonth);
        LocalDate finalDate =  LocalDate.of(year, monthReceived, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        ContractService contractService = ContractService.ContractServiceFactory.getInstance();

        return contractService.findAllContractInForceInPeriod(initialDate, finalDate);

    }
}
