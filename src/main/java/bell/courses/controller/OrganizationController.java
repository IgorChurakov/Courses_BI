package bell.courses.controller;

import bell.courses.model.Organization;
import bell.courses.service.OrganizationDataService;
import bell.courses.view.request.OrganizationFilterView;
import bell.courses.view.request.OrganizationSaveView;
import bell.courses.view.request.OrganizationUpdateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller class for operating with {@link Organization} entities
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@RestController
@RequestMapping(value = "api/organization", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrganizationController {

    private final OrganizationDataService organizationDataService;

    @Autowired
    OrganizationController(OrganizationDataService organizationDataService) {
        this.organizationDataService = organizationDataService;
    }

    /**
     * Method for getting a single Organization by it's ID
     * @param id organization ID
     * @return {@link Organization} entity
     */
    @GetMapping(path = "/{id}")
    public Organization getOrganization(@PathVariable long id) {
        return organizationDataService.get(id);
    }

    /**
     * Method for getting a list of organisations filtered by specified parameters
     * @param organizationFilterView filtering parameters
     * @return List of {@link Organization}
     */
    @PostMapping(path = "/list")
    public List<Organization> listOrganizations(@RequestBody @Valid OrganizationFilterView organizationFilterView) {
        return organizationDataService.list(organizationFilterView);
    }

    /**
     * Method for updating an {@link Organization} in the database
     * @param organizationUpdateView update parameters
     * @return true if successfully updated
     */
    @PostMapping(path = "/update")
    public Boolean updateOrganization(@RequestBody @Valid OrganizationUpdateView organizationUpdateView) {
        return organizationDataService.update(organizationUpdateView);
    }

    /**
     * Method for creating a new {@link Organization} in the database
     * @param organizationSaveView save parameters
     * @return true if successfully saved
     */
    @PostMapping(path = "/save")
    public Boolean saveOrganization(@RequestBody @Valid OrganizationSaveView organizationSaveView) {
        return organizationDataService.save(organizationSaveView);
    }
}