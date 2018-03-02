package gmoldes.domain;

import java.util.regex.Pattern;

public class NieNif {

    private String firstLetterNIF;
    private String numbersNIF;
    private String lastLetterNIF;

    private static final Integer LEGAL_LENGTH_NIF = 9;


    private static final Pattern lettersPattern = Pattern.compile("[A-Z]");
    private static final Pattern numbersPattern = Pattern.compile("[0-9]");

    private static final Pattern nieNifPattern = Pattern.compile("[[A-H][J-N][P-S]UVW][0-9]{7}[0-9A-J]");

    private static final Pattern humanPersonNifPattern = Pattern.compile("[[0-9][A-H][J-N][P-T][V-Z]]" + "[[P-S]N[W-Z]]");
    private static final Pattern nifDniPattern = Pattern.compile("[[0-9][A-H][J-N][P-T][V-Z]]");
    private static final Pattern nifNiePattern = Pattern.compile("[[0-9][P-S]N[W-Z]]");

    private static final Pattern initialSingleLetterForNifNotDni = Pattern.compile("[[A-H]JV]");
    private static final Pattern initialAndFinalLetterForNifNotDni = Pattern.compile("[P-S]N[W-Z]]");
    private String finalLetterOfNIFDNI[] = {"T","R","W","A","G","M","Y","F","P","D","X","B","N","J","Z","S","Q","V","H","L","C","K","E"};

    private static final String ONLY_NUMBERS_CONTROL = "ABEH"; /* Sólo admiten números como caracter de control */
    private static final String ONLY_LETTERS_CONTROL = "KPQS"; /* Sólo admiten letras como caracter de control */
    private static final String NUMBER_TO_LETTER_CONTROL = "JABCDEFGHI"; /* Conversión de dígito a letra de control. */

//    private String initialSingleLetterForNIFnotDNI[] = {"A","B","C","D","E","F","G","H","J","V"};
//    private String initialAndFinalLetterForNIFnotDNI[] = {"X","Y","Z","N","P","Q","R","S","W"};

    public NieNif(String nif) {

        this.firstLetterNIF = nif.substring(0, 0);
        this.numbersNIF = nif.substring(1, nif.length()-2);
        this.lastLetterNIF = nif.substring(nif.length()-1, nif.length()-1);

    }

    public String getFirstLetterNIF() {
        return firstLetterNIF;
    }

    public void setFirstLetterNIF(String firstLetterNIF) {
        this.firstLetterNIF = firstLetterNIF;
    }

    public String getNumbersNIF() {
        return numbersNIF;
    }

    public void setNumbersNIF(String numbersNIF) {
        this.numbersNIF = numbersNIF;
    }

    public String getLastLetterNIF() {
        return lastLetterNIF;
    }

    public void setLastLetterNIF(String lastLetterNIF) {
        this.lastLetterNIF = lastLetterNIF;
    }

    public Integer getNifLength() {
        return LEGAL_LENGTH_NIF;
    }

    public void setNifLength(Integer nifLength) {
        this.LEGAL_LENGTH_NIF = nifLength;
    }

    @Override
    public String toString(){
        return firstLetterNIF + numbersNIF + lastLetterNIF;
    }

    public Boolean isADNI(String firstLetterNIF){

    }

    private Character calculateLetterOfNIF_DNI(Integer digitsOfDNI) {
        return finalLetterOfNIFDNI[digitsOfDNI % 23].charAt(0);
    }

    public Boolean validateNIF_DNI(String nif_DNI){
        Boolean isValidDNI = true;

        if (!humanPersonNifPattern.matcher(nif_DNI).matches()) {
            return false;
        }else{

        }



        return isValidDNI;
    }

    public static boolean validateNIF_notDNI(String nifNotDNI) {
        try {
            if (!nieNifPattern.matcher(nifNotDNI).matches()) {
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
                    // ¿el caracter de control es válido como letra?
                    (ONLY_NUMBERS_CONTROL.indexOf(initialLetter) < 0 && NUMBER_TO_LETTER_CONTROL.charAt(digitD) == finalCharacter)
                            ||
                            // ¿el caracter de control es válido como dígito?
                            (ONLY_LETTERS_CONTROL.indexOf(initialLetter) < 0 && digitD == Character.digit(finalCharacter, 10));
            return isValidControl;

        } catch (Exception e) {
            return false;
        }
    }

    public String formatAsNIF(){
        String nif_DNI = (firstLetterNIF + numbersNIF + lastLetterNIF);
        Integer nifLength = nif_DNI.length();
        String nifFormatted = null;
        Integer i;

        /* Societies and other entities  */
        if(initialSingleLetterForNifNotDni.matcher(nif_DNI).matches()){
                nifFormatted = firstLetterNIF + "-" + numbersNIF;
                return nifFormatted;
            }

        /* Foreign persons */
        if(initialAndFinalLetterForNifNotDni.matcher(nif_DNI).matches()){
            if (nifLength == 9) {
                /* NIF: Letter + 7 + letter */
                nifFormatted = firstLetterNIF + "-" + numbersNIF.substring(1, 2) + "." + numbersNIF.substring(2, 5) + "." + numbersNIF.substring(5, 8) + "-" + lastLetterNIF;
                return nifFormatted;
            } else {
                nifFormatted = firstLetterNIF + "-" + numbersNIF.substring(1, numbersNIF.length() - 2) + numbersNIF.substring(numbersNIF.length() - 2, numbersNIF.length() - 1);
                return nifFormatted;
            }
        }

        /* NIF: 8 + letter */
        if (nifLength == 9)
            nifFormatted = numbersNIF.substring(0,2) + "." + numbersNIF.substring(2,5) + "." + numbersNIF.substring(5,8) + "-" + lastLetterNIF;
        else
            nifFormatted = numbersNIF.substring(0,1) + "." + numbersNIF.substring(1,4) + "." + numbersNIF.substring(4,7) + "-" + lastLetterNIF;

        return nifFormatted;
    }
}
