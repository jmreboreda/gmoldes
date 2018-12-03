package gmoldes.components.contract.new_contract.components;

import java.time.Duration;

public class ContractConstants {

    /** Contract data */
    public static final String FULL_WORKDAY = "A tiempo completo";
    public static final String PARTIAL_WORKDAY = "A tiempo parcial";
    public static final String UNDEFINED_DURATION_TEXT = "Indefinido";
    public static final String TEMPORAL_DURATION_TEXT = "Temporal";
    public static final String HOURS_WORK_WEEK_TEXT = " Horas de trabajo por semana";
    public static final Duration LEGAL_MAXIMUM_HOURS_OF_WORK_PER_WEEK = Duration.parse("PT40H"); // 40 hours of work per week.
    public static final String NOT_REVISED = " No revisado.";
    public static final String REVISION_WITH_ERRORS = " Revisado. Con errores o datos pendientes.";
    public static final String REVISION_WITHOUT_ERRORS = " Revisado. Sin errores ni datos pendientes.";
    public static final String CONTRACT_DATA_SUBFOLFER_TO_PRINTER_OK = "Se ha enviado a la impresora la carpetilla de datos del contrato.";
    public static final String SUBFOLFER_RECORD_OF_CONTRACT_HISTORY_TO_PRINTER_OK = "Se ha enviado a la impresora la carpetilla de expediente del contrato.";

    /** Contract schedule */
    public static final Integer FIRST_ROW_SCHEDULE_TABLE = 0;
    public static final Integer LAST_ROW_SCHEDULE_TABLE = 6;
    public static final String DAY_OF_WEEK_COLUMN_TITLE = "Día de la\n semana";
    public static final String TIME_AM_FROM_COLUMN_TITLE = "Mañana desde";
    public static final String TIME_AM_TO_COLUMN_TITLE = "Mañana hasta";
    public static final String TIME_PM_FROM_COLUMN_TITLE = "Tarde desde";
    public static final String TIME_PM_TO_COLUMN_TITLE = "Tarde hasta";
    public static final String TIME_DURATION_WORK_DAY_COLUMN_TITLE = "Jornada:\nHoras de\nduración";

    /** Contract forms*/
    public static final String CONTRACT_SUBFOLDER_RECORD_HISTORY_TEXT = "Expediente_contrato_trabajo_";

    /** Contract variation **/
    public static final String IS_NOT_VALID_DATE_FOR_CONTRACT_VARIATION = "Fecha no válida para efectuar variaciones en el contrato.";
    public static final String CONTRACT_EXTINCTION_PERSISTENCE_OK = "Extincion del contrato registrada correctamente.";
    public static final String CONTRACT_EXTINCTION_PERSISTENCE_NOT_OK = "No se ha registrado correctamente la extincion del contrato.\n" +
            "Debe revisarse la base de datos para garantizar la consistencia de los datos.";
    public static final String DATE_NOTIFICATION_NOT_ESTABLISHED = "Falta la fecha de notificación del cliente.";
    public static final String HOUR_NOTIFICATION_NOT_ESTABLISHED = "Falta la hora de notificación del cliente.";
    public static final String EXTINCTION_CAUSE_NOT_ESTABLISHED = "No se ha establecido la causa de extinción del contrato.";
    public static final String ERROR_IN_EXTINCTION_DATA = "La fecha de extinción del contrato es errónea.";
    public static final String HOLIDAYS_SITUATION_NOT_ESTABLISHED = "No se ha establecido la situación de disfrute de las vacaciones.";
    public static final String EXTINCTION_DATE_EXCEEDED = "No se puede extinguir un contrato en una fecha posterior a su fecha de extinción prevista anteriormente.";


}
