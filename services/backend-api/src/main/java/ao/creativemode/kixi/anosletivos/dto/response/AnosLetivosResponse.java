package ao.creativemode.kixi.anosletivos.dto.response;

import ao.creativemode.kixi.model.AnosLetivos;

public record AnosLetivosResponse(
    Long id,
    Integer anoInicio,
    Integer anoFim
) {
    public static AnosLetivosResponse from(AnosLetivos entity) {
        return new AnosLetivosResponse(entity.getId(), entity.getAnoInicio(), entity.getAnoFim());
    }
}
