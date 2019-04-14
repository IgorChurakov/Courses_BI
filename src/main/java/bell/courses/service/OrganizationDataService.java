package bell.courses.service;

import bell.courses.dao.OrganizationRepository;
import bell.courses.model.Organization;
import bell.courses.view.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrganizationDataService {

    private OrganizationRepository organizationRepository;

    @Autowired
    OrganizationDataService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    private OrganizationView wrapInView(Organization organization){
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

    private void setData(Organization organization,String name, String fullName, String inn, String kpp,
                                 String address, String phone, Boolean isActive){
        organization.setName(name);
        organization.setFullName(fullName);
        organization.setInn(inn);
        organization.setKpp(kpp);
        organization.setAddress(address);
        if (phone != null) {
            organization.setPhone(phone);
        }
        if (isActive != null) {
            organization.setIsActive(isActive);
        }
    }

    public Responseable get(Long id) {
        Organization organization;
        try {
            organization = organizationRepository.getById(id);
            if (organization == null) {
                return new ErrorView("No organizations with specified ID found");
            }
        } catch (DataAccessException e) {
            log.error("Organization data loading error in getById(id=" + id + ")", e);
            return new ErrorView("Internal Organization data loading error");
        }
        return wrapInView(organization);
    }

    public List<Responseable> list(String name, String inn, Boolean isActive) {
        List<Responseable> result = new ArrayList<>();
        try {
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
            if (organizations.isEmpty()) {
                result.add(new ErrorView("No organizations with specified parameters were found"));
                return result;
            } else {
                for (Organization organization : organizations) {
                    result.add(new OrganizationListingView(
                            organization.getId(),
                            organization.getName(),
                            organization.getIsActive()));
                }
            }
        } catch (DataAccessException e) {
            log.error("Organization data loading error in listByName(name=" + name + ")", e);
            result.add(new ErrorView("Internal Organization data loading error"));
            return result;
        }
        return result;
    }

    public Responseable update(Long id, String name, String fullName, String inn,
                               String kpp, String address, String phone, Boolean isActive){
        Organization requestedOrganization;
        try {
            requestedOrganization = organizationRepository.getById(id);
        } catch (DataAccessException e) {
            log.error("Organization data loading error in update(id="+id+")", e);
            return new ErrorView("Internal Organization data loading error");
        }
        if (requestedOrganization == null) {
            return new ErrorView("Organization with specified ID does not exist");
        }

        setData(requestedOrganization,name,fullName,inn,kpp,address,phone,isActive);

        try {
            organizationRepository.save(requestedOrganization);
        } catch (DataAccessException e) {
            log.error("Organization data updating error in update(id="+id+")", e);
            return new ErrorView("Internal Organization data updating error");
        }

        return new ResultView("success");
    }

    public Responseable save(String name, String fullName, String inn, String kpp,
                             String address, String phone, Boolean isActive){
        Organization organization = new Organization();
        setData(organization,name,fullName,inn,kpp,address,phone,isActive);
        try {
            organizationRepository.save(organization);
        } catch (DataAccessException e) {
            log.error("Organization data saving error in save(name="+name+")", e);
            return new ErrorView("Internal Organization data saving error");
        }

        return new ResultView("success");
    }
}