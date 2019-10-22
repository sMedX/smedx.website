package app.web;

import app.core.DB.DataModel.User;
import app.core.DB.Services.UserService;
import app.core.Security.Authority;
import app.core.Security.CredentialsAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RequestContextResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private CredentialsAuthProvider authProvider;

    @Autowired
    private UserService userService;

    /*@Inject
    public void setUserService(final UserService userService) {
        this.userService = userService;
    }
*/
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(RequestContext.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory
    ) throws Exception, UsernameNotFoundException {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);

        RequestContext requestContext = new RequestContext();
        requestContext.setRequest(request);
        requestContext.setResponse(response);
        requestContext.setLocale(request.getLocale());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        /*String header = requestContext.getRequest().getHeader("Authorization");

        JSONObject credentials;
        if (header != null) {
            String encodedData = header.replaceAll("Basic ", "");
            String decoded = new String(Base64.getDecoder().decode(encodedData));
            credentials = new JSONObject(decoded);
        } else {
            credentials = new JSONObject("{username:\"testadmin\", password:\"\"}");
        }
        if (authentication == null
                || authentication.getPrincipal().toString().equalsIgnoreCase(credentials.getString("username"))) {


            Authentication artificialAuth = new Authentication() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return null;
                }

                @Override
                public Object getCredentials() {
                    return credentials.getString("password");
                }

                @Override
                public Object getDetails() {
                    return null;
                }

                @Override
                public Object getPrincipal() {
                    return null;
                }

                @Override
                public boolean isAuthenticated() {
                    return false;
                }

                @Override
                public void setAuthenticated(boolean b) throws IllegalArgumentException {

                }

                @Override
                public String getName() {
                    return credentials.getString("username");
                }
            };
            //CredentialsAuthProvider authProvider = new CredentialsAuthProvider();

            SecurityContextHolder.getContext().setAuthentication(authProvider.authenticate(artificialAuth));
            authentication = SecurityContextHolder.getContext().getAuthentication();
        }*/

        /*} else {
            System.out.println("header is null");
        }*/
        if (authentication == null) {
            System.out.println("Something goes wrong. Authentication is null for.....");
        } else {
            final Object principal = authentication.getPrincipal();
            if (principal instanceof String) {
                final User user = userService.findByMail((String) principal);
                requestContext.setUser(user);

                List<Authority> authorities = authentication.getAuthorities().stream()
                        .map(authority -> Authority.valueOf(((GrantedAuthority) authority).getAuthority()))
                        .collect(Collectors.toList());

                requestContext.setAuthorities(authorities);

            }
        }

        return requestContext;
    }
}
