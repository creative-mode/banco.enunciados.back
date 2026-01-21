package ao.creativemode.kixi.anosletivos.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateAnosLetivosRequest(
    @NotNull(message = "Ano de início é obrigatório")
    @Positive(message = "Ano de início deve ser positivo")
    Integer anoInicio,

    @NotNull(message = "Ano de fim é obrigatório")
    @Positive(message = "Ano de fim deve ser positivo")
    Integer anoFim
) {}
