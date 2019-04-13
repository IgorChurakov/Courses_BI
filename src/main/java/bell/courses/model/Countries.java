package bell.courses.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Countries")
@Table(name = "countries")
public class Countries {

    @Id
    private Long code;

    @Version
    private Long version = 0L;

    @Column(nullable = false, unique = true, name = "name")
    private String name;
}