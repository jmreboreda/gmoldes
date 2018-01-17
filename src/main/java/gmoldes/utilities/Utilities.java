package gmoldes.utilities;

import javafx.util.StringConverter;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utilities {

    public enum Months {
        ENERO       ("enero"),
        FEBRERO     ("febrero"),
        MARZO       ("marzo"),
        ABRIL       ("abril"),
        MAYO        ("mayo"),
        JUNIO       ("junio"),
        JULIO       ("julio"),
        AGOSTO      ("agosto"),
        SEPTIEMBRE  ("septiembre"),
        OCTUBRE     ("octubre"),
        NOVIEMBRE   ("noviembre"),
        DICIEMBRE   ("diciembre");

        String month;

        Months(String p){
            month = p;
        }
        public String getMonthName() {
            return month;
        }
    }

    public enum TypeClients {
        PERSONA_FISICA      ("PF"),
        PERSONA_JURIDICA    ("PJ"),
        OTROS               ("OT");

        String typeClient;

        TypeClients(String p){
            typeClient = p;
        }
        public String getTypeClient() {
            return typeClient;
        }
    }

    public enum ServicesGM {
        ASESORIA_FISCAL         ("Asesoría fiscal"),
        CONTABILIDAD            ("Contabilidad"),
        REGISTRO_FACTURAS       ("Libros Registro IGI"),
        REGISTRO_PARA_IVA       ("Registro de IVA en Módulos"),
        ASESORIA_LABORAL        ("Asesoría laboral");

        String serviceGM;

        ServicesGM(String p){
            serviceGM = p;
        }
        public String getServiceGM() {
            return serviceGM;
        }
    }


    public static StringConverter converter = new StringConverter<LocalDate>() {
        DateTimeFormatter dateFormatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy");
        @Override
        public String toString(LocalDate date) {
            if (date != null) {
                return dateFormatter.format(date);
            } else {
                return "";
            }
        }
        @Override
        public LocalDate fromString(String string) {
            if (string != null && !string.isEmpty()) {
                return LocalDate.parse(string, dateFormatter);
            } else {
                return null;
            }
        }
    };

    public static Date verifyHourValue(String time){
        Date hour;
        DateFormat hourFormatter = new SimpleDateFormat("HH:mm");
        hourFormatter.setLenient(false);
        try{
            hour = hourFormatter.parse(time);
        }catch(ParseException e){
            return null;
        }
        return hour;
    }

    public static String replaceWithUnderscore(String aString){

        return aString.replace(". ", "")
                .replace(".", "")
                .replace(",", "")
                .replace(" ", "_");
    }

    public static void deleteFileFromPath(String pathToFile) throws IOException {
        Path path = FileSystems.getDefault().getPath(pathToFile);
        Files.delete(path);
    }

    public static boolean validateDate(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            dateFormat.setLenient(false);
            dateFormat.parse(date);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

    public static String formatAsNIF(String dni){
        String nifFormatted = null;
        int i;

        String initialSingleLetter[] = {"A","B","C","D","E","F","G","H","J","V"};
        String initialAndFinalLetter[] = {"X","Y","Z","N","P","Q","R","S","W"};

        for (i = 0; i < initialSingleLetter.length - 1; i++)
        {
            if (dni.substring(0, 1).equals(initialSingleLetter[i]))
            {
                nifFormatted = dni.substring(0,1)+"-"+dni.substring(1, dni.length());
                return nifFormatted;
            }
        }
        for (i = 0; i < initialAndFinalLetter.length - 1; i++)
        {
            if (dni.substring(0, 1).equals(initialAndFinalLetter[i]))
                if (dni.length() == 9)
                {
                    // Letra + 7 + letra
                    nifFormatted = dni.substring(0,1)+"-"+dni.substring(1,2)+"."+dni.substring(2,5)+"."+dni.substring(5,8)+"-"+dni.substring(8,9);
                    return nifFormatted;
                }
                else
                {
                    nifFormatted = dni.substring(0,1)+"-"+ dni.substring(1, dni.length()-2) + dni.substring(dni.length()-2,dni.length()-1 );
                    return nifFormatted;
                }
        }
        // NIF: 8 + letra
        if (dni.length() == 9)
            nifFormatted = dni.substring(0,2)+"."+dni.substring(2,5)+"."+dni.substring(5,8)+"-"+dni.substring(8,9);
        else
            nifFormatted = dni.substring(0,1)+"."+dni.substring(1,4)+"."+dni.substring(4,7)+"-"+dni.substring(7,8);

        return nifFormatted;
    }
}
