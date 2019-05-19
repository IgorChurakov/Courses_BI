package bell.courses.dao;

import bell.courses.model.Document;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for operating with {@link Document} table
 * @see org.springframework.data.repository.CrudRepository
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
public interface DocumentRepository extends CrudRepository<Document,Long> {
}
