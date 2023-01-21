package mt.rest;

import mt.exception.NotFoundException;
import mt.exception.ValidationException;
import mt.model.Band;
import mt.model.Event;
import mt.model.Post;
import mt.model.Ticket;
import mt.model.dto.EventDto;
import mt.rest.util.RestUtils;
import mt.service.BandService;
import mt.service.EventService;
import mt.service.PostService;
import mt.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static mt.rest.util.RestUtils.*;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/rest/events")
public class EventController {

    private static final Logger LOG = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;
    private final BandService bandService;
    private final PostService postService;
    private final TicketService ticketService;

    @Autowired
    public EventController(EventService eventService, BandService bandService, PostService postService, TicketService ticketService) {
        this.eventService = eventService;
        this.bandService = bandService;
        this.postService = postService;
        this.ticketService = ticketService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getEvents() {
        return eventService.findAll();
    }

    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createEvent(@RequestBody EventDto eventDto) {
        Event event = new Event(eventDto.getName(), eventDto.getPlace(), convertToLocalDateViaInstant(eventDto.getSellingStart()), convertToLocalDateViaInstant(eventDto.getStart()), convertToLocalDateViaInstant(eventDto.getEnd()), null);
        eventService.persist(event);
        LOG.debug("Created event {}.", event);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", event.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Event getById(@PathVariable Long id) {
        final Event event = eventService.find(id);
        if (Objects.equals(event, null)) {
            throw NotFoundException.createException("Event", id);
        }
        return event;
    }

    @PutMapping(value = "/update-{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateEvent(@PathVariable Long id, @RequestBody Event event) {
        final Event original = getById(id);
        if (!original.getId().equals(event.getId())) {
            throw new ValidationException("Event identifier in the data does not match the one in the request URL.");
        }
        eventService.update(event, original.getId());
        LOG.debug("Updated event {}.", event);
    }

    @GetMapping(value = "/{id}/bands", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Band> getBandsByEvent(@PathVariable Long id) {
        return bandService.findAllByEvent(getById(id));
    }

    @PostMapping(value = "/{id}/band-add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void addBandToEvent(@PathVariable Long id, @RequestBody Band band) {
        final Event event = getById(id);
        eventService.addBand(event, band);
        LOG.debug("Band {} added into event {}.", band, event);
    }

    @DeleteMapping(value = "/{eventId}/bands/remove-{bandId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBandFromEvent(@PathVariable Long eventId,
                                          @PathVariable Long bandId) {
        final Event event = getById(eventId);
        final Band toRemove = bandService.find(bandId);
        if (toRemove == null) {
            throw NotFoundException.createException("Band", bandId);
        }
        eventService.removeBand(event, toRemove);
        LOG.debug("Band {} removed from event {}.", toRemove, event);
    }

    @GetMapping(value = "/{id}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Post> getPostsByEvent(@PathVariable Long id) {
        return postService.findAllByEvent(getById(id));
    }

    @PostMapping(value = "/{id}/post-add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void addPostToEvent(@PathVariable Long id, @RequestBody Post post) {
        final Event event = getById(id);
        eventService.addPost(event, post);
        LOG.debug("Post {} added into event {}.", post, event);
    }

    @DeleteMapping(value = "/{eventId}/posts/remove-{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePostFromEvent(@PathVariable Long eventId,
                                          @PathVariable Long postId) {
        final Event event = getById(eventId);
        final Post toRemove = postService.find(postId);
        if (toRemove == null) {
            throw NotFoundException.createException("Post", postId);
        }
        eventService.removePost(event, toRemove);
        LOG.debug("Post {} removed from event {}.", toRemove, event);
    }

    @GetMapping(value = "/{id}/tickets", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Ticket> getTicketsByEvent(@PathVariable Long id) {
        return ticketService.findAllByEvent(getById(id));
    }

    @PostMapping(value = "/{id}/ticket-add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void addTicketToEvent(@PathVariable Long id, @RequestBody Ticket ticket) {
        final Event event = getById(id);
        eventService.addTicket(event, ticket);
        LOG.debug("Ticket {} added into event {}.", ticket, event);
    }

    @DeleteMapping(value = "/{eventId}/tickets/remove-{ticketId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTicketFromEvent(@PathVariable Long eventId,
                                    @PathVariable Long ticketId) {
        final Event event = getById(eventId);
        final Ticket toRemove = ticketService.find(ticketId);
        if (toRemove == null) {
            throw NotFoundException.createException("Ticket", ticketId);
        }
        eventService.removeTicket(toRemove);
        LOG.debug("Ticket {} removed from event {}.", toRemove, event);
    }

    @DeleteMapping(value = "/delete-{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEvent(@PathVariable Long id) {
        final Event toRemove = eventService.find(id);
        if (toRemove == null) {
            return;
        }
        eventService.remove(toRemove);
        LOG.debug("Removed event {}.", toRemove);
    }
}
