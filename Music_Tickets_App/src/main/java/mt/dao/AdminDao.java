package mt.dao;

import mt.model.Admin;
import mt.model.User;
import org.springframework.stereotype.Repository;
import javax.persistence.NoResultException;

@Repository
public class AdminDao extends BaseDao<Admin>{

    public AdminDao(){
         super(Admin.class);
    }

    public User findByEmail(String email) {
        try {
            return em.createNamedQuery("User.findByEmail", Admin.class)
                    .setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
