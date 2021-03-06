package gmoldes.services;

import gmoldes.ApplicationConstants;
import gmoldes.utilities.Parameters;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;
import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.attribute.Attribute;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.*;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

public class Printer {

    public static String printPDF(String pathToPDF, Map<String, String> printAttributes) throws IOException, PrinterException {

        PDDocument PDFDocumentLoaded = PDDocument.load(new File(pathToPDF));

        PrintRequestAttributeSet praSet = new HashPrintRequestAttributeSet();
        MediaSizeName DINA4 = MediaSize.ISO.A4.getMediaSizeName();
        MediaSizeName DINA3 = MediaSize.ISO.A3.getMediaSizeName();

        if(printAttributes.get("papersize").equals("A4")) {
            praSet.add(DINA4);
        } else{
            praSet.add(DINA3);
        }
        if(printAttributes.get("sides").equals("DUPLEX")) {
            praSet.add(Sides.TWO_SIDED_SHORT_EDGE);
        } else{
            praSet.add(Sides.ONE_SIDED);
        }
        if(printAttributes.get("chromacity").equals("MONOCHROME")) {
            praSet.add(Chromaticity.MONOCHROME);
        }else{
            praSet.add(Chromaticity.COLOR);
        }
        if(printAttributes.get("orientation").equals("LANDSCAPE")) {
            praSet.add(OrientationRequested.LANDSCAPE);
        }else{
            praSet.add(OrientationRequested.PORTRAIT);
        }
        praSet.add(new Copies(1));
        praSet.add(new JobName("GmoldesPrintJob", Locale.getDefault()));

        // Check existence of printing services for the indicated attributes
        PrintService printServiceForAttributes = getPrintServiceForAttributes(praSet);

        if(printServiceForAttributes == null){
            PDFDocumentLoaded.close();

            return "fail";
        }
        else {

            PrinterJob printerJob = PrinterJob.getPrinterJob();
            printerJob.setPageable(new PDFPageable(PDFDocumentLoaded));
            printerJob.setPrintable(new PDFPrintable(PDFDocumentLoaded, Scaling.ACTUAL_SIZE));

            printerJob.setPrintService(printServiceForAttributes);
            printerJob.print(praSet);

            PDFDocumentLoaded.close();
        }

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
                DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
                if (printService.getName().contains(ApplicationConstants.DEFAULT_PRINTER)) {
                    serviceForPrint = printService;
                    break;
                } else {
                    serviceForPrint = ServiceUI.printDialog(null, 100, 100, printServicesForAttributes, null, flavor, datts);
                }
            }
        }

        return serviceForPrint;
    }

    private static MediaTray getTrayToA3Paper(PrintService printServiceForAttributes){

        // We chose something compatible with the printable interface
        DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;

        MediaTray selectedTray = null;
        Object o = printServiceForAttributes.getSupportedAttributeValues(Media.class, flavor, null);
        if (o != null && o.getClass().isArray()) {
            for (Media media : (Media[]) o) {
                if (media instanceof MediaTray) {
                    System.out.println(media.getValue() + " : " + media + " - " + media.getClass().getName());
                    if(media.toString().equals(Parameters.PRINTER_TRAY_OF_A3)){
                        selectedTray = (MediaTray) media;
                    }
                }
            }
        }

        return selectedTray;
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
        for(int i = 0; i < docF.length; i++){
            System.out.println(docF[i]);
        }
        return docF;
    }
}
