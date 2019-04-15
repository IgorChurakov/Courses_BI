package bell.courses.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity(name = "Document")
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private long version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_type", nullable = false)
    private DocTypes document;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(name = "doc_number", nullable = false)
    private String docNumber;

    @Column(name = "doc_date", nullable = false)
    private Date docDate;
}