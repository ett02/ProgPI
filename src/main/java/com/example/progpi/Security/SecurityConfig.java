package com.example.progpi.Security;

//import com.example.progpi.Security.JwtConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    public static final String ADMIN = "admin";
    public static final String USER = "user";
    private final JwtConverter jwtConverter;
    @Bean



    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(t-> t.disable());


        http.cors(Customizer.withDefaults())
                .authorizeHttpRequests((authz) ->
                        authz.requestMatchers(HttpMethod.PUT, "/users/add").permitAll()
                                .requestMatchers(HttpMethod.GET, "/product/getAllProduct/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/product/getAllByName/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/product/addUpdate").hasRole("admin")
                                .anyRequest().authenticated()

                );



        http.sessionManagement(sess -> sess.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS));
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)));


        return http.build();
    }


    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin(null);
        configuration.addAllowedHeader("*");
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedMethod("OPTIONS");
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedOrigin("http://localhost:8080");
        configuration.addAllowedHeader("Access-Control-Allow-Headers");
        configuration.addAllowedHeader("Access-Control-Allow-Origin");
        configuration.addAllowedHeader("Access-Control-Allow-Methods");
        configuration.addExposedHeader("Authorization"); // Allowing specific headers to be exposed to the client
        configuration.setMaxAge(3600L); // Cache preflight response for 1 hour
        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }


}