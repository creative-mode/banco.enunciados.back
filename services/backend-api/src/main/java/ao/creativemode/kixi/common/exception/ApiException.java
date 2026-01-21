package ao.creativemode.kixi.common.exception;

public class ApiException extends RuntimeException {
    private final int status;

    public ApiException(String message, int status) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static ApiException notFound(String message) {
        return new ApiException(message, 404);
    }

    public static ApiException badRequest(String message) {
        return new ApiException(message, 400);
    }

    public static ApiException internalError(String message) {
        return new ApiException(message, 500);
    }
}
