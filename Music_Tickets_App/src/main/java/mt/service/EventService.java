package mt.service;

import mt.dao.BandDao;
import mt.dao.EventDao;
import mt.dao.PostDao;
import mt.dao.TicketDao;
import mt.model.Band;
import mt.model.Event;
import mt.model.Post;
import mt.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class EventService {
    private final EventDao eventDao;
    private final TicketDao ticketDao;
    private final BandDao bandDao;
    private final PostDao postDao;
    @Autowired
    public EventService(EventDao eventDao, TicketDao ticketDao, BandDao bandDao, PostDao postDao) {
        this.eventDao = eventDao;
        this.ticketDao = ticketDao;
        this.bandDao = bandDao;
        this.postDao = postDao;
    }

    public void persist(Event event) {
        Objects.requireNonNull(event);
        eventDao.persist(event);
    }

    @Transactional(readOnly = true)
    public Event find(Long id) {
        return eventDao.find(id);
    }

    @Transactional
    public Event update(Event event, Long id){
        Objects.requireNonNull(event);
        return eventDao.update(event, id);
    }

    @Transactional
    public void remove(Event event){
        Objects.requireNonNull(event);
        eventDao.remove(event);
    }

    public List<Event> findAll() {
        return eventDao.findAll();
    }

    /**
     * Adds the specified ticket to the specified event.
     *
     * @param event Target event
     * @param ticket  Ticket to add
     */
    @Transactional
    public void addTicket(Event event, Ticket ticket) {
        Objects.requireNonNull(event);
        Objects.requireNonNull(ticket);
        ticket.setEvent(event);
        ticketDao.update(ticket);
    }

    /**
     * Removes the specified ticket from its event.
     *
     * @param ticket  Ticket to remove
     */
    @Transactional
    public void removeTicket(Ticket ticket) {
        Objects.requireNonNull(ticket);
        ticket.setEvent(null);
        ticketDao.update(ticket);
    }
    /**
     * Adds the specified ticket to the specified event.
     *
     * @param event Target event
     * @param post  Post to add
     */
    @Transactional
    public void addPost(Event event, Post post) {
        Objects.requireNonNull(event);
        Objects.requireNonNull(post);
        post.setEvent(event);
        event.addPost(post);
        postDao.update(post);
    }

    /**
     * Removes the specified product from the specified category.
     *
     * @param event Event to remove post from
     * @param post  Post to remove
     */
    @Transactional
    public void removePost(Event event, Post post) {
        Objects.requireNonNull(event);
        Objects.requireNonNull(post);
        post.setEvent(event);
        event.removePost(post);
        postDao.update(post);
    }
    /**
     * Adds the specified ticket to the specified event.
     *
     * @param event Target event
     * @param band  Band to add
     */
    @Transactional
    public void addBand(Event event, Band band) {
        Objects.requireNonNull(event);
        Objects.requireNonNull(band);
        event.addBand(band);
        eventDao.update(event);
    }

    /**
     * Removes the specified product from the specified category.
     *
     * @param event Event to remove band from
     * @param band  Band to remove
     */
    @Transactional
    public void removeBand(Event event, Band band) {
        Objects.requireNonNull(event);
        Objects.requireNonNull(band);
        event.removeBand(band);
        eventDao.update(event);
    }
}
