package com.vuxuanbac.security.securitybeginner.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration {
    // From Security 5.7.0, WebSecurityConfigurerAdapter [Adapter] is deprecated
    @Autowired
    @Qualifier("dbUserDetailsService")
    private UserDetailsService userDetailsService;

    // Instead of overriding configure(HttpSecurity http) from Adapter
    // Create a Bean SecurityFilterChain with the same configuration on HttpSecurity
    // and return http.build() in additional;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("** filterChain");
        // https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/HttpSecurity.html
        http.csrf().disable();
        http.exceptionHandling().authenticationEntryPoint(unauthorizedHandler());
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
        http.authorizeRequests()                   // all the request need to be authorized using RequestMatcher
                // .anyRequest().authenticated()       // all the request need to be authenticated first

                //    ? matches one character
                //    * matches zero or more characters
                //    ** matches zero or more 'directories' in a path
                //    {spring:[a-z]+} matches the regexp [a-z]+ as a path variable named "spring"
                //          Ex: com/{filename:\\w+}.jsp will match com/test.jsp and assign the value test to the filename variable
                // The order is very important, which appears first is applied first
                .antMatchers("/api/login", "/api/register", "/api/hello").permitAll()
//                .antMatchers("/api/user/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/api/**").authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authenticationProvider(authenticationProvider());

        // Add this custom filter before UsernamePasswordAuthenticationFilter.
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedHandler(){
        return new CustomAuthenticationEntryPoint();
    }
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }
}
