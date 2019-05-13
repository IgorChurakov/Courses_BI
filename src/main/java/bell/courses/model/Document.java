package bell.courses.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity(name = "Document")
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Version
    private long version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_type", nullable = false)
    private DocTypes docType;

    @Column(name = "doc_number", nullable = false, length = 64)
    private String docNumber;

    @Column(name = "doc_date", nullable = false)
    private Date docDate;
}