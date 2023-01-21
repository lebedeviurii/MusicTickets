package mt.service;

import mt.dao.PostDao;
import mt.model.Band;
import mt.model.Event;
import mt.model.Post;
import mt.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    private final PostDao dao;

    @Autowired
    public PostService(PostDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public Post find(Long id) {
        return dao.find(id);
    }

    @Transactional
    public void persist(Post post) {
        dao.persist(post);
    }

    @Transactional
    public void update(Post post) {
        dao.update(post);
    }

    public List<Post> findAll() {
        return dao.findAll();
    }

    public List<Post> findAllByEvent(Event byId) {
        return dao.findAllByEvent(byId.getId());
    }

    public void remove(Post toRemove) {
        dao.remove(toRemove);
    }
}
