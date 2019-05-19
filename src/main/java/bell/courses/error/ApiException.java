package bell.courses.error;

/**
 * Custom exception for throwing ErrorViews throughout application
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}