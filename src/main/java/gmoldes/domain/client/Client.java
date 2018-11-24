package gmoldes.domain.client;

import gmoldes.domain.client.persistence.vo.ServiceGMVO;

import java.util.Date;
import java.util.Set;

public class Client {

    Integer id;
    Integer clientId;
    Boolean isNaturalPerson;
    Boolean isLegalPerson;
    String typeClient;
    String nifCif;
    Set<String> bussinessNames;
    String lastName1;
    String lastName2;
    String firstName;
    Set<ServiceGMVO> servicesGM;
    Set<String> quoteAccountCodes;
    Date dateFrom;
    Date dateTo;
    Date withoutActivity;

}
