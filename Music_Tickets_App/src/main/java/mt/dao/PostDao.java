package mt.dao;

import mt.model.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Objects;

@Repository
public class PostDao extends BaseDao<Post> {

    public PostDao() {
        super(Post.class);
    }


    @Override
    public Post find(Long id) {
        Objects.requireNonNull(id);
        return em.find(Post.class, id);
    }

    public Post update(Post post, Long id) {
        Objects.requireNonNull(post);
        try {
            var p = find(id);
            p = em.merge(post);
            return p;
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void remove(Post post) {
        Objects.requireNonNull(post);
        try {
            em.remove(post);
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    public List<Post> findAllByEvent(Long event_id) {
        return em.createNamedQuery("Post.findByEvent", Post.class).setParameter("event_id", event_id).getResultList();
    }
}
