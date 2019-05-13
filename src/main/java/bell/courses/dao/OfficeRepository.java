package bell.courses.dao;

import bell.courses.model.Office;
import bell.courses.model.Organization;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public interface OfficeRepository extends CrudRepository<Office,Long>, JpaSpecificationExecutor<Office> {
    Office getById(Long id);

    static Specification<Office> hasOrganization(Organization organization) {
        if (organization == null)
            return null;
        return (office, cq, cb) -> cb.equal(office.get("organization"), organization);
    }

    static Specification<Office> hasIsActive(Boolean isActive) {
        if (isActive == null)
            return null;
        return (office, cq, cb) -> cb.equal(office.get("isActive"), isActive);
    }

    static Specification<Office> nameContains(String name) {
        return (office, cq, cb) -> cb.like(office.get("name"), "%" + Objects.requireNonNullElse(name, "") + "%");
    }

    static Specification<Office> phoneContains(String phone) {
        return (office, cq, cb) -> cb.like(office.get("phone"), "%" + Objects.requireNonNullElse(phone, "") + "%");
    }
}