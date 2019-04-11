package bell.courses.controller;

import bell.courses.dao.OrganizationRepository;
import bell.courses.model.Organization;
import bell.courses.view.ErrorView;
import bell.courses.view.OrganizationListingView;
import bell.courses.view.Responseable;
import bell.courses.view.ResultView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/organization")
public class OrganizationController {

    private OrganizationRepository organizationRepository;

    @Autowired
    OrganizationController(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Responseable getOrganization(@PathVariable long id) {
        Responseable response;
        try {
            response = organizationRepository.findById(id);
            if (response == null) {
                return new ErrorView("No organizations with specified ID found");
            }
        } catch (DataAccessException e) {
            log.error("Organization data loading error in GET controller", e);
            return new ErrorView("Organization data loading error in GET controller");
        }
        return response;
    }

    @PostMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Responseable> listOrganizations(@RequestParam String name,
                                                @RequestParam(required = false) String inn,
                                                @RequestParam(required = false) Boolean isActive) {
        List<Organization> query;
        List<Responseable> responseables = new ArrayList<>();
        try {
            if (isActive == null) {
                if (inn == null) {
                    query = organizationRepository.findAllByNameContaining(name);
                } else {
                    query = organizationRepository.findAllByNameContainingAndInn(name, inn);
                }
            } else {
                if (inn == null) {
                    query = organizationRepository.findAllByNameContainingAndIsActive(name, isActive);
                } else {
                    query = organizationRepository.findAllByNameContainingAndInnAndIsActive(name, inn, isActive);
                }
            }
        } catch (DataAccessException e) {
            log.error("Organization data loading error in listing controller", e);
            responseables.add(new ErrorView("Organization data loading error in listing controller"));
            return responseables;
        }
        if (query.isEmpty()) {
            responseables.add(new ErrorView("No organizations with specified parameters were found"));
            return responseables;
        }
        for (Organization organization : query) {
            responseables.add(new OrganizationListingView(organization.getId(), organization.getName(), organization.getIsActive()));
        }
        return responseables;
    }

    @PostMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public Responseable updateOrganization(@RequestParam long id,
                                           @RequestParam String name,
                                           @RequestParam String fullName,
                                           @RequestParam String inn,
                                           @RequestParam String kpp,
                                           @RequestParam String address,
                                           @RequestParam(required = false) String phone,
                                           @RequestParam(required = false) Boolean isActive) {
        Organization requestedOrganization;
        try {
            requestedOrganization = organizationRepository.findById(id);
        } catch (DataAccessException e) {
            log.error("Organization data loading error in UPDATE controller", e);
            return new ErrorView("Organization data loading error in UPDATE controller");
        }
        if (requestedOrganization == null) {
            return new ErrorView("Organization with specified ID does not exist");
        }
        requestedOrganization.setName(name);
        requestedOrganization.setFullName(fullName);
        requestedOrganization.setInn(inn);
        requestedOrganization.setKpp(kpp);
        requestedOrganization.setAddress(address);
        if (phone != null) {
            requestedOrganization.setPhone(phone);
        }
        if (isActive != null) {
            requestedOrganization.setIsActive(isActive);
        }
        try {
            organizationRepository.save(requestedOrganization);
        } catch (DataAccessException e) {
            log.error("Organization data updating error in UPDATE controller", e);
            return new ErrorView("Organization data updating error in UPDATE controller");
        }
        return new ResultView("success");
    }
}