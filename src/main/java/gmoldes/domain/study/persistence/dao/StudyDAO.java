package gmoldes.domain.study.persistence.dao;


import gmoldes.domain.study.persistence.vo.StudyVO;
import gmoldes.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class StudyDAO {

    private SessionFactory sessionFactory;
    private Session session;

    public StudyDAO() {
    }

     public static class StudyDAOFactory {

        private static StudyDAO studyDAO;

        public static StudyDAO getInstance() {
            if(studyDAO == null) {
                studyDAO = new StudyDAO(HibernateUtil.retrieveGlobalSession());
            }
            return studyDAO;
        }

    }

    public StudyDAO(Session session) {

        this.session = session;
    }

    public List findAllStudy(){
        Query query = session.createNamedQuery(StudyVO.FIND_ALL_STUDY, StudyVO.class);

        return query.getResultList();
    }

    public StudyVO findStudyById(Integer id){
        Query query = session.createNamedQuery(StudyVO.FIND_STUDY_BY_ID, StudyVO.class);
        query.setParameter("code", id);

        return (StudyVO) query.getSingleResult();
    }
}
