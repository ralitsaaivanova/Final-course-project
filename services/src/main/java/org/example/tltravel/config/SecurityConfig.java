package org.example.tltravel.config;

import org.example.tltravel.repositories.UserRepository;
//import org.example.tltravel.service.impl.UserDetailsServiceImpl;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
//
@EnableWebSecurity
@Configuration
public class SecurityConfig {
//    @Bean
//    public UserDetailsService userDetailsService(UserRepository userRepository) {
//        return new UserDetailsServiceImpl();
//    }
//
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // 1) Disable CSRF so you can POST/PUT/DELETE without a token
                .csrf().disable()

                // 2) Allow all requests through without login
//                .authorizeRequests()
//                .anyRequest().permitAll();

//        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/assets/**").permitAll()
                        .requestMatchers( "/login","/logout").permitAll()
                        .requestMatchers("/Admin/**").hasRole("ADMIN")
//                        .requestMatchers("/Users/**").hasRole("AGENT")
                        .requestMatchers("/Agent/**").hasRole("AGENT")
                        .requestMatchers("/Client/**").hasRole("CLIENT")
                        .requestMatchers("/Extras/**").hasAnyRole("OPERATOR", "ADMIN")
                        .requestMatchers("/FeedingType/**").hasAnyRole("OPERATOR", "ADMIN")
                        .requestMatchers("/Hotel/**").hasAnyRole("OPERATOR","ADMIN")
                        .requestMatchers("/HotelPhotos/**").hasAnyRole("OPERATOR","ADMIN")
                        .requestMatchers("/RoomPhotos/**").hasAnyRole("OPERATOR","ADMIN")
                        .requestMatchers("/Operator/**").hasRole("OPERATOR")
                        .anyRequest().authenticated()
                )

                .formLogin(
                formLogin -> {
                    formLogin
                            .loginPage("/login")
                            .usernameParameter("email")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/index",true)
                            .failureForwardUrl("/login-error");
                }
        ).logout(
                logout -> {
                    logout.logoutUrl("/logout")
                            .logoutSuccessUrl("/")
                            .invalidateHttpSession(true);
                }
        )
                ;
        return http.build();

//        http
//
//                // authorize requests
//                .authorizeHttpRequests(authz ->authz
//                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
//                        .anyRequest().authenticated().anyRequest().authenticated())
//                ;

//        return http.build();

//        http.csrf().disable();
//        http
//                .authorizeHttpRequests(authz -> authz
//                        .requestMatchers(HttpMethod.POST, "/Users").permitAll()
//                        .anyRequest().authenticated()
//                ).httpBasic();  // <-- enables Basic auth

//        return http.build();
    }
//
@Bean
public PasswordEncoder passwordEncoder() {
    return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
}
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }
}
