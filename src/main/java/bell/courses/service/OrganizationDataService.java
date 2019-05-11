package bell.courses.service;

import bell.courses.dao.OrganizationRepository;
import bell.courses.model.Organization;
import bell.courses.view.ApiException;
import bell.courses.view.OrganizationListingView;
import bell.courses.view.OrganizationView;
import bell.courses.view.ResponseView;
import bell.courses.view.ResultView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class OrganizationDataService {

    private final OrganizationRepository organizationRepository;

    @Autowired
    OrganizationDataService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public ResponseView get(Long id) {
        Organization organization;
        organization = organizationRepository.getById(id);
        if (organization == null) {
            throw new ApiException("Organization with specified ID does not exist");
        }
        return wrapInView(organization);
    }

    public List<ResponseView> list(String name, String inn, Boolean isActive) {
        List<Organization> organizations = getOrganizationList(name, inn, isActive);
        if (organizations.isEmpty()) {
            throw new ApiException("No organizations with specified parameters were found");
        } else {
            List<ResponseView> result = new ArrayList<>();
            for (Organization organization : organizations) {
                result.add(new OrganizationListingView(
                        organization.getId(),
                        organization.getName(),
                        organization.getIsActive()));
            }
            return result;
        }
    }

    public ResponseView update(Long id, String name, String fullName, String inn,
                               String kpp, String address, String phone, Boolean isActive) {
        Organization requestedOrganization;
        requestedOrganization = organizationRepository.getById(id);
        if (requestedOrganization == null) {
            throw new ApiException("Organization with specified ID does not exist");
        }
        setData(requestedOrganization, name, fullName, inn, kpp, address, phone, isActive);
        organizationRepository.save(requestedOrganization);
        return new ResultView("success");
    }

    public ResponseView save(String name, String fullName, String inn, String kpp,
                             String address, String phone, Boolean isActive) {
        Organization organization = new Organization();
        setData(organization, name, fullName, inn, kpp, address, phone, isActive);
        organizationRepository.save(organization);
        return new ResultView("success");
    }

    private OrganizationView wrapInView(Organization organization) {
        return new OrganizationView(
                organization.getId(),
                organization.getName(),
                organization.getFullName(),
                organization.getInn(),
                organization.getKpp(),
                organization.getAddress(),
                organization.getPhone(),
                organization.getIsActive());
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
        if (isActive == null) {
            if (inn == null) {
                organizations = organizationRepository.findAllByNameContaining(name);
            } else {
                organizations = organizationRepository.findAllByNameContainingAndInn(name, inn);
            }
        } else {
            if (inn == null) {
                organizations = organizationRepository.findAllByNameContainingAndIsActive(name, isActive);
            } else {
                organizations = organizationRepository.findAllByNameContainingAndInnAndIsActive(name, inn, isActive);
            }
        }
        return organizations;
    }
}