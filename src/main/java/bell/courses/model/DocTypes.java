package bell.courses.model;

import bell.courses.view.response.CatalogueListingView;
import bell.courses.view.response.ResponseView;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Entity for storing Document Types in the database
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@Data
@Entity(name = "DocTypes")
@Table(name = "doc_types")
public class DocTypes implements ResponseView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Version
    private long version;

    @Column(nullable = false, name = "code")
    private Integer code;

    @Column(nullable = false, unique = true, name = "name", length = 256)
    private String name;

    /**
     * not yet implemented
     */
    @Override
    public Object wrapInView() {
        throw new UnsupportedOperationException("wrapInView() for DocTypes is not supported in this version");
    }

    /**
     * @see ResponseView
     * @return {@link CatalogueListingView}
     */
    @Override
    public CatalogueListingView wrapInListView() {
        return new CatalogueListingView(this.getName(), this.getCode());
    }
}