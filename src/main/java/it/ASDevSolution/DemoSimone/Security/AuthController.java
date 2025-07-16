package it.ASDevSolution.DemoSimone.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUtenteDetailsService customUtenteDetailsService;


    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {

        // Prendiamo il token di refresh inviato nella richiesta
        String requestRefreshToken = request.getRefreshToken();

        // Verifichiamo se il token di refresh è valido (firma, scadenza, ecc)
        if (jwtUtils.validateToken(requestRefreshToken)) {
            // Estraiamo il nome utente (email) dal token di refresh
            String username = jwtUtils.extractUsername(requestRefreshToken);

            // Carichiamo i dettagli dell'utente dal database tramite il servizio custom
            UserDetails userDetails = customUtenteDetailsService.loadUserByUsername(username);

            // Generiamo un nuovo token di accesso (access token) per l'utente
            String newAccessToken = jwtUtils.generateAccessToken(userDetails);

            // Ritorniamo la risposta con il nuovo access token e il refresh token originale
            return ResponseEntity.ok(new TokenRefreshResponse(newAccessToken, requestRefreshToken));
        } else {
            // Se il token di refresh non è valido, ritorniamo un errore 403 Forbidden
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Refresh Token");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        // Creiamo un token di autenticazione con le credenziali ricevute (email + password)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        // Se l'autenticazione va a buon fine, salviamo il contesto di sicurezza attuale
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Otteniamo i dettagli dell'utente autenticato
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Generiamo il token di accesso (JWT) per l'utente
        String accessToken = jwtUtils.generateAccessToken(userDetails);

        // Generiamo anche il token di refresh per l'utente
        String refreshToken = jwtUtils.generateRefreshToken(userDetails);

        // Ritorniamo la risposta con access token e refresh token al client
        return ResponseEntity.ok(new TokenRefreshResponse(accessToken, refreshToken));
    }
}
