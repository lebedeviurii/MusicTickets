package mt.model;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Music_Ticekts_USER")
@NamedQueries({
        @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email")
})
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Basic(optional = false)
    @Column(nullable = false)
    private String username;

    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    private String email;

    @Basic(optional = false)
    @Column(nullable = false)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void erasePassword() {
        this.password = null;
    }

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                username + email + ")}";
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }
}
