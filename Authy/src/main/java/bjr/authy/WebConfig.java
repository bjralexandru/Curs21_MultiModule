package bjr.authy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.Security;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebConfig {

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImplements();
    }

    @Bean
    public SecurityFilterChain config(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf().disable()
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/users", "/user/**").authenticated()
                .requestMatchers("/wallets", "/wallet/**").authenticated()
                .requestMatchers("/transactions", "/transaction/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .usernameParameter("username")
                .defaultSuccessUrl("/users")
                .and()
                .logout()
                .and()
                .build();
    }
}
