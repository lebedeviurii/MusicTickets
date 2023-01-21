package mt.dao;

import mt.model.LoggedUser;
import org.springframework.stereotype.Repository;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Objects;

@Repository
public class LoggedUserDao extends BaseDao<LoggedUser>{
    public LoggedUserDao(){
        super(LoggedUser.class);
    }

    @Override
    public LoggedUser find(Long id) {
        Objects.requireNonNull(id);
        return em.find(LoggedUser.class, id);
    }

    public LoggedUser update(LoggedUser user, Long id) {
        Objects.requireNonNull(user);
        try {
            var e = find(id);
            e = em.merge(user);
            return e;
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void remove(LoggedUser user) {
        Objects.requireNonNull(user);
        try {
            em.remove(user);
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<LoggedUser> findAll(){
        return em.createQuery("SELECT '*' FROM LoggedUser").getResultList();
    }

    public List<LoggedUser> findAllByName(String name, String surname){
        return em.createNamedQuery("LoggedUser.findByNameSurname", LoggedUser.class)
                .setParameter("name", name).setParameter("surname", surname).getResultList();
    }
}
