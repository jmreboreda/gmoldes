package gmoldes.domain.client.persistence.dao;


import gmoldes.domain.client.persistence.vo.ClientVO;
import gmoldes.domain.client.persistence.vo.ClientVOOk;
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

    public Integer createClient(ClientVOOk clientVO) {

        try {
            session.beginTransaction();
            session.saveOrUpdate(clientVO);
            session.getTransaction().commit();
        }
        catch (org.hibernate.exception.ConstraintViolationException cve){

        }
            return clientVO.getId();
    }

    public ClientVOOk findClientById(Integer clientId){

        TypedQuery<ClientVOOk> query = session.createNamedQuery(ClientVOOk.FIND_CLIENT_BY_CLIENT_ID, ClientVOOk.class);
        query.setParameter("clientId", clientId);

        return query.getSingleResult();
    }

    public List<ClientVOOk> findAllActiveClientsInAlphabeticalOrder(){

        TypedQuery<ClientVOOk> query = session.createNamedQuery(ClientVOOk.FIND_ALL_ACTIVE_CLIENTS_IN_ALPHABETICAL_ORDER, ClientVOOk.class);

        return query.getResultList();
    }

    public List<ClientVOOk> findAllActiveClientsByNamePatternInAlphabeticalOrder(String nameLetters){

        TypedQuery<ClientVOOk> query = session.createNamedQuery(ClientVOOk.FIND_ALL_ACTIVE_CLIENTS_BY_NAME_PATTERN_IN_ALPHABETICAL_ORDER, ClientVOOk.class);
        query.setParameter("lettersOfName", "%" + nameLetters.toLowerCase() + "%");

        return query.getResultList();
    }

    public List<ClientVOOk> findAllClientGMWithInvoiceInForceInPeriod(LocalDate initialDate, LocalDate finalDate){

        TypedQuery<ClientVOOk> query = session.createNamedQuery(ClientVOOk.FIND_ALL_CLIENT_WITH_INVOICES_TO_BE_REQUIRED_IN_PERIOD, ClientVOOk.class);
        Date initialDateOfPeriod = Date.valueOf(initialDate);
        Date finalDateOfPeriod = Date.valueOf(finalDate);

//        query.setParameter("initialDate", initialDateOfPeriod);
        query.setParameter("finalDate", finalDateOfPeriod);


        return query.getResultList();
    }
}
