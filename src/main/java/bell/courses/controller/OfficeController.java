package bell.courses.controller;

import bell.courses.model.Office;
import bell.courses.service.OfficeDataService;
import bell.courses.view.request.OfficeFilterView;
import bell.courses.view.request.OfficeSaveView;
import bell.courses.view.request.OfficeUpdateView;
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
 * Controller class for operating with {@link Office} entities
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@RestController
@RequestMapping(value = "api/office", produces = MediaType.APPLICATION_JSON_VALUE)
public class OfficeController {
    private final OfficeDataService officeDataService;

    @Autowired
    OfficeController(OfficeDataService officeDataService) {
        this.officeDataService = officeDataService;
    }

    /**
     * Method for getting Office by it's ID
     * @param id office's ID
     * @return {@link Office} entity
     */
    @GetMapping(path = "/{id}")
    public Office getOffice(@PathVariable Long id) {
        return officeDataService.get(id);
    }

    /**
     * Method for getting list of offices filtered by specified parameters
     * @param officeFilterView filtering parameters
     * @return List of {@link Office}
     */
    @PostMapping(path = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Office> listOffices(@RequestBody @Valid OfficeFilterView officeFilterView) {
        return officeDataService.list(officeFilterView);
    }

    /**
     * Method for updating an {@link Office} in the database
     * @param officeUpdateView update parameters
     * @return true if successfully updated
     */
    @PostMapping(path = "/update")
    public Boolean updateOffice(@RequestBody @Valid OfficeUpdateView officeUpdateView) {
        return officeDataService.update(officeUpdateView);
    }

    /**
     * Method for creating a new {@link Office} in the database
     * @param officeSaveView save parameters
     * @return true if successfully saved
     */
    @PostMapping(path = "/save")
    public Boolean saveOffice(@RequestBody @Valid OfficeSaveView officeSaveView) {
        return officeDataService.save(officeSaveView);
    }
}