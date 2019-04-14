package bell.courses.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "User")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private long version;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_code")
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizenship_code")
    private Countries country;

    @Column(name = "is_identified")
    private Boolean isIdentified;
}