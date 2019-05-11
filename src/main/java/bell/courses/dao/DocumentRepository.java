package bell.courses.dao;

import bell.courses.model.Document;
import org.springframework.data.repository.CrudRepository;

public interface DocumentRepository extends CrudRepository<Document,Long> {
}
