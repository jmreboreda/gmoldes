package gmoldes.domain.client.persistence.dao;


import gmoldes.domain.client.persistence.vo.ClientVO;
import gmoldes.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class ClientDAO {

    private SessionFactory sessionFactory;
    private Session session;

    public ClientDAO() {
    }

     public static class ClientDAOFactory {

        private static ClientDAO clientDAO;

        public static ClientDAO getInstance() {
            if(clientDAO == null) {
                clientDAO = new ClientDAO(HibernateUtil.retrieveGlobalSession());
            }
            return clientDAO;
        }

    }

    public ClientDAO(Session session) {
        this.session = session;
    }

    public Integer createClient(ClientVO clientVO) {

        try {
            session.beginTransaction();
            session.saveOrUpdate(clientVO);
            session.getTransaction().commit();
        }
        catch (org.hibernate.exception.ConstraintViolationException cve){

        }
            return clientVO.getId();
    }

    public List<ClientVO> findClientById(Integer clientId){

        TypedQuery<ClientVO> query = session.createNamedQuery(ClientVO.FIND_CLIENT_BY_CLIENT_ID, ClientVO.class);
        query.setParameter("clientId", clientId);

        return query.getResultList();
    }

    public List<ClientVO> findAllActiveClientsInAlphabeticalOrder(){

        TypedQuery<ClientVO> query = session.createNamedQuery(ClientVO.FIND_ALL_ACTIVE_CLIENTS_IN_ALPHABETICAL_ORDER, ClientVO.class);

        return query.getResultList();
    }

    public List<ClientVO> findAllActiveClientsByNamePattern(String nameLetters){

        TypedQuery<ClientVO> query = session.createNamedQuery(ClientVO.FIND_ALL_ACTIVE_CLIENTS_BY_NAME_PATTERN, ClientVO.class);
        query.setParameter("lettersOfName", "%" + nameLetters.toLowerCase() + "%");

        return query.getResultList();
    }

    public List<ClientVO> findAllClientGMWithInvoiceInForceInPeriod(LocalDate initialDate, LocalDate finalDate){

        TypedQuery<ClientVO> query = session.createNamedQuery(ClientVO.FIND_ALL_CLIENT_WITH_INVOICES_TO_BE_REQUIRED_IN_PERIOD, ClientVO.class);
        Date initialDateOfPeriod = Date.valueOf(initialDate);
        Date finalDateOfPeriod = Date.valueOf(finalDate);

//        query.setParameter("initialDate", initialDateOfPeriod);
        query.setParameter("finalDate", finalDateOfPeriod);


        return query.getResultList();
    }
}
