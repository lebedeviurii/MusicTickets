package mt.model;



import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@NamedQuery(name = "Post.findByEvent", query = "SELECT p from Post p WHERE 'event_id' = :event_id order by p.title asc ")
public class Post {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "title", nullable = false)
    private String title;

    @Basic(optional = false)
    @Column(name = "post_body", nullable = false)
    private String postBody;

    @ManyToOne
    @JoinColumn(name = "event")
    private Event event;

    @ManyToMany
    private Map<LocalDateTime, Admin> changes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public void addChange(Admin admin){
        changes.put(java.time.LocalDateTime.now(), admin);
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Post() {
        changes = new HashMap<>();
    }

    public Post(String title, String postBody, Admin admin) {
        this.title = title;
        this.postBody = postBody;
        changes = new HashMap<>();
        changes.put(LocalDateTime.now(), admin);
    }
}
