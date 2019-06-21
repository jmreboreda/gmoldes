package gmoldes.components.contract_documentation_control;

public class ContractDocumentationControlConstants {

    public static final String BLUE_COLOR = "-fx-text-inner-color: #000FFF;";
    public static final String RED_COLOR = "-fx-text-inner-color: #640000;";

    public static final Integer RECEPTION_FROM_MANAGER_COLUMN = 1;
    public static final Integer DELIVERY_TO_CLIENT_COLUMN = 2;

    public static final Integer IDC_ROW = 0;
    public static final Integer DELIVERY_DOCUMENTS_ROW = 1;
    public static final Integer CONTRACT_END_NOTICE_ROW = 2;
    public static final Integer LAST_CODE_FOR_INITIAL_CONTRACT = 199;
    public static final Integer CODE_FOR_CONTRACT_EXTENSION = 220;




    public static final String TRACEABILITY_MODIFICATION_SAVED_OK = "Las modificaciones de la trazabilidad se han guardado correctamente en la base de datos.";;
    public static final String TRACEABILITY_MODIFICATION_SAVED_NOT_OK = "No se han podido guardar en la base de datos las modificaciones de la trazabilidad.";;
    public static final String INEM_NUMBER_MODIFICATION_SAVED_OK = "El número de identificación del contrato del Servicio de Empleo se ha registrado correctamente.";
    public static final String INEM_NUMBER_MODIFICATION_NOT_SAVED_OK = "Problemas al guardar el número de identificación del contrato del Servicio de Empleo.";

    public static final String PERSON_SAVED_OK = "Persona guardada correctamente en la base de datos.";
    public static final String PERSON_NOT_SAVED_OK = "ERROR!\n\nNo se ha podido guardar la persona en la base de datos.";
    public static final String PERSON_MODIFICATION_SAVED_OK = "Las modificaciones de la persona se han guardado correctamente en la base de datos.";
    public static final String PERSON_MODIFICATION_NOT_SAVED_OK = "ERROR!\n\nNo se han podido guardar las modificaciones de la persona en la base de datos.";
    public static final String QUESTION_IS_CORRECT_REPEATED_NIE_NIF = "El NIE/NIF introducido ya existe en la tabla de personas para:\n\n";
    public static final String IS_NOT_CORRECT_REPEATED_NASS = "El NASS introducido ya existe en la tabla de personas para:\n\n";

    public static final String OPTION_NOT_IMPLEMENTED_YET = "Opción aún no implementada.";

    public static final String TOOLTIP_NORMALIZE_TEXT = "Cuando está activado, pone en mayúsculas las letras iniciales de las palabras contenidas en los campos del formulario al" +
            "pulsar el botón \"Aceptar\"";

}
