package bell.courses.model;

import bell.courses.view.response.OfficeListingView;
import bell.courses.view.response.OfficeView;
import bell.courses.view.response.ResponseView;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Entity for storing Offices in the database
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@Data
@Entity(name = "Office")
@Table(name = "office")
public class Office implements ResponseView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Version
    private long version;

    @Column(nullable = false, length = 256)
    private String name;

    @Column(nullable = false, length = 256)
    private String address;

    @Column(length = 256)
    private String phone;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private Organization organization;

    /**
     * @see ResponseView
     * @return {@link OfficeView}
     */
    @Override
    public OfficeView wrapInView() {
        return new OfficeView(this.getId(), this.getName(), this.getAddress(), this.getPhone(), this.getIsActive());
    }

    /**
     * @see ResponseView
     * @return {@link OfficeListingView}
     */
    @Override
    public OfficeListingView wrapInListView() {
        return new OfficeListingView(this.getId(),this.getName(),this.getIsActive());
    }
}