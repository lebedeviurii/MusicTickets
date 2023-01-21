package mt.dao;


import mt.model.Ticket;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class TicketDao extends BaseDao<Ticket>{
    public TicketDao() {
        super(Ticket.class);
    }

    public List<Ticket> findAllByOwner(Long user_id){
        return em.createNamedQuery("Ticket.findByOwner", Ticket.class).setParameter("user_id", user_id).getResultList();
    }
    public List<Ticket> findAllByEvent(Long event_id){
        return em.createNamedQuery("Ticket.findByEvent", Ticket.class).setParameter("event_id", String.valueOf(event_id)).getResultList();
    }
}
