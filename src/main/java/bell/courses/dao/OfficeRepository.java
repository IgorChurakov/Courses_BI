package bell.courses.dao;

import bell.courses.model.Office;
import bell.courses.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficeRepository extends CrudRepository<Office,Long> {
    Office getById(Long id);

    List<Office> findAllByOrganization(Organization organization);

    List<Office> findAllByOrganizationAndNameContaining(Organization organization, String name);

    List<Office> findAllByOrganizationAndPhoneContaining(Organization organization, String phone);

    List<Office> findAllByOrganizationAndNameContainingAndPhoneContaining(Organization organization, String name, String phone);

    List<Office> findAllByOrganizationAndIsActive(Organization organization, Boolean isActive);

    List<Office> findAllByOrganizationAndIsActiveAndNameContaining(Organization organization, Boolean isActive, String name);

    List<Office> findAllByOrganizationAndIsActiveAndPhoneContaining(Organization organization, Boolean isActive, String phone);

    List<Office> findAllByOrganizationAndIsActiveAndPhoneContainingAndNameContaining(Organization organization, Boolean isActive, String phone, String name);
}