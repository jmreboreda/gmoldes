package gmoldes.components.contract.new_contract.services;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import gmoldes.ApplicationConstants;
import gmoldes.components.contract.ContractConstants;
import gmoldes.components.contract.new_contract.forms.ContractDataSubfolder;
import gmoldes.utilities.Parameters;
import gmoldes.utilities.Utilities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;

public class NewContractRecordHistorySubfolderPDFCreator {

    private static final String PATH_TO_PDF_TEMPLATE = "/pdf_forms/DGM_006_Portada_Expediente_Contrato_Trabajo.pdf";

    public NewContractRecordHistorySubfolderPDFCreator() {
    }

    public static Path createContractRecordHistorySubfolderPDF(ContractDataSubfolder contractDataSubfolder, Path pathOut) throws IOException, DocumentException {

        PdfReader reader = new PdfReader(PATH_TO_PDF_TEMPLATE);
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(pathOut.toString()));
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);

        String workDays = "";

        AcroFields contractDataSubfolderPDFFields = stamp.getAcroFields();
        contractDataSubfolderPDFFields.setField("employerFullName",contractDataSubfolder.getEmployerFullName());
        contractDataSubfolderPDFFields.setField("quoteAccountCode",contractDataSubfolder.getEmployerQuoteAccountCode());
        contractDataSubfolderPDFFields.setField("employeeFullName",contractDataSubfolder.getEmployeeFullName());
        contractDataSubfolderPDFFields.setField("employeeNIF",contractDataSubfolder.getEmployeeNif());
        contractDataSubfolderPDFFields.setField("employeeNASS",contractDataSubfolder.getEmployeeNASS());
        contractDataSubfolderPDFFields.setField("employeeBirthDate",contractDataSubfolder.getEmployeeBirthDate());
        contractDataSubfolderPDFFields.setField("employeeCivilState",contractDataSubfolder.getEmployeeCivilState());
        contractDataSubfolderPDFFields.setField("employeeNationality",contractDataSubfolder.getEmployeeNationality());
        contractDataSubfolderPDFFields.setField("employeeAddress",contractDataSubfolder.getEmployeeFullAddress());
        contractDataSubfolderPDFFields.setField("employeeMaximumStudyLevel",contractDataSubfolder.getEmployeeMaxStudyLevel());
        if(contractDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.MONDAY)) {
            workDays = workDays + "l";
        }
        if(contractDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.TUESDAY)) {
            workDays = workDays + " m";
        }
        if(contractDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.WEDNESDAY)) {
            workDays = workDays + " x";
        }
        if(contractDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.THURSDAY)) {
            workDays = workDays + " j";
        }
        if(contractDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.FRIDAY)) {
            workDays = workDays + " v";
        }
        if(contractDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.SATURDAY)) {
            workDays = workDays + " s";
        }
        if(contractDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.SUNDAY)) {
            workDays = workDays + " d";
        }
        contractDataSubfolderPDFFields.setField("dateFrom", contractDataSubfolder.getStartDate().format(dateFormatter));
        if(contractDataSubfolder.getEndDate() != null) {
            contractDataSubfolderPDFFields.setField("dateTo", contractDataSubfolder.getEndDate().format(dateFormatter));
        }else{
            contractDataSubfolderPDFFields.setField("dateTo", null);
        }
        if(contractDataSubfolder.getDurationDays().toDays() > 0) {
            contractDataSubfolderPDFFields.setField("durationDays", contractDataSubfolder.getDurationDays().toDays() + " días");
        }else{
            contractDataSubfolderPDFFields.setField("durationDays", ContractConstants.UNDEFINED_DURATION_TEXT);
        }
        contractDataSubfolderPDFFields.setField("contractTypeDescription",contractDataSubfolder.getContractTypeDescription());
        contractDataSubfolderPDFFields.setField("laborCategory",contractDataSubfolder.getLaborCategory());
        contractDataSubfolderPDFFields.setField("hoursWorkWeek", Utilities.converterDurationToTimeString(contractDataSubfolder.getHoursWorkWeek()));

        contractDataSubfolderPDFFields.setField("workDays",workDays);

        stamp.setFormFlattening(true);
        stamp.close();

        return pathOut;
    }
}
