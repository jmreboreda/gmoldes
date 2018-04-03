package gmoldes.domain.client.persistence.dao;


import gmoldes.domain.client.persistence.vo.ClientCCCVO;
import gmoldes.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class ClientCCCDAO {

    private SessionFactory sessionFactory;
    private Session session;

    public ClientCCCDAO() {
    }

     public static class ClientCCCDAOFactory {

        private static ClientCCCDAO clientCCCDAO;

        public static ClientCCCDAO getInstance() {
            if(clientCCCDAO == null) {
                clientCCCDAO = new ClientCCCDAO(HibernateUtil.retrieveGlobalSession());
            }
            return clientCCCDAO;
        }
    }

    public ClientCCCDAO(Session session) {
        this.session = session;
    }

    public List<ClientCCCVO> findAllCCCByClientId(Integer id){

        Query query = session.createNamedQuery(ClientCCCVO.FIND_ALL_CCC_BY_CLIENTID, ClientCCCVO.class);
        query.setParameter("code", id);

        return (List<ClientCCCVO>) query.getResultList();
    }
}
