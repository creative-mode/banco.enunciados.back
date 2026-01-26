package ao.creativemode.kixi.controller;

import ao.creativemode.kixi.dto.roles.RoleRequest;
import ao.creativemode.kixi.dto.roles.RoleResponse;
import ao.creativemode.kixi.service.RoleService;
import ao.creativemode.kixi.common.exception.ApiException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import java.net.URI;
import java.util.List;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);
    private final RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }

    @GetMapping
    public Mono<ResponseEntity<List<RoleResponse>>> listAllActive() {
        return service.findAllActive()
                .collectList()
                .map(ResponseEntity::ok);
    }

    @GetMapping("/trash")
    public Mono<ResponseEntity<List<RoleResponse>>> listTrashed() {
        return service.findAllDeleted()
                .collectList()
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<RoleResponse>> getById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            LOG.warn("Requisição GET com ID inválido: {}", id);
            return Mono.error(ApiException.badRequest("ID deve ser um número positivo"));
        }

        LOG.debug("Buscando papel por ID: {}", id);
        return service.findByIdActive(id)
                .map(ResponseEntity::ok)
                .doOnSuccess(response -> LOG.debug("Papel encontrado: {}", id))
                .doOnError(error -> LOG.warn("Erro ao buscar papel id={}: {}", id, error.getMessage()));
    }

    @PostMapping
    public Mono<ResponseEntity<RoleResponse>> create(
            @Valid @RequestBody RoleRequest request,
            UriComponentsBuilder uriBuilder) {

        LOG.info("Criando novo papel: name={}", request.name());

        return service.create(request)
                .map(created -> {
                    URI location = uriBuilder
                            .path("/api/v1/roles/{id}")
                            .buildAndExpand(created.id())
                            .toUri();

                    LOG.info("Papel criado com sucesso: id={}, name={}", created.id(), created.name());
                    return ResponseEntity.created(location).body(created);
                })
                .doOnError(error -> LOG.error("Erro ao criar papel: {}", error.getMessage()));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<RoleResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody RoleRequest request) {

        if (id == null || id <= 0) {
            LOG.warn("Requisição PUT com ID inválido: {}", id);
            return Mono.error(ApiException.badRequest("ID deve ser um número positivo"));
        }

        LOG.info("Atualizando papel: id={}, newName={}", id, request.name());

        return service.update(id, request)
                .map(ResponseEntity::ok)
                .doOnSuccess(response -> LOG.info("Papel atualizado: id={}", id))
                .doOnError(error -> LOG.error("Erro ao atualizar papel id={}: {}", id, error.getMessage()));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> softDelete(@PathVariable Long id) {
        if (id == null || id <= 0) {
            LOG.warn("Requisição DELETE com ID inválido: {}", id);
            return Mono.error(ApiException.badRequest("ID deve ser um número positivo"));
        }

        LOG.info("Soft-deletando papel: id={}", id);
        return service.softDelete(id)
                .thenReturn(ResponseEntity.status(NO_CONTENT).build())
                .doOnSuccess(response -> LOG.info("Papel deletado: id={}", id))
                .doOnError(error -> LOG.error("Erro ao deletar papel id={}: {}", id, error.getMessage()));
    }

    @PostMapping("/{id}/restore")
    public Mono<ResponseEntity<Void>> restore(@PathVariable Long id) {
        if (id == null || id <= 0) {
            LOG.warn("Requisição RESTORE com ID inválido: {}", id);
            return Mono.error(ApiException.badRequest("ID deve ser um número positivo"));
        }

        LOG.info("Restaurando papel: id={}", id);
        return service.restore(id)
                .thenReturn(ResponseEntity.ok().build())
                .doOnSuccess(response -> LOG.info("Papel restaurado: id={}", id))
                .doOnError(error -> LOG.error("Erro ao restaurar papel id={}: {}", id, error.getMessage()));
    }
}
