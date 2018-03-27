package gmoldes.utilities;

import javafx.scene.input.KeyCode;

import java.time.Duration;

public class Parameters {

    /** Printer */
    public static final String DEFAULT_PRINTER = "KONICA MINOLTA";

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
    public static final String QUESTION_SEND_MAIL_TO_CONTRACT_AGENT = "¿Se envía correo-e de datos al gestor?";

    /** Various */
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
