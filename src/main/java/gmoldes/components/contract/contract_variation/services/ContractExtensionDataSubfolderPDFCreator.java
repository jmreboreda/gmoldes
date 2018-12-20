package gmoldes.components.contract.contract_variation.services;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import gmoldes.components.contract.contract_variation.forms.ContractExtensionDataSubfolder;
import gmoldes.components.contract.contract_variation.forms.ContractExtinctionDataSubfolder;
import gmoldes.components.contract.new_contract.components.WorkDaySchedule;
import gmoldes.utilities.Utilities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class ContractExtensionDataSubfolderPDFCreator {

    private static final String PATH_TO_PDF_TEMPLATE = "/pdf_forms/DGM_003_Datos_Alta_o_Cambio_Contrato_Trabajo.pdf";

    public ContractExtensionDataSubfolderPDFCreator() {
    }

    public static Path createContractExtensionDataSubfolderPDF(ContractExtensionDataSubfolder contractExtensionDataSubfolder, Path pathOut) throws IOException, DocumentException {

        PdfReader reader = new PdfReader(PATH_TO_PDF_TEMPLATE);
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(pathOut.toString()));
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String startDate = contractExtensionDataSubfolder.getStartDate() != null ? contractExtensionDataSubfolder.getStartDate().format(dateFormatter) : "";
        String endDate = contractExtensionDataSubfolder.getEndDate() != null ? contractExtensionDataSubfolder.getEndDate().format(dateFormatter) : "";

        AcroFields contractExtensionDataSubfolderPDFFields = stamp.getAcroFields();
        contractExtensionDataSubfolderPDFFields.setField("notificationType",contractExtensionDataSubfolder.getNotificationType());
        contractExtensionDataSubfolderPDFFields.setField("officialContractNumber",contractExtensionDataSubfolder.getOfficialContractNumber());
        contractExtensionDataSubfolderPDFFields.setField("employerFullName",contractExtensionDataSubfolder.getEmployerFullName());
        contractExtensionDataSubfolderPDFFields.setField("employerCCC",contractExtensionDataSubfolder.getEmployerQuoteAccountCode());
        contractExtensionDataSubfolderPDFFields.setField("notificationDate",contractExtensionDataSubfolder.getNotificationDate().format(dateFormatter));
        contractExtensionDataSubfolderPDFFields.setField("notificationHour",contractExtensionDataSubfolder.getNotificationHour().format(timeFormatter));
        contractExtensionDataSubfolderPDFFields.setField("employeeFullName",contractExtensionDataSubfolder.getEmployeeFullName());
        contractExtensionDataSubfolderPDFFields.setField("employeeNIF",contractExtensionDataSubfolder.getEmployeeNif());
        contractExtensionDataSubfolderPDFFields.setField("employeeNASS",contractExtensionDataSubfolder.getEmployeeNASS());
        contractExtensionDataSubfolderPDFFields.setField("employeeBirthDate",contractExtensionDataSubfolder.getEmployeeBirthDate());
        contractExtensionDataSubfolderPDFFields.setField("employeeCivilState",contractExtensionDataSubfolder.getEmployeeCivilState());
        contractExtensionDataSubfolderPDFFields.setField("employeeNationality",contractExtensionDataSubfolder.getEmployeeNationality());
        contractExtensionDataSubfolderPDFFields.setField("employeeFullAddress",contractExtensionDataSubfolder.getEmployeeFullAddress());
        contractExtensionDataSubfolderPDFFields.setField("employeeMaxStudyLevel",contractExtensionDataSubfolder.getEmployeeMaxStudyLevel());
        if(contractExtensionDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.MONDAY)) {
            contractExtensionDataSubfolderPDFFields.setField("monday", "Yes");
        }
        if(contractExtensionDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.TUESDAY)) {
            contractExtensionDataSubfolderPDFFields.setField("tuesday", "Yes");
        }
        if(contractExtensionDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.WEDNESDAY)) {
            contractExtensionDataSubfolderPDFFields.setField("wednesday", "Yes");
        }
        if(contractExtensionDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.THURSDAY)) {
            contractExtensionDataSubfolderPDFFields.setField("thursday", "Yes");
        }
        if(contractExtensionDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.FRIDAY)) {
            contractExtensionDataSubfolderPDFFields.setField("friday", "Yes");
        }
        if(contractExtensionDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.SATURDAY)) {
            contractExtensionDataSubfolderPDFFields.setField("saturday", "Yes");
        }
        if(contractExtensionDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.SUNDAY)) {
            contractExtensionDataSubfolderPDFFields.setField("sunday", "Yes");
        }
        contractExtensionDataSubfolderPDFFields.setField("contractType",contractExtensionDataSubfolder.getContractTypeDescription());
        contractExtensionDataSubfolderPDFFields.setField("startDate", startDate);
        contractExtensionDataSubfolderPDFFields.setField("endDate", endDate);
        contractExtensionDataSubfolderPDFFields.setField("durationDays", contractExtensionDataSubfolder.getDurationDays().toString() + " días");


        /* Start of the form fill loop*/
        for(WorkDaySchedule workDay : contractExtensionDataSubfolder.getSchedule()){
            if(workDay.getDayOfWeek().equals(DayOfWeek.MONDAY.getDisplayName(TextStyle.FULL, Locale.getDefault()))){
                String workDayPDF = "";
                String dateOne = "";
                String A1AMfrom = "";
                String A1AMto = "";
                String A1PMfrom = "";
                String A1PMto = "";
                String durationHours = "";
                if(workDay.getDayOfWeek() != null){
                    workDayPDF = workDay.getDayOfWeek();
                }
                if(workDay.getDate() != null){
                    dateOne = workDay.getDate().format(dateFormatter);
                }
                if(workDay.getAmFrom() != null){
                    A1AMfrom = workDay.getAmFrom().format(timeFormatter);
                }
                if(workDay.getAmTo() != null){
                    A1AMto = workDay.getAmTo().format(timeFormatter);
                }

                if(workDay.getPmFrom() != null){
                    A1PMfrom = workDay.getPmFrom().format(timeFormatter);
                }
                if(workDay.getPmTo() != null){
                    A1PMto = workDay.getPmTo().format(timeFormatter);
                }
                if(workDay.getDurationHours() != null){
                    durationHours = Utilities.converterDurationToTimeString(workDay.getDurationHours());
                }
                contractExtensionDataSubfolderPDFFields.setField("dayOne", workDayPDF);
                contractExtensionDataSubfolderPDFFields.setField("dateOne", dateOne);
                contractExtensionDataSubfolderPDFFields.setField("A1AMfrom",A1AMfrom);
                contractExtensionDataSubfolderPDFFields.setField("A1AMto", A1AMto);
                contractExtensionDataSubfolderPDFFields.setField("A1PMfrom", A1PMfrom);
                contractExtensionDataSubfolderPDFFields.setField("A1PMto", A1PMto);
                contractExtensionDataSubfolderPDFFields.setField("hoursOne", durationHours);
            }

            if(workDay.getDayOfWeek().equals(DayOfWeek.TUESDAY.getDisplayName(TextStyle.FULL, Locale.getDefault()))){
                String workDayPDF = "";
                String dateTwo = "";
                String A2AMfrom = "";
                String A2AMto = "";
                String A2PMfrom = "";
                String A2PMto = "";
                String durationHours = "";
                if(workDay.getDayOfWeek() != null){
                    workDayPDF = workDay.getDayOfWeek();
                }
                if(workDay.getDate() != null){
                    dateTwo = workDay.getDate().format(dateFormatter);
                }
                if(workDay.getAmFrom() != null){
                    A2AMfrom = workDay.getAmFrom().format(timeFormatter);
                }
                if(workDay.getAmTo() != null){
                    A2AMto = workDay.getAmTo().format(timeFormatter);
                }

                if(workDay.getPmFrom() != null){
                    A2PMfrom = workDay.getPmFrom().format(timeFormatter);
                }
                if(workDay.getPmTo() != null){
                    A2PMto = workDay.getPmTo().format(timeFormatter);
                }
                if(workDay.getDurationHours() != null){
                    durationHours = Utilities.converterDurationToTimeString(workDay.getDurationHours());
                }
                contractExtensionDataSubfolderPDFFields.setField("dayTwo", workDayPDF);
                contractExtensionDataSubfolderPDFFields.setField("dateTwo", dateTwo);
                contractExtensionDataSubfolderPDFFields.setField("A2AMfrom",A2AMfrom );
                contractExtensionDataSubfolderPDFFields.setField("A2AMto", A2AMto);
                contractExtensionDataSubfolderPDFFields.setField("A2PMfrom", A2PMfrom);
                contractExtensionDataSubfolderPDFFields.setField("A2PMto", A2PMto);
                contractExtensionDataSubfolderPDFFields.setField("hoursTwo", durationHours);
            }

            if(workDay.getDayOfWeek().equals(DayOfWeek.WEDNESDAY.getDisplayName(TextStyle.FULL, Locale.getDefault()))){
                String workDayPDF = "";
                String dateThree = "";
                String A3AMfrom = "";
                String A3AMto = "";
                String A3PMfrom = "";
                String A3PMto = "";
                String durationHours = "";
                if(workDay.getDayOfWeek() != null){
                    workDayPDF = workDay.getDayOfWeek();
                }
                if(workDay.getDate() != null){
                    dateThree = workDay.getDate().format(dateFormatter);
                }
                if(workDay.getAmFrom() != null){
                    A3AMfrom = workDay.getAmFrom().format(timeFormatter);
                }
                if(workDay.getAmTo() != null){
                    A3AMto = workDay.getAmTo().format(timeFormatter);
                }

                if(workDay.getPmFrom() != null){
                    A3PMfrom = workDay.getPmFrom().format(timeFormatter);
                }
                if(workDay.getPmTo() != null){
                    A3PMto = workDay.getPmTo().format(timeFormatter);
                }
                if(workDay.getDurationHours() != null){
                    durationHours = Utilities.converterDurationToTimeString(workDay.getDurationHours());
                }
                contractExtensionDataSubfolderPDFFields.setField("dayThree", workDayPDF);
                contractExtensionDataSubfolderPDFFields.setField("dateThree", dateThree);
                contractExtensionDataSubfolderPDFFields.setField("A3AMfrom",A3AMfrom );
                contractExtensionDataSubfolderPDFFields.setField("A3AMto", A3AMto);
                contractExtensionDataSubfolderPDFFields.setField("A3PMfrom", A3PMfrom);
                contractExtensionDataSubfolderPDFFields.setField("A3PMto", A3PMto);
                contractExtensionDataSubfolderPDFFields.setField("hoursThree", durationHours);
            }

            if(workDay.getDayOfWeek().equals(DayOfWeek.THURSDAY.getDisplayName(TextStyle.FULL, Locale.getDefault()))){
                String workDayPDF = "";
                String dateFour = "";
                String A4AMfrom = "";
                String A4AMto = "";
                String A4PMfrom = "";
                String A4PMto = "";
                String durationHours = "";
                if(workDay.getDayOfWeek() != null){
                    workDayPDF = workDay.getDayOfWeek();
                }
                if(workDay.getDate() != null){
                    dateFour = workDay.getDate().format(dateFormatter);
                }
                if(workDay.getAmFrom() != null){
                    A4AMfrom = workDay.getAmFrom().format(timeFormatter);
                }
                if(workDay.getAmTo() != null){
                    A4AMto = workDay.getAmTo().format(timeFormatter);
                }

                if(workDay.getPmFrom() != null){
                    A4PMfrom = workDay.getPmFrom().format(timeFormatter);
                }
                if(workDay.getPmTo() != null){
                    A4PMto = workDay.getPmTo().format(timeFormatter);
                }
                if(workDay.getDurationHours() != null){
                    durationHours = Utilities.converterDurationToTimeString(workDay.getDurationHours());
                }
                contractExtensionDataSubfolderPDFFields.setField("dayFour", workDayPDF);
                contractExtensionDataSubfolderPDFFields.setField("dateFour", dateFour);
                contractExtensionDataSubfolderPDFFields.setField("A4AMfrom",A4AMfrom );
                contractExtensionDataSubfolderPDFFields.setField("A4AMto", A4AMto);
                contractExtensionDataSubfolderPDFFields.setField("A4PMfrom", A4PMfrom);
                contractExtensionDataSubfolderPDFFields.setField("A4PMto", A4PMto);
                contractExtensionDataSubfolderPDFFields.setField("hoursFour", durationHours);
            }

            if(workDay.getDayOfWeek().equals(DayOfWeek.FRIDAY.getDisplayName(TextStyle.FULL, Locale.getDefault()))){
                String workDayPDF = "";
                String dateFive = "";
                String A5AMfrom = "";
                String A5AMto = "";
                String A5PMfrom = "";
                String A5PMto = "";
                String durationHours = "";
                if(workDay.getDayOfWeek() != null){
                    workDayPDF = workDay.getDayOfWeek();
                }
                if(workDay.getDate() != null){
                    dateFive = workDay.getDate().format(dateFormatter);
                }
                if(workDay.getAmFrom() != null){
                    A5AMfrom = workDay.getAmFrom().format(timeFormatter);
                }
                if(workDay.getAmTo() != null){
                    A5AMto = workDay.getAmTo().format(timeFormatter);
                }

                if(workDay.getPmFrom() != null){
                    A5PMfrom = workDay.getPmFrom().format(timeFormatter);
                }
                if(workDay.getPmTo() != null){
                    A5PMto = workDay.getPmTo().format(timeFormatter);
                }
                if(workDay.getDurationHours() != null){
                    durationHours = Utilities.converterDurationToTimeString(workDay.getDurationHours());
                }
                contractExtensionDataSubfolderPDFFields.setField("dayFive", workDayPDF);
                contractExtensionDataSubfolderPDFFields.setField("dateFive", dateFive);
                contractExtensionDataSubfolderPDFFields.setField("A5AMfrom",A5AMfrom );
                contractExtensionDataSubfolderPDFFields.setField("A5AMto", A5AMto);
                contractExtensionDataSubfolderPDFFields.setField("A5PMfrom", A5PMfrom);
                contractExtensionDataSubfolderPDFFields.setField("A5PMto", A5PMto);
                contractExtensionDataSubfolderPDFFields.setField("hoursFive", durationHours);
            }

            if(workDay.getDayOfWeek().equals(DayOfWeek.SATURDAY.getDisplayName(TextStyle.FULL, Locale.getDefault()))){
                String workDayPDF = "";
                String dateSix = "";
                String A6AMfrom = "";
                String A6AMto = "";
                String A6PMfrom = "";
                String A6PMto = "";
                String durationHours = "";
                if(workDay.getDayOfWeek() != null){
                    workDayPDF = workDay.getDayOfWeek();
                }
                if(workDay.getDate() != null){
                    dateSix = workDay.getDate().format(dateFormatter);
                }
                if(workDay.getAmFrom() != null){
                    A6AMfrom = workDay.getAmFrom().format(timeFormatter);
                }
                if(workDay.getAmTo() != null){
                    A6AMto = workDay.getAmTo().format(timeFormatter);
                }

                if(workDay.getPmFrom() != null){
                    A6PMfrom = workDay.getPmFrom().format(timeFormatter);
                }
                if(workDay.getPmTo() != null){
                    A6PMto = workDay.getPmTo().format(timeFormatter);
                }
                if(workDay.getDurationHours() != null){
                    durationHours = Utilities.converterDurationToTimeString(workDay.getDurationHours());
                }
                contractExtensionDataSubfolderPDFFields.setField("daySix", workDayPDF);
                contractExtensionDataSubfolderPDFFields.setField("dateSix", dateSix);
                contractExtensionDataSubfolderPDFFields.setField("A6AMfrom",A6AMfrom );
                contractExtensionDataSubfolderPDFFields.setField("A6AMto", A6AMto);
                contractExtensionDataSubfolderPDFFields.setField("A6PMfrom", A6PMfrom);
                contractExtensionDataSubfolderPDFFields.setField("A6PMto", A6PMto);
                contractExtensionDataSubfolderPDFFields.setField("hoursSix", durationHours);
            }

            if(workDay.getDayOfWeek().equals(DayOfWeek.SUNDAY.getDisplayName(TextStyle.FULL, Locale.getDefault()))){
                String workDayPDF = "";
                String dateSeven = "";
                String A7AMfrom = "";
                String A7AMto = "";
                String A7PMfrom = "";
                String A7PMto = "";
                String durationHours = "";
                if(workDay.getDayOfWeek() != null){
                    workDayPDF = workDay.getDayOfWeek();
                }
                if(workDay.getDate() != null){
                    dateSeven = workDay.getDate().format(dateFormatter);
                }
                if(workDay.getAmFrom() != null){
                    A7AMfrom = workDay.getAmFrom().format(timeFormatter);
                }
                if(workDay.getAmTo() != null){
                    A7AMto = workDay.getAmTo().format(timeFormatter);
                }

                if(workDay.getPmFrom() != null){
                    A7PMfrom = workDay.getPmFrom().format(timeFormatter);
                }
                if(workDay.getPmTo() != null){
                    A7PMto = workDay.getPmTo().format(timeFormatter);
                }
                if(workDay.getDurationHours() != null){
                    durationHours = Utilities.converterDurationToTimeString(workDay.getDurationHours());
                }
                contractExtensionDataSubfolderPDFFields.setField("daySeven", workDayPDF);
                contractExtensionDataSubfolderPDFFields.setField("dateSeven", dateSeven);
                contractExtensionDataSubfolderPDFFields.setField("A7AMfrom",A7AMfrom );
                contractExtensionDataSubfolderPDFFields.setField("A7AMto", A7AMto);
                contractExtensionDataSubfolderPDFFields.setField("A7PMfrom", A7PMfrom);
                contractExtensionDataSubfolderPDFFields.setField("A7PMto", A7PMto);
                contractExtensionDataSubfolderPDFFields.setField("hoursSeven", durationHours);
            }
        }

        contractExtensionDataSubfolderPDFFields.setField("additionalData",contractExtensionDataSubfolder.getAdditionalData());
        contractExtensionDataSubfolderPDFFields.setField("laborCategory",contractExtensionDataSubfolder.getLaborCategory());
        contractExtensionDataSubfolderPDFFields.setField("GMcontractNumber",contractExtensionDataSubfolder.getGmContractNumber());

        stamp.setFormFlattening(true);
        stamp.close();

        return pathOut;
    }
}
