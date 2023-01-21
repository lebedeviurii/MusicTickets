package mt.dao;

import mt.model.Event;
import org.springframework.stereotype.Repository;
import javax.persistence.PersistenceException;
import java.util.Objects;

@Repository
public class EventDao extends BaseDao<Event>{

    public EventDao() {
        super(Event.class);
    }

    @Override
    public Event find(Long id) {
        Objects.requireNonNull(id);
        return em.find(Event.class, id);
    }

    public Event update(Event event, Long id) {
        Objects.requireNonNull(event);
        try {
            var e = find(id);
            e = em.merge(event);
            return e;
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void remove(Event event) {
        Objects.requireNonNull(event);
        try {
            if (event != null) {
                em.remove(event);
            }
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }
}
