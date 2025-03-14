package NextLevel.demo.role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {
    USER("ROLE_USER") ,
    ADMIN("ROLE_ADMIN", "ROLE_USER");

    public String[] roles;

    UserRole(String... rols) {
        this.roles = rols;
    }

    public static UserRole getRole(String dbRoleName) {
        return Arrays.stream(UserRole.values())
            .filter(R -> R.name().equals(dbRoleName))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Invalid role: " + dbRoleName));
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(this.roles)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toCollection(ArrayList::new));
    }
}
