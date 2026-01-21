package ao.creativemode.kixi.service;

import java.time.LocalDateTime;

import ao.creativemode.kixi.model.AnosLetivos;
import ao.creativemode.kixi.repository.AnosLetivosRepository;
import ao.creativemode.kixi.anosletivos.dto.request.CreateAnosLetivosRequest;
import ao.creativemode.kixi.anosletivos.dto.request.UpdateAnosLetivosRequest;
import ao.creativemode.kixi.anosletivos.dto.response.AnosLetivosResponse;
import ao.creativemode.kixi.common.exception.ApiException;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AnosLetivosService {

    private final AnosLetivosRepository repository;

    public AnosLetivosService(AnosLetivosRepository repository) {
        this.repository = repository;
    }

    /* ======================= GET ======================= */

    public Flux<AnosLetivosResponse> getAll() {
        return repository.findAllByDeletedFalse()
                .map(this::toResponse);
    }

    public Mono<AnosLetivosResponse> getById(Long id) {
        return repository.findByIdAndDeletedFalse(id)
                .switchIfEmpty(Mono.error(
                        ApiException.notFound("Ano letivo não encontrado")
                ))
                .map(this::toResponse);
    }

    /* ======================= CREATE ======================= */

    public Mono<AnosLetivosResponse> create(CreateAnosLetivosRequest dto) {
        AnosLetivos entity = new AnosLetivos();
        entity.setAnoInicio(dto.anoInicio());
        entity.setAnoFim(dto.anoFim());
        entity.setDeleted(false);
        entity.setDeletedAt(null);

        return repository.save(entity)
                .map(this::toResponse);
    }

    /* ======================= UPDATE ======================= */

    public Mono<AnosLetivosResponse> update(Long id, UpdateAnosLetivosRequest dto) {
        return repository.findByIdAndDeletedFalse(id)
                .switchIfEmpty(Mono.error(
                        ApiException.notFound("Ano letivo não encontrado")
                ))
                .flatMap(entity -> {
                    entity.setAnoInicio(dto.anoInicio());
                    entity.setAnoFim(dto.anoFim());
                    return repository.save(entity);
                })
                .map(this::toResponse);
    }

    /* ======================= SOFT DELETE ======================= */

    public Mono<Void> delete(Long id) {
        return repository.findByIdAndDeletedFalse(id)
                .switchIfEmpty(Mono.error(
                        ApiException.notFound("Ano letivo não encontrado")
                ))
                .flatMap(entity -> {
                    entity.setDeleted(true);
                    entity.setDeletedAt(LocalDateTime.now());
                    return repository.save(entity);
                })
                .then();
    }

    public Mono<Void> purge(Long id) {
    return repository.findById(id)
            .switchIfEmpty(
                    Mono.error(ApiException.notFound("Ano letivo não encontrado"))
            )
            .flatMap(repository::delete);
}


    /* ======================= RESTORE ======================= */

    public Mono<Void> restore(Long id) {
        return repository.findByIdAndDeletedTrue(id)
                .switchIfEmpty(Mono.error(
                        ApiException.notFound("Ano letivo não está deletado")
                ))
                .flatMap(entity -> {
                    entity.setDeleted(false);
                    entity.setDeletedAt(null);
                    return repository.save(entity);
                })
                .then();
    }

    /* ======================= MAPPER ======================= */

    private AnosLetivosResponse toResponse(AnosLetivos entity) {
        return new AnosLetivosResponse(
                entity.getId(),
                entity.getAnoInicio(),
                entity.getAnoFim()
        );
    }
}
