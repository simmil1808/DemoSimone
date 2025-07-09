package it.ASDevSolution.DemoSimone.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "utente")
@Data
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utente", nullable = false)
    private Integer idUtente;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cognome")
    private String cognome;

    @ManyToOne
    @JoinColumn(name = "id_ruolo")
    private Ruolo ruolo;

    @Column(name = "email")
    private String email;
}
