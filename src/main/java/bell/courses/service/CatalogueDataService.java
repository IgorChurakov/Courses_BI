package bell.courses.service;

import bell.courses.dao.CountriesRepository;
import bell.courses.dao.DocTypesRepository;
import bell.courses.error.ApiException;
import bell.courses.model.Countries;
import bell.courses.model.DocTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for operating with {@link DocTypes} and {@link Countries} catalogues
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@Service
public class CatalogueDataService {
    private final DocTypesRepository docTypesRepository;
    private final CountriesRepository countriesRepository;

    @Autowired
    CatalogueDataService(DocTypesRepository docTypesRepository, CountriesRepository countriesRepository) {
        this.docTypesRepository = docTypesRepository;
        this.countriesRepository = countriesRepository;
    }

    /**
     * Method for getting a list of Document Types
     * @return List of {@link DocTypes}
     */
    public List<DocTypes> listDocs() {
        List<DocTypes> docTypes = docTypesRepository.getAllBy();
        if (docTypes.isEmpty()) {
            throw new ApiException("There's no docTypes in the database");
        }
        return docTypes;
    }

    /**
     * Method for getting a list of Countries
     * @return List of {@link Countries}
     */
    public List<Countries> listCountries() {
        List<Countries> countries = countriesRepository.getAllBy();
        if (countries.isEmpty()) {
            throw new ApiException("There's no countries in the database");
        }
        return countries;
    }
}