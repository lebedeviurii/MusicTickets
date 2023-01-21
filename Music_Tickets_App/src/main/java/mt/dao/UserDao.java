package mt.dao;

import mt.model.LoggedUser;
import mt.model.User;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class UserDao extends BaseDao<User>{

    @PersistenceContext
    private EntityManager em;


    public UserDao() {
        super(User.class);
    }

    public User findByEmail(String email) {
        try {
            return em.createNamedQuery("User.findByEmail", LoggedUser.class)
                    .setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
