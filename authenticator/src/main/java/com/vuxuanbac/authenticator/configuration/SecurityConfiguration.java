package com.vuxuanbac.authenticator.configuration;

import com.vuxuanbac.authenticator.repository.AccountRepository;
import com.vuxuanbac.authenticator.service.DbUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
// Flow on Security:
//      Request -> Security Filter Chain -> Authentication Manager -> Authentication Provider -> User Details Service
// - Security Filter Chain:
//      + A filter is an object that performs filtering tasks on either
//        the request to a resource (a servlet or static content), or on the response from a resource, or both.
// - Authentication Manager: Process an Authentication Request
//      + authenticate(): Attempts to authenticate the passed Authentication object,
//        returning a fully populated Authentication object (including granted authorities) if successful.
//          . return Authentication object itself: Valid
//          . throw AuthenticationException: Invalid. The Exception is handled by the Application (render page or return 401 Response)
//          . return null: Not decided
//      + AuthenticationProvider: Same as AuthenticationManager but has a method for indicating this AuthenticationManager support an Authentication type.
//      + ProviderManager: Support multiple Authentication mechanisms by delegating to a chain of AuthenticationProvider.

// - Authentication Provider:
// - User Details Service:
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    // From Security 5.7.0, WebSecurityConfigurerAdapter [Adapter] is deprecated
    @Autowired
    @Qualifier("dbUserDetailsService")
    private UserDetailsService userDetailsService;

    // Instead of overriding configure(HttpSecurity http) from Adapter
    // Create a Bean SecurityFilterChain with the same configuration on HttpSecurity
    // and return http.build() in additional;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/HttpSecurity.html
        http
                .csrf().disable()
                .authorizeRequests()                    // all the request need to be authorized using RequestMatcher
                    // .anyRequest().authenticated()       // all the request need to be authenticated first

                    //    ? matches one character
                    //    * matches zero or more characters
                    //    ** matches zero or more 'directories' in a path
                    //    {spring:[a-z]+} matches the regexp [a-z]+ as a path variable named "spring"
                    //          Ex: com/{filename:\\w+}.jsp will match com/test.jsp and assign the value test to the filename variable
                // The order is very important, which appears first is applied first
                    .antMatchers("/registration.html", "/login").permitAll() // everybody can access registration.html (all the paths returning "registration.html" are public) or localhost:7777/login/...
                    .antMatchers("/user/**").authenticated()       // every authenticated account can access localhost:7777/user/...
                    .antMatchers("/admin/**").hasRole("ADMIN")     // every authenticated account with ROLES ADMIN can access localhost:7777/admin/...
                    .antMatchers("/manager/users/delete").hasAuthority("DELETE_ACCOUNT") // every authenticated account with PERMISSION DELETE_ACCOUNT can access localhost:7777/manager/users/delete/...
                    .antMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")     // every authenticated account with ROLES ADMIN or MANAGER can access localhost:7777/manager/...

                    .antMatchers("/api/login", "/api/register").permitAll()
                    .antMatchers("/api/**").authenticated()
//                .and()
//                //.httpBasic();                 // Use Http (Basic) Authentication
//                .formLogin()                    // Use Form based Authentication
//                    .loginPage("/login").permitAll() // The path to the login form
//                    .usernameParameter("username") // The value of "name" attributes in login form for searching USERNAME
//                    .passwordParameter("password") // The value of "name" attributes in login form for searching PASSWORD
//                    .loginProcessingUrl("/login")  // The path to submit data from login form to (form-action) of the form
//
//                .and()
//                .logout()           // Logout config (for Form based and Http Authentication)
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))   // Logout on Security (end session) when reach localhost:7777/logout with GET
//                    .logoutSuccessUrl("/login") // After logout, redirect to localhost:7777/login
//                .and()
//                .rememberMe()
//                    .userDetailsService(userDetailsService)
//                    .tokenValiditySeconds(30)           // validate within 30s
//                    .key("AUniqueKey") // A key used for hashing with username and password saved on browser (default: auto generated)
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
        // Add this custom filter before UsernamePasswordAuthenticationFilter.
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder builder){

    }
    @Bean
    public AuthTokenFilter jwtTokenFilter(){
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        var provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    // Instead of overriding configure(AuthenticationManagerBuilder auth) from Adapter
    // Create a Bean UserDetailsService: InMemoryUserDetailsManager(Collection<UserDetails>)
    //                                   or a custom class implement UserDetailsService
}
