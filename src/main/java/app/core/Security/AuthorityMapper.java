package app.core.Security;

import java.util.ArrayList;
import java.util.List;

public class AuthorityMapper {

    public static List<Authority> getAuthority(String roleName) {
        List<Authority> userAuthorities = new ArrayList<>();
        userAuthorities.add(Authority.ROLE_USER);
        if (roleName != null) {
            if (roleName.equalsIgnoreCase("admin")) {
                userAuthorities.add(Authority.ROLE_ADMIN);
            }
        }
        return userAuthorities;
    }
}
