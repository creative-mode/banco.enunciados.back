package ao.creativemode.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ao.creativemode.dto.anosletivos.AnosLetivosRequestDTO;
import ao.creativemode.dto.anosletivos.AnosLetivosResponseDTO;
import ao.creativemode.service.AnosLetivosService;
import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/anos")
public class AnosLetivosController {

    private final AnosLetivosService service;

    public AnosLetivosController(AnosLetivosService service) {
        this.service = service;
    }

    @GetMapping
    public Flux<AnosLetivosResponseDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Mono<AnosLetivosResponseDTO> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Mono<AnosLetivosResponseDTO> create(@Valid @RequestBody AnosLetivosRequestDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public Mono<AnosLetivosResponseDTO> update(@PathVariable Long id, @Valid @RequestBody AnosLetivosRequestDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
