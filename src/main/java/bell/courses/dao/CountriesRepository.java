package bell.courses.dao;

import bell.courses.model.Countries;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository for operating with {@link Countries} table
 * @see org.springframework.data.repository.CrudRepository
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
public interface CountriesRepository extends CrudRepository<Countries,Long> {
    Countries getByCode(Integer code);

    List<Countries> getAllBy();
}