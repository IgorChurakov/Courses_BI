package bell.courses.controller;

import bell.courses.model.Organization;
import bell.courses.service.OrganizationDataService;
import bell.courses.view.request.OrganizationFilterView;
import bell.courses.view.request.OrganizationSaveView;
import bell.courses.view.request.OrganizationUpdateView;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping(value = "api/organization", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrganizationController {

    private final OrganizationDataService organizationDataService;

    @Autowired
    OrganizationController(OrganizationDataService organizationDataService) {
        this.organizationDataService = organizationDataService;
    }

    @GetMapping(path = "/{id}")
    public Organization getOrganization(@PathVariable long id) {
        return organizationDataService.get(id);
    }

    @PostMapping(path = "/list")
    public List<Organization> listOrganizations(@RequestBody @Valid OrganizationFilterView organizationFilterView) {
        return organizationDataService.list(organizationFilterView);
    }

    @PostMapping(path = "/update")
    public Boolean updateOrganization(@RequestBody @Valid OrganizationUpdateView organizationUpdateView) {
        return organizationDataService.update(organizationUpdateView);
    }

    @PostMapping(path = "/save")
    public Boolean saveOrganization(@RequestBody @Valid OrganizationSaveView organizationSaveView) {
        return organizationDataService.save(organizationSaveView);
    }
}