package gmoldes.components.contract.contract_variation.services;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import gmoldes.components.contract.contract_variation.forms.ContractVariationDataSubfolder;
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

public class ContractVariationDataSubfolderPDFCreator {

    private static final String PATH_TO_PDF_TEMPLATE = "/pdf_forms/DGM_003_Datos_Alta_o_Cambio_Contrato_Trabajo.pdf";

    public ContractVariationDataSubfolderPDFCreator() {
    }

    public static Path createContractVariationDataSubfolderPDF(ContractVariationDataSubfolder contractVariationDataSubfolder, Path pathOut) throws IOException, DocumentException {

        PdfReader reader = new PdfReader(PATH_TO_PDF_TEMPLATE);
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(pathOut.toString()));
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parameters.DEFAULT_DATE_FORMAT);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(Parameters.DEFAULT_TIME_FORMAT);

        AcroFields contractExtinctionDataSubfolderPDFFields = stamp.getAcroFields();
        contractExtinctionDataSubfolderPDFFields.setField("notificationType",contractVariationDataSubfolder.getNotificationType());
        contractExtinctionDataSubfolderPDFFields.setField("officialContractNumber",contractVariationDataSubfolder.getOfficialContractNumber());
        contractExtinctionDataSubfolderPDFFields.setField("employerFullName",contractVariationDataSubfolder.getEmployerFullName());
        contractExtinctionDataSubfolderPDFFields.setField("employerCCC",contractVariationDataSubfolder.getEmployerQuoteAccountCode());
        contractExtinctionDataSubfolderPDFFields.setField("notificationDate",contractVariationDataSubfolder.getNotificationDate());
        contractExtinctionDataSubfolderPDFFields.setField("notificationHour",contractVariationDataSubfolder.getNotificationHour());
        contractExtinctionDataSubfolderPDFFields.setField("employeeFullName",contractVariationDataSubfolder.getEmployeeFullName());
        contractExtinctionDataSubfolderPDFFields.setField("employeeNIF",contractVariationDataSubfolder.getEmployeeNif());
        contractExtinctionDataSubfolderPDFFields.setField("employeeNASS",contractVariationDataSubfolder.getEmployeeNASS());
        contractExtinctionDataSubfolderPDFFields.setField("employeeBirthDate",contractVariationDataSubfolder.getEmployeeBirthDate());
        contractExtinctionDataSubfolderPDFFields.setField("employeeCivilState",contractVariationDataSubfolder.getEmployeeCivilState());
        contractExtinctionDataSubfolderPDFFields.setField("employeeNationality",contractVariationDataSubfolder.getEmployeeNationality());
        contractExtinctionDataSubfolderPDFFields.setField("employeeFullAddress",contractVariationDataSubfolder.getEmployeeFullAddress());
        contractExtinctionDataSubfolderPDFFields.setField("employeeMaxStudyLevel",contractVariationDataSubfolder.getEmployeeMaxStudyLevel());
        if(contractVariationDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.MONDAY)) {
            contractExtinctionDataSubfolderPDFFields.setField("monday", "Yes");
        }
        if(contractVariationDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.TUESDAY)) {
            contractExtinctionDataSubfolderPDFFields.setField("tuesday", "Yes");
        }
        if(contractVariationDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.WEDNESDAY)) {
            contractExtinctionDataSubfolderPDFFields.setField("wednesday", "Yes");
        }
        if(contractVariationDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.THURSDAY)) {
            contractExtinctionDataSubfolderPDFFields.setField("thursday", "Yes");
        }
        if(contractVariationDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.FRIDAY)) {
            contractExtinctionDataSubfolderPDFFields.setField("friday", "Yes");
        }
        if(contractVariationDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.SATURDAY)) {
            contractExtinctionDataSubfolderPDFFields.setField("saturday", "Yes");
        }
        if(contractVariationDataSubfolder.getDayOfWeekSet().contains(DayOfWeek.SUNDAY)) {
            contractExtinctionDataSubfolderPDFFields.setField("sunday", "Yes");
        }
        contractExtinctionDataSubfolderPDFFields.setField("contractType",contractVariationDataSubfolder.getContractTypeDescription());
        contractExtinctionDataSubfolderPDFFields.setField("startDate", contractVariationDataSubfolder.getStartDate());
        contractExtinctionDataSubfolderPDFFields.setField("endDate", contractVariationDataSubfolder.getEndDate());
        contractExtinctionDataSubfolderPDFFields.setField("durationDays", contractVariationDataSubfolder.getDurationDays());


        /* Start of the form fill loop*/
        for(WorkDaySchedule workDay : contractVariationDataSubfolder.getSchedule()){
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
                contractExtinctionDataSubfolderPDFFields.setField("dayOne", workDayPDF);
                contractExtinctionDataSubfolderPDFFields.setField("dateOne", dateOne);
                contractExtinctionDataSubfolderPDFFields.setField("A1AMfrom",A1AMfrom);
                contractExtinctionDataSubfolderPDFFields.setField("A1AMto", A1AMto);
                contractExtinctionDataSubfolderPDFFields.setField("A1PMfrom", A1PMfrom);
                contractExtinctionDataSubfolderPDFFields.setField("A1PMto", A1PMto);
                contractExtinctionDataSubfolderPDFFields.setField("hoursOne", durationHours);
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
                contractExtinctionDataSubfolderPDFFields.setField("dayTwo", workDayPDF);
                contractExtinctionDataSubfolderPDFFields.setField("dateTwo", dateTwo);
                contractExtinctionDataSubfolderPDFFields.setField("A2AMfrom",A2AMfrom );
                contractExtinctionDataSubfolderPDFFields.setField("A2AMto", A2AMto);
                contractExtinctionDataSubfolderPDFFields.setField("A2PMfrom", A2PMfrom);
                contractExtinctionDataSubfolderPDFFields.setField("A2PMto", A2PMto);
                contractExtinctionDataSubfolderPDFFields.setField("hoursTwo", durationHours);
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
                contractExtinctionDataSubfolderPDFFields.setField("dayThree", workDayPDF);
                contractExtinctionDataSubfolderPDFFields.setField("dateThree", dateThree);
                contractExtinctionDataSubfolderPDFFields.setField("A3AMfrom",A3AMfrom );
                contractExtinctionDataSubfolderPDFFields.setField("A3AMto", A3AMto);
                contractExtinctionDataSubfolderPDFFields.setField("A3PMfrom", A3PMfrom);
                contractExtinctionDataSubfolderPDFFields.setField("A3PMto", A3PMto);
                contractExtinctionDataSubfolderPDFFields.setField("hoursThree", durationHours);
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
                contractExtinctionDataSubfolderPDFFields.setField("dayFour", workDayPDF);
                contractExtinctionDataSubfolderPDFFields.setField("dateFour", dateFour);
                contractExtinctionDataSubfolderPDFFields.setField("A4AMfrom",A4AMfrom );
                contractExtinctionDataSubfolderPDFFields.setField("A4AMto", A4AMto);
                contractExtinctionDataSubfolderPDFFields.setField("A4PMfrom", A4PMfrom);
                contractExtinctionDataSubfolderPDFFields.setField("A4PMto", A4PMto);
                contractExtinctionDataSubfolderPDFFields.setField("hoursFour", durationHours);
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
                contractExtinctionDataSubfolderPDFFields.setField("dayFive", workDayPDF);
                contractExtinctionDataSubfolderPDFFields.setField("dateFive", dateFive);
                contractExtinctionDataSubfolderPDFFields.setField("A5AMfrom",A5AMfrom );
                contractExtinctionDataSubfolderPDFFields.setField("A5AMto", A5AMto);
                contractExtinctionDataSubfolderPDFFields.setField("A5PMfrom", A5PMfrom);
                contractExtinctionDataSubfolderPDFFields.setField("A5PMto", A5PMto);
                contractExtinctionDataSubfolderPDFFields.setField("hoursFive", durationHours);
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
                contractExtinctionDataSubfolderPDFFields.setField("daySix", workDayPDF);
                contractExtinctionDataSubfolderPDFFields.setField("dateSix", dateSix);
                contractExtinctionDataSubfolderPDFFields.setField("A6AMfrom",A6AMfrom );
                contractExtinctionDataSubfolderPDFFields.setField("A6AMto", A6AMto);
                contractExtinctionDataSubfolderPDFFields.setField("A6PMfrom", A6PMfrom);
                contractExtinctionDataSubfolderPDFFields.setField("A6PMto", A6PMto);
                contractExtinctionDataSubfolderPDFFields.setField("hoursSix", durationHours);
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
                contractExtinctionDataSubfolderPDFFields.setField("daySeven", workDayPDF);
                contractExtinctionDataSubfolderPDFFields.setField("dateSeven", dateSeven);
                contractExtinctionDataSubfolderPDFFields.setField("A7AMfrom",A7AMfrom );
                contractExtinctionDataSubfolderPDFFields.setField("A7AMto", A7AMto);
                contractExtinctionDataSubfolderPDFFields.setField("A7PMfrom", A7PMfrom);
                contractExtinctionDataSubfolderPDFFields.setField("A7PMto", A7PMto);
                contractExtinctionDataSubfolderPDFFields.setField("hoursSeven", durationHours);
            }
        }

        contractExtinctionDataSubfolderPDFFields.setField("additionalData",contractVariationDataSubfolder.getAdditionalData());
        contractExtinctionDataSubfolderPDFFields.setField("laborCategory",contractVariationDataSubfolder.getLaborCategory());
        contractExtinctionDataSubfolderPDFFields.setField("GMcontractNumber",contractVariationDataSubfolder.getGmContractNumber());

        stamp.setFormFlattening(true);
        stamp.close();

        return pathOut;
    }
}
