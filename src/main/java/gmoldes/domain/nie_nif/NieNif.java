package gmoldes.domain.nie_nif;

import java.util.regex.Pattern;

public class NieNif {

    private String nif;

    private String normalizedNif = null;
    private String formattedNif = null;

    private String firstLetterNIF;
    private String numbersNIF;
    private String lastLetterNIF;

    private final Pattern dniPattern = Pattern.compile("\\d{8}[[A-H][J-N][P-T][V-Z]]");
    private final Pattern niePattern = Pattern.compile("[XYZ]\\d{7}[[A-H][J-N][P-T][V-Z]]");
    private final Pattern otherNifPatternFinalNumber = Pattern.compile("[[A-H]JUV]\\d{8}");
    private final Pattern otherNifPatternFinalLetter = Pattern.compile( 	"[N[P-S]UV]\\d{7}[A-J]");

    private final String[] finalLetterOfNIFDNI = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};

    private final String ONLY_NUMBERS_CONTROL = "ABEH";
    private final String ONLY_LETTERS_CONTROL = "KPQS";
    private final String NUMBER_TO_LETTER_CONTROL = "JABCDEFGHI";

    public NieNif(String nif) {
        this.nif = nif;
    }

    public String getNif() {
        return nif;
    }

    @Override
    public String toString(){
        return nif;
    }


    public Boolean validateNieNif(){
        this.normalizedNif = this.nif.toUpperCase();

        /* Pattern DNI */
        if(dniPattern.matcher(normalizedNif).matches()){
            this.firstLetterNIF = null;
            this.numbersNIF = normalizedNif.substring(0,8);
            this.lastLetterNIF = normalizedNif.substring(8,9);
            if(validateAsDni(normalizedNif)){
                formattedNif = numbersNIF.substring(0,2) + "." + numbersNIF.substring(2, 5) + "." + numbersNIF.substring(5, 8) + "-" + lastLetterNIF;
                return true;
            }

            return false;
        }

        /* Pattern NIE */
        if(niePattern.matcher(normalizedNif).matches()){
            this.firstLetterNIF = normalizedNif.substring(0,1);
            this.numbersNIF = normalizedNif.substring(1,8);
            this.lastLetterNIF = normalizedNif.substring(8,9);

            if(validateAsNie(normalizedNif)){
                formattedNif = firstLetterNIF + "-" + numbersNIF.substring(0,1) + "." + numbersNIF.substring(1, 4) + "." + numbersNIF.substring(4, 7) + "-" + lastLetterNIF;
                return true;
            }

            return false;
        }

        /* Pattern other NIF final number*/
        if(otherNifPatternFinalNumber.matcher(normalizedNif).matches()){
            this.firstLetterNIF = normalizedNif.substring(0,1);
            this.numbersNIF = normalizedNif.substring(1,8);
            this.lastLetterNIF = normalizedNif.substring(8,9); // En este caso, 'lastLetterNIF' es un número.

            if(validateAsOtherNif(normalizedNif)){
                formattedNif = firstLetterNIF + "-" + numbersNIF.substring(0,2) + "." + numbersNIF.substring(2, 5) + "." + numbersNIF.substring(5, 7) + lastLetterNIF;
                return true;
            }

            return false;
        }

        /* Pattern other NIF final letter*/
        if(otherNifPatternFinalLetter.matcher(normalizedNif).matches()){
            this.firstLetterNIF = normalizedNif.substring(0,1);
            this.numbersNIF = normalizedNif.substring(1,8);
            this.lastLetterNIF = normalizedNif.substring(8,9);

            if(validateAsOtherNif(normalizedNif)){
                formattedNif = firstLetterNIF + "-" + numbersNIF.substring(0,1) + "." + numbersNIF.substring(1, 4) + "." + numbersNIF.substring(4, 7) + "-" + lastLetterNIF;
                return true;
            }

            return false;
        }

        return false;
    }

    private Boolean validateAsDni(String nieNif){
        String lastLetterThisNif = nieNif.substring(8, 9);
        String numbersThisNif = nieNif.substring(0, 8);
        String calculatedLetter = finalLetterOfNIFDNI[Integer.parseInt(numbersThisNif) % 23];
        if(lastLetterThisNif.equals(calculatedLetter)){
            return true;
        }

        return false;
    }

    private Boolean validateAsNie(String nieNif){
        if(firstLetterNIF.equals("X")){
            nieNif = "0" + numbersNIF + lastLetterNIF;
        }else
        if(firstLetterNIF.equals("Y")){
            nieNif = "1" + numbersNIF + lastLetterNIF;
        }else
        if(firstLetterNIF.equals("Z")){
            nieNif = "2" + numbersNIF + lastLetterNIF;
        }

        if(validateAsDni(nieNif)){
            return true;
        }

        return false;
    }

    public boolean validateAsOtherNif(String otherNif) {
        try {
            int parA = 0;
            for (int i = 2; i < 8; i += 2) {
                final int digit = Character.digit(otherNif.charAt(i), 10);
                if (digit < 0) {
                    return false;
                }
                parA += digit;
            }

            int nonB = 0;
            for (int i = 1; i < 9; i += 2) {
                final int digit = Character.digit(otherNif.charAt(i), 10);
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
            final char initialLetter = otherNif.charAt(0);
            final char finalCharacter = otherNif.charAt(8);

            return (ONLY_NUMBERS_CONTROL.indexOf(initialLetter) < 0 && NUMBER_TO_LETTER_CONTROL.charAt(digitD) == finalCharacter)
                    ||
                    // ¿el caracter de control es válido como dígito?
                    (ONLY_LETTERS_CONTROL.indexOf(initialLetter) < 0 && digitD == Character.digit(finalCharacter, 10));

        } catch (Exception e) {
            return false;
        }
    }

    public String formattedAsNIF(){

       return formattedNif;
    }
}
