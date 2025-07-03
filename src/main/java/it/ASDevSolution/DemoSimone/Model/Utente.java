package it.ASDevSolution.DemoSimone.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

@Entity
@Table(name = "comune")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@EqualsAndHashCode
public class Utente implements Serializable {

    private static final long serialVersionUID = 1L;

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
}
