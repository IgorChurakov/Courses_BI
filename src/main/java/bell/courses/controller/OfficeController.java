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

@RestController
@RequestMapping(value = "api/office", produces = MediaType.APPLICATION_JSON_VALUE)
public class OfficeController {
    private final OfficeDataService officeDataService;

    @Autowired
    OfficeController(OfficeDataService officeDataService) {
        this.officeDataService = officeDataService;
    }

    @GetMapping(path = "/{id}")
    public Office getOffice(@PathVariable Long id) {
        return officeDataService.get(id);
    }

    @PostMapping(path = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Office> listOffices(@RequestBody @Valid OfficeFilterView officeFilterView) {
        return officeDataService.list(officeFilterView);
    }

    @PostMapping(path = "/update")
    public Boolean updateOffice(@RequestBody @Valid OfficeUpdateView officeUpdateView) {
        return officeDataService.update(officeUpdateView);
    }

    @PostMapping(path = "/save")
    public Boolean saveOffice(@RequestBody @Valid OfficeSaveView officeSaveView) {
        return officeDataService.save(officeSaveView);
    }
}