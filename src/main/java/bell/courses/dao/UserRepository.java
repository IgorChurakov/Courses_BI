package bell.courses.dao;

import bell.courses.model.Office;
import bell.courses.model.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public interface UserRepository extends CrudRepository<User,Long>, JpaSpecificationExecutor<User> {
    User getById(Long id);

    static Specification<User> hasOffice(Office office) {
        return (user, cq, cb) -> cb.equal(user.get("office"), office);
    }

    static Specification<User> firstNameContains(String firstName) {
        return (user, cq, cb) -> cb.like(user.get("firstName"), "%" + Objects.requireNonNullElse(firstName, "") + "%");
    }

    static Specification<User> secondNameContains(String secondName) {
        return (user, cq, cb) -> cb.like(user.get("secondName"), "%" + Objects.requireNonNullElse(secondName, "") + "%");
    }

    static Specification<User> middleNameContains(String middleName) {
        return (user, cq, cb) -> cb.like(user.get("middleName"), "%" + Objects.requireNonNullElse(middleName, "") + "%");
    }

    static Specification<User> positionContains(String position) {
        return (user, cq, cb) -> cb.like(user.get("position"), "%" + Objects.requireNonNullElse(position, "") + "%");
    }

    static Specification<User> hasDocType(Integer docCode) {
        if (docCode == null)
            return null;
        return (user, cq, cb) -> cb.equal(user.get("document").get("docType").get("code"), docCode);
    }

    static Specification<User> hasCitizenship(Integer citizenshipCode) {
        if (citizenshipCode == null)
            return null;
        return (user, cq, cb) -> cb.equal(user.get("country").get("code"), citizenshipCode);
    }
}