package bell.courses.view.response;

import lombok.Data;

/**
 * Response view for listing catalogues
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@Data
public class CatalogueListingView {
    private String name;
    private int code;

    public CatalogueListingView(String name, int code) {
        this.name = name;
        this.code = code;
    }
}