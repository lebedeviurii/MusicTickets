package mt.dao;

import mt.model.Band;
import org.springframework.stereotype.Repository;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Objects;

@Repository
public class BandDao extends BaseDao<Band>{

    public BandDao() {
        super(Band.class);
    }

    @Override
    public Band find(Long id) {
        Objects.requireNonNull(id);
        return em.find(Band.class, id);
    }

    public Band update(Band band, Long id) {
        Objects.requireNonNull(band);
        try {
            var e = find(id);
            e = em.merge(band);
            return e;
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void remove(Band band) {
        Objects.requireNonNull(band);
        try {
                em.remove(band);
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    public List<Band> findAllByEvent(Long event_id) {
        return em.createNamedQuery("Band.findByEvent", Band.class).setParameter("event_id", event_id).getResultList();
    }
}
