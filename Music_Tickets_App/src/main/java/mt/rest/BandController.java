package mt.rest;

import mt.exception.NotFoundException;
import mt.exception.ValidationException;
import mt.model.Band;
import mt.model.dto.BandDto;
import mt.rest.util.RestUtils;
import mt.service.BandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/rest/bands")
public class BandController {

    private static final Logger LOG = LoggerFactory.getLogger(BandController.class);

    private final BandService bandService;

    @Autowired
    public BandController(BandService bandService) {
        this.bandService = bandService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Band> getBands() {
        return bandService.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createBand(@RequestBody BandDto bandDto) {
        Band band = new Band(bandDto.getName(), bandDto.getGenre(), bandDto.getInfo());
        bandService.persist(band);
        LOG.debug("Created band {}.", band);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", band.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Band getById(@PathVariable Long id) {
        final Band band = bandService.find(id);
        if (Objects.equals(band, null)) {
            throw NotFoundException.createException("Band", id);
        }
        return band;
    }

    @PutMapping(value = "/update-{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateBand(@PathVariable Long id, @RequestBody Band band) {
        final Band original = getById(id);
        if (!original.getId().equals(band.getId())) {
            throw new ValidationException("Band identifier in the data does not match the one in the request URL.");
        }
        bandService.update(band, original.getId());
        LOG.debug("Updated band {}.", band);
    }

    @DeleteMapping(value = "/delete-{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBand(@PathVariable Long id) {
        final Band toRemove = bandService.find(id);
        if (toRemove == null) {
            return;
        }
        bandService.remove(toRemove);
        LOG.debug("Removed band {}.", toRemove);
    }
}
