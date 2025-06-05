package NextLevel.demo.config.security;

import NextLevel.demo.role.UserRole;
import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class CustomAuthentication implements Authentication {
    private UserRole role;
    private Long userId;

    public CustomAuthentication(Long userId, String role) {
        this.userId = userId;
        this.role = UserRole.getRole(role);
    }

    public UserRole getRole() {return role; }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }
    @Override
    public Object getPrincipal() {
        return userId;
    }

    // no uses
    @Override
    public Object getCredentials() {
        return null;
    }
    @Override
    public Object getDetails() {
        return null;
    }
    @Override
    public boolean isAuthenticated() {
        return true;
    }
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    }
    @Override
    public boolean equals(Object another) {
        return false;
    }
    @Override
    public String toString() {
        return "";
    }
    @Override
    public int hashCode() {
        return 0;
    }
    @Override
    public String getName() {
        return "";
    }
}
