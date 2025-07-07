package it.ASDevSolution.DemoSimone.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UtenteDto {

    private Integer idUtente;
    @NotEmpty
    private String nome;
    @NotEmpty
    private String cognome;
    private RuoloDto ruoloDto;
}
