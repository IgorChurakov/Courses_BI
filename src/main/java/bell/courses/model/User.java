package bell.courses.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity(name = "User")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id")
    private Office office;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(nullable = false, name = "position")
    private String position;

    @Column
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_code")
    private Docs docCode;

    @Column(name = "doc_name")
    private String docName;

    @Column(name = "doc_number")
    private String docNumber;

    @Column(name = "doc_date")
    private Date docDate;

    @Column(name = "citizenship_code")
    private Integer citizenshipCode;

    @Column(name = "is_identified")
    private Boolean isIdentified;
}
