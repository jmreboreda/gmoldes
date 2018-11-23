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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PayrollCheckList {

    private List<String> employerFullNameList;
    private List<String>  workerFullNameList;
    private Month month;
    private Integer year;
    private Clipboard clipboard;

    public PayrollCheckList() {

        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    }

    public PayrollCheckList(List<String> employerFullNameList, List<String> workerFullNameList) {
        this.employerFullNameList = employerFullNameList;
        this.workerFullNameList = workerFullNameList;
    }

    public List<String> getEmployerFullNameList() {
        return employerFullNameList;
    }

    public void setEmployerFullNameList(List<String> employerFullNameList) {
        this.employerFullNameList = employerFullNameList;
    }

    public List<String> getWorkerFullNameList() {
        return workerFullNameList;
    }

    public void setWorkerFullNameList(List<String> workerFullNameList) {
        this.workerFullNameList = workerFullNameList;
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

    public void loadClipboard(Month month, Integer year){
        String clipboardData = "";
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

        List<PayrollCheckListDTO> payrollCheckListDTOList1Sorted = payrollCheckListDTOList
                .stream()
                .sorted(Comparator.comparing(PayrollCheckListDTO::getEmployerFullName)).collect(Collectors.toList());

        for(PayrollCheckListDTO payrollCheckListDTO : payrollCheckListDTOList1Sorted){

            clipboardData = clipboardData + payrollCheckListDTO.getEmployerFullName() + ";" + payrollCheckListDTO.getWorkerFullName() + "\n";

        }

        StringSelection ss = new StringSelection(clipboardData);
        clipboard.setContents(ss, ss);
    }

    public List<ContractNewVersionDTO> findAllContractInForceInPeriod(Month month, Integer year){

        Integer monthReceived = month.getValue();
        Integer dayReceived = 15;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthReceived - 1, dayReceived);

        Integer firstDayOfMonth = calendar.getMinimum(Calendar.DAY_OF_MONTH);
        LocalDate initialDate = LocalDate.of(year, monthReceived, firstDayOfMonth);
        LocalDate finalDate =  LocalDate.of(year, monthReceived, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        ApplicationMainController applicationMainController = new ApplicationMainController();

        return applicationMainController.findAllContractInForcerInPeriod(initialDate, finalDate);

    }
}
