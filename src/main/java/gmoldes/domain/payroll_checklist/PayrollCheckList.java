package gmoldes.domain.payroll_checklist;

import gmoldes.ApplicationMainController;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.payroll_checklist.dto.PayrollCheckListDTO;
import gmoldes.domain.person.dto.PersonDTO;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class PayrollCheckList {

    private String employerFullName;
    private String  workerFullName;
    private Month month;
    private Integer year;
    private Clipboard clipboard;

    public PayrollCheckList() {

        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    }

    public PayrollCheckList(String employerFullNameList, String workerFullNameList) {
        this.employerFullName = employerFullNameList;
        this.workerFullName = workerFullNameList;
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

    @Override
    public String toString(){

        return getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    public void loadClipboard(Month month, Integer year){

        String clipboardData = "";

        List<PayrollCheckListDTO> payrollCheckListDTOList = retrieveAllContractInForceInPeriod(month, year);
        for(PayrollCheckListDTO payrollCheckListDTO : payrollCheckListDTOList){

            clipboardData = clipboardData + payrollCheckListDTO.getEmployerFullName() + ";" + payrollCheckListDTO.getWorkerFullName() + "\n";
        }

        StringSelection ss = new StringSelection(clipboardData);
        clipboard.setContents(ss, ss);
    }

    public List<PayrollCheckListDTO> retrieveAllContractInForceInPeriod(Month month, Integer year){

        List<PayrollCheckListDTO> payrollCheckListDTOList = new ArrayList<>();
        ApplicationMainController applicationMainController = new ApplicationMainController();
        List<ContractNewVersionDTO> contractNewVersionDTOList = findAllContractInForceInPeriod(month, year);
        for(ContractNewVersionDTO contractNewVersionDTO : contractNewVersionDTOList){
            ClientDTO employer = applicationMainController.findClientById(contractNewVersionDTO.getContractJsonData().getClientGMId());
            String employerName = employer.getPersonOrCompanyName();
            PersonDTO worker = applicationMainController.findPersonById(contractNewVersionDTO.getContractJsonData().getWorkerId());
            String workerName = worker.getApellidos() + ", " + worker.getNom_rzsoc();

            PayrollCheckListDTO payrollCheckListDTO = new PayrollCheckListDTO(employerName, workerName);
            payrollCheckListDTOList.add(payrollCheckListDTO);
        }

        return payrollCheckListDTOList
                .stream()
                .sorted(Comparator.comparing(PayrollCheckListDTO::getEmployerFullName)).collect(Collectors.toList());
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