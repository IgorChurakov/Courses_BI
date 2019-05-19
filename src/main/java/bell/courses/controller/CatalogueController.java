package bell.courses.controller;

import bell.courses.model.Countries;
import bell.courses.model.DocTypes;
import bell.courses.service.CatalogueDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class CatalogueController {
    private final CatalogueDataService catalogueDataService;

    @Autowired
    CatalogueController(CatalogueDataService catalogueDataService) {
        this.catalogueDataService = catalogueDataService;
    }

    @GetMapping("/docs")
    public List<DocTypes> getDocs() {
        return catalogueDataService.listDocs();
    }

    @GetMapping("/countries")
    public List<Countries> getCountries() {
        return catalogueDataService.listCountries();
    }
}