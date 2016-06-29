import com.lofitskyi.entity.Role;
import com.lofitskyi.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Role role = new Role("MANAGER");
        session.save(role);
        session.getTransaction().commit();
        HibernateUtil.shutdown();
    }
}
