package bell.courses.service;

import bell.courses.dao.OfficeRepository;
import bell.courses.dao.OrganizationRepository;
import bell.courses.error.ApiException;
import bell.courses.model.Office;
import bell.courses.model.Organization;
import bell.courses.view.request.OfficeFilterView;
import bell.courses.view.request.OfficeSaveView;
import bell.courses.view.request.OfficeUpdateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static bell.courses.dao.OfficeRepository.hasIsActive;
import static bell.courses.dao.OfficeRepository.hasOrganization;
import static bell.courses.dao.OfficeRepository.nameContains;
import static bell.courses.dao.OfficeRepository.phoneContains;

/**
 * Service for operating with Offices in the database
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@Service
public class OfficeDataService {

    private final OfficeRepository officeRepository;
    private final OrganizationRepository organizationRepository;

    @Autowired
    OfficeDataService(OfficeRepository officeRepository, OrganizationRepository organizationRepository) {
        this.officeRepository = officeRepository;
        this.organizationRepository = organizationRepository;
    }

    /**
     * Method for getting Office by it's ID
     * @param id Office's ID in the database
     * @return {@link Office}
     */
    public Office get(Long id) {
        Office office;
        office = officeRepository.getById(id);
        if (office == null) {
            throw new ApiException("Office with specified ID does not exist");
        }
        return office;
    }

    /**
     * Method for getting list of Offices from the database
     * @param request {@link OfficeFilterView} with filter params
     * @return List of {@link Office}
     */
    public List<Office> list(OfficeFilterView request) {
        Organization organization = organizationRepository.getById(request.getOrgId());
        if (organization == null) {
            throw new ApiException("No organizations with specified id found");
        }
        List<Office> offices = getOfficeList(organization, request.getName(), request.getPhone(), request.getIsActive());
        if (offices.isEmpty()) {
            throw new ApiException("No offices with specified parameters found");
        } else {
            return offices;
        }
    }

    /**
     * Method for updating an Office in the database
     * @param request {@link OfficeUpdateView}
     * @return true if successful
     */
    public Boolean update(OfficeUpdateView request) {
        Office requestedOffice = officeRepository.getById(request.getId());
        if (requestedOffice == null) {
            throw new ApiException("No offices with specified ID found");
        }
        setData(requestedOffice, request.getName(), request.getAddress(), request.getPhone(), request.getIsActive());
        officeRepository.save(requestedOffice);
        return true;
    }

    /**
     * Method for creating a new Office in the database
     * @param request {@link OfficeSaveView}
     * @return true if successful
     */
    public Boolean save(OfficeSaveView request) {
        Office office = new Office();
        Organization officeOrganization = organizationRepository.getById(request.getOrgId());
        if (officeOrganization == null) {
            throw new ApiException("No organizations with specified orgId found");
        }
        office.setOrganization(officeOrganization);
        setData(office, request.getName(), request.getAddress(), request.getPhone(), Objects.requireNonNullElse(request.getIsActive(), false));
        officeRepository.save(office);
        return true;
    }

    @SuppressWarnings("Duplicates")
    private void setData(Office office, String name, String address, String phone, Boolean isActive) {
        office.setName(name);
        office.setAddress(address);
        if (phone != null) {
            office.setPhone(phone);
        }
        if (isActive != null) {
            office.setIsActive(isActive);
        }
    }

    private List<Office> getOfficeList(Organization organization, String name, String phone, Boolean isActive) {
        List<Office> offices;
        offices = officeRepository.findAll(Specification.where(hasOrganization(organization))
                                                        .and(nameContains(name))
                                                        .and(phoneContains(phone))
                                                        .and(hasIsActive(isActive)));
        return offices;
    }
}