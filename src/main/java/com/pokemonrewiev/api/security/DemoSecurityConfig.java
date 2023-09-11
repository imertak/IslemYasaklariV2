package com.pokemonrewiev.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
public class DemoSecurityConfig implements WebMvcConfigurer {

    private CustomUserDetailService customUserDetailService;
    private JwtAuthEntryPoint authEntryPoint;

    @Autowired
    public DemoSecurityConfig(CustomUserDetailService customUserDetailService, JwtAuthEntryPoint authEntryPoint) {
        this.customUserDetailService = customUserDetailService;
        this.authEntryPoint = authEntryPoint;

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers("/api/google").permitAll()
                        .requestMatchers("/api/tum-yasaklar").authenticated()
                        .requestMatchers("/api/get-db").authenticated()
                        .requestMatchers("/api/add").authenticated()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/delete/**").permitAll()
                        .requestMatchers("/api/update/**").permitAll()
                        .requestMatchers("/user/get/**").permitAll()
                        .anyRequest().authenticated()

        );

        // use HTTP Basic Authentication
        //HTTP Basic Authentication'ın kullanılacağını belirtir. İstemciden kullanıcı adı ve şifre ile kimlik doğrulama istenecektir.
        httpSecurity.httpBasic(Customizer.withDefaults());

        //disable CSRF (cross site request forgery)
        // in general not rquired for stateless REST ApIs that use POST, PUT, DELETE, and or PATCH
        httpSecurity.csrf(csrf -> csrf.disable());
        //httpSecurity.exceptionHandling()
        //        .authenticationEntryPoint(authEntryPoint)
        //        .and()
        //        .sessionManagement()
        //        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.cors();
        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000"); // İzin verilen kök URL
        configuration.addAllowedMethod("*"); // Tüm HTTP metotlarına izin ver
        configuration.addAllowedHeader("*"); // Tüm başlıklara izin ver
        configuration.setAllowCredentials(true); // Kimlik bilgisiyle istekleri destekle

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(){
        return new JWTAuthenticationFilter();
    }

}
