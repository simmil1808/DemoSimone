package it.ASDevSolution.DemoSimone.Service;

import it.ASDevSolution.DemoSimone.Model.Ruolo;
import it.ASDevSolution.DemoSimone.Model.Utente;
import it.ASDevSolution.DemoSimone.Repository.RuoloRepository;
import it.ASDevSolution.DemoSimone.Repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

import static it.ASDevSolution.DemoSimone.Constants.RuoloConstants.*;

@Service
public class CustomOAuthUtenteService extends DefaultOAuth2UserService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private RuoloRepository ruoloRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {

        // 1. Carica i dati utente dal provider OAuth2 (es. Google)
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 2. Estrai gli attributi (email, nome, ecc) ricevuti dal provider
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String nome = (String) attributes.get("name");

        // 3. Cerca nel DB un utente con quell'email, se non c'è lo crea con ruolo USER
        Utente utente = utenteRepository.findByEmail(email).orElseGet(() -> {
            Utente nuovoUtente = new Utente();
            nuovoUtente.setEmail(email);
            nuovoUtente.setNome(nome);

            // Associa ruolo USER
            Ruolo ruolo = ruoloRepository.findByNomeRuolo(USER);
            nuovoUtente.setRuolo(ruolo);

            return utenteRepository.save(nuovoUtente);
        });

        // 4. Crea un DefaultOAuth2User con:
        // - l'autorità ROLE_USER (per autorizzazioni Spring)
        // - gli attributi presi dal provider (email, nome, ecc)
        // - "email" come chiave per identificare l'utente nella sessione di sicurezza
        return new DefaultOAuth2User(
                Set.of(new SimpleGrantedAuthority(utente.getRuolo().getNomeRuolo())),
                oAuth2User.getAttributes(),
                "email"
        );

    }
}
