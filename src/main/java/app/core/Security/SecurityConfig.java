package app.core.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.DelegatingAuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.LinkedHashMap;

@Configuration
// @ComponentScan("app.core.Security")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       /* http.authorizeRequests().anyRequest().authenticated()
                .and()
                .httpBasic();*/
        /*http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/**").authenticated()
                .antMatchers("/api/**").hasRole(Authority.ROLE_USER.name().replaceAll("ROLE_", ""))
                .and()
                .logout()
                .and()
                .csrf();*/

        http.httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .authorizeRequests()
                // .anyRequest().authenticated()
                .antMatchers("/", "/static/**", "favicon.ico", "/api/auth/check")
                .permitAll()
                .antMatchers("/assets/**", "/*.ico", "/*.js", "/*.js.map", "/*.woff")
                .permitAll()
                .antMatchers("/login")
                .anonymous()
                .antMatchers("/api/**")
                //TODO: permit all for test
                .permitAll()
                //.hasAnyAuthority(Authority.ROLE_USER.name())
                //.anyRequest()
                //.hasAnyAuthority(Authority.ROLE_USER.name())
                .and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .headers().frameOptions().sameOrigin();
    }


   /* @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    protected AuthenticationProvider authenticationProvider() {
        return new CredentialsAuthProvider();
    }*/


    @Autowired
    private CredentialsAuthProvider authProvider;

    @Override
    protected void configure(
            AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(authProvider);
    }

    @Bean
    protected AuthenticationEntryPoint authenticationEntryPoint() {
        final String[] statusPatterns = {
                "/api/**",
                "/grpahql/**",
                "/**"
        };

        final LinkedHashMap<RequestMatcher, AuthenticationEntryPoint> entryPoints = new LinkedHashMap<>();
        final AuthenticationEntryPoint statusEntryPoint = new HttpMessageEntryPoint();
        for (String statusPattern : statusPatterns) {
            entryPoints.put(new AntPathRequestMatcher(statusPattern), statusEntryPoint);
        }
        AuthenticationEntryPoint redirectEntryPoint = new LoginUrlAuthenticationEntryPoint("/login");
        DelegatingAuthenticationEntryPoint resultEntryPoint = new DelegatingAuthenticationEntryPoint(entryPoints);
        resultEntryPoint.setDefaultEntryPoint(redirectEntryPoint);
        return resultEntryPoint;
    }

    /*@Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        AuthenticationManager authManger = new AuthenticationManager() {
            private CredentialsAuthProvider provider = new CredentialsAuthProvider();

            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return provider.authenticate(authentication);
            }
        };

        return authManger;
    }*/

   /* @Bean
    protected LogoutHandler invalidationLogoutHandler(){
        return new Invalidation
    }*/
}
