package mt.service;

import mt.dao.EventDao;
import mt.dao.TicketDao;
import mt.model.Event;
import mt.model.LoggedUser;
import mt.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class TicketService {
    private static int MaximumPerUser = 10;
    private final TicketDao ticketDao;

    private final EventDao eventDao;

    @Autowired
    public TicketService(TicketDao ticketDao, EventDao eventDao) {
        this.ticketDao = ticketDao;
        this.eventDao = eventDao;
    }

    @Transactional
    public Ticket create(Event event, BigDecimal price, String sector, int seat) {
        Objects.requireNonNull(event);
        final Ticket ticket = new Ticket(price, seat, sector, event, null);
        ticketDao.persist(ticket);
        return ticket;
    }

    @Transactional(readOnly = true)
    public Ticket find(Long id) {
        return ticketDao.find(id);
    }

    @Transactional
    public Ticket update(Ticket ticket, Long id){
        Objects.requireNonNull(ticket);
        return ticketDao.update(ticket);
    }

    @Transactional
    public void remove(Ticket ticket){
        Objects.requireNonNull(ticket);
        ticketDao.remove(ticket);
    }

    @Transactional
    public Ticket buy(LoggedUser user, Long id){
        Objects.requireNonNull(user);
        final var ticket = ticketDao.find(id);
        if (ticket.getEvent().isActiveForSelling() && !ticket.getEvent().getBand().isEmpty()){
            if (ticketDao.findAllByOwner(user.getId()).size() <= MaximumPerUser) {
                updateAbilityToBuyToEvent(ticket);
                ticket.setUser(user);
                ticketDao.persist(ticket);
            } else {
                throw new RuntimeException(
                        "The amount of Tickets to Event: " + ticket.getEvent().getName() + " you already have cannot afford you to buy more tickets on this event; \n" + MaximumPerUser + "per one customer.\n We are apologize :( ");
            }
        } else {
            var eventId = ticket.getEvent().getId();
            eventDao.find(eventId).setActiveForSelling(false);
            eventDao.update(eventDao.find(eventId), eventId);
            throw new RuntimeException(
                    "Tickets to Event: " + ticket.getEvent().getName() + " are not allowed to be bought right now \n We are apologize :( ");
        }
        return ticket;
    }

    @Transactional
    public void updateAbilityToBuyToEvent(Ticket ticket){
        final Event event = ticket.getEvent();
        if (!ticketDao.findAllByEvent(event.getId()).isEmpty()){
            throw new RuntimeException(
                    "The amount of Tickets to Event: " + event.getName() + " is insufficient to buy.");
        }
        if (ticketDao.findAllByEvent(event.getId()).size() == 1){
            event.setActiveForSelling(false);
        }
        eventDao.update(event, event.getId());
    }

    public List<Ticket> findAll() {
        return ticketDao.findAll();
    }

    public List<Ticket> findAllByEvent(Event byId) {
        return ticketDao.findAllByEvent(byId.getId());
    }
}
