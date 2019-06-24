package gmoldes.domain.types_contract_variations.persistence.dao;


import gmoldes.domain.types_contract_variations.persistence.vo.TypesContractVariationsVO;
import gmoldes.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

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

    public List<TypesContractVariationsVO> findAllTypesContractVariations(){
        Query query = session.createNamedQuery(TypesContractVariationsVO.FIND_ALL_TYPES_CONTRACT_VARIATIONS, TypesContractVariationsVO.class);

        return query.getResultList();
    }

    public TypesContractVariationsVO findVariationDescriptionById(Integer id){

        Query query = session.createNamedQuery(TypesContractVariationsVO.FIND_TYPES_CONTRACT_VARIATIONS_BY_ID, TypesContractVariationsVO.class);
        query.setParameter("code", id);

        return (TypesContractVariationsVO) query.getSingleResult();
    }

    public TypesContractVariationsVO findTypesContractVariationsById(Integer id){

        Query query = session.createNamedQuery(TypesContractVariationsVO.FIND_TYPES_CONTRACT_VARIATIONS_BY_ID, TypesContractVariationsVO.class);
        query.setParameter("code", id);

        return (TypesContractVariationsVO) query.getSingleResult();
    }
}
