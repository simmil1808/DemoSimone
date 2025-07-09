package it.ASDevSolution.DemoSimone.Repository;

import it.ASDevSolution.DemoSimone.Model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Integer> {

    Optional<Utente> findByEmail(String email);
}
