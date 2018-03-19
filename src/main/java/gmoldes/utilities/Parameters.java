package gmoldes.utilities;

import javafx.scene.input.KeyCode;

import java.time.Duration;

public class Parameters {

    /** Printer */
    public static final String DEFAULT_PRINTER = "KONICA MINOLTA";

    /** Contract data */
    public static final String FULL_WORKDAY = "A tiempo completo";
    public static final String PARTIAL_WORKDAY = "A tiempo parcial";
    public static final String UNDEFINED_DURATION = "Indefinido";
    public static final String HOURS_WORK_WEEK_TEXT = "Horas de trabajo por semana";
    public static final Duration LEGAL_MAXIMUM_HOURS_OF_WORK_PER_WEEK = Duration.parse("PT40H"); // 40 hours of work per week.
    public static final String NORMAL_CONTRACT_TYPE_DESCRIPTION = "NORMAL";
    public static final String ORDINARY_CONTRACT_TYPE_DESCRIPTION = "ORDINARIO";

    /** Contract schedule */
    public static final String DAY_OF_WEEK_TEXT = "Día de la\n semana";
    public static final String TIME_AM_FROM_TEXT = "Mañana desde";
    public static final String TIME_AM_TO_TEXT = "Mañana hasta";
    public static final String TIME_PM_FROM_TEXT = "Tarde desde";
    public static final String TIME_PM_TO_TEXT = "Tarde hasta";
    public static final String TIME_DURATION_WORK_DAY_TEXT = "Jornada:\nHoras de\nduración";



    /** Various */
    public static final Integer MAXIMUM_VALUE_MINUTES_IN_HOUR = 59;
    public static final String DATE_LABEL_TEXT = "Fecha";
    public static final String DATE_FROM_TEXT = "Desde";
    public static final String DATE_TO_TEXT = "Hasta";
    public static final String DEFAULT_DATE_FORMAT = "dd-MM-yyyy";

}
