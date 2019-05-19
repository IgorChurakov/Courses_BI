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

/**
 * Controller class for the catalogues, such as {@link DocTypes} and {@link Countries}
 *
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class CatalogueController {
    private final CatalogueDataService catalogueDataService;

    @Autowired
    CatalogueController(CatalogueDataService catalogueDataService) {
        this.catalogueDataService = catalogueDataService;
    }

    /**
     * Method for getting {@link DocTypes} from the database
     * @return List of {@link DocTypes}
     */
    @GetMapping("/docs")
    public List<DocTypes> getDocs() {
        return catalogueDataService.listDocs();
    }

    /**
     * Method for getting {@link Countries} from the database
     * @return List of {@link Countries}
     */
    @GetMapping("/countries")
    public List<Countries> getCountries() {
        return catalogueDataService.listCountries();
    }
}