package org.softuni.ebankdemoproject.config;

import org.softuni.ebankdemoproject.domain.entities.users.RoleConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                    .csrfTokenRepository(csrfTokenRepository())
                .and()
                    .authorizeRequests()
                    .antMatchers("/js/*", "/css/*").permitAll()
                    .antMatchers("/", "/users/login", "/users/register").anonymous()
                    .antMatchers("/users/all-users", "/users/edit/*", "/users/delete/*",
                            "/users/rolechange/**", "/confirmations/all").hasAuthority(RoleConstant.EMPLOYEE.name())
                    .antMatchers("/bankaccounts/all").hasAuthority(RoleConstant.EMPLOYEE.name())
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                        .loginPage("/users/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home")
                .and()
                    .logout()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/users/logout"))
                .and()
                    .exceptionHandling().accessDeniedPage("/unauthorized");
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");
        return repository;
    }
}
