package mt.service;

import mt.dao.AdminDao;
import mt.dao.AdminDao;
import mt.model.Admin;
import mt.model.Admin;
import mt.model.Admin;
import mt.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class AdminService {

    private final AdminDao dao;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(AdminDao dao, PasswordEncoder passwordEncoder) {
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public Admin find(Long id) {
        return dao.find(id);
    }

    @org.springframework.transaction.annotation.Transactional
    public void persist(Admin admin) {
        Objects.requireNonNull(admin);
        admin.encodePassword(passwordEncoder);
        dao.persist(admin);
    }

    @Transactional(readOnly = true)
    public boolean exists(String email) {
        return dao.findByEmail(email) != null;
    }

}
