package bell.courses.controller;

import bell.courses.service.CatalogueDataService;
import bell.courses.view.ResponseView;
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
    public List<ResponseView> getDocs() {
        return catalogueDataService.listDocs();
    }

    @GetMapping("/countries")
    public List<ResponseView> getCountries() {
        return catalogueDataService.listCountries();
    }
}