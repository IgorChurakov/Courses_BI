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
@Entity(name = "DocTypes")
@Table(name = "doc_types")
public class DocTypes {

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
}