package it.ASDevSolution.DemoSimone.Security;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    //OncePerRequestFilter è una classe base di Spring che garantisce l'esecuzione del filtro una sola volta per ogni richiesta HTTP

    @Autowired
    private JwtUtils jwtUtils; // Classe per firmare e validare JWT

    @Autowired
    private CustomUtenteDetailsService customUtenteDetailsService; // Carica i dati utente dal DB

    //doFilterInternal viene chiamato *una sola volta* per ogni richiesta HTTP, questo comportamento è garantito dal padre OncePerRequestFilter
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException, java.io.IOException {

        // 1️⃣ Legge header Authorization
        final String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // 2️⃣ Verifica che l’header inizi con "Bearer "
        //Esempio di header Authorization con Bearer:
        //Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6...
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            // Estrae l’username (email) dal token
            username = jwtUtils.extractUsername(token);
        }

        // 3️⃣ Se abbiamo un username e non c’è già un’autenticazione in corso
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Carica i dettagli utente
            UserDetails userDetails = customUtenteDetailsService.loadUserByUsername(username);

            // 4️⃣ Verifica che il token sia valido per l’utente
            if (jwtUtils.validateToken(token, userDetails)) {
                // Crea un AuthenticationToken con i ruoli utente
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );
                authToken.setDetails(
                        new org.springframework.security.web.authentication.WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );
                // Imposta l’utente autenticato nel SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continua la catena dei filtri
        filterChain.doFilter(request, response);
    }
}
