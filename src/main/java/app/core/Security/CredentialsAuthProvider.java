package app.core.Security;

import app.core.DB.DataModel.User;
import app.core.DB.Services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Collection;
import java.util.stream.Collectors;

@Component("credentialsAuthProvider")
public class CredentialsAuthProvider implements AuthenticationProvider {

    private static Logger log = Logger.getLogger(CredentialsAuthProvider.class.getName());

    @Autowired
    private UserService userService;


    public CredentialsAuthProvider() {
        //System.out.println("CredentialsAuthProvider is alive!!");
        log.info("CredentialsAuthProvider is alive!!");
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //final HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        //final HttpServletResponse response = ((ServletRequestAttributes) requestAttributes).getResponse();

        final String username = authentication.getName();
        final String password = (String) authentication.getCredentials();

        System.out.println("Credential provider authenticate {" + username /*+ ":" + password*/ + "}");

        User user = null;
        Authentication authenticated = null;
        if (username.contains("select") || username.contains("join") || username.contains("from") || username.contains("from")
                || username.contains("*")) {
            throw new UsernameNotFoundException(username);
        }
        if (password.contains("select") || password.contains("join") || password.contains("from") || password.contains("from")
                || password.contains("*")) {
            throw new UsernameNotFoundException(username);
        }
        try {
            /*if(userService == null){
                userService = (UserService) SpringUtils.ctx.getBean(UserService.class);
            }*/
            user = userService.authenticate(username, password);
            if (user != null) {
                //todo: get user authorities
                final Collection<GrantedAuthority> authorities = AuthorityMapper.getAuthority(user.getRole()).stream()
                        .map(authority -> new SimpleGrantedAuthority(authority.name()))
                        .collect(Collectors.toList());
                authenticated = new UsernamePasswordAuthenticationToken(username, password, authorities);
            } else {
                System.out.println("User not found while auth with email:" + username);
            }

        } catch (final Throwable e) {
            System.out.println("error in CredentialAuthProvider line 93");
            System.out.println(e.getMessage());
        }

        if (user == null) {
            throw new UsernameNotFoundException(username);
        } else {
            return authenticated;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}
