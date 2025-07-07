package it.ASDevSolution.DemoSimone.Controller;

import it.ASDevSolution.DemoSimone.DTO.UtenteDto;
import it.ASDevSolution.DemoSimone.Service.UtenteCrudService;
import it.ASDevSolution.DemoSimone.Utils.GenericListDTO;
import it.ASDevSolution.DemoSimone.Utils.GenericMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static it.ASDevSolution.DemoSimone.Constants.CrudControllerConstants.*;


@RestController
@RequestMapping(path = "/utente")
public class UtenteCrudController {

    @Autowired
    private UtenteCrudService utenteCrudService;


    //@GetMapping(value = READ_ALL, produces = MediaType.APPLICATION_JSON_VALUE)

    @GetMapping(value = READ_ALL)
    public ResponseEntity<GenericListDTO<UtenteDto>> getAll() {
        return ResponseEntity.ok(utenteCrudService.getAll());
    }

    @GetMapping(value = GET)
    public ResponseEntity<UtenteDto> get(@PathVariable(name = "id") Integer id)  {
        return ResponseEntity.ok(utenteCrudService.get(id));
    }

    @PutMapping(value = UPDATE)
    public ResponseEntity<UtenteDto> update(@PathVariable(name = "id") Integer id, @RequestBody UtenteDto dto)  {
        return ResponseEntity.ok(utenteCrudService.update(id, dto));
    }

    @PostMapping(value = CREATE)
    public ResponseEntity<UtenteDto> create(@RequestBody UtenteDto dto)  {
        return ResponseEntity.ok(utenteCrudService.create(dto));
    }

    /*@DeleteMapping(value = DELETE)
    public ResponseEntity<GenericMessageDTO> delete(@PathVariable(name = "id") Integer id)  {
        utenteCrudService.delete(id);
        return ResponseEntity.ok(new GenericMessageDTO("OK"));
    }*/
}
