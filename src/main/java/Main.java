import com.lofitskyi.entity.User;
import com.lofitskyi.service.PersistentException;
import com.lofitskyi.service.RoleDao;
import com.lofitskyi.service.UserDao;
import com.lofitskyi.service.hibernate.HibernateRoleDao;
import com.lofitskyi.service.hibernate.HibernateUserDao;

import java.util.List;

public class Main {

    public static void main(String[] args) throws PersistentException {
        UserDao dao = new HibernateUserDao();
        RoleDao roleDao = new HibernateRoleDao();
        final List<User> all = dao.findAll();
        for (User u: all){
            System.out.println(u.getRole().getName());
        }

        System.out.println(roleDao.findByName("user").getName());
        System.out.println(roleDao.findByName("user").getId());
        System.out.println(dao.findByEmail("edosgk@gmail.com").getLastName());
        System.out.println(dao.findByLogin("user1").getLastName());
    }
}
