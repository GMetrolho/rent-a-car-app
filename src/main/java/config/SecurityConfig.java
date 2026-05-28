package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import service.JwtService;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtService jwtService;

    public SecurityConfig(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtService);

        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .formLogin(form -> form.disable())       // Desativa o login HTML do Spring
            .httpBasic(basic -> basic.disable())     // Desativa o popup de auth do browser
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Páginas frontend públicas
                .requestMatchers("/", "/index.html", "/login.html", "/register.html",
                 "/perfil.html", "/dashboard.html", "/admin_dashboard.html",
                 "/favicon.ico").permitAll()   // ← adicionar favicon.ico
                .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()

                // Autenticação e registo
                .requestMatchers("/utilizadores/login", "/utilizadores/registar").permitAll()

                // Gestão de utilizadores — só ADMIN
                .requestMatchers("/utilizadores").hasRole("ADMIN")
                .requestMatchers("/utilizadores/*/cargo").hasRole("ADMIN")

                // Veículos e categorias: leitura pública, escrita só para staff
                .requestMatchers(HttpMethod.GET, "/veiculos/**", "/categorias/**").permitAll()
                .requestMatchers("/veiculos/**", "/categorias/**").hasAnyRole("ADMIN", "FUNCIONARIO")

                // Alugueres exigem autenticação
                .requestMatchers("/alugueres/**").authenticated()

                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
            .authenticationEntryPoint((request, response, authException) ->
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // CORRIGIDO: origins explícitas em vez de "*"
        // Adiciona aqui os endereços onde o teu frontend corre
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:8081",
            "http://localhost:3000",
            "http://127.0.0.1:5500",   // VS Code Live Server
            "http://localhost:5500"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
