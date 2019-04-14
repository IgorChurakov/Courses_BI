package bell.courses.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Docs")
@Table(name = "docs")
public class Docs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private long version;

    @Column(nullable = false, name = "code")
    private Integer code;

    @Column(nullable = false,unique = true, name = "name")
    private String name;
}