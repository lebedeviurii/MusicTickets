package mt.dao;

import mt.MTApplication;
import mt.model.Admin;
import mt.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@ComponentScan(basePackageClasses = MTApplication.class)
public class BaseDaoTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private PostDao postDao;

    Admin admin;
    Post post;
    @BeforeEach
    public void setUp() {
        admin = new Admin();  // create new default admin
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setEmail("adminMTA@gmail.com");
        post = new Post("Title", "body", admin);
    }

    @Test
    public void persistAndFind_newPost_saveSpecifiedInstance() {
        postDao.persist(post);

        Post result = em.find(Post.class, post.getId());
        assertNotNull(result);
        assertEquals(post.getId(), result.getId());
        assertEquals(post.getTitle(), result.getTitle());
    }

    @Test
    public void update_newPost_updatedPost() {
        postDao.persist(post);

        final Post postUpdate = new Post("UpdatedTitle", "UpdatedBody", admin);
        postUpdate.setId(post.getId());
        postDao.update(postUpdate);

        String resultTitle = "UpdatedTitle";
        String resultBody = "UpdatedBody";

        assertNotNull(post);
        assertEquals(resultTitle, post.getTitle());
        assertEquals(resultBody, post.getPostBody());
    }

    @Test
    public void remove_newPost_noPost() {
        postDao.persist(post);
        assertNotNull(postDao.find(post.getId()));
        postDao.remove(post);
        assertNull(em.find(Post.class, post.getId()));
    }
}
