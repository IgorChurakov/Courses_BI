package bell.courses.view.response;

import lombok.Data;

/**
 * Response view for exceptions
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@Data
public class ErrorView {
    private String error;

    public ErrorView(String error) {
        this.error = error;
    }
}