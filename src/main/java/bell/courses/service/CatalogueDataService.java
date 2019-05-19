package bell.courses.service;

import bell.courses.dao.CountriesRepository;
import bell.courses.dao.DocTypesRepository;
import bell.courses.error.ApiException;
import bell.courses.model.Countries;
import bell.courses.model.DocTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogueDataService {
    private final DocTypesRepository docTypesRepository;
    private final CountriesRepository countriesRepository;

    @Autowired
    CatalogueDataService(DocTypesRepository docTypesRepository, CountriesRepository countriesRepository) {
        this.docTypesRepository = docTypesRepository;
        this.countriesRepository = countriesRepository;
    }

    public List<DocTypes> listDocs() {
        List<DocTypes> docTypes = docTypesRepository.getAllBy();
        if (docTypes.isEmpty()) {
            throw new ApiException("There's no docTypes in the database");
        }
        return docTypes;
    }

    public List<Countries> listCountries() {
        List<Countries> countries = countriesRepository.getAllBy();
        if (countries.isEmpty()) {
            throw new ApiException("There's no countries in the database");
        }
        return countries;
    }
}