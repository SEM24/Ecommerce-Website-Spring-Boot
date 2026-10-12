package com.khomsi.site_project.configuration;

import com.khomsi.site_project.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private DataSource dataSource;

    /*
        loginPage() – the custom login page
        loginProcessingUrl() – the URL to submit the username and password
        defaultSuccessUrl() – the landing page after a successful login
        failureUrl() – the landing page after an unsuccessful login
     */
//
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/admin/**").hasAuthority(Role.ADMIN.getAuthority())
                        .requestMatchers("/js/**", "/css/**", "/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login") //enable this to go to your own custom login page
                        .loginProcessingUrl("/login") //enable this to use login page provided by spring security
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error")
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(PathPatternRequestMatcher.withDefaults().matcher("/logout"))
                        .logoutSuccessUrl("/")
                );
//                .logoutRequestMatcher(PathPatternRequestMatcher.withDefaults().matcher("/logout")).permitAll().and().exceptionHandling()
//                .accessDeniedPage("/error403");
        return http.build();
    }

    @Bean
    public JdbcUserDetailsManager userDetailsService() {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        manager.setUsersByUsernameQuery("select login, password, 'true' as enabled from user where login=?");
        manager.setAuthoritiesByUsernameQuery("select login, role from user where login=?");
        return manager;
    }

}
