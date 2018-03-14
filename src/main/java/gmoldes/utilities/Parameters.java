package gmoldes.utilities;

import javafx.scene.input.KeyCode;

import java.time.Duration;

public class Parameters {

    /** Printer */
    public static final String DEFAULT_PRINTER = "KONICA MINOLTA";


    /** Contract data */
    public static final String DATE_TEXT = "Fecha";
    public static final String HOURS_WORK_WEEK_TEXT = "Horas de trabajo por semana";
    public static final Duration LEGAL_MAXIMUM_HOURS_OF_WORK_PER_WEEK = Duration.parse("PT40H"); // 40 hours of work per week.
    public static final String NORMAL_CONTRACT_TYPE_DESCRIPTION = "NORMAL";
    public static final String ORDINARY_CONTRACT_TYPE_DESCRIPTION = "ORDINARIO";

    /** Various */
    public static final Integer MAXIMUM_VALUE_MINUTES_IN_HOUR = 59;

}
