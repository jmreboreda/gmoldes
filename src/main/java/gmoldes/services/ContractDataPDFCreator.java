package gmoldes.services;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import gmoldes.forms.ContractDataSubfolder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

public class ContractDataPDFCreator {

    private static final String PATH_TO_PDF_TEMPLATE = "/pdf_forms/DGM_003_Alta_o_Cambio_Contrato_Trabajo_A3_LO_Versión2.pdf";

    public ContractDataPDFCreator() {
    }

    public static Path createContractDataSubfolderPDF(ContractDataSubfolder contractDataSubfolder, Path pathOut) throws IOException, DocumentException {

        PdfReader reader = new PdfReader(PATH_TO_PDF_TEMPLATE);
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(pathOut.toString()));

        AcroFields timeRecordPDFFields = stamp.getAcroFields();
        //HashMap map = timeRecordPDF.getFields();
//        timeRecordPDFFields.setField("nameOfMonth",timeRecord.getNameOfMonth());
//        timeRecordPDFFields.setField("yearNumber",timeRecord.getYearNumber());
//        timeRecordPDFFields.setField("enterpriseName",timeRecord.getEnterpriseName());
//        timeRecordPDFFields.setField("quoteAccountCode",timeRecord.getQuoteAccountCode());
//        timeRecordPDFFields.setField("employeeName",timeRecord.getEmployeeName());
//        timeRecordPDFFields.setField("employeeNIF",timeRecord.getEmployeeNIF());
//        timeRecordPDFFields.setField("numberHoursPerWeek",timeRecord.getNumberHoursPerWeek());
//        timeRecordPDFFields.setField("enterpriseSignature","Firmado: ".concat(timeRecord.getEnterpriseName()));
//        timeRecordPDFFields.setField("employeeSignature","Firmado: ".concat(timeRecord.getEmployeeName()));

        stamp.setFormFlattening(true);
        stamp.close();

        return pathOut;
    }
}
