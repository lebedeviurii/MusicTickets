package mt.rest;

import mt.exception.ValidationException;
import mt.model.LoggedUser;
import mt.model.Ticket;
import mt.model.dto.TicketDto;
import mt.model.dto.UserDto;
import mt.rest.util.RestUtils;
import mt.service.EventService;
import mt.service.LoggedUserService;
import mt.service.TicketService;
import mt.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/rest/tickets")
public class TicketController {

    private static final Logger LOG = LoggerFactory.getLogger(TicketController.class);

    private final TicketService ticketService;
    private final LoggedUserService userService;
    private final EventService eventService;

    @Autowired
    public TicketController(TicketService ticketService, LoggedUserService userService, EventService eventService) {
        this.ticketService = ticketService;
        this.userService = userService;
        this.eventService = eventService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Ticket> getTickets() {
        return ticketService.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTicket(@RequestBody TicketDto ticketDto) {
        Ticket ticket = new Ticket(BigDecimal.valueOf(ticketDto.getPrice()), ticketDto.getSeat(), ticketDto.getSector(), eventService.find(ticketDto.getEventId()), null);
        ticketService.create(ticket.getEvent(), ticket.getPrice(), ticket.getSector(), ticket.getSeat());
        LOG.debug("Created ticket {}.", ticket);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", ticket.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Ticket getById(@PathVariable Long id) {
        final Ticket ticket = ticketService.find(id);
        if (Objects.equals(ticket, null)) {
            throw NotFoundException.createException("Ticket", id);
        }
        return ticket;
    }

    @PutMapping(value = "/buy-{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Ticket buyTicket(@RequestBody UserDto userDto, @PathVariable Long id) {
        LoggedUser user = userService.find((long) userDto.getUserId());
        return ticketService.buy(user, id);
    }

    @PutMapping(value = "/update-{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        final Ticket original = getById(id);
        if (!original.getId().equals(ticket.getId())) {
            throw new ValidationException("Ticket identifier in the data does not match the one in the request URL.");
        }
        ticketService.update(ticket, original.getId());
        LOG.debug("Updated ticket {}.", ticket);
    }

    @DeleteMapping(value = "/delete-{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTicket(@PathVariable Long id) {
        final Ticket toRemove = ticketService.find(id);
        if (toRemove == null) {
            return;
        }
        ticketService.remove(toRemove);
        LOG.debug("Removed ticket {}.", toRemove);
    }
}
