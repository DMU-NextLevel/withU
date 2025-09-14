package NextLevel.demo.role;

import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {
    SOCIAL("ROLE_SOCIAL") ,
    USER("ROLE_USER","ROLE_SOCIAL") ,
    ADMIN("ROLE_ADMIN", "ROLE_USER", "ROLE_SOCIAL");

    public String[] roles;

    UserRole(String... rols) {
        this.roles = rols;
    }

    public static UserRole getRole(String dbRoleName) {
        return Arrays.stream(UserRole.values())
            .filter(R -> R.name().equals(dbRoleName))
            .findFirst()
            .orElseThrow(() -> new CustomException(ErrorCode.SIBAL_WHAT_IS_IT,"Invalid role: " + dbRoleName));
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(this.roles)
            .map(SimpleGrantedAuthority::new)
            .toList();
    }
}
