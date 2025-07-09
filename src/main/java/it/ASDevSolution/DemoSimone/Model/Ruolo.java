package it.ASDevSolution.DemoSimone.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ruolo")
@Data
public class Ruolo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ruolo", nullable = false)
    private Integer idRuolo;

    @Column(name = "nome_ruolo")
    private String nomeRuolo;

}
