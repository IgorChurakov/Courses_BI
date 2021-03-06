package bell.courses.dao;

import bell.courses.model.Office;
import bell.courses.model.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.JoinType;
import java.util.Objects;

/**
 * Repository for operating with {@link User} table; also contains specifications for filtering
 * @see org.springframework.data.repository.CrudRepository
 * @see org.springframework.data.jpa.repository.JpaSpecificationExecutor for specifications
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@Repository
public interface UserRepository extends CrudRepository<User,Long>, JpaSpecificationExecutor<User> {
    User getById(Long id);

    static Specification<User> hasId(Long id){
        return (user, cq, cb) -> {
            user.fetch("document", JoinType.LEFT).fetch("docType", JoinType.LEFT);
            user.fetch("country", JoinType.LEFT);
            return cb.equal(user.get("id"), id);
        };
    }

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