package it.ASDevSolution.DemoSimone.Test;

import it.ASDevSolution.DemoSimone.DTO.RuoloDto;
import it.ASDevSolution.DemoSimone.Model.Ruolo;
import it.ASDevSolution.DemoSimone.ModelConverter.RuoloModelConverter;
import it.ASDevSolution.DemoSimone.Repository.RuoloRepository;
import it.ASDevSolution.DemoSimone.Service.RuoloCrudService;
import it.ASDevSolution.DemoSimone.Utils.GenericListDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RuoloTest {

    //@ExtendWith(MockitoExtension.class)
    //Inizializza tutti i campi annotati con @Mock, @InjectMocks, @Spy, ecc.

    //@Mock
    //Serve a creare un oggetto finto (mock) di una dipendenza.

    //@InjectMocks
    //Serve a creare un'istanza reale della classe da testare, e iniettarvi automaticamente i mock delle sue dipendenze.

    @InjectMocks
    private RuoloCrudService ruoloCrudService;

    @Mock
    private RuoloRepository ruoloRepository;

    @Mock
    private RuoloModelConverter ruoloModelConverter;


    @Test
    void testGetAll() {
        // Mock dati
        Ruolo ruolo1 = new Ruolo(); ruolo1.setIdRuolo(1);
        Ruolo ruolo2 = new Ruolo(); ruolo2.setIdRuolo(2);
        RuoloDto dto1 = new RuoloDto(); dto1.setIdRuolo(1);
        RuoloDto dto2 = new RuoloDto(); dto2.setIdRuolo(2);

        // Simula il comportamento del repository: quando viene chiamato findAll(),
        // restituisce una lista contenente ruolo1 e ruolo2
        when(ruoloRepository.findAll()).thenReturn(Arrays.asList(ruolo1, ruolo2));

        // Simula la conversione da modello a DTO per ruolo1
        // quando viene chiamato modelToDto(ruolo1), restituisce dto1
        when(ruoloModelConverter.modelToDto(ruolo1)).thenReturn(dto1);
        when(ruoloModelConverter.modelToDto(ruolo2)).thenReturn(dto2);

        // Esegui metodo
        GenericListDTO<RuoloDto> result = ruoloCrudService.getAll();

        // Verifica

        // Verifica che il risultato non sia null
        assertNotNull(result);

        // Ottiene la lista di DTO dal risultato del servizio
        List<RuoloDto> list = result.getList();

        // Verifica che la lista contenga esattamente 2 elementi
        assertEquals(2, list.size());

        // Controlla che il primo DTO abbia id = 1/2
        assertEquals(1, list.get(0).getIdRuolo());
        assertEquals(2, list.get(1).getIdRuolo());
    }

    @Test
    void testGetById() {
        Ruolo ruolo = new Ruolo(); ruolo.setIdRuolo(1);
        RuoloDto dto = new RuoloDto(); dto.setIdRuolo(1);

        when(ruoloRepository.findById(1)).thenReturn(Optional.of(ruolo));
        when(ruoloModelConverter.modelToDto(ruolo)).thenReturn(dto);

        RuoloDto result = ruoloCrudService.get(1);

        assertNotNull(result);
        assertEquals(1, result.getIdRuolo());
    }

    @Test
    void testCreate() {
        RuoloDto ruoloDto = new RuoloDto();
        Ruolo model = new Ruolo();
        Ruolo saved = new Ruolo(); saved.setIdRuolo(10);
        RuoloDto resultDto = new RuoloDto(); resultDto.setIdRuolo(10);

        when(ruoloModelConverter.dtoToModel(ruoloDto)).thenReturn(model);

        // Perché il save deve restituire un modello (model) diverso?
        // Simuliamo il comportamento reale di save:
        // Nel mondo reale, quando chiami save su un repository Spring Data JPA, di solito:
        // Passi un oggetto model senza ID (ad esempio, un nuovo oggetto da inserire).
        // Dopo il salvataggio, il database assegna un ID (o modifica qualche campo, come timestamp).
        // Il metodo save ritorna una nuova istanza o la stessa istanza con l'ID impostato (quindi diverso da quello passato in input).
        when(ruoloRepository.save(model)).thenReturn(saved);

        when(ruoloModelConverter.modelToDto(saved)).thenReturn(resultDto);

        RuoloDto result = ruoloCrudService.create(ruoloDto);

        assertEquals(10, result.getIdRuolo());
    }

    @Test
    void testDelete() {

        ruoloCrudService.delete(5);

        // Verifica che il metodo deleteById sia stato chiamato esattamente una volta con l'ID 5.
        // Questo assicura che la logica di cancellazione stia invocando correttamente il repository.
        verify(ruoloRepository, times(1)).deleteById(5);
    }

    @Test
    void testUpdate() {
        int id = 7;
        RuoloDto inputDto = new RuoloDto(); inputDto.setIdRuolo(7);
        Ruolo model = new Ruolo(); model.setIdRuolo(7);
        Ruolo updated = new Ruolo(); updated.setIdRuolo(7);
        RuoloDto resultDto = new RuoloDto(); resultDto.setIdRuolo(7);

        when(ruoloRepository.findById(id)).thenReturn(Optional.of(model));

        // perché ci interessa solo testare il comportamento del servizio senza eseguire la logica interna del converter.
        doNothing().when(ruoloModelConverter).updateToModel(inputDto, model);

        when(ruoloRepository.save(model)).thenReturn(updated);
        when(ruoloModelConverter.modelToDto(updated)).thenReturn(resultDto);

        RuoloDto result = ruoloCrudService.update(id, inputDto);

        assertNotNull(result);
        assertEquals(7, result.getIdRuolo());
    }
}
