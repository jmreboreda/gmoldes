package gmoldes.utilities;

import javafx.scene.input.KeyCode;

import java.time.Duration;

public class Parameters {

    /** Printer */
    public static final String DEFAULT_PRINTER = "KONICA MINOLTA";
    public static final String NO_PRINTER_FOR_THESE_PARAMETERS = "No hay impresora para imprimir con los atributos indicados.";
    public static final String CONTRACT_DATA_SUBFOLFER_TO_PRINTER_OK = "Se ha enviado a la impresora la carpetilla de datos del contrato.";
    public static final String SUBFOLFER_RECORD_OF_CONTRACT_HISTORY_TO_PRINTER_OK = "Se ha enviado a la impresora la carpetilla de expediente del contrato.";

    /** System */
    public static final String OPERATING_SYSTEM = System.getProperty("os.name");
    public static final String USER_HOME = System.getProperty("user.home");

    /** Colors */
    public static final String GREEN_COLOR = "-fx-text-fill: #006400;";
    public static final String RED_COLOR ="-fx-text-fill: #c90c0c;";

    /** Contract data */
    public static final String FULL_WORKDAY = "A tiempo completo";
    public static final String PARTIAL_WORKDAY = "A tiempo parcial";
    public static final String UNDEFINED_DURATION_TEXT = "Indefinido";
    public static final String TEMPORAL_DURATION_TEXT = "Temporal";
    public static final String HOURS_WORK_WEEK_TEXT = "Horas de trabajo por semana";
    public static final Duration LEGAL_MAXIMUM_HOURS_OF_WORK_PER_WEEK = Duration.parse("PT40H"); // 40 hours of work per week.
    public static final String NOT_REVISED = " No revisado.";
    public static final String REVISION_WITH_ERRORS = " Revisado. Con errores o datos pendientes.";
    public static final String REVISION_WITHOUT_ERRORS = " Revisado. Sin errores ni datos pendientes.";

    /** Contract schedule */
    public static final Integer FIRST_ROW_SCHEDULE_TABLE = 0;
    public static final Integer LAST_ROW_SCHEDULE_TABLE = 6;
    public static final String DAY_OF_WEEK_TEXT = "Día de la\n semana";
    public static final String TIME_AM_FROM_TEXT = "Mañana desde";
    public static final String TIME_AM_TO_TEXT = "Mañana hasta";
    public static final String TIME_PM_FROM_TEXT = "Tarde desde";
    public static final String TIME_PM_TO_TEXT = "Tarde hasta";
    public static final String TIME_DURATION_WORK_DAY_TEXT = "Jornada:\nHoras de\nduración";

    /** Messages */
    public static final String SYSTEM_INFORMATION_TEXT = "Información del sistema";
    public static final String EXCEEDED_MAXIMUM_LEGAL_WEEKLY_HOURS = "Excedido el máximo de horas de la semana laboral legal.";
    public static final String SCHEDULE_HOURS_GREATER_THAN_CONTRACT_HOURS = "El total de horas de la pestaña \"Horario\" es mayor que el total de horas de la pestaña \"Contrato\".";
    public static final String CONTRACT_HOURS_DIFFERENT_FROM_SCHEDULE_HOURS = "El total de horas de la pestaña \"Contrato\" es distinto que el total de horas de la pestaña \"Horario\".";

    public static final String EMPLOYER_IS_NOT_SELECTED = "Pestaña Empleador\\Empleado\nNo se ha seleccionado el cliente\\empleador.";
    public static final String EMPLOYEE_IS_NOT_SELECTED = "Pestaña Empleador\\Empleado\nNo se ha seleccionado el empleado.";
    public static final String QUESTION_NULL_CCC_CODE_IS_CORRECT = "Pestaña Empleador\\Empleado\nEl CCC no está disponible o no ha sido seleccionado. ¿Es correcto?";
    public static final String HOUR_NOTIFICATION_IS_NOT_ESTABLISHED = "Pestaña \"Contrato\"\nRevise los datos de notificación.\nHora no establecida.";
    public static final String DIFFERENT_NUMBER_HOURS_CONTRACT_DATA_AND_CONTRACT_SCHEDULE = "Pestañas \"Contrato\" y \"Horario\"\nLos números de horas de trabajo por semana son distintos " +
            "en la pestaña \"Contrato\" y en la pestaña \"Horario\".";
    public static final String DAYS_TO_WORK_ARE_NOT_SELECTED = "Pestaña \"Contrato\"\nNo se han indicado los días de trabajo de la semana.";
    public static final String LABOR_CATEGORY_IS_NOT_ESTABLISHED = "Pestaña \"Contrato\"\nNo se ha establecido la categoría laboral.";
    public static final String WORKING_DAYS_ARE_DIFFERENT_IN_CONTRACTDATA_AND_CONTRACTSCHEDULE = "Los días de semana a trabajar en la pestaña \"Contrato\" " +
            "son distintos de los de la pestaña \"Horario\".";
    public static final String QUESTION_SAVE_NEW_CONTRACT = "El contrato no tiene errores ni datos pendientes.\n¿Desea guardar el contrato en la base de datos?";
    public static final String INVALID_CONTRACT_DURATION = "El número de días de duración del contrato es erróneo.";
    public static final String CONTRACT_TYPE_NOT_SELECTED = "No se ha seleccionado el tipo de contrato.";
    public static final String CONTRACT_SAVED_OK = "Contrato guardado con el número ";
    public static final String CONTRACT_NOT_SAVED_OK = "El contrato no ha podido guardarse en la base de datos.";
    public static final String QUESTION_SEND_MAIL_TO_CONTRACT_AGENT = "¿Se envía correo-e de datos al gestor?";
    public static final String CONTRACT_SAVED_BUT_NOT_SENDED_TO_CONTRACT_AGENT = "Contrato guardado pero no enviado al gestor.\n ¿Es correcto?";
    public static final String TIME_RECORD_PDF_NOT_CREATED = "No se ha podido crear el PDF del registro horario.";
    /** Various */
    public static final String WINDOWS_TEMPORAL_DIR = "/AppData/Local/Temp/Borrame";
    public static final String LINUX_TEMPORAL_DIR = "/Temp/Borrame";

    public static final Integer MAXIMUM_VALUE_MINUTES_IN_HOUR = 59;
    public static final String DATE_LABEL_TEXT = "Fecha";
    public static final String DATE_FROM_TEXT = "Desde";
    public static final String DATE_TO_TEXT = "Hasta";
    public static final String DEFAULT_DATE_FORMAT = "dd-MM-yyyy";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm";
    public static final Integer ID_INITIAL_CONTRACT_TYPE_VARIATION = 100;

    /** ContractDataSubfolder*/
    public static final String NEW_CONTRACT_TEXT = "Nuevo contrato";





}
