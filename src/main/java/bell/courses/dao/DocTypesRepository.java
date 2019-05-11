package bell.courses.dao;

import bell.courses.model.DocTypes;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DocTypesRepository extends CrudRepository<DocTypes,Long> {
    DocTypes getByNameContaining(String name);

    DocTypes getByCode(Integer code);

    List<DocTypes> getAllBy();
}