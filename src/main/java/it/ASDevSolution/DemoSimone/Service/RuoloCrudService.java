package it.ASDevSolution.DemoSimone.Service;

import it.ASDevSolution.DemoSimone.DTO.RuoloDto;
import it.ASDevSolution.DemoSimone.Model.Ruolo;
import it.ASDevSolution.DemoSimone.ModelConverter.RuoloModelConverter;
import it.ASDevSolution.DemoSimone.Repository.RuoloRepository;
import it.ASDevSolution.DemoSimone.Utils.GenericListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RuoloCrudService {

    @Autowired
    private RuoloRepository ruoloRepository;

    @Autowired
    private RuoloModelConverter ruoloModelConverter;

    @Transactional
    public GenericListDTO<RuoloDto> getAll() {
        GenericListDTO<RuoloDto> list = new GenericListDTO<>();
        List<RuoloDto> collect = ruoloRepository.findAll()
                .stream()
                .map(ruoloModelConverter::modelToDto)
                .collect(Collectors.toList());
        list.setList(collect);
        return list;
    }


    @Transactional
    public RuoloDto get(Integer id) {
        Ruolo model = ruoloRepository.findById(id).orElse(null);
        RuoloDto dto = ruoloModelConverter.modelToDto(model);
        return dto;
    }


    @Transactional
    public RuoloDto create(RuoloDto dto) {
        Ruolo model = ruoloModelConverter.dtoToModel(dto);
        model = ruoloRepository.save(model);
        dto = ruoloModelConverter.modelToDto(model);
        return dto;
    }

    @Transactional
    public void delete(Integer id) {
        ruoloRepository.deleteById(id);
    }

    @Transactional
    public RuoloDto update(Integer id, RuoloDto dto) {
        Ruolo model = ruoloRepository.findById(id).orElse(null);
        ruoloModelConverter.updateToModel(dto,model);
        model = ruoloRepository.save(model);
        dto = ruoloModelConverter.modelToDto(model);
        return dto;
    }

}
