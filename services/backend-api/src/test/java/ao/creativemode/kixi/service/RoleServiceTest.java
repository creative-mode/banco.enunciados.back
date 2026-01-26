package ao.creativemode.kixi.service;

import ao.creativemode.kixi.common.exception.ApiException;
import ao.creativemode.kixi.dto.roles.RoleRequest;
import ao.creativemode.kixi.dto.roles.RoleResponse;
import ao.creativemode.kixi.model.Role;
import ao.creativemode.kixi.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository repository;

    private RoleService roleService;

    @BeforeEach
    void setUp() {
        roleService = new RoleService(repository);
    }

    @Test
    void testFindAllActive() {
        Role role1 = new Role(1L, "ADMIN", "Administrator", LocalDateTime.now(), LocalDateTime.now(), null);
        Role role2 = new Role(2L, "USER", "Standard User", LocalDateTime.now(), LocalDateTime.now(), null);

        when(repository.findAllByDeletedAtIsNull())
            .thenReturn(Flux.just(role1, role2));

        StepVerifier.create(roleService.findAllActive())
            .expectNextCount(2)
            .verifyComplete();

        verify(repository, times(1)).findAllByDeletedAtIsNull();
    }

    @Test
    void testFindAllDeleted() {
        Role role = new Role(1L, "ADMIN", "Administrator", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());

        when(repository.findAllByDeletedAtIsNotNull())
            .thenReturn(Flux.just(role));

        StepVerifier.create(roleService.findAllDeleted())
            .expectNextCount(1)
            .verifyComplete();

        verify(repository, times(1)).findAllByDeletedAtIsNotNull();
    }

    @Test
    void testFindByIdActive_Success() {
        Long roleId = 1L;
        Role role = new Role(roleId, "ADMIN", "Administrator", LocalDateTime.now(), LocalDateTime.now(), null);

        when(repository.findByIdAndDeletedAtIsNull(roleId))
            .thenReturn(Mono.just(role));

        StepVerifier.create(roleService.findByIdActive(roleId))
            .expectNextMatches(response -> response.id().equals(roleId) && response.name().equals("ADMIN"))
            .verifyComplete();

        verify(repository, times(1)).findByIdAndDeletedAtIsNull(roleId);
    }

    @Test
    void testFindByIdActive_NotFound() {
        Long roleId = 999L;

        when(repository.findByIdAndDeletedAtIsNull(roleId))
            .thenReturn(Mono.empty());

        StepVerifier.create(roleService.findByIdActive(roleId))
            .expectError(ApiException.class)
            .verify();

        verify(repository, times(1)).findByIdAndDeletedAtIsNull(roleId);
    }

    @Test
    void testCreate_Success() {
        RoleRequest request = new RoleRequest("admin", "Administrator Role");

        Role savedRole = new Role(1L, "ADMIN", "Administrator Role", LocalDateTime.now(), LocalDateTime.now(), null);
        when(repository.save(any(Role.class)))
            .thenReturn(Mono.just(savedRole));

        StepVerifier.create(roleService.create(request))
            .expectNextMatches(response -> response.name().equals("ADMIN"))
            .verifyComplete();

        verify(repository, times(1)).save(any(Role.class));
    }

    @Test
    void testCreate_NameExists() {
        RoleRequest request = new RoleRequest("ADMIN", "Administrator Role");

        when(repository.save(any(Role.class)))
            .thenReturn(Mono.error(new DataIntegrityViolationException("Duplicate name")));

        StepVerifier.create(roleService.create(request))
            .expectError(ApiException.class)
            .verify();

        verify(repository, times(1)).save(any(Role.class));
    }

    @Test
    void testUpdate_Success() {
        Long roleId = 1L;
        RoleRequest request = new RoleRequest("MODERATOR", "Moderator Role");

        Role existingRole = new Role(roleId, "ADMIN", "Old Description", LocalDateTime.now(), LocalDateTime.now(), null);

        when(repository.findByIdAndDeletedAtIsNull(roleId))
            .thenReturn(Mono.just(existingRole));

        Role updatedRole = new Role(roleId, "MODERATOR", "Moderator Role", LocalDateTime.now(), LocalDateTime.now(), null);
        when(repository.save(any(Role.class)))
            .thenReturn(Mono.just(updatedRole));

        StepVerifier.create(roleService.update(roleId, request))
            .expectNextMatches(response -> response.name().equals("MODERATOR"))
            .verifyComplete();

        verify(repository, times(1)).findByIdAndDeletedAtIsNull(roleId);
        verify(repository, times(1)).save(any(Role.class));
    }

    @Test
    void testUpdate_NotFound() {
        Long roleId = 999L;
        RoleRequest request = new RoleRequest("MODERATOR", "Moderator Role");

        when(repository.findByIdAndDeletedAtIsNull(roleId))
            .thenReturn(Mono.empty());

        StepVerifier.create(roleService.update(roleId, request))
            .expectError(ApiException.class)
            .verify();

        verify(repository, times(1)).findByIdAndDeletedAtIsNull(roleId);
        verify(repository, never()).save(any(Role.class));
    }

    @Test
    void testUpdate_NameExists() {
        Long roleId = 1L;
        RoleRequest request = new RoleRequest("ADMIN", "Updated Role");

        Role existingRole = new Role(roleId, "MODERATOR", "Old Role", LocalDateTime.now(), LocalDateTime.now(), null);

        when(repository.findByIdAndDeletedAtIsNull(roleId))
            .thenReturn(Mono.just(existingRole));

        when(repository.save(any(Role.class)))
            .thenReturn(Mono.error(new DataIntegrityViolationException("Duplicate name")));

        StepVerifier.create(roleService.update(roleId, request))
            .expectError(ApiException.class)
            .verify();

        verify(repository, times(1)).findByIdAndDeletedAtIsNull(roleId);
        verify(repository, times(1)).save(any(Role.class));
    }

    @Test
    void testSoftDelete_Success() {
        Long roleId = 1L;
        Role role = new Role(roleId, "ADMIN", "Administrator", LocalDateTime.now(), LocalDateTime.now(), null);

        when(repository.findByIdAndDeletedAtIsNull(roleId))
            .thenReturn(Mono.just(role));

        Role deletedRole = new Role(roleId, "ADMIN", "Administrator", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
        when(repository.save(any(Role.class)))
            .thenReturn(Mono.just(deletedRole));

        StepVerifier.create(roleService.softDelete(roleId))
            .verifyComplete();

        verify(repository, times(1)).findByIdAndDeletedAtIsNull(roleId);
        verify(repository, times(1)).save(any(Role.class));
    }

    @Test
    void testSoftDelete_NotFound() {
        Long roleId = 999L;

        when(repository.findByIdAndDeletedAtIsNull(roleId))
            .thenReturn(Mono.empty());

        StepVerifier.create(roleService.softDelete(roleId))
            .expectError(ApiException.class)
            .verify();

        verify(repository, times(1)).findByIdAndDeletedAtIsNull(roleId);
        verify(repository, never()).save(any(Role.class));
    }

    @Test
    void testRestore_Success() {
        Long roleId = 1L;
        Role deletedRole = new Role(roleId, "ADMIN", "Administrator", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());

        when(repository.findByIdAndDeletedAtIsNotNull(roleId))
            .thenReturn(Mono.just(deletedRole));

        Role restoredRole = new Role(roleId, "ADMIN", "Administrator", LocalDateTime.now(), LocalDateTime.now(), null);
        when(repository.save(any(Role.class)))
            .thenReturn(Mono.just(restoredRole));

        StepVerifier.create(roleService.restore(roleId))
            .verifyComplete();

        verify(repository, times(1)).findByIdAndDeletedAtIsNotNull(roleId);
        verify(repository, times(1)).save(any(Role.class));
    }

    @Test
    void testRestore_NotDeleted() {
        Long roleId = 1L;

        when(repository.findByIdAndDeletedAtIsNotNull(roleId))
            .thenReturn(Mono.empty());

        StepVerifier.create(roleService.restore(roleId))
            .expectError(ApiException.class)
            .verify();

        verify(repository, times(1)).findByIdAndDeletedAtIsNotNull(roleId);
        verify(repository, never()).save(any(Role.class));
    }
}
