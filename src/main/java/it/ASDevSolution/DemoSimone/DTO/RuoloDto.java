package it.ASDevSolution.DemoSimone.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RuoloDto {

    private Integer idRuolo;
    @NotEmpty
    private String nomeRuolo;
}
