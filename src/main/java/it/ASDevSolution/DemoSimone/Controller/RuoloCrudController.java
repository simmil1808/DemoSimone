package it.ASDevSolution.DemoSimone.Controller;

import it.ASDevSolution.DemoSimone.DTO.RuoloDto;
import it.ASDevSolution.DemoSimone.DTO.UtenteDto;
import it.ASDevSolution.DemoSimone.Service.RuoloCrudService;
import it.ASDevSolution.DemoSimone.Service.UtenteCrudService;
import it.ASDevSolution.DemoSimone.Utils.GenericListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static it.ASDevSolution.DemoSimone.Constants.CrudControllerConstants.*;
import static it.ASDevSolution.DemoSimone.Constants.CrudControllerConstants.CREATE;

@RestController
@RequestMapping(path = "/ruolo")
public class RuoloCrudController {

    @Autowired
    private RuoloCrudService ruoloCrudService;


    //@GetMapping(value = READ_ALL, produces = MediaType.APPLICATION_JSON_VALUE)

    @GetMapping(value = READ_ALL)
    public ResponseEntity<GenericListDTO<RuoloDto>> getAll() {
        return ResponseEntity.ok(ruoloCrudService.getAll());
    }

    @GetMapping(value = GET)
    public ResponseEntity<RuoloDto> get(@PathVariable(name = "id") Integer id)  {
        return ResponseEntity.ok(ruoloCrudService.get(id));
    }

    @PutMapping(value = UPDATE)
    public ResponseEntity<RuoloDto> update(@PathVariable(name = "id") Integer id, @RequestBody RuoloDto dto)  {
        return ResponseEntity.ok(ruoloCrudService.update(id, dto));
    }

    @PostMapping(value = CREATE)
    public ResponseEntity<RuoloDto> create(@RequestBody RuoloDto dto)  {
        return ResponseEntity.ok(ruoloCrudService.create(dto));
    }

    /*@DeleteMapping(value = DELETE)
    public ResponseEntity<GenericMessageDTO> delete(@PathVariable(name = "id") Integer id)  {
        ruoloCrudService.delete(id);
        return ResponseEntity.ok(new GenericMessageDTO("OK"));
    }*/
}
