package gmoldes.services;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.print.*;
import javax.print.attribute.Attribute;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.*;

import gmoldes.utilities.Message;
import gmoldes.utilities.Parameters;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

public class Printer {

    public static String printPDF(String pathToPDF, Map<String, String> printAttributes) throws IOException, PrinterException {

        PrintService serviceForPrint = null;
        PDDocument PDFDocumentLoaded = PDDocument.load(new File(pathToPDF));

//        PDViewerPreferences PDFDocumentPreferences = PDFDocumentLoaded.getDocumentCatalog().getViewerPreferences();
//        System.out.println("Preferencias: " + PDFDocumentPreferences);

        PrintRequestAttributeSet datts = new HashPrintRequestAttributeSet();
        MediaSizeName DINA4 = MediaSize.ISO.A4.getMediaSizeName();
        MediaSizeName DINA3 = MediaSize.ISO.A3.getMediaSizeName();

        if(printAttributes.get("papersize").equals("A4")) {
            datts.add(DINA4);
        } else{
            datts.add(DINA3);
        }
        if(printAttributes.get("sides").equals("DUPLEX")) {
            datts.add(Sides.TWO_SIDED_SHORT_EDGE);
        } else{
            datts.add(Sides.ONE_SIDED);
        }
        if(printAttributes.get("chromacity").equals("MONOCHROME")) {
            datts.add(Chromaticity.MONOCHROME);
        }else{
            datts.add(Chromaticity.COLOR);
        }
        if(printAttributes.get("orientation").equals("LANDSCAPE")) {
            datts.add(OrientationRequested.LANDSCAPE);
        }else{
            datts.add(OrientationRequested.PORTRAIT);
        }
        datts.add(new Copies(1));
        datts.add(new JobName("GmoldesJob", null));

        PrintService printServiceForAttributes = getPrintServiceForAttributes(datts);
        if(printServiceForAttributes == null){
            System.out.println("No printer can print the PDF with those attributes...");
            return "fail";
        }
        else {
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            printerJob.setPageable(new PDFPageable(PDFDocumentLoaded));
            printerJob.setPrintService(printServiceForAttributes);
            printerJob.print(datts);
        }
        PDFDocumentLoaded.close();

        return "ok";
    }

    private static PrintService getPrintServiceForAttributes(PrintRequestAttributeSet datts){
        PrintService serviceForPrint = null;
        PrintService[] printServicesForAttributes = PrintServiceLookup.lookupPrintServices(null, datts);
        if(printServicesForAttributes.length == 0){
            return null;
        }
        else {
            for (PrintService printService : printServicesForAttributes) {
//                AttributeSet attributes = getAttributesForPrintService(printService);
//                DocFlavor[] docF = getSupportedDocFlavorForPrintService(printService);
                if (printService.getName().contains(Parameters.DEFAULT_PRINTER)) {
                    serviceForPrint = printService;
                    break;
                } else {
                    serviceForPrint = ServiceUI.printDialog(null, 100, 100, printServicesForAttributes, null, null, datts);
                }
            }
        }

        return serviceForPrint;
    }

    private static AttributeSet getAttributesForPrintService(PrintService printService){
        AttributeSet attributes = printService.getAttributes();
        for (Attribute a : attributes.toArray()) {
            String name = a.getName();
            String value = attributes.get(a.getClass()).toString();
            System.out.println(name + " : " + value);
        }
            return attributes;
    }

    private static DocFlavor[] getSupportedDocFlavorForPrintService(PrintService printService){
        DocFlavor[] docF = printService.getSupportedDocFlavors();
        for(int i = 0; i <docF.length; i++){
            System.out.println(docF[i]);
        }
        return docF;
    }
}
