package gmoldes.domain.client;

import gmoldes.domain.client.vo.ServiceGMVO;

import java.util.Date;
import java.util.Set;

public class Client {

    Integer id;
    String typeClient;
    String taxIdentificationNumber;
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
