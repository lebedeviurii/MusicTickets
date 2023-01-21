package mt.rest;

import mt.model.Admin;
import mt.model.LoggedUser;
import mt.rest.util.RestUtils;
import mt.security.model.AuthenticationToken;
import mt.service.AdminService;
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
@RequestMapping("/rest/admins")
public class AdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @PreAuthorize("admin.username.contains('admin')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> register(@RequestBody Admin admin){
        Objects.requireNonNull(admin);
        HttpHeaders headers =  new HttpHeaders();
        if (!adminService.exists(admin.getEmail())){
            adminService.persist(admin);
            LOGGER.debug("Admin {} was successfully registered", admin);
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
