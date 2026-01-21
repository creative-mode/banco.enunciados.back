package ao.creativemode.kixi.service;

import ao.creativemode.kixi.anosletivos.dto.request.CreateAnosLetivosRequest;
import ao.creativemode.kixi.anosletivos.dto.request.UpdateAnosLetivosRequest;
import ao.creativemode.kixi.anosletivos.dto.response.AnosLetivosResponse;
import ao.creativemode.kixi.model.AnosLetivos;
import ao.creativemode.kixi.repository.AnosLetivosRepository;
import org.springframework.stereotype.Service;

@Service
public class AnosLetivosService {

    private final AnosLetivosRepository repository;

    public AnosLetivosService(AnosLetivosRepository repository) {
        this.repository = repository;
    }


    public reactor.core.publisher.Flux<AnosLetivosResponse> getAll() {
        return repository.findAll()
                .map(AnosLetivosResponse::from);
    }

    public reactor.core.publisher.Mono<AnosLetivosResponse> getById(Long id) {
        return repository.findById(id)
                .map(AnosLetivosResponse::from);
    }

    public reactor.core.publisher.Mono<AnosLetivosResponse> create(CreateAnosLetivosRequest dto) {
        AnosLetivos entity = new AnosLetivos();
        entity.setAnoInicio(dto.anoInicio());
        entity.setAnoFim(dto.anoFim());
        return repository.save(entity)
                .map(AnosLetivosResponse::from);
    }

    public reactor.core.publisher.Mono<AnosLetivosResponse> update(Long id, UpdateAnosLetivosRequest dto) {
        return repository.findById(id)
                .flatMap(existing -> {
                    if (dto.anoInicio() != null) existing.setAnoInicio(dto.anoInicio());
                    if (dto.anoFim() != null) existing.setAnoFim(dto.anoFim());
                    return repository.save(existing);
                })
                .map(AnosLetivosResponse::from);
    }

    public reactor.core.publisher.Mono<Void> delete(Long id) {
        return repository.deleteById(id);
    }
}
