package bell.courses.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Docs")
@Table(name = "docs")
public class Docs {

    @Id
    private Byte code;

    @Version
    private Integer version;

    @Column(nullable = false,unique = true)
    private String name;
}