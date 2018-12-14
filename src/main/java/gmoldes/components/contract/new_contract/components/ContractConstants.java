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
    public static final String VERIFY_IS_VALID_DATE_TO_NOTIFY_CONTRACT_VARIATION_TO_ADMINISTRATION = "La fecha establecida de variación del contrato aparentemente excede el plazo máximo" +
            " de  tres días para su notificación a la Administración laboral.\n ¿Es correcta la fecha establecida?.";
    public static final String CONTRACT_EXTINCTION_PERSISTENCE_OK = "Extincion del contrato registrada correctamente.";
    public static final String CONTRACT_EXTINCTION_PERSISTENCE_NOT_OK = "No se ha registrado correctamente la extincion del contrato.\n" +
            "Debe revisarse la base de datos para garantizar la consistencia de los datos.";
    public static final String DATE_NOTIFICATION_NOT_ESTABLISHED = "Falta la fecha de notificación del cliente.";
    public static final String HOUR_NOTIFICATION_NOT_ESTABLISHED = "Falta la hora de notificación del cliente.";
    public static final String EXTINCTION_CAUSE_NOT_ESTABLISHED = "No se ha establecido la causa de extinción del contrato.";
    public static final String ERROR_IN_EXTINCTION_DATA = "La fecha de extinción del contrato es errónea.";
    public static final String HOLIDAYS_SITUATION_NOT_ESTABLISHED = "No se ha establecido la situación de disfrute de las vacaciones.";
    public static final String EXTINCTION_DATE_EXCEEDED_BY_DATE_REQUESTED = "No se puede extinguir un contrato en una fecha posterior a su fecha de extinción prevista anteriormente.";
    public static final String  EXIST_FUTURE_VARIATION_OF_SELECTED_CONTRACT  = "Existen variaciones registradas en el futuro para el contrato seleccionado " +
            "con fecha posterior a la seleccionada.\nNo es posible registrar nuevas variaciones en la fecha seleccionada.";
    public static final String ERROR_UPDATING_LAST_CONTRACT_VARIATION_RECORD = "Error actualizando el último registro de \"contractvariation\" en la base de datos.";
    public static final String ERROR_INSERTING_NEW_EXTINCTION_RECORD_IN_CONTRACT_VARIATION = "Error creando nuevo registro de extinción de contrato en \"contractvariation\" en la base de datos.";
    public static final String ERROR_UPDATING_EXTINCTION_DATE_IN_INITIAL_CONTRACT = "Error actualizando la fecha de extinción del contrato en \"initialcontract\" en la base de datos.";
    public static final String CONTRACT_EXTINCTION_SAVED_BUT_NOT_SENDED_TO_CONTRACT_AGENT = "Se ha registrado la variación del contrato pero no enviado al gestor.\n ¿Es correcto?";
    // Prórrogas de contratos
    public static final String MAXIMUM_NUMBER_LEGALLY_PERMITTED_EXTENSIONS_IS_ALREADY_REGISTERED = "Ya están registradas el número máximo de prórrogas legalmente permitidas para este contrato.";
    public static final String MAXIMUM_LEGAL_NUMBER_OF_MONTHS_OF_CONTRACT_IS_EXCEEDED = "Con las fechas seleccionadas se sobrepasa el máximo número legal de meses de duración del contrato.";
    public static final String SELECTED_CONTRACT_IS_NOT_EXTENDABLE = "El contrato seleccionado no admite prórrogas.";
    public static final String UNDEFINED_DURATION_CONTRACTS_ARE_NOT_EXTENDABLE = "Los contratos indefinidos no son prorrogables.";
    public static final String THIS_TYPE_OF_DETERMINED_DURATION_CONTRACT_IS_NOT_EXTENDABLE = "Este tipo de contratos de duración determinada no es prorrogable.";
    public static final String ERROR_EXTENSION_CONTRACT_DATE_FROM = "La fecha de inicio de la prórroga es errónea.";
    public static final String ERROR_EXTENSION_CONTRACT_DATE_TO = "La fecha de finalización de la prórroga es errónea.";
    public static final String EXTENSION_DATE_EXCEEDED = "No se puede prorrogar un contrato desde una fecha posterior a su fecha de extinción prevista anteriormente.";
    public static final String INCORRECT_CONTRACT_EXTENSION_DATE_FROM = "La fecha de inicio de la prórroga es errónea.";
    public static final String CONTRACT_EXTENSION_PERSISTENCE_NOT_OK = "No se ha registrado correctamente la prórroga del contrato.\n" +
            "Debe revisarse la base de datos para garantizar la consistencia de los datos.";
    public static final String ERROR_EXTENSION_CONTRACT_INCOHERENT_DATES = "Las fechas de inicio y finalización de la prórroga no son coherentes.";
    public static final String NECESSARY_DATA_FOR_VARIATION_CONTRACT_HAVE_BEEN_INTRODUCED = "Se han introducido los datos de necesarios para la variación del contrato.";
    public static final String PERSIST_CONTRACT_VARIATION_QUESTION = "La variación de contrato está lista para ser registrada.\n ¿Desea proceder a su registro en la base de datos?";
    public static final String EXIST_PREVIOUS_CONTRACT_VARIATION_EXTINCTION = "Ya está registrada una extinción de contrato para este contrato.";
    public static final String INITIAL_DATE_EXTENSION_MUST_BE_IMMEDIATELY_AFTER_CONTRACT_EXPECTED_END_DATE = "La fecha inicial de la prórroga de un contrato" +
            " tiene que  ser la inmediatamente posterior a la de finalización establecida para el contrato.";
    public static final String EXIST_PREVIOUS_INCOMPATIBLE_CONTRACT_VARIATION_EXTENSION = "Está registrada una prórroga del contrato que es incompatible con la solicitada.";
    public static final String CONTRACT_EXTENSION_PERSISTENCE_OK = "Prórroga del contrato registrada correctamente.";
    public static final String CONTRACT_VARIATIONS_IN_THE_FUTURE_NOT_ALLOW_EXTINCTION_ON_REQUESTED_DATE = "Operaciones registradas con fecha posterior a la fecha de inicio solicitada" +
            " no permiten la extinción del contrato en la fecha solicitada.";
    public static final String ERROR_PERSISTING_TRACEABILITY_CONTROL_DATA = "Ha ocurrido un error al pesistir la trazabilidad del contrato en \"traceability_contract_documentation\".";

}
