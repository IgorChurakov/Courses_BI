package bell.courses.view.response;

/**
 * Base interface for converting raw data in response views
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
public interface ResponseView {

    /**
     * This method should wrap a single entity into it's respective view
     * @return a view for current entity
     */
    Object wrapInView();

    /**
     * This method should wrap a single entity into a view for listing such entities
     * @return a listing view for current entity
     */
    Object wrapInListView();
}