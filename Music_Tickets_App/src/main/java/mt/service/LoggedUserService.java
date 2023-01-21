package mt.service;

import mt.dao.UserDao;
import mt.model.Admin;
import mt.model.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;

@Service
public class LoggedUserService {
    private final UserDao dao;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoggedUserService(UserDao dao, PasswordEncoder passwordEncoder) {
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public LoggedUser find(Long id) {
        return (LoggedUser) dao.find(id);
    }

    @Transactional
    public void persist(LoggedUser user) {
        Objects.requireNonNull(user);
        user.encodePassword(passwordEncoder);
        dao.persist(user);
    }

    @Transactional(readOnly = true)
    public boolean exists(String email) {
        return dao.findByEmail(email) != null;
    }
}
