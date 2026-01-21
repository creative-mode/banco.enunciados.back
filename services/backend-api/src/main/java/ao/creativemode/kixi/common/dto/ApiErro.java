package ao.creativemode.kixi.common.dto;

import java.util.Map;

public record ApiErro(
    String codigo,
    int statusHttp,
    String detalhe,
    Map<String, String> campos
) {}
