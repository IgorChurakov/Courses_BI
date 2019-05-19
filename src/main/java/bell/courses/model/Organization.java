package bell.courses.model;

import bell.courses.view.response.OrganizationListingView;
import bell.courses.view.response.OrganizationView;
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
 * Entity for storing Organizations in the database
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@Data
@Entity(name = "Organization")
@Table(name = "organization")
public class Organization implements ResponseView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Version
    private long version;

    @Column(nullable = false, length = 256)
    private String name;

    @Column(nullable = false, length = 256)
    private String fullName;

    @Column(nullable = false, length = 64)
    private String inn;

    @Column(nullable = false, length = 64)
    private String kpp;

    @Column(nullable = false, length = 256)
    private String address;

    @Column(length = 256)
    private String phone;

    @Column
    private Boolean isActive;

    /**
     * @see ResponseView
     * @return {@link OrganizationView}
     */
    @Override
    public OrganizationView wrapInView() {
        return new OrganizationView(
                this.getId(),
                this.getName(),
                this.getFullName(),
                this.getInn(),
                this.getKpp(),
                this.getAddress(),
                this.getPhone(),
                this.getIsActive());
    }

    /**
     * @see ResponseView
     * @return {@link OrganizationListingView}
     */
    @Override
    public OrganizationListingView wrapInListView() {
        return new OrganizationListingView(
                this.getId(),
                this.getName(),
                this.getIsActive());
    }
}