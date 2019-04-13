package bell.courses.model;

import bell.courses.view.Responseable;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Version;
import javax.persistence.Column;

@Data
@Entity(name = "Organization")
@Table(name = "organization")
public class Organization implements Responseable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version = 0L;

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
    private Boolean isActive = true;
}
