package pl.pl.mgr.editnow.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import pl.pl.mgr.editnow.configuration.filter.UUIDCookieFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UUIDCookieFilter uuidCookieFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No session will be created or used by spring security
//        .and()
//          .httpBasic()
        .and()
          .addFilterBefore(uuidCookieFilter, FilterSecurityInterceptor.class)
          .authorizeRequests()
            .antMatchers("/api/user/uuid").permitAll()
            .anyRequest().authenticated()
        .and()
            .csrf().disable(); // disable cross site request forgery, as we don't use cookies - otherwise ALL PUT, POST, DELETE will get HTTP 403!
    }

}
