package gmoldes.services;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import gmoldes.components.contract.new_contract.forms.ContractDataToContractAgent;
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

public class ContractDataToContractAgentPDFCreator {

    private static final String PATH_TO_PDF_TEMPLATE = "/pdf_forms/Datos_Alta_o_Cambio_Contrato_Trabajo_A4_Notificar_Gestor.pdf";

    public ContractDataToContractAgentPDFCreator() {
    }

    public static Path createContractDataToContractAgentPDF(ContractDataToContractAgent contractDataToContractAgent, Path pathOut) throws IOException, DocumentException {

        PdfReader reader = new PdfReader(PATH_TO_PDF_TEMPLATE);
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(pathOut.toString()));
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        AcroFields contractDataSToContractAgentPDFFields = stamp.getAcroFields();
        contractDataSToContractAgentPDFFields.setField("notificationType",contractDataToContractAgent.getNotificationType());
        contractDataSToContractAgentPDFFields.setField("officialContractNumber",null);
        contractDataSToContractAgentPDFFields.setField("employerFullName",contractDataToContractAgent.getEmployerFullName());
        contractDataSToContractAgentPDFFields.setField("employerCCC",contractDataToContractAgent.getEmployerQuoteAccountCode());
        contractDataSToContractAgentPDFFields.setField("notificationDate",contractDataToContractAgent.getNotificationDate().format(dateFormatter));
        contractDataSToContractAgentPDFFields.setField("notificationHour",contractDataToContractAgent.getNotificationHour().format(timeFormatter));
        contractDataSToContractAgentPDFFields.setField("employeeFullName",contractDataToContractAgent.getEmployeeFullName());
        contractDataSToContractAgentPDFFields.setField("employeeNIF",contractDataToContractAgent.getEmployeeNif());
        contractDataSToContractAgentPDFFields.setField("employeeNASS",contractDataToContractAgent.getEmployeeNASS());
        contractDataSToContractAgentPDFFields.setField("employeeBirthDate",contractDataToContractAgent.getEmployeeBirthDate());
        contractDataSToContractAgentPDFFields.setField("employeeCivilState",contractDataToContractAgent.getEmployeeCivilState());
        contractDataSToContractAgentPDFFields.setField("employeeNationality",contractDataToContractAgent.getEmployeeNationality());
        contractDataSToContractAgentPDFFields.setField("employeeFullAddress",contractDataToContractAgent.getEmployeeFullAddress());
        contractDataSToContractAgentPDFFields.setField("employeeMaxStudyLevel",contractDataToContractAgent.getEmployeeMaxStudyLevel());
        if(contractDataToContractAgent.getDayOfWeekSet().contains(DayOfWeek.MONDAY)) {
            contractDataSToContractAgentPDFFields.setField("monday", "Yes");
        }
        if(contractDataToContractAgent.getDayOfWeekSet().contains(DayOfWeek.TUESDAY)) {
            contractDataSToContractAgentPDFFields.setField("tuesday", "Yes");
        }
        if(contractDataToContractAgent.getDayOfWeekSet().contains(DayOfWeek.THURSDAY)) {
            contractDataSToContractAgentPDFFields.setField("thursday", "Yes");
        }
        if(contractDataToContractAgent.getDayOfWeekSet().contains(DayOfWeek.WEDNESDAY)) {
            contractDataSToContractAgentPDFFields.setField("wednesday", "Yes");
        }
        if(contractDataToContractAgent.getDayOfWeekSet().contains(DayOfWeek.FRIDAY)) {
            contractDataSToContractAgentPDFFields.setField("friday", "Yes");
        }
        if(contractDataToContractAgent.getDayOfWeekSet().contains(DayOfWeek.SATURDAY)) {
            contractDataSToContractAgentPDFFields.setField("saturday", "Yes");
        }
        if(contractDataToContractAgent.getDayOfWeekSet().contains(DayOfWeek.SUNDAY)) {
            contractDataSToContractAgentPDFFields.setField("sunday", "Yes");
        }
        contractDataSToContractAgentPDFFields.setField("contractType",contractDataToContractAgent.getContractTypeDescription());
        contractDataSToContractAgentPDFFields.setField("startDate", contractDataToContractAgent.getStartDate().format(dateFormatter));
        if(contractDataToContractAgent.getEndDate() != null) {
            contractDataSToContractAgentPDFFields.setField("endDate", contractDataToContractAgent.getEndDate().format(dateFormatter));
        }else{
            contractDataSToContractAgentPDFFields.setField("endDate", null);
        }
        if(contractDataToContractAgent.getDurationDays().toDays() > 0) {
            contractDataSToContractAgentPDFFields.setField("durationDays", contractDataToContractAgent.getDurationDays().toDays() + " días");
        }else{
            contractDataSToContractAgentPDFFields.setField("durationDays", Parameters.UNDEFINED_DURATION_TEXT);
        }

        /* Start of the form fill loop*/
        for(WorkDaySchedule workDay : contractDataToContractAgent.getSchedule()){
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
                contractDataSToContractAgentPDFFields.setField("dayOne", workDayPDF);
                contractDataSToContractAgentPDFFields.setField("dateOne", dateOne);
                contractDataSToContractAgentPDFFields.setField("A1AMfrom",A1AMfrom);
                contractDataSToContractAgentPDFFields.setField("A1AMto", A1AMto);
                contractDataSToContractAgentPDFFields.setField("A1PMfrom", A1PMfrom);
                contractDataSToContractAgentPDFFields.setField("A1PMto", A1PMto);
                contractDataSToContractAgentPDFFields.setField("hoursOne", durationHours);
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
                contractDataSToContractAgentPDFFields.setField("dayTwo", workDayPDF);
                contractDataSToContractAgentPDFFields.setField("dateTwo", dateTwo);
                contractDataSToContractAgentPDFFields.setField("A2AMfrom",A2AMfrom );
                contractDataSToContractAgentPDFFields.setField("A2AMto", A2AMto);
                contractDataSToContractAgentPDFFields.setField("A2PMfrom", A2PMfrom);
                contractDataSToContractAgentPDFFields.setField("A2PMto", A2PMto);
                contractDataSToContractAgentPDFFields.setField("hoursTwo", durationHours);
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
                contractDataSToContractAgentPDFFields.setField("dayThree", workDayPDF);
                contractDataSToContractAgentPDFFields.setField("dateThree", dateThree);
                contractDataSToContractAgentPDFFields.setField("A3AMfrom",A3AMfrom );
                contractDataSToContractAgentPDFFields.setField("A3AMto", A3AMto);
                contractDataSToContractAgentPDFFields.setField("A3PMfrom", A3PMfrom);
                contractDataSToContractAgentPDFFields.setField("A3PMto", A3PMto);
                contractDataSToContractAgentPDFFields.setField("hoursThree", durationHours);
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
                contractDataSToContractAgentPDFFields.setField("dayFour", workDayPDF);
                contractDataSToContractAgentPDFFields.setField("dateFour", dateFour);
                contractDataSToContractAgentPDFFields.setField("A4AMfrom",A4AMfrom );
                contractDataSToContractAgentPDFFields.setField("A4AMto", A4AMto);
                contractDataSToContractAgentPDFFields.setField("A4PMfrom", A4PMfrom);
                contractDataSToContractAgentPDFFields.setField("A4PMto", A4PMto);
                contractDataSToContractAgentPDFFields.setField("hoursFour", durationHours);
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
                contractDataSToContractAgentPDFFields.setField("dayFive", workDayPDF);
                contractDataSToContractAgentPDFFields.setField("dateFive", dateFive);
                contractDataSToContractAgentPDFFields.setField("A5AMfrom",A5AMfrom );
                contractDataSToContractAgentPDFFields.setField("A5AMto", A5AMto);
                contractDataSToContractAgentPDFFields.setField("A5PMfrom", A5PMfrom);
                contractDataSToContractAgentPDFFields.setField("A5PMto", A5PMto);
                contractDataSToContractAgentPDFFields.setField("hoursFive", durationHours);
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
                contractDataSToContractAgentPDFFields.setField("daySix", workDayPDF);
                contractDataSToContractAgentPDFFields.setField("dateSix", dateSix);
                contractDataSToContractAgentPDFFields.setField("A6AMfrom",A6AMfrom );
                contractDataSToContractAgentPDFFields.setField("A6AMto", A6AMto);
                contractDataSToContractAgentPDFFields.setField("A6PMfrom", A6PMfrom);
                contractDataSToContractAgentPDFFields.setField("A6PMto", A6PMto);
                contractDataSToContractAgentPDFFields.setField("hoursSix", durationHours);
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
                contractDataSToContractAgentPDFFields.setField("daySeven", workDayPDF);
                contractDataSToContractAgentPDFFields.setField("dateSeven", dateSeven);
                contractDataSToContractAgentPDFFields.setField("A7AMfrom",A7AMfrom );
                contractDataSToContractAgentPDFFields.setField("A7AMto", A7AMto);
                contractDataSToContractAgentPDFFields.setField("A7PMfrom", A7PMfrom);
                contractDataSToContractAgentPDFFields.setField("A7PMto", A7PMto);
                contractDataSToContractAgentPDFFields.setField("hoursSeven", durationHours);
            }
        }

        contractDataSToContractAgentPDFFields.setField("additionalData",contractDataToContractAgent.getAdditionalData());
        contractDataSToContractAgentPDFFields.setField("laborCategory",contractDataToContractAgent.getLaborCategory());

        stamp.setFormFlattening(true);
        stamp.close();

        return pathOut;
    }
}
