package bell.courses.service;

import bell.courses.dao.OrganizationRepository;
import bell.courses.error.ApiException;
import bell.courses.model.Organization;
import bell.courses.view.request.OrganizationFilterView;
import bell.courses.view.request.OrganizationSaveView;
import bell.courses.view.request.OrganizationUpdateView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static bell.courses.dao.OrganizationRepository.hasInn;
import static bell.courses.dao.OrganizationRepository.hasIsActive;
import static bell.courses.dao.OrganizationRepository.nameContains;

@Slf4j
@Service
public class OrganizationDataService {

    private final OrganizationRepository organizationRepository;

    @Autowired
    OrganizationDataService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public Organization get(Long id) {
        Organization organization;
        organization = organizationRepository.getById(id);
        if (organization == null) {
            throw new ApiException("Organization with specified ID does not exist");
        }
        return organization;
    }

    public List<Organization> list(OrganizationFilterView request) {
        List<Organization> organizations = getOrganizationList(request.getName(), request.getInn(), request.getIsActive());
        if (organizations.isEmpty()) {
            throw new ApiException("No organizations with specified parameters were found");
        } else {
            return organizations;
        }
    }

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