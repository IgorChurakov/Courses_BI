package bell.courses.dao;

import bell.courses.model.Countries;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CountriesRepository extends CrudRepository<Countries,Long> {
    Countries getByCode(Integer code);

    List<Countries> getAllBy();
}