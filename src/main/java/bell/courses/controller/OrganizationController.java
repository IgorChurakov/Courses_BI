package bell.courses.controller;

import bell.courses.service.OrganizationDataService;
import bell.courses.view.Responseable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/organization")
public class OrganizationController {

    private OrganizationDataService organizationDataService;

    @Autowired
    OrganizationController(OrganizationDataService organizationDataService) {
        this.organizationDataService = organizationDataService;
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Responseable getOrganization(@PathVariable long id) {
        return organizationDataService.get(id);
    }

    @PostMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Responseable> listOrganizations(@RequestParam String name,
                                                @RequestParam(required = false) String inn,
                                                @RequestParam(required = false) Boolean isActive) {
        return organizationDataService.list(name, inn, isActive);
    }

    @PostMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public Responseable updateOrganization(@RequestParam Long id,
                                           @RequestParam String name,
                                           @RequestParam String fullName,
                                           @RequestParam String inn,
                                           @RequestParam String kpp,
                                           @RequestParam String address,
                                           @RequestParam(required = false) String phone,
                                           @RequestParam(required = false) Boolean isActive) {
        return organizationDataService.update(id, name, fullName, inn, kpp, address, phone, isActive);
    }

    @PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public Responseable saveOrganization(@RequestParam String name,
                                         @RequestParam String fullName,
                                         @RequestParam String inn,
                                         @RequestParam String kpp,
                                         @RequestParam String address,
                                         @RequestParam(required = false) String phone,
                                         @RequestParam(required = false) Boolean isActive) {
        return organizationDataService.save(name,fullName,inn,kpp,address,phone,isActive);
    }
}