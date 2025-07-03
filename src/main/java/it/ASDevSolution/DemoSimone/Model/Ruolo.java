package it.ASDevSolution.DemoSimone.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

@Entity
@Table(name = "comune")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@EqualsAndHashCode
public class Ruolo {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ruolo", nullable = false)
    private Integer idRuolo;

    @Column(name = "nome_ruolo")
    private String nomeRuolo;
}
