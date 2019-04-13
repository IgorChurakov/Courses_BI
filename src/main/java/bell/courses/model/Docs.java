package bell.courses.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Docs")
@Table(name = "docs")
public class Docs {

    @Id
    private Long code;

    @Version
    private Long version = 0L;

    @Column(nullable = false,unique = true)
    private String name;
}