package ao.creativemode.kixi.dto.anosletivos;

import jakarta.validation.constraints.NotNull;

public record AnosLetivosRequestDTO(
        @NotNull(message = "Ano de início é obrigatório")
        Integer anoInicio,

        @NotNull(message = "Ano de fim é obrigatório")
        Integer anoFim
) {}
