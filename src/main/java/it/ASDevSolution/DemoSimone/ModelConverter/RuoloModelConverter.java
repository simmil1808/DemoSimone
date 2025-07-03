package it.ASDevSolution.DemoSimone.ModelConverter;

import it.ASDevSolution.DemoSimone.DTO.RuoloDto;
import it.ASDevSolution.DemoSimone.Model.Ruolo;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring"
)
public interface RuoloModelConverter {

    RuoloDto modelToDto(Ruolo model);

    @InheritInverseConfiguration
    Ruolo dtoToModel(RuoloDto dto);

    void updateToModel(RuoloDto dto, @MappingTarget Ruolo model);

}
