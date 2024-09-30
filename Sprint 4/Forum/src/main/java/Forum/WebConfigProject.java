package Forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebConfigProject {

    private final ContaUserDetailsService contaUserDetailsService;

    @Autowired
    public WebConfigProject(ContaUserDetailsService contaUserDetailsService) {
        this.contaUserDetailsService = contaUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/nova-conta").permitAll()
                        .requestMatchers("/adicionar-conta").permitAll()
                        .requestMatchers("/logout").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .loginProcessingUrl("/login-validation")
                        .usernameParameter("email")
                        .passwordParameter("senha")
                        .defaultSuccessUrl("/mostrar-perguntas", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(contaUserDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurity webSecurity() {
        return new WebSecurity();
    }

    public static class WebSecurity {
        public boolean checkEmail(Authentication authentication, String allowedEmail) {
            if (authentication == null || !authentication.isAuthenticated()) {
                return false;
            }
            ContaUserDetailsImpl contaUserDetails = (ContaUserDetailsImpl) authentication.getPrincipal();
            return contaUserDetails.getUsername().equals(allowedEmail);
        }
    }
}
