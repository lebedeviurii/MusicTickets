package mt.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.Basic;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "LoggedUser.findByNameSurname", query = "SELECT l from LoggedUser l WHERE 'name' = :name AND 'surname' =:surname")
public class LoggedUser extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @Basic(optional = false)
    @Column(nullable = false)
    private String surname;

    @Basic
    private String favoriteGenre;

    @Basic
    private String phoneNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFavoriteGenre() {
        return favoriteGenre;
    }

    public void setFavoriteGenre(String favoriteGenre) {
        this.favoriteGenre = favoriteGenre;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LoggedUser(){
        super();
    }

    public LoggedUser(String name, String surname, String email, String username, String password) {
        super(username, email, password);
        this.name = name;
        this.surname = surname;
    }
}
