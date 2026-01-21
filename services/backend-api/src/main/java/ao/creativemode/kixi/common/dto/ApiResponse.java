package ao.creativemode.kixi.common.dto;

import java.util.Map;

public record ApiResponse<T>(
    boolean sucesso,
    String mensagem,
    T dados,
    Map<String, Object> metadados,
    ApiErro erro
) {
    public static <T> ApiResponse<T> sucesso(T dados, String mensagem) {
        return new ApiResponse<>(true, mensagem, dados, null, null);
    }
    public static <T> ApiResponse<T> sucesso(T dados) {
        return new ApiResponse<>(true, null, dados, null, null);
    }
    public static <T> ApiResponse<T> sucesso(T dados, Map<String, Object> metadados) {
        return new ApiResponse<>(true, null, dados, metadados, null);
    }
    public static <T> ApiResponse<T> erro(String mensagem, ApiErro erro) {
        return new ApiResponse<>(false, mensagem, null, null, erro);
    }
}