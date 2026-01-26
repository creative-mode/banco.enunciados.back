package ao.creativemode.kixi.service;

import ao.creativemode.kixi.common.exception.ApiException;
import ao.creativemode.kixi.dto.roles.RoleRequest;
import ao.creativemode.kixi.dto.roles.RoleResponse;
import ao.creativemode.kixi.model.Role;
import ao.creativemode.kixi.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RoleServiceTest {

    @Mock
    private RoleRepository repository;

    @InjectMocks
    private RoleService service;

    private Role roleEntity;
    private RoleRequest roleRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        roleEntity = new Role("ADMIN", "Administrador do sistema");
        roleEntity.setId(1L);
        roleEntity.setCreatedAt(LocalDateTime.now());
        roleEntity.setUpdatedAt(LocalDateTime.now());
        roleEntity.setDeletedAt(null);

        roleRequest = new RoleRequest("ADMIN", "Administrador do sistema");
    }

    @Test
    void testFindAllActive() {
        when(repository.findAllByDeletedAtIsNull()).thenReturn(Flux.just(roleEntity));

        StepVerifier.create(service.findAllActive())
                .expectNextMatches(response -> response.id().equals(1L) && response.name().equals("ADMIN"))
                .verifyComplete();

        verify(repository, times(1)).findAllByDeletedAtIsNull();
    }

    @Test
    void testFindAllDeleted() {
        when(repository.findAllByDeletedAtIsNotNull()).thenReturn(Flux.just(roleEntity));

        StepVerifier.create(service.findAllDeleted())
                .expectNextMatches(response -> response.id().equals(1L) && response.name().equals("ADMIN"))
                .verifyComplete();

        verify(repository, times(1)).findAllByDeletedAtIsNotNull();
    }

    @Test
    void testFindByIdActive_Success() {
        when(repository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Mono.just(roleEntity));

        StepVerifier.create(service.findByIdActive(1L))
                .expectNextMatches(response -> response.id().equals(1L) && response.name().equals("ADMIN"))
                .verifyComplete();

        verify(repository, times(1)).findByIdAndDeletedAtIsNull(1L);
    }

    @Test
    void testFindByIdActive_NotFound() {
        when(repository.findByIdAndDeletedAtIsNull(999L)).thenReturn(Mono.empty());

        StepVerifier.create(service.findByIdActive(999L))
                .expectErrorMatches(e -> e instanceof ApiException)
                .verify();

        verify(repository, times(1)).findByIdAndDeletedAtIsNull(999L);
    }

    @Test
    void testCreate_Success() {
        when(repository.findByNameAndDeletedAtIsNull("ADMIN")).thenReturn(Mono.empty());
        when(repository.save(any(Role.class))).thenReturn(Mono.just(roleEntity));

        StepVerifier.create(service.create(roleRequest))
                .expectNextMatches(response -> response.name().equals("ADMIN"))
                .verifyComplete();

        verify(repository, times(1)).findByNameAndDeletedAtIsNull("ADMIN");
        verify(repository, times(1)).save(any(Role.class));
    }

    @Test
    void testCreate_NameAlreadyExists() {
        when(repository.findByNameAndDeletedAtIsNull("ADMIN")).thenReturn(Mono.just(roleEntity));

        StepVerifier.create(service.create(roleRequest))
                .expectErrorMatches(e -> e instanceof ApiException)
                .verify();

        verify(repository, times(1)).findByNameAndDeletedAtIsNull("ADMIN");
        verify(repository, never()).save(any(Role.class));
    }

    @Test
    void testUpdate_Success() {
        Role updatedEntity = new Role("EDITOR", "Editor do sistema");
        updatedEntity.setId(1L);
        updatedEntity.setCreatedAt(LocalDateTime.now());
        updatedEntity.setUpdatedAt(LocalDateTime.now());

        when(repository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Mono.just(roleEntity));
        when(repository.findByNameAndIdNotAndDeletedAtIsNull("EDITOR", 1L)).thenReturn(Mono.empty());
        when(repository.save(any(Role.class))).thenReturn(Mono.just(updatedEntity));

        RoleRequest updateRequest = new RoleRequest("EDITOR", "Editor do sistema");

        StepVerifier.create(service.update(1L, updateRequest))
                .expectNextMatches(response -> response.name().equals("EDITOR"))
                .verifyComplete();

        verify(repository, times(1)).findByIdAndDeletedAtIsNull(1L);
        verify(repository, times(1)).findByNameAndIdNotAndDeletedAtIsNull("EDITOR", 1L);
        verify(repository, times(1)).save(any(Role.class));
    }

    @Test
    void testUpdate_NotFound() {
        when(repository.findByIdAndDeletedAtIsNull(999L)).thenReturn(Mono.empty());

        StepVerifier.create(service.update(999L, roleRequest))
                .expectErrorMatches(e -> e instanceof ApiException)
                .verify();

        verify(repository, times(1)).findByIdAndDeletedAtIsNull(999L);
        verify(repository, never()).save(any(Role.class));
    }

    @Test
    void testUpdate_NameAlreadyExists() {
        Role anotherRole = new Role("EDITOR", "Editor do sistema");
        anotherRole.setId(2L);

        when(repository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Mono.just(roleEntity));
        when(repository.findByNameAndIdNotAndDeletedAtIsNull("EDITOR", 1L)).thenReturn(Mono.just(anotherRole));

        RoleRequest updateRequest = new RoleRequest("EDITOR", "Editor do sistema");

        StepVerifier.create(service.update(1L, updateRequest))
                .expectErrorMatches(e -> e instanceof ApiException)
                .verify();

        verify(repository, times(1)).findByIdAndDeletedAtIsNull(1L);
        verify(repository, times(1)).findByNameAndIdNotAndDeletedAtIsNull("EDITOR", 1L);
        verify(repository, never()).save(any(Role.class));
    }

    @Test
    void testSoftDelete_Success() {
        when(repository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Mono.just(roleEntity));
        when(repository.save(any(Role.class))).thenReturn(Mono.just(roleEntity));

        StepVerifier.create(service.softDelete(1L))
                .verifyComplete();

        verify(repository, times(1)).findByIdAndDeletedAtIsNull(1L);
        verify(repository, times(1)).save(any(Role.class));
    }

    @Test
    void testSoftDelete_NotFound() {
        when(repository.findByIdAndDeletedAtIsNull(999L)).thenReturn(Mono.empty());

        StepVerifier.create(service.softDelete(999L))
                .expectErrorMatches(e -> e instanceof ApiException)
                .verify();

        verify(repository, times(1)).findByIdAndDeletedAtIsNull(999L);
        verify(repository, never()).save(any(Role.class));
    }

    @Test
    void testRestore_Success() {
        roleEntity.markAsDeleted();

        when(repository.findByIdAndDeletedAtIsNotNull(1L)).thenReturn(Mono.just(roleEntity));
        when(repository.save(any(Role.class))).thenReturn(Mono.just(roleEntity));

        StepVerifier.create(service.restore(1L))
                .verifyComplete();

        verify(repository, times(1)).findByIdAndDeletedAtIsNotNull(1L);
        verify(repository, times(1)).save(any(Role.class));
    }

    @Test
    void testRestore_NotDeleted() {
        when(repository.findByIdAndDeletedAtIsNotNull(1L)).thenReturn(Mono.empty());

        StepVerifier.create(service.restore(1L))
                .expectErrorMatches(e -> e instanceof ApiException)
                .verify();

        verify(repository, times(1)).findByIdAndDeletedAtIsNotNull(1L);
        verify(repository, never()).save(any(Role.class));
    }
}
