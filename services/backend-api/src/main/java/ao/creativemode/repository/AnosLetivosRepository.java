package ao.creativemode.repository;

import ao.creativemode.model.AnosLetivos;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface AnosLetivosRepository extends ReactiveCrudRepository<AnosLetivos, Long> {
    Flux<AnosLetivos> findByAnoInicio(Integer anoInicio);
}
