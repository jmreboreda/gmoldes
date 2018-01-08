package gmoldes.utilities;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {
    
    private static SessionFactory sessionFactory;
    
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
         
        return sessionFactory;
    }
    
    private static Session session;
    
    public static Session retrieveGlobalSession() {
        if(session == null) {
            session = getSessionFactory().openSession();
        }
        return session;
    }
    
}
