package mt.rest;

import mt.model.LoggedUser;
import mt.model.dto.LoggedUserDto;
import mt.rest.util.RestUtils;
import mt.security.model.AuthenticationToken;
import mt.service.LoggedUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

@RestController
@RequestMapping("/rest/users")
public class LoggedUserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggedUserController.class);

    private final LoggedUserService loggedUserService;

    @Autowired
    public LoggedUserController(LoggedUserService loggedUserService) {
        this.loggedUserService = loggedUserService;
    }


    @PreAuthorize("!loggedUser.username.contains('admin') ")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> register(@RequestBody LoggedUserDto loggedUserDto){
        LoggedUser user = new LoggedUser(loggedUserDto.getName(), loggedUserDto.getSurname(), loggedUserDto.getEmail(), loggedUserDto.getUsername(), loggedUserDto.getPassword());
        Objects.requireNonNull(user);
        HttpHeaders headers =  new HttpHeaders();
        if (!loggedUserService.exists(user.getEmail())){
            loggedUserService.persist(user);
            LOGGER.debug("User {} was successfully registered", user);
            headers = RestUtils.createLocationHeaderFromCurrentUri("/current");
        }
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public LoggedUser getCurrent(Principal principal) {
        final AuthenticationToken auth = (AuthenticationToken) principal;
        return auth.getPrincipal().getUser();
    }
}
