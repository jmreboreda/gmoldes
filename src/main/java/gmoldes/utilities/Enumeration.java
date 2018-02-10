package gmoldes.utilities;

public class Enumeration {

    public enum Months {
        ENERO       ("enero"),
        FEBRERO     ("febrero"),
        MARZO       ("marzo"),
        ABRIL       ("abril"),
        MAYO        ("mayo"),
        JUNIO       ("junio"),
        JULIO       ("julio"),
        AGOSTO      ("agosto"),
        SEPTIEMBRE  ("septiembre"),
        OCTUBRE     ("octubre"),
        NOVIEMBRE   ("noviembre"),
        DICIEMBRE   ("diciembre");

        String month;

        Months(String p){
            month = p;
        }
        public String getMonthName() {
            return month;
        }
    }

    public enum TypeClients {
        PERSONA_FISICA      ("PF"),
        PERSONA_JURIDICA    ("PJ"),
        OTROS               ("OT");

        String typeClient;

        TypeClients(String p){
            typeClient = p;
        }
        public String getTypeClient() {
            return typeClient;
        }
    }

    public enum ServicesGM {
        ASESORIA_FISCAL         ("Asesoría fiscal"),
        CONTABILIDAD            ("Contabilidad"),
        REGISTRO_FACTURAS       ("Libros Registro IGI"),
        REGISTRO_PARA_IVA       ("Registro de IVA en Módulos"),
        ASESORIA_LABORAL        ("Asesoría laboral");

        String serviceGM;

        ServicesGM(String p){
            serviceGM = p;
        }
        public String getServiceGM() {
            return serviceGM;
        }
    }

}
