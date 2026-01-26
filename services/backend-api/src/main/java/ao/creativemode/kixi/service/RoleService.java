package ao.creativemode.kixi.service;

import ao.creativemode.kixi.model.Role;
import ao.creativemode.kixi.repository.RoleRepository;
import ao.creativemode.kixi.dto.roles.RoleResponse;
import ao.creativemode.kixi.dto.roles.RoleRequest;
import ao.creativemode.kixi.common.exception.ApiException;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.dao.DataIntegrityViolationException;

@Service
public class RoleService {

    private static final Logger LOG = LoggerFactory.getLogger(RoleService.class);
    private static final String MSG_ROLE_NOT_FOUND = "Papel não encontrado";
    private static final String MSG_NAME_EXISTS = "Um papel com o nome '%s' já existe";
    private static final int RETRY_ATTEMPTS = 1;

    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public Flux<RoleResponse> findAllActive() {
        return repository.findAllByDeletedAtIsNull()
                .map(this::toResponse);
    }

    public Flux<RoleResponse> findAllDeleted() {
        return repository.findAllByDeletedAtIsNotNull()
                .map(this::toResponse);
    }

    public Mono<RoleResponse> findByIdActive(Long id) {
        return repository.findByIdAndDeletedAtIsNull(id)
                .switchIfEmpty(Mono.error(ApiException.notFound(MSG_ROLE_NOT_FOUND)))
                .map(this::toResponse);
    }

    public Mono<RoleResponse> create(RoleRequest dto) {
        if (!StringUtils.hasText(dto.name())) {
            LOG.warn("Tentativa de criar papel com nome vazio");
            return Mono.error(ApiException.badRequest("Nome é obrigatório"));
        }

        String normalizedName = dto.name().trim().toUpperCase();
        LOG.info("Criando novo papel: {}", normalizedName);

        return repository.findByNameAndDeletedAtIsNull(normalizedName)
                .flatMap(existing -> {
                    LOG.warn("Papel já existe: {}", normalizedName);
                    return Mono.error(ApiException.conflict(String.format(MSG_NAME_EXISTS, normalizedName)));
                })
                .switchIfEmpty(
                        Mono.defer(() -> {
                            Role entity = new Role(normalizedName, dto.description());
                            return repository.save(entity)
                                    .retry(RETRY_ATTEMPTS)
                                    .map(this::toResponse)
                                    .doOnSuccess(result -> LOG.info("Papel criado com sucesso: id={}, name={}", result.id(), result.name()))
                                    .onErrorMap(DataIntegrityViolationException.class, e -> {
                                        LOG.error("Erro de integridade ao criar papel: {}", normalizedName, e);
                                        return ApiException.conflict(String.format(MSG_NAME_EXISTS, normalizedName));
                                    });
                        })
                );
    }

    public Mono<RoleResponse> update(Long id, RoleRequest dto) {
        if (id == null || id <= 0) {
            LOG.warn("Tentativa de atualizar com ID inválido: {}", id);
            return Mono.error(ApiException.badRequest("ID inválido"));
        }

        String normalizedName = dto.name() != null ? dto.name().trim().toUpperCase() : null;
        LOG.info("Atualizando papel: id={}, newName={}", id, normalizedName);

        return repository.findByIdAndDeletedAtIsNull(id)
                .switchIfEmpty(Mono.error(ApiException.notFound(MSG_ROLE_NOT_FOUND)))
                .flatMap(entity -> checkNameUniqueness(normalizedName, id)
                        .then(Mono.defer(() -> {
                            entity.setName(normalizedName);
                            entity.setDescription(dto.description());
                            return repository.save(entity)
                                    .retry(RETRY_ATTEMPTS)
                                    .doOnSuccess(result -> LOG.info("Papel atualizado: id={}, name={}", result.getId(), result.getName()));
                        })))
                .map(this::toResponse)
                .onErrorMap(DataIntegrityViolationException.class, e -> {
                    LOG.error("Erro de integridade ao atualizar papel: id={}, name={}", id, normalizedName, e);
                    return ApiException.conflict(String.format(MSG_NAME_EXISTS, normalizedName));
                });
    }

    public Mono<Void> softDelete(Long id) {
        return repository.findByIdAndDeletedAtIsNull(id)
                .switchIfEmpty(Mono.error(ApiException.notFound(MSG_ROLE_NOT_FOUND)))
                .flatMap(entity -> {
                    entity.markAsDeleted();
                    return repository.save(entity);
                })
                .then();
    }

    public Mono<Void> restore(Long id) {
        return repository.findByIdAndDeletedAtIsNotNull(id)
                .switchIfEmpty(Mono.error(ApiException.badRequest("Papel não está deletado")))
                .flatMap(entity -> {
                    entity.restore();
                    return repository.save(entity);
                })
                .then();
    }

    private Mono<Void> checkNameUniqueness(String name, Long id) {
        if (!StringUtils.hasText(name)) {
            return Mono.error(ApiException.badRequest("Nome não pode estar vazio"));
        }

        return repository.findByNameAndIdNotAndDeletedAtIsNull(name, id)
                .flatMap(existing -> {
                    LOG.warn("Nome já em uso por outro papel: {} (id={})", name, existing.getId());
                    return Mono.error(ApiException.conflict(String.format(MSG_NAME_EXISTS, name)));
                })
                .then();
    }

    private RoleResponse toResponse(Role entity) {
        return new RoleResponse(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }
}
