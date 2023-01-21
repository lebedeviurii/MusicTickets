package mt.model;

import javax.persistence.*;

@Entity
@NamedQuery(name = "Band.findByEvent", query = "SELECT b from Band b WHERE 'event_id' = :event_id order by b.name")
public class Band {

    public Band(String name, String genre, String info) {
        this.name = name;
        this.genre = genre;
        this.info = info;
    }

    public Band() {
        this.name = null;
        this.genre = null;
        this.info = null;
    }

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private  Long id;

    @Basic(optional = false)
    @Column(name = "name", nullable = false)
    private String name;

    @Basic(optional = false)
    @Column(name = "genre", nullable = false)
    private String genre;

    @Basic(optional = false)
    @Column(name = "info", nullable = false)
    private String info;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
