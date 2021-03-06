package gmoldes.components.contract.new_contract.services;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import gmoldes.ApplicationConstants;
import gmoldes.components.contract.ContractConstants;
import gmoldes.components.contract.new_contract.forms.ContractDataSubfolder;
import gmoldes.components.contract.new_contract.components.WorkDaySchedule;
import gmoldes.utilities.Parameters;
import gmoldes.utilities.Utilities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class NewContractDataSubfolderPDFCreator {

    private static final String PATH_TO_PDF_TEMPLATE = "/pdf_forms/DGM_003_Datos_Alta_o_Cambio_Contrato_Trabajo.pdf";

    public NewContractDataSubfolderPDFCreator() {
    }

    public static Path createContractDataSubfolderPDF(ContractDataSubfolder contractDataSubfolder, Path pathOut) throws IOException, DocumentException {

        PdfReader reader = new PdfReader(PATH_TO_PDF_TEMPLATE);
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(pathOut.toString()));
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_TIME_FORMAT);

        String startDate = contractDataSubfolder.getStartDate() != null ? contractDataSubfolder.getStartDate().format(dateFormatter) : "";
        String endDate = contractDataSubfolder.getEndDate() != null ? contractDataSubfolder.getEndDate().format(dateFormatter) : "";
        Long durationDays = contractDataSubfolder.getDurationDays().toDays() > 0 ? contractDataSubfolder.getDurationDays().toDays() : null;


        AcroFields contractDataSubfolderPDFFields = stamp.getAcroFields();
        contractDataSubfolderPDFFields.setField("notificationType",contractDataSubfolder.getNotificationType());
        contractDataSubfolderPDFFields.setField("officialContractNumber",contractDataSubfolder.getOfficialContractNumber());
        contractDataSubfolderPDFFields.setField("employerFullName",contractDataSubfolder.getEmployerFullName());
        contractDataSubfolderPDFFields.setField("employerCCC",contractDataSubfolder.getEmployerQuoteAccountCode());
        contractDataSubfolderPDFFields.setField("notificationDate",contractDataSubfolder.getNotificationDate().format(dateFormatter));
        contractDataSubfolderPDFFields.setField("notificationHour",contractDataSubfolder.getNotificationHour().format(timeFormatter));
        contractDataSubfolderPDFFields.setField("employeeFullName",contractDataSubfolder.getEmployeeFullName());
        contractDataSubfolderPDFFields.setField("employeeNIF",contractDataSubfolder.getEmployeeNif());
        contractDataSubfolderPDFFields.setField("employeeNASS",contractDataSubfolder.getEmployeeNASS());
        contractDataSubfolderPDFFields.setField("employeeBirthDate",contractDataSubfolder.getEmployeeBirthDate());
        contractDataSubfolderPDFFields.setField("employeeCivilState",contractDataSubfolder.getEmployeeCivilState());
        contractDataSubfolderPDFFields.setField("employeeNationality",contractDataSubfolder.getEmployeeNationality());
        contractDataSubfolderPDFFields.setField("employeeFullAddress",contractDataSubfolder.getEmployeeFullAddress());
        contractDataSubfolderPDFFields.setField("employeeMaxStudyLevel",contractDataSubfolder.getEmployeeMaxStudyLevel());
        if(contractDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.MONDAY)) {
            contractDataSubfolderPDFFields.setField("monday", "Yes");
        }
        if(contractDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.TUESDAY)) {
            contractDataSubfolderPDFFields.setField("tuesday", "Yes");
        }
        if(contractDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.WEDNESDAY)) {
            contractDataSubfolderPDFFields.setField("wednesday", "Yes");
        }
        if(contractDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.THURSDAY)) {
            contractDataSubfolderPDFFields.setField("thursday", "Yes");
        }
        if(contractDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.FRIDAY)) {
            contractDataSubfolderPDFFields.setField("friday", "Yes");
        }
        if(contractDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.SATURDAY)) {
            contractDataSubfolderPDFFields.setField("saturday", "Yes");
        }
        if(contractDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.SUNDAY)) {
            contractDataSubfolderPDFFields.setField("sunday", "Yes");
        }
        contractDataSubfolderPDFFields.setField("contractType",contractDataSubfolder.getContractTypeDescription());
        contractDataSubfolderPDFFields.setField("startDate", startDate);
        contractDataSubfolderPDFFields.setField("endDate", endDate);

        if(durationDays != null) {
            contractDataSubfolderPDFFields.setField("durationDays", durationDays + " días");
        }else{
            contractDataSubfolderPDFFields.setField("durationDays", ContractConstants.UNDEFINED_DURATION_TEXT);
        }

        /* Start of the form fill loop*/
        for(WorkDaySchedule workDay : contractDataSubfolder.getSchedule()){
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
                contractDataSubfolderPDFFields.setField("dayOne", workDayPDF);
                contractDataSubfolderPDFFields.setField("dateOne", dateOne);
                contractDataSubfolderPDFFields.setField("A1AMfrom",A1AMfrom);
                contractDataSubfolderPDFFields.setField("A1AMto", A1AMto);
                contractDataSubfolderPDFFields.setField("A1PMfrom", A1PMfrom);
                contractDataSubfolderPDFFields.setField("A1PMto", A1PMto);
                contractDataSubfolderPDFFields.setField("hoursOne", durationHours);
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
                contractDataSubfolderPDFFields.setField("dayTwo", workDayPDF);
                contractDataSubfolderPDFFields.setField("dateTwo", dateTwo);
                contractDataSubfolderPDFFields.setField("A2AMfrom",A2AMfrom );
                contractDataSubfolderPDFFields.setField("A2AMto", A2AMto);
                contractDataSubfolderPDFFields.setField("A2PMfrom", A2PMfrom);
                contractDataSubfolderPDFFields.setField("A2PMto", A2PMto);
                contractDataSubfolderPDFFields.setField("hoursTwo", durationHours);
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
                contractDataSubfolderPDFFields.setField("dayThree", workDayPDF);
                contractDataSubfolderPDFFields.setField("dateThree", dateThree);
                contractDataSubfolderPDFFields.setField("A3AMfrom",A3AMfrom );
                contractDataSubfolderPDFFields.setField("A3AMto", A3AMto);
                contractDataSubfolderPDFFields.setField("A3PMfrom", A3PMfrom);
                contractDataSubfolderPDFFields.setField("A3PMto", A3PMto);
                contractDataSubfolderPDFFields.setField("hoursThree", durationHours);
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
                contractDataSubfolderPDFFields.setField("dayFour", workDayPDF);
                contractDataSubfolderPDFFields.setField("dateFour", dateFour);
                contractDataSubfolderPDFFields.setField("A4AMfrom",A4AMfrom );
                contractDataSubfolderPDFFields.setField("A4AMto", A4AMto);
                contractDataSubfolderPDFFields.setField("A4PMfrom", A4PMfrom);
                contractDataSubfolderPDFFields.setField("A4PMto", A4PMto);
                contractDataSubfolderPDFFields.setField("hoursFour", durationHours);
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
                contractDataSubfolderPDFFields.setField("dayFive", workDayPDF);
                contractDataSubfolderPDFFields.setField("dateFive", dateFive);
                contractDataSubfolderPDFFields.setField("A5AMfrom",A5AMfrom );
                contractDataSubfolderPDFFields.setField("A5AMto", A5AMto);
                contractDataSubfolderPDFFields.setField("A5PMfrom", A5PMfrom);
                contractDataSubfolderPDFFields.setField("A5PMto", A5PMto);
                contractDataSubfolderPDFFields.setField("hoursFive", durationHours);
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
                contractDataSubfolderPDFFields.setField("daySix", workDayPDF);
                contractDataSubfolderPDFFields.setField("dateSix", dateSix);
                contractDataSubfolderPDFFields.setField("A6AMfrom",A6AMfrom );
                contractDataSubfolderPDFFields.setField("A6AMto", A6AMto);
                contractDataSubfolderPDFFields.setField("A6PMfrom", A6PMfrom);
                contractDataSubfolderPDFFields.setField("A6PMto", A6PMto);
                contractDataSubfolderPDFFields.setField("hoursSix", durationHours);
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
                contractDataSubfolderPDFFields.setField("daySeven", workDayPDF);
                contractDataSubfolderPDFFields.setField("dateSeven", dateSeven);
                contractDataSubfolderPDFFields.setField("A7AMfrom",A7AMfrom );
                contractDataSubfolderPDFFields.setField("A7AMto", A7AMto);
                contractDataSubfolderPDFFields.setField("A7PMfrom", A7PMfrom);
                contractDataSubfolderPDFFields.setField("A7PMto", A7PMto);
                contractDataSubfolderPDFFields.setField("hoursSeven", durationHours);
            }
        }

        contractDataSubfolderPDFFields.setField("additionalData",contractDataSubfolder.getAdditionalData());
        contractDataSubfolderPDFFields.setField("laborCategory",contractDataSubfolder.getLaborCategory());
        contractDataSubfolderPDFFields.setField("GMcontractNumber",contractDataSubfolder.getGmContractNumber());

        stamp.setFormFlattening(true);
        stamp.close();

        return pathOut;
    }
}
