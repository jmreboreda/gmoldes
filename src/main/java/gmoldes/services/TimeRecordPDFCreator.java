package gmoldes.services;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import gmoldes.forms.TimeRecord;

import java.io.FileOutputStream;
import java.io.IOException;

public class TimeRecordPDFCreator {

    private static final String OS_ALIAS = System.getProperty("os.name");
    private static final String USER_HOME = System.getProperty("user.home");
    private static final String PATH_TO_PDF_TEMPLATE = "/pdf_forms/DGM_002_Registro_Horario.pdf";

    public TimeRecordPDFCreator() {
    }

    public static String createTimeRecordPDF(TimeRecord timeRecord) throws IOException, DocumentException {

        String desktopDirName = "";

        if(OS_ALIAS.toLowerCase().contains("windows")){
            desktopDirName = "Desktop";
        }else if(OS_ALIAS.toLowerCase().contains("linux")){
            desktopDirName = "Escritorio";
        }

        final String pathOut = USER_HOME.concat("/").concat(desktopDirName).concat("/").concat(timeRecord.toFileName()).concat(".pdf");
        PdfReader reader = new PdfReader(PATH_TO_PDF_TEMPLATE);
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(pathOut));

        AcroFields timeRecordPDFFields = stamp.getAcroFields();
        //HashMap map = timeRecordPDF.getFields();
        timeRecordPDFFields.setField("nameOfMonth",timeRecord.getNameOfMonth());
        timeRecordPDFFields.setField("yearNumber",timeRecord.getYearNumber());
        timeRecordPDFFields.setField("enterpriseName",timeRecord.getEnterpriseName());
        timeRecordPDFFields.setField("quoteAccountCode",timeRecord.getQuoteAccountCode());
        timeRecordPDFFields.setField("employeeName",timeRecord.getEmployeeName());
        timeRecordPDFFields.setField("employeeNIF",timeRecord.getEmployeeNIF());
        timeRecordPDFFields.setField("numberHoursPerWeek",timeRecord.getNumberHoursPerWeek());
        timeRecordPDFFields.setField("enterpriseSignature","Firmado: ".concat(timeRecord.getEnterpriseName()));
        timeRecordPDFFields.setField("employeeSignature","Firmado: ".concat(timeRecord.getEmployeeName()));

        stamp.setFormFlattening(true);
        stamp.close();

        return pathOut;
    }
}
