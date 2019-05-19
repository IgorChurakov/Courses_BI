package bell.courses.model;

import bell.courses.view.response.ResponseView;
import bell.courses.view.response.UserListingView;
import bell.courses.view.response.UserView;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import java.sql.Date;

/**
 * Entity for storing Users in the database
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@Data
@Entity(name = "User")
@Table(name = "users")
public class User implements ResponseView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Version
    private long version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id")
    private Office office;

    @Column(nullable = false, name = "first_name", length = 256)
    private String firstName;

    @Column(name = "second_name", length = 256)
    private String secondName;

    @Column(name = "middle_name", length = 256)
    private String middleName;

    @Column(nullable = false, name = "position", length = 256)
    private String position;

    @Column(length = 256)
    private String phone;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_code")
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizenship_code")
    private Countries country;

    @Column(name = "is_identified")
    private Boolean isIdentified;

    /**
     * @see ResponseView
     * @return {@link UserView}
     */
    @Override
    public UserView wrapInView() {
        String docName = null;
        String docNumber = null;
        Date docDate = null;
        String countryName = null;
        Integer countryCode = null;

        Document document = this.getDocument();
        if (document != null){
            docName = document.getDocType().getName();
            docNumber = document.getDocNumber();
            docDate = document.getDocDate();
        }

        Countries country = this.getCountry();
        if (country!=null){
            countryName=country.getName();
            countryCode=country.getCode();
        }
        return new UserView(this.getId(),
                            this.getFirstName(),
                            this.getSecondName(),
                            this.getMiddleName(),
                            this.getPosition(),
                            this.getPhone(),
                            docName,
                            docNumber,
                            docDate,
                            countryName,
                            countryCode,
                            this.getIsIdentified());
    }

    /**
     * @see ResponseView
     * @return {@link UserListingView}
     */
    @Override
    public UserListingView wrapInListView() {
        return new UserListingView(this.getId(),
                                   this.getFirstName(),
                                   this.getSecondName(),
                                   this.getMiddleName(),
                                   this.getPosition());
    }
}