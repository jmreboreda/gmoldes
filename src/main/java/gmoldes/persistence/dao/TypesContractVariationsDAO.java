package gmoldes.persistence.dao;


import gmoldes.persistence.vo.TypesContractVariationsVO;
import gmoldes.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;

public class TypesContractVariationsDAO {

    private SessionFactory sessionFactory;
    private Session session;

    public TypesContractVariationsDAO() {
    }

     public static class TypesContractVariationsDAOFactory {

        private static TypesContractVariationsDAO typesContractVariationsDAO;

        public static TypesContractVariationsDAO getInstance() {
            if(typesContractVariationsDAO == null) {
                typesContractVariationsDAO = new TypesContractVariationsDAO(HibernateUtil.retrieveGlobalSession());
            }
            return typesContractVariationsDAO;
        }

    }

    public TypesContractVariationsDAO(Session session) {

        this.session = session;
    }

    public TypesContractVariationsVO findVariationDescriptionById(Integer id){

        Query query = session.createNamedQuery(TypesContractVariationsVO.FIND_TYPES_CONTRACT_VARIATIONS_BY_ID, TypesContractVariationsVO.class);
        query.setParameter("code", id);

        return (TypesContractVariationsVO) query.getSingleResult();
    }
}
