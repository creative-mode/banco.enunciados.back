package ao.creativemode.kixi.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ao.creativemode.kixi.anosletivos.dto.request.CreateAnosLetivosRequest;
import ao.creativemode.kixi.anosletivos.dto.request.UpdateAnosLetivosRequest;
import ao.creativemode.kixi.anosletivos.dto.response.AnosLetivosResponse;
import ao.creativemode.kixi.common.dto.ApiResponse;
import ao.creativemode.kixi.service.AnosLetivosService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/anos-letivos")
public class AnosLetivosController {

    private final AnosLetivosService service;

    public AnosLetivosController(AnosLetivosService service) {
        this.service = service;
    }

    @GetMapping
    public Mono<ResponseEntity<ApiResponse<Iterable<AnosLetivosResponse>>>> getAll() {
        return service.getAll()
            .collectList()
            .map(list -> ResponseEntity.ok(ApiResponse.sucesso(list)));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<AnosLetivosResponse>>> getById(@PathVariable Long id) {
        return service.getById(id)
            .map(dto -> ResponseEntity.ok(ApiResponse.sucesso(dto)))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<ApiResponse<AnosLetivosResponse>>> create(@Valid @RequestBody CreateAnosLetivosRequest dto) {
        return service.create(dto)
            .map(saved -> ResponseEntity.status(201).body(ApiResponse.sucesso(saved, "Ano letivo criado com sucesso")));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<AnosLetivosResponse>>> update(@PathVariable Long id, @Valid @RequestBody UpdateAnosLetivosRequest dto) {
        return service.update(id, dto)
            .map(updated -> ResponseEntity.ok(ApiResponse.sucesso(updated, "Ano letivo atualizado com sucesso")))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<Void>>> delete(@PathVariable Long id) {
        return service.delete(id)
            .thenReturn(ResponseEntity.ok(ApiResponse.sucesso(null, "Ano letivo removido com sucesso")));
    }
}
