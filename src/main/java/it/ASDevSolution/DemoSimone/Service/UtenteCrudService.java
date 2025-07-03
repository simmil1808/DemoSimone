package it.ASDevSolution.DemoSimone.Service;

import it.ASDevSolution.DemoSimone.DTO.UtenteDto;
import it.ASDevSolution.DemoSimone.Model.Utente;
import it.ASDevSolution.DemoSimone.ModelConverter.UtenteModelConverter;
import it.ASDevSolution.DemoSimone.Repository.UtenteRepository;
import it.ASDevSolution.DemoSimone.Utils.GenericListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtenteCrudService {
    
    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private UtenteModelConverter utenteModelConverter;

    @Transactional
    public GenericListDTO<UtenteDto> getAll() {
        GenericListDTO<UtenteDto> list = new GenericListDTO<>();
        List<UtenteDto> collect = utenteRepository.findAll()
                .stream()
                .map(utenteModelConverter::modelToDto)
                .collect(Collectors.toList());
        list.setList(collect);
        return list;
    }


    @Transactional
    public UtenteDto get(Integer id) {
        Utente model = utenteRepository.findById(id).orElse(null);
        UtenteDto dto = utenteModelConverter.modelToDto(model);
        return dto;
    }


    @Transactional
    public UtenteDto create(UtenteDto dto) {
        Utente model = utenteModelConverter.dtoToModel(dto);
        model = utenteRepository.save(model);
        dto = utenteModelConverter.modelToDto(model);
        return dto;
    }

    @Transactional
    public void delete(Integer id) {
        utenteRepository.deleteById(id);
    }

    @Transactional
    public UtenteDto update(Integer id, UtenteDto dto) {
        Utente model = utenteRepository.findById(id).orElse(null);
        utenteModelConverter.updateToModel(dto,model);
        model = utenteRepository.save(model);
        dto = utenteModelConverter.modelToDto(model);
        return dto;
    }
    
}
