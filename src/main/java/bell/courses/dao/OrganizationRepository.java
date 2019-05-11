package bell.courses.dao;

import bell.courses.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization,Long> {
    Organization getById(Long id);

    List<Organization> findAllByNameContaining(String name);

    List<Organization> findAllByNameContainingAndInn(String name, String inn);

    List<Organization> findAllByNameContainingAndIsActive(String name, Boolean isActive);

    List<Organization> findAllByNameContainingAndInnAndIsActive(String name, String inn, Boolean isActive);
}