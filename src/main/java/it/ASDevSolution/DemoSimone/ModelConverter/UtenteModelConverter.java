package it.ASDevSolution.DemoSimone.ModelConverter;

import it.ASDevSolution.DemoSimone.DTO.UtenteDto;
import it.ASDevSolution.DemoSimone.Model.Utente;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = {RuoloModelConverter.class}
)
public interface UtenteModelConverter {

    @Mappings({
            @Mapping(source = "ruolo", target = "ruoloDto")
    })
    UtenteDto modelToDto(Utente model);

    @InheritInverseConfiguration
    Utente dtoToModel(UtenteDto dto);

    void updateToModel(UtenteDto dto, @MappingTarget Utente model);

}
