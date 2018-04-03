package gmoldes.utilities;

public class Parameters {

    /** Printer */
    public static final String DEFAULT_PRINTER = "KONICA MINOLTA";
    public static final String NO_PRINTER_FOR_THESE_PARAMETERS = "No hay impresora para imprimir con los atributos indicados.";

    /** System */
    public static final String OPERATING_SYSTEM = System.getProperty("os.name");
    public static final String USER_HOME = System.getProperty("user.home");
    public static final String WINDOWS_TEMPORAL_DIR = "/AppData/Local/Temp/Borrame";
    public static final String LINUX_TEMPORAL_DIR = "/Temp/Borrame";


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
    public static final String NEW_CONTRACT_TEXT = "Nuevo contrato";

    /** Various */
    public static final Integer MAXIMUM_VALUE_MINUTES_IN_HOUR = 59;

}
