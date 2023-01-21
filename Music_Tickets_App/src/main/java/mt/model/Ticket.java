package mt.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.Basic;
import javax.persistence.NamedQuery;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.math.BigDecimal;

@Entity
@NamedQuery(name = "Ticket.findByOwner", query = "SELECT t from Ticket t WHERE 'user_id' = :user_id ")
@NamedQuery(name = "Ticket.findByEvent", query = "SELECT t from Ticket t WHERE 'event_id' = :event_id order by t.price ASC ")
public class Ticket {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    private BigDecimal price;
    @Basic
    private String sector;

    @Basic
    private int seat;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private LoggedUser user;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public LoggedUser getUser() {
        return user;
    }

    public void setUser(LoggedUser user) {
        this.user = user;
    }

    public Ticket() {
    }

    public Ticket(BigDecimal price, int seat, String sector, Event event, LoggedUser user) {
        this.price = price;
        this.seat = seat;
        this.sector = sector;
        this.event = event;
        this.user = user;
    }
}
