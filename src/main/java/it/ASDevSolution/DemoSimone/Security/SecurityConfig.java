package it.ASDevSolution.DemoSimone.Security;

import it.ASDevSolution.DemoSimone.Security.OAuth2.CustomOAuthUtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration  // Indica che è una classe di configurazione Spring
@EnableWebSecurity  // Abilita il supporto per Spring Security
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter; // filtro JWT per ogni richiesta

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Imposta la politica di gestione della sessione
                .sessionManagement(session ->
                                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        // SessionCreationPolicy.STATELESS significa che Spring Security non creerà né userà sessioni HTTP.
                        // Questo è tipico per API REST che si basano su token (es. JWT) per autenticare ogni richiesta, senza mantenere lo stato sul server.
                        // Ogni richiesta deve quindi portare con sé le credenziali (token).
                )

                // Configura la protezione CSRF (Cross-Site Request Forgery)
                .csrf(csrf ->
                                csrf.disable()
                        // Disabilita la protezione CSRF.
                        // CSRF è importante per applicazioni web con sessioni e form login, perché impedisce attacchi da siti esterni.
                        // Tuttavia, per API REST stateless che usano token (es. JWT), CSRF non è necessario perché non si usano cookie di sessione.
                        // Disabilitarlo evita problemi con richieste API da client esterni.
                )


                // 🔐 AUTORIZZAZIONE: definisce chi può accedere a cosa
                .authorizeHttpRequests(auth -> auth

                        // Le seguenti rotte sono accessibili a tutti (senza login)
                        .requestMatchers("/", "/login", "/error").permitAll()

                        // Tutte le altre rotte richiedono l'autenticazione
                        .anyRequest().authenticated()
                )

                // Login classico con form login (username/password)
                .formLogin(form -> form

                        // Se vuoi una pagina di login personalizzata (opzionale)
                        .loginPage("/login")

                        // Permette a tutti gli utenti (anche non autenticati) di accedere all’endpoint di logout
                        .permitAll()
                )

                // 🔑 LOGIN OAUTH2 (es. Google)
                .oauth2Login(oauth2 -> oauth2

                        // Se vuoi una pagina di login personalizzata (opzionale)
                        .loginPage("/login")

                        // Definisce come caricare le informazioni dell’utente da Google
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuthUtenteService())  // <-- la tua classe CustomOAuthUtenteService
                        )
                )

                // 🔓 LOGOUT
                .logout(logout -> logout

                        // Dopo il logout, reindirizza l’utente alla homepage "/"
                        .logoutSuccessUrl("/")

                        // Permette a tutti gli utenti (anche non autenticati) di accedere all’endpoint di logout
                        .permitAll()
                )

                // Filtro JWT per autenticazione stateless
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 🔧 Fornisce un bean della tua classe che carica i dati utente da Googl
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> customOAuthUtenteService() {
        return new CustomOAuthUtenteService();  // <-- tua classe che implementa loadUser(...)
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}