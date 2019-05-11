package bell.courses.service;

import bell.courses.dao.CountriesRepository;
import bell.courses.dao.DocTypesRepository;
import bell.courses.model.Countries;
import bell.courses.model.DocTypes;
import bell.courses.view.ApiException;
import bell.courses.view.CatalogueListingView;
import bell.courses.view.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<ResponseView> listDocs() {
        List<DocTypes> docTypes = docTypesRepository.getAllBy();
        if (docTypes.isEmpty()) {
            throw new ApiException("There's no docTypes in the database");
        }
        List<ResponseView> result = new ArrayList<>();
        for (DocTypes docType : docTypes) {
            result.add(new CatalogueListingView(docType.getName(), docType.getCode()));
        }
        return result;
    }

    public List<ResponseView> listCountries() {
        List<Countries> countries = countriesRepository.getAllBy();
        if (countries.isEmpty()) {
            throw new ApiException("There's no countries in the database");
        }
        List<ResponseView> result = new ArrayList<>();
        for (Countries country : countries) {
            result.add(new CatalogueListingView(country.getName(), country.getCode()));
        }
        return result;
    }
}