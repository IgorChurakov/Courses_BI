package bell.courses.controller;

import bell.courses.service.OfficeDataService;
import bell.courses.view.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseView getOffice(@PathVariable Long id) {
        return officeDataService.get(id);
    }

    @PostMapping(path = "/list")
    public List<ResponseView> listOffices(@RequestParam Long orgId,
                                          @RequestParam(required = false) String name,
                                          @RequestParam(required = false) String phone,
                                          @RequestParam(required = false) Boolean isActive) {
        return officeDataService.list(orgId, name, phone, isActive);
    }

    @PostMapping(path = "/update")
    public ResponseView updateOffice(@RequestParam Long id,
                                     @RequestParam String name,
                                     @RequestParam String address,
                                     @RequestParam(required = false) String phone,
                                     @RequestParam(required = false) Boolean isActive) {
        return officeDataService.update(id, name, address, phone, isActive);
    }

    @PostMapping(path = "/save")
    public ResponseView saveOffice(@RequestParam Long orgId,
                                   @RequestParam String name,
                                   @RequestParam String address,
                                   @RequestParam(required = false) String phone,
                                   @RequestParam(required = false) Boolean isActive) {
        return officeDataService.save(orgId, name, address, phone, isActive);
    }
}