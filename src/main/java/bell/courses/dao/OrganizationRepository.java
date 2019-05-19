package bell.courses.dao;

import bell.courses.model.Organization;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Objects;

/**
 * Repository for operating with {@link Organization} table; also contains specifications for filtering
 * @see org.springframework.data.repository.CrudRepository
 * @see org.springframework.data.jpa.repository.JpaSpecificationExecutor for specifications
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@Repository
public interface OrganizationRepository extends CrudRepository<Organization,Long>, JpaSpecificationExecutor<Organization> {
    Organization getById(Long id);

    static Specification<Organization> nameContains(String name) {
        return (organization, cq, cb) -> cb.like(organization.get("name"), "%" + Objects.requireNonNullElse(name, "") + "%");
    }

    static Specification<Organization> hasInn(String inn) {
        if (inn == null)
            return null;
        return (organization, cq, cb) -> cb.equal(organization.get("inn"), inn);
    }

    static Specification<Organization> hasIsActive(Boolean isActive) {
        if (isActive == null)
            return null;
        return (organization, cq, cb) -> cb.equal(organization.get("isActive"), isActive);
    }
}