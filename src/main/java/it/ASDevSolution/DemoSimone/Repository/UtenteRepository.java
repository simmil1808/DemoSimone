package it.ASDevSolution.DemoSimone.Repository;

import it.ASDevSolution.DemoSimone.Model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteRepository extends JpaRepository<Utente, Integer> {

}
