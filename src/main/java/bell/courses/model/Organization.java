package bell.courses.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Organization")
@Table(name = "organization")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private long version;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String inn;

    @Column(nullable = false)
    private String kpp;

    @Column(nullable = false)
    private String address;

    @Column
    private String phone;

    @Column
    private Boolean isActive;
}
