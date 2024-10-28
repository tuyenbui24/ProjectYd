package shopYd.com.MyProjectYD.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import shopYd.com.MyProjectYD.entity.Role;
import shopYd.com.MyProjectYD.entity.User;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ShopUserDetails implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;
    private final User user;

    public ShopUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getRoles();

        List<SimpleGrantedAuthority> authories = new ArrayList<>();

        for (Role role : roles) {
            authories.add(new SimpleGrantedAuthority(role.getName()));
        }

        return authories;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
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
        return user.isEnabled();
    }

    public String getFullName() {
        return this.user.getFirstName() + " " + this.user.getLastName();
    }
//
//    public void setFirstName(String firstName) {
//        this.user.setFirstName(firstName);
//    }
//
//    public void setLastName(String lastName) {
//        this.user.setLastName(lastName);
//    }
//    public boolean hasRole(String roleName) {
//        return user.hasRole(roleName);
//    }
}
