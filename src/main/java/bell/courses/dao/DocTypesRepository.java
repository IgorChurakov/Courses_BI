package bell.courses.dao;

import bell.courses.model.DocTypes;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository for operating with {@link DocTypes} table
 * @see org.springframework.data.repository.CrudRepository
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
public interface DocTypesRepository extends CrudRepository<DocTypes,Long> {
    DocTypes getByNameContaining(String name);

    DocTypes getByCode(Integer code);

    List<DocTypes> getAllBy();
}