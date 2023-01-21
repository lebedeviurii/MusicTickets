package mt.security.model;

import mt.model.LoggedUser;
import mt.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private LoggedUser user;


    public UserDetails(LoggedUser user) {
        Objects.requireNonNull(user);
        this.user = user;
    }

    public UserDetails(LoggedUser user, Collection<GrantedAuthority> authorities) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(authorities);
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public LoggedUser getUser() {
        return user;
    }

    public void eraseCredentials() {
        user.erasePassword();
    }
}
