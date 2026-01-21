package ao.creativemode.kixi.service;

import ao.creativemode.dto.anosletivos.AnosLetivosRequestDTO;
import ao.creativemode.dto.anosletivos.AnosLetivosResponseDTO;
import ao.creativemode.model.AnosLetivos;
import ao.creativemode.repository.AnosLetivosRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AnosLetivosService {

    private final AnosLetivosRepository repository;

    public AnosLetivosService(AnosLetivosRepository repository) {
        this.repository = repository;
    }

    public Flux<AnosLetivosResponseDTO> getAll() {
        return repository.findAll()
                .map(a -> new AnosLetivosResponseDTO(a.getId(), a.getAnoInicio(), a.getAnoFim()));
    }

    public Mono<AnosLetivosResponseDTO> getById(Long id) {
        return repository.findById(id)
                .map(a -> new AnosLetivosResponseDTO(a.getId(), a.getAnoInicio(), a.getAnoFim()));
    }

    public Mono<AnosLetivosResponseDTO> create(AnosLetivosRequestDTO dto) {
        AnosLetivos entity = new AnosLetivos();
        entity.setAnoInicio(dto.anoInicio());
        entity.setAnoFim(dto.anoFim());

        return repository.save(entity)
                .map(a -> new AnosLetivosResponseDTO(a.getId(), a.getAnoInicio(), a.getAnoFim()));
    }

    public Mono<AnosLetivosResponseDTO> update(Long id, AnosLetivosRequestDTO dto) {
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setAnoInicio(dto.anoInicio());
                    existing.setAnoFim(dto.anoFim());
                    return repository.save(existing);
                })
                .map(a -> new AnosLetivosResponseDTO(a.getId(), a.getAnoInicio(), a.getAnoFim()));
    }

    public Mono<Void> delete(Long id) {
        return repository.deleteById(id);
    }
}
