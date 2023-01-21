package mt.service;

import mt.model.Admin;
import mt.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.Month;

@Component
public class SystemInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(SystemInitializer.class);

    private final AdminService adminService;

    private final PlatformTransactionManager txManager;
    private final EventService eventService;

    @Autowired
    public SystemInitializer(AdminService adminService, PlatformTransactionManager txManager, EventService eventService) {
        this.adminService = adminService;
        this.txManager = txManager;
        this.eventService = eventService;
    }

    @PostConstruct
    private void initSystem() {
        TransactionTemplate txTemplate = new TransactionTemplate(txManager);
        txTemplate.execute((status) -> {
            generateAdmin();
/*            generateTestEvent();*/
            return null;
        });
    }

    private void generateAdmin() {
        if (!adminService.exists("mtAdmin@gmail.com")){
            Admin admin = new Admin();
            admin.setEmail("mtAdmin@gmail.com");
            admin.setUsername("admin");
            admin.setPassword("adm1n");
            LOG.info("Generated admin user with credentials " + admin.getUsername() + "/" + admin.getPassword());
            adminService.persist(admin);
        }
    }
    private void generateTestEvent() {
        Event event = new Event();  // create new event
        event.setStartDate(LocalDateTime.of(2022, Month.NOVEMBER, 11, 12, 0));
        event.setEndDate(LocalDateTime.of(2022, Month.NOVEMBER, 20, 12, 0));
        event.setName("Test Event");
        event.setPlace("Prague");
        eventService.persist(event);
    }
}
