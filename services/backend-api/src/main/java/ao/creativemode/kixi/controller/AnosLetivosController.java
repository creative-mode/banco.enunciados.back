package ao.creativemode.kixi.controller;

import java.util.List;

import ao.creativemode.kixi.anosletivos.dto.request.CreateAnosLetivosRequest;
import ao.creativemode.kixi.anosletivos.dto.request.UpdateAnosLetivosRequest;
import ao.creativemode.kixi.anosletivos.dto.response.AnosLetivosResponse;
import ao.creativemode.kixi.common.dto.ApiResponse;
import ao.creativemode.kixi.service.AnosLetivosService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/anos-letivos")
public class AnosLetivosController {

    private final AnosLetivosService service;

    public AnosLetivosController(AnosLetivosService service) {
        this.service = service;
    }

    @GetMapping
    public Mono<ResponseEntity<ApiResponse<List<AnosLetivosResponse>>>> getAll() {
        return service.getAll()
                .collectList()
                .map(list -> ResponseEntity.ok(
                        ApiResponse.<List<AnosLetivosResponse>>sucesso(list)));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<AnosLetivosResponse>>> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(resp -> ResponseEntity.ok(
                        ApiResponse.<AnosLetivosResponse>sucesso(resp)))
                .switchIfEmpty(
                        Mono.just(
                                ResponseEntity.status(404)
                                        .body(ApiResponse.<AnosLetivosResponse>erro(
                                                "Ano letivo não encontrado",
                                                null))));
    }

    @PostMapping
    public Mono<ResponseEntity<ApiResponse<AnosLetivosResponse>>> create(
            @Valid @RequestBody CreateAnosLetivosRequest dto) {

        return service.create(dto)
                .map(resp -> ResponseEntity.status(201)
                        .body(ApiResponse.<AnosLetivosResponse>sucesso(
                                resp,
                                "Ano letivo criado com sucesso")));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<AnosLetivosResponse>>> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAnosLetivosRequest dto) {

        return service.update(id, dto)
                .map(resp -> ResponseEntity.ok(
                        ApiResponse.<AnosLetivosResponse>sucesso(
                                resp,
                                "Ano letivo atualizado com sucesso")))
                .switchIfEmpty(
                        Mono.just(
                                ResponseEntity.status(404)
                                        .body(ApiResponse.<AnosLetivosResponse>erro(
                                                "Ano letivo não encontrado",
                                                null))));
    }

    @DeleteMapping("/{id}/purge")
    public Mono<ResponseEntity<ApiResponse<Void>>> purge(@PathVariable Long id) {
        return service.purge(id)
                .thenReturn(
                        ResponseEntity.ok(
                                ApiResponse.sucesso(
                                        null,
                                        "Ano letivo removido permanentemente")));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<Void>>> delete(@PathVariable Long id) {
        return service.delete(id)
                .thenReturn(
                        ResponseEntity.ok(
                                ApiResponse.<Void>sucesso(
                                        null,
                                        "Ano letivo removido com sucesso")))
                .onErrorResume(ex -> Mono.just(
                        ResponseEntity.status(500)
                                .body(ApiResponse.<Void>erro(
                                        ex.getMessage(),
                                        null))));
    }
}
