package ao.creativemode.kixi.repository;

import ao.creativemode.kixi.model.AnosLetivos;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AnosLetivosRepository extends ReactiveCrudRepository<AnosLetivos, Long> {

    Flux<AnosLetivos> findAllByDeletedFalse();

    Mono<AnosLetivos> findByIdAndDeletedFalse(Long id);

    Mono<AnosLetivos> findByIdAndDeletedTrue(Long id);
}
