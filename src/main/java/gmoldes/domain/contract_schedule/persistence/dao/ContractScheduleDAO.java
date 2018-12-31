package gmoldes.domain.contract_schedule.persistence.dao;

import gmoldes.domain.contract_schedule.persistence.vo.ContractScheduleVO;
import gmoldes.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ContractScheduleDAO {

    private SessionFactory sessionFactory;
    private Session session;

    public static class ContractScheduleDAOFactory {

        private static ContractScheduleDAO contractScheduleDAO;

        public static ContractScheduleDAO getInstance() {
            if(contractScheduleDAO == null) {
                contractScheduleDAO = new ContractScheduleDAO(HibernateUtil.retrieveGlobalSession());
            }
            return contractScheduleDAO;
        }
    }

    public ContractScheduleDAO(Session session) {
        this.session = session;
    }

    public Integer create(ContractScheduleVO contractScheduleVO) {

        try {
            session.beginTransaction();
            session.saveOrUpdate(contractScheduleVO);
            session.getTransaction().commit();
        }
        catch (Exception e){
            System.err.println("No se ha podido guardar el horario del contrato: " + e.getMessage());
            System.out.println(e);
        }

        return contractScheduleVO.getId();
    }
}
