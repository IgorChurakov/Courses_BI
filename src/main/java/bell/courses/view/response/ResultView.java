package bell.courses.view.response;

import lombok.Data;

/**
 * Response view for returning a result of update/save operations
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@Data
public class ResultView {
    private String result;

    public ResultView(String result) {
        this.result = result;
    }
}