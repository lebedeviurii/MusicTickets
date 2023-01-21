package mt.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import java.time.Month;

public class EventTest {

    Event event;
    Band band;
    Admin admin;
    Post post;

    @BeforeEach
    public void setUp() {
        event = new Event();  // create new event
        event.setStartDate(LocalDateTime.of(2022, Month.NOVEMBER, 11, 12, 0));
        event.setEndDate(LocalDateTime.of(2022, Month.NOVEMBER, 20, 12, 0));
        event.setName("Test Event");
        event.setPlace("Prague");
        event.setId(1L);

        band = new Band();  // create new band
        band.setId(0L);
        band.setGenre("rock");
        band.setName("Rammstein");

        admin = new Admin();  // create new default admin
        post = new Post("Post title", "Post body", admin);  // create new post

        event.addPost(post);
        event.addBand(band);
    }

    @Test
    public void addBand_newEventWithPostAndBand_one() {
        int expected = 1;

        assertEquals(expected, event.getBand().size());
    }

    @Test
    public void addPost_newEventWithPostAndBand_one() {
        int expected = 1;

        assertEquals(expected, event.getPosts().size());
    }

    @Test
    public void addBand_newEventWithPostAndBand_fullBandInfo() {
        String expectedName = "Rammstein";
        String expectedGenre = "rock";
        long expectedId = 0L;

        assertEquals(expectedName, event.getBand().get(0).getName());
        assertEquals(expectedGenre, event.getBand().get(0).getGenre());
        assertEquals(expectedId,  event.getBand().get(0).getId());
    }

    @Test
    public void addPost_newEventWithPostAndBand_fullPostInfo() {
        String expectedTitle = "Post title";
        String expectedBody = "Post body";

        assertEquals(expectedTitle, event.getPosts().get(0).getTitle());
        assertEquals(expectedBody, event.getPosts().get(0).getPostBody());
    }

    @Test
    public void removeBand_newEventWithPostAndBand_zeroBandInEvent() {
        event.removeBand(band);
        int expected = 0;

        assertEquals(expected, event.getBand().size());
    }
}
