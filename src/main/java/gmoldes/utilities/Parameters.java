package gmoldes.utilities;

public class Parameters {

    /** Printer */
    public static final String DEFAULT_PRINTER = "KONICA MINOLTA";
    public static final String PRINTER_TRAY_OF_A3 = "Bandeja 3";
    public static final String NO_PRINTER_FOR_THESE_ATTRIBUTES = "No hay impresora para imprimir con los atributos indicados.";

    /** System */
    public static final String OPERATING_SYSTEM = System.getProperty("os.name");
    public static final String USER_HOME = System.getProperty("user.home");
    public static final String OS_LINUX = "linux";
    public static final String OS_WINDOWS = "windows";



    /** Colors */
    public static final String GREEN_COLOR = "-fx-text-fill: #006400;";
    public static final String RED_COLOR ="-fx-text-fill: #c90c0c;";

    /** Messages */
    public static final String SYSTEM_INFORMATION_TEXT = "Información del sistema";
    public static final String DATE_LABEL_TEXT = "Fecha";
    public static final String DATE_FROM_TEXT = "Desde";
    public static final String DATE_TO_TEXT = "Hasta";
    public static final String DEFAULT_DATE_FORMAT = "dd-MM-yyyy";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm";

    /** Various */
    public static final String PDF_EXTENSION = ".pdf";
    public static final Integer MAXIMUM_VALUE_MINUTES_IN_HOUR = 59;
    public static final Integer NUMBER_OF_SECONDS_IN_ONE_HOUR = 3600;

}
