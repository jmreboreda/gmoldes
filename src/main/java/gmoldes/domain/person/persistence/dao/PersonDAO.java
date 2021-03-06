package gmoldes.domain.person.persistence.dao;


import gmoldes.domain.person.persistence.vo.PersonVO;
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
    public static String FIND_ALL_PERSON_IN_ALPHABETICAL_ORDER = "FROM PersonVO ORDER BY apellidos, nom_rzsoc";

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

    public Integer updatePerson(PersonVO personVO){
        PersonVO personReadVO = null;
        try {
            session.beginTransaction();
            personReadVO = session.get(PersonVO.class, personVO.getIdpersona());
            personReadVO.setIdpersona(personVO.getIdpersona());
            personReadVO.setApellidos(personVO.getApellidos());
            personReadVO.setNom_rzsoc(personVO.getNom_rzsoc());
            personReadVO.setNifcif(personVO.getNifcif());
            personReadVO.setNifcifdup(personVO.getNifcifdup());
            personReadVO.setNumafss(personVO.getNumafss());
            personReadVO.setFechanacim(personVO.getFechanacim());
            personReadVO.setEstciv(personVO.getEstciv());
            personReadVO.setDireccion(personVO.getDireccion());
            personReadVO.setLocalidad(personVO.getLocalidad());
            personReadVO.setCodpostal(personVO.getCodpostal());
            personReadVO.setNivestud(personVO.getNivestud());
            personReadVO.setNacionalidad(personVO.getNacionalidad());
            session.update(personReadVO);
            session.getTransaction().commit();
        }
        catch (Exception e){
            System.err.println("No se han podido actualizar las modificaciones de la persona en \"person\": " + e.getMessage());
        }

        return personVO.getIdpersona();
    }

    public PersonVO findPersonById(Integer id){

        TypedQuery<PersonVO> query = session.createNamedQuery(PersonVO.FIND_PERSON_BY_ID, PersonVO.class);
        query.setParameter("code", id);

        return (PersonVO) query.getSingleResult();
    }

    public List<PersonVO> findPersonByNieNif(String nieNif){
        TypedQuery<PersonVO> query = session.createNamedQuery(PersonVO.FIND_PERSON_BY_NIE_NIF, PersonVO.class);
        query.setParameter("nieNif", nieNif);

        return query.getResultList();
    }

    public List<PersonVO> findPersonByNass(String nass){
        TypedQuery<PersonVO> query = session.createNamedQuery(PersonVO.FIND_PERSON_BY_NASS, PersonVO.class);
        query.setParameter("nass", nass);

        return query.getResultList();
    }

    public PersonVO findClientById(Integer id){

        Query query = session.createQuery(FIND_PERSON_BY_ID);
        query.setParameter("code", id);

        return (PersonVO) query.getSingleResult();
    }
     
    public List<PersonVO> findAllPersonInAlphabeticalOrder(){
        
        Query query = session.createQuery(FIND_ALL_PERSON_IN_ALPHABETICAL_ORDER);

        return query.getResultList();
    }

    public List<PersonVO> findAllPersonsByNamePattern(String nameLetters){

        TypedQuery<PersonVO> query = session.createNamedQuery(PersonVO.FIND_ALL_PERSONS_BY_NAME_PATTERN, PersonVO.class);
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
