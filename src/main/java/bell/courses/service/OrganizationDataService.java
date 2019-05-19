package bell.courses.service;

import bell.courses.dao.OrganizationRepository;
import bell.courses.error.ApiException;
import bell.courses.model.Organization;
import bell.courses.view.request.OrganizationFilterView;
import bell.courses.view.request.OrganizationSaveView;
import bell.courses.view.request.OrganizationUpdateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static bell.courses.dao.OrganizationRepository.hasInn;
import static bell.courses.dao.OrganizationRepository.hasIsActive;
import static bell.courses.dao.OrganizationRepository.nameContains;

/**
 * Service for operating with Organizations in the database
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@Service
public class OrganizationDataService {

    private final OrganizationRepository organizationRepository;

    @Autowired
    OrganizationDataService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    /**
     * Method for getting Organization by it's ID
     * @param id Organization's ID in the database
     * @return {@link Organization}
     */
    public Organization get(Long id) {
        Organization organization;
        organization = organizationRepository.getById(id);
        if (organization == null) {
            throw new ApiException("Organization with specified ID does not exist");
        }
        return organization;
    }

    /**
     * Method for getting list of Organizations from the database
     * @param request {@link OrganizationFilterView} with filter params
     * @return List of {@link Organization}
     */
    public List<Organization> list(OrganizationFilterView request) {
        List<Organization> organizations = getOrganizationList(request.getName(), request.getInn(), request.getIsActive());
        if (organizations.isEmpty()) {
            throw new ApiException("No organizations with specified parameters were found");
        } else {
            return organizations;
        }
    }

    /**
     * Method for updating an Organization in the database
     * @param request {@link OrganizationUpdateView}
     * @return true if successful
     */
    @SuppressWarnings("Duplicates")
    public Boolean update(OrganizationUpdateView request) {
        Organization requestedOrganization;
        requestedOrganization = organizationRepository.getById(request.getId());
        if (requestedOrganization == null) {
            throw new ApiException("Organization with specified ID does not exist");
        }
        setData(requestedOrganization, request.getName(), request.getFullName(), request.getInn(), request.getKpp(), request.getAddress(), request.getPhone(), request.getIsActive());
        organizationRepository.save(requestedOrganization);
        return true;
    }

    /**
     * Method for creating a new Organization in the database
     * @param request {@link OrganizationSaveView}
     * @return true if successful
     */
    @SuppressWarnings("Duplicates")
    public Boolean save(OrganizationSaveView request) {
        Organization organization = new Organization();
        setData(organization, request.getName(), request.getFullName(), request.getInn(), request.getKpp(), request.getAddress(), request.getPhone(), request.getIsActive());
        organizationRepository.save(organization);
        return true;
    }


    @SuppressWarnings("Duplicates")
    private void setData(Organization organization, String name, String fullName, String inn, String kpp,
                         String address, String phone, Boolean isActive) {
        organization.setName(name);
        organization.setFullName(fullName);
        organization.setInn(inn);
        organization.setKpp(kpp);
        organization.setAddress(address);
        if (phone != null) {
            organization.setPhone(phone);
        }
        organization.setIsActive(Objects.requireNonNullElse(isActive, false));
    }

    private List<Organization> getOrganizationList(String name, String inn, Boolean isActive) {
        List<Organization> organizations;

        organizations = organizationRepository.findAll(Specification.where(nameContains(name))
                .and(hasInn(inn))
                .and(hasIsActive(isActive)));

        return organizations;
    }
}