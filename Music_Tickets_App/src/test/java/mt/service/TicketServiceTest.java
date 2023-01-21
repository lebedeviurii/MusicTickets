package mt.service;

import mt.dao.EventDao;
import mt.dao.TicketDao;
import mt.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class TicketServiceTest {

    @PersistenceContext
    private EntityManager em;

    private TicketDao ticketDao;
    private EventDao eventDao;

    @Autowired
    private TicketService ticketService;

    private Event event;
    private Ticket ticket;

    private Band band;

    @BeforeEach
    public void setUp() {
        event = new Event();  // create new event
        event.setStartDate(LocalDateTime.of(2022, Month.NOVEMBER, 11, 12, 0));
        event.setEndDate(LocalDateTime.of(2022, Month.NOVEMBER, 20, 12, 0));
        event.setName("Test Event");
        event.setPlace("Prague");
        event.setId(1L);
        event.setActiveForSelling(true);

        eventDao.persist(event);

        band = new Band();  // create new band
        band.setId(0L);
        band.setGenre("rock");
        band.setName("Sabaton");
        event.addBand(band);

        ticketService = new TicketService(ticketDao, eventDao);
        ticket = ticketService.create(event, BigDecimal.valueOf(100000), "Standing", 0);
        ticket.setId(2L);
    }

    @Test
    public void buyTicket_newUser_true(){
        LoggedUser user = new LoggedUser("userName", "userSurname", "mail@mail.cz", "testUser", "12345");
        user.setId(3L);
        ticketService.buy(user, ticket.getId());

        assertEquals(ticket.getUser(), user);
    }
}
