package gmoldes.domain.client.persistence.dao;


import gmoldes.domain.client.persistence.vo.ClientVO;
import gmoldes.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.TypedQuery;
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

//    public ClientVO findClientById(Integer id){
//
//        Query query = session.createQuery(FIND_PERSON_BY_ID);
//        query.setParameter("code", id);
//        ClientVO clientVO =  (ClientVO) query.getSingleResult();
//
//        return clientVO;
//    }
//
//    public List<ClientVO> findAllPersonInAlphabeticalOrder(){
//
//        Query query = session.createQuery(FIND_ALL_PERSON_IN_ALPHABETICAL_ORDER);
//        List<ClientVO> personVOList = query.getResultList();
//
//        return personVOList;
//    }

    public List<ClientVO> findAllActiveClientsInAlphabeticalOrder(){

        TypedQuery<ClientVO> query = session.createNamedQuery(ClientVO.FIND_ALL_ACTIVE_CLIENTS_IN_ALPHABETICAL_ORDER, ClientVO.class);

        return query.getResultList();
    }

    public List<ClientVO> findAllActiveClientsByNamePatternInAlphabeticalOrder(String nameLetters){

        TypedQuery<ClientVO> query = session.createNamedQuery(ClientVO.FIND_ALL_ACTIVE_CLIENTS_BY_NAME_PATTERN_IN_ALPHABETICAL_ORDER, ClientVO.class);
        query.setParameter("code", "%" + nameLetters.toLowerCase() + "%");

        return query.getResultList();
    }


//    public Integer findPersonBySameName(ClientVO personVO){
//
//        List<ClientVO> personVOList;
//        Integer personId = null;
//
//        Query query = session.createQuery(FIND_PERSON_BY_STRICT_NAME)
//            .setParameter("code1", personVO.getApellido1())
//            .setParameter("code2", personVO.getApellido2())
//            .setParameter("code3", personVO.getNombre());
//
//            personVOList = query.getResultList();
//            for(ClientVO pVO : personVOList){
//                personId = pVO.getId();
//            }
//        return personId;
//    }

//    public Session getSession() {
//        return session;
//    }
//
//    public void setSession(Session session) {
//        this.session = session;
//    }

}
