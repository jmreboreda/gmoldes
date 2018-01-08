package gmoldes.persistence.dao;


import gmoldes.persistence.vo.ClientVO;
import gmoldes.persistence.vo.PersonVO;
import gmoldes.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class PersonDAO {

    private SessionFactory sessionFactory;
    private Session session;

    public static String FIND_PERSON_BY_ID = "FROM PersonVO WHERE id = :code";
    public static String FIND_ALL_PERSON_IN_ALPHABETICAL_ORDER = "SELECT id, apellido1, apellido2, nombre FROM PersonVO ORDER BY apellido1, apellido2, nombre";
//    public static String FIND_ALL_PERSON_BY_NAME_PATTERN_IN_ALPHABETICAL_ORDER =
//            "FROM PersonVO WHERE LOWER(apellido1) LIKE :code OR LOWER(apellido2) LIKE :code OR LOWER(nombre) LIKE :code ORDER BY apellido1, apellido2, nombre";
    public static String FIND_PERSON_BY_STRICT_NAME = "FROM PersonVO WHERE apellido1 = :code1 AND apellido2 = :code2 AND nombre = :code3";

    public PersonDAO() {
    }

     public static class PersonDAOFactory {

        private static PersonDAO personDAO;

        public static PersonDAO getInstance() {
            if(personDAO == null) {
                personDAO = new PersonDAO(HibernateUtil.retrieveGlobalSession());
            }
            return personDAO;
        }

    }

    public PersonDAO(Session session) {
        this.session = session;
    }

    public Integer createPerson(PersonVO personVO) {

        try {
            session.beginTransaction();
            session.saveOrUpdate(personVO);
            session.getTransaction().commit();
        }
        catch (org.hibernate.exception.ConstraintViolationException cve){

        }
            return personVO.getIdpersona();
    }

    public PersonVO findPersonById(Integer id){

        TypedQuery<PersonVO> query = session.createNamedQuery(PersonVO.FIND_PERSON_BY_ID, PersonVO.class);
        query.setParameter("code", id);

        return (PersonVO) query.getSingleResult();
    }

    public PersonVO findClientById(Integer id){

        Query query = session.createQuery(FIND_PERSON_BY_ID);
        query.setParameter("code", id);

        return (PersonVO) query.getSingleResult();
    }
     
    public List<ClientVO> findAllPersonInAlphabeticalOrder(){
        
        Query query = session.createQuery(FIND_ALL_PERSON_IN_ALPHABETICAL_ORDER);

        return query.getResultList();
    }

    public List<PersonVO> findAllPersonsByNamePatternInAlphabeticalOrder(String nameLetters){

        TypedQuery<PersonVO> query = session.createNamedQuery(PersonVO.FIND_ALL_PERSONS_BY_NAME_PATTERN_IN_ALPHABETICAL_ORDER, PersonVO.class);
        query.setParameter("code", "%" + nameLetters.toLowerCase() + "%");

        return query.getResultList();
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

}
