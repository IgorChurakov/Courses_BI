package bell.courses.dao;

import bell.courses.model.Office;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeRepository extends CrudRepository<Office,Long> {
}