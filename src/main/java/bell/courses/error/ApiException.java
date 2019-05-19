package bell.courses.error;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}