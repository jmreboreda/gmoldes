package gmoldes.domain.timerecord.service;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import gmoldes.components.timerecord.forms.TimeRecord;
import gmoldes.services.Printer;
import gmoldes.utilities.Parameters;
import gmoldes.utilities.Utilities;

import java.awt.print.PrinterException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class TimeRecordPDFCreator {

    private static final String PATH_TO_PDF_TEMPLATE = "/pdf_forms/DGM_002_Registro_Horario.pdf";

    public TimeRecordPDFCreator() {
    }

    public static Path createTimeRecordPDF(TimeRecord timeRecord) throws IOException, DocumentException {

        String temporalDir = null;
        if(Parameters.OPERATING_SYSTEM.toLowerCase().contains(Parameters.OS_WINDOWS)){
            temporalDir = Parameters.WINDOWS_TEMPORAL_DIR;
        }else if(Parameters.OPERATING_SYSTEM.toLowerCase().contains(Parameters.OS_LINUX)){
            temporalDir = Parameters.LINUX_TEMPORAL_DIR;
        }

        Path pathToTimeRecordPDF = Paths.get(Parameters.USER_HOME, temporalDir, timeRecord.toFileName().concat(".pdf"));
        Path directoriesTree = Files.createDirectories(pathToTimeRecordPDF.getParent());
        if(directoriesTree == null){
            return null;
        }

        PdfReader reader = new PdfReader(PATH_TO_PDF_TEMPLATE);
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(pathToTimeRecordPDF.toString()));

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

        return pathToTimeRecordPDF;
    }

    public static String printTimeRecord(Path pathToTimeRecordPDF){
        Map<String, String> attributes = new HashMap<>();
        attributes.put("papersize","A4");
        attributes.put("sides", "DUPLEX");
        attributes.put("chromacity","MONOCHROME");
        attributes.put("orientation","LANDSCAPE");

        String resultPrint = null;
        try {
            resultPrint = Printer.printPDF(pathToTimeRecordPDF.toString(), attributes);
            Utilities.deleteFileFromPath(pathToTimeRecordPDF.toString());

        } catch (IOException | PrinterException e) {
            e.printStackTrace();
        }

        return resultPrint;
    }
}
