package bell.courses.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import java.sql.Date;

/**
 * Entity for storing Documents in the database
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
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