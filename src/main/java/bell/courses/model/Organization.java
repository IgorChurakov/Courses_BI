package bell.courses.model;

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


@Data
@Entity(name = "Organization")
@Table(name = "organization")
public class Organization {

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
}