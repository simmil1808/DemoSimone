package it.ASDevSolution.DemoSimone.DTO;

import lombok.Data;

@Data
public class UtenteDto {

    private Integer idUtente;
    private String nome;
    private String cognome;
    private RuoloDto ruoloDto;
}
