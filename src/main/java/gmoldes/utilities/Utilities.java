package gmoldes.utilities;

import javafx.util.StringConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class Utilities {

    public static StringConverter dateConverter = new StringConverter<LocalDate>() {
        DateTimeFormatter dateFormatter =
                DateTimeFormatter.ofPattern(Parameters.DEFAULT_DATE_FORMAT);
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

    public static Duration converterTimeStringToDuration(String timeAsString){
        String minutes = null;
        if(timeAsString.length() == 5){
            minutes = timeAsString.substring(3,5);
        }
        else if(timeAsString.length() == 4){
            minutes = timeAsString.substring(2,4);
        }

        assert minutes != null;
        if(Integer.parseInt(minutes) > Parameters.MAXIMUM_VALUE_MINUTES_IN_HOUR) {

            return null;
        }

        String stringDuration = timeAsString.replace(":", "H");
        stringDuration = "PT" + stringDuration + "M";

        return Duration.parse(stringDuration);
    }

    public static  String converterDurationToTimeString(Duration duration){
        if(duration == Duration.ZERO){
            return "00:00";
        }

        String durationToString = duration.toString();
        durationToString = durationToString.replace("PT","");
        durationToString = durationToString.replace("H",":");
        durationToString = durationToString.replace("M","");

        Long durationHours = duration.toHours();
        Long durationMinutes = duration.toMinutes();
        Long minutes = durationMinutes - durationHours * 60;

        durationToString = minutes == 0 ? durationToString + "00" : durationToString;

        durationToString = durationToString.length() < 5 ? "0" +  durationToString : durationToString;

        return durationToString;
    }

    public static Duration convertIntegerToDuration(Integer intergerToConvert){

        return Duration.parse("P" + intergerToConvert + "D");
    }

    public static DayOfWeek converterStringToDayOfWeek(String dayOfWeekString){
        DayOfWeek dayOfWeek = null;
        if (dayOfWeekString.equals(DayOfWeek.MONDAY.getDisplayName(TextStyle.FULL, Locale.getDefault()))) {
                dayOfWeek = DayOfWeek.MONDAY;
        }
        else if (dayOfWeekString.equals(DayOfWeek.TUESDAY.getDisplayName(TextStyle.FULL, Locale.getDefault()))) {
            dayOfWeek = DayOfWeek.TUESDAY;
        }
        else if (dayOfWeekString.equals(DayOfWeek.WEDNESDAY.getDisplayName(TextStyle.FULL, Locale.getDefault()))) {
            dayOfWeek = DayOfWeek.WEDNESDAY;
        }
        else if (dayOfWeekString.equals(DayOfWeek.THURSDAY.getDisplayName(TextStyle.FULL, Locale.getDefault()))) {
            dayOfWeek = DayOfWeek.THURSDAY;
        }
        else if (dayOfWeekString.equals(DayOfWeek.FRIDAY.getDisplayName(TextStyle.FULL, Locale.getDefault()))) {
            dayOfWeek = DayOfWeek.FRIDAY;
        }
        else if (dayOfWeekString.equals(DayOfWeek.SATURDAY.getDisplayName(TextStyle.FULL, Locale.getDefault()))) {
            dayOfWeek = DayOfWeek.SATURDAY;
        }
        else if (dayOfWeekString.equals(DayOfWeek.SUNDAY.getDisplayName(TextStyle.FULL, Locale.getDefault()))) {
            dayOfWeek = DayOfWeek.SUNDAY;
        }

        return dayOfWeek;
    }

    public static Date validateStringAsTime(String time){
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
            SimpleDateFormat dateFormat = new SimpleDateFormat(Parameters.DEFAULT_DATE_FORMAT);
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
        for (i = 0; i < initialAndFinalLetter.length - 1; i++){
            if (dni.substring(0, 1).equals(initialAndFinalLetter[i])) {
                if (dni.length() == 9) {
                    /* Letra + 7 + letra */
                    nifFormatted = dni.substring(0, 1) + "-" + dni.substring(1, 2) + "." + dni.substring(2, 5) + "." + dni.substring(5, 8) + "-" + dni.substring(8, 9);
                    return nifFormatted;
                } else {
                    nifFormatted = dni.substring(0, 1) + "-" + dni.substring(1, dni.length() - 2) + dni.substring(dni.length() - 2, dni.length() - 1);
                    return nifFormatted;
                }
            }
        }
        /* NIF: 8 + letra */
        if (dni.length() == 9)
            nifFormatted = dni.substring(0,2)+"."+dni.substring(2,5)+"."+dni.substring(5,8)+"-"+dni.substring(8,9);
        else
            nifFormatted = dni.substring(0,1)+"."+dni.substring(1,4)+"."+dni.substring(4,7)+"-"+dni.substring(7,8);

        return nifFormatted;
    }

    private Character calculateNIF_DNILetter(String digitsNIF){
        String lettertable[] = {"T","R","W","A","G","M","Y","F","P","D","X","B","N","J","Z","S","Q","V","H","L","C","K","E"};
        Integer numberNIF = Integer.parseInt(digitsNIF);
        return lettertable[numberNIF % 23].charAt(0);

    }

    public static boolean validateNIF_notDNI(String nifNotDNI) {
        Pattern cifPattern = Pattern.compile("[[A-H][J-N][P-S]UVW][0-9]{7}[0-9A-J]");
        String ONLY_NUMBERS_CONTROL = "ABEH"; // Only support numbers as control character
        String ONLY_LETTERS_CONTROL = "KPQS"; // They only support letters as a control character
        String NUMBER_TO_LETTER_CONTROL = "JABCDEFGHI"; // Conversión de dígito a letra de control.

        try {
            if (!cifPattern.matcher(nifNotDNI).matches()) {
                /* No cumple el patrón */
                return false;
            }

            int parA = 0;
            for (int i = 2; i < 8; i += 2) {
                final int digit = Character.digit(nifNotDNI.charAt(i), 10);
                if (digit < 0) {
                    return false;
                }
                parA += digit;
            }

            int nonB = 0;
            for (int i = 1; i < 9; i += 2) {
                final int digit = Character.digit(nifNotDNI.charAt(i), 10);
                if (digit < 0) {
                    return false;
                }
                int nn = 2 * digit;
                if (nn > 9) {
                    nn = 1 + (nn - 10);
                }
                nonB += nn;
            }

            final int partialC = parA + nonB;
            final int digitE = partialC % 10;
            final int digitD = (digitE > 0) ? (10 - digitE) : 0;
            final char initialLetter = nifNotDNI.charAt(0);
            final char finalCharacter = nifNotDNI.charAt(8);

            final boolean isValidControl =
                    // Character control is valid as a letter?
                    (ONLY_NUMBERS_CONTROL.indexOf(initialLetter) < 0 && NUMBER_TO_LETTER_CONTROL.charAt(digitD) == finalCharacter)
                            ||
                            // Character control is valid as a digit?
                            (ONLY_LETTERS_CONTROL.indexOf(initialLetter) < 0 && digitD == Character.digit(finalCharacter, 10));
            return isValidControl;

        } catch (Exception e) {
            return false;
        }
    }
}
