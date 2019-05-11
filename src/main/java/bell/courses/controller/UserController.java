package bell.courses.controller;

import bell.courses.service.UserDataService;
import bell.courses.view.ResponseView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserDataService userDataService;

    @Autowired
    UserController(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @GetMapping("/{id}")
    public ResponseView getUser(@PathVariable Long id) {
        return userDataService.get(id);
    }

    @PostMapping("/list")
    public List<ResponseView> listUsers(@RequestParam Long officeId,
                                        @RequestParam(required = false) String firstName,
                                        @RequestParam(required = false) String lastName,
                                        @RequestParam(required = false) String middleName,
                                        @RequestParam(required = false) String position,
                                        @RequestParam(required = false) Integer docCode,
                                        @RequestParam(required = false) Integer citizenshipCode) {
        return userDataService.list(officeId, firstName, lastName, middleName, position, docCode, citizenshipCode);
    }

    @PostMapping("/save")
    public ResponseView saveUser(@RequestParam Long officeId,
                                 @RequestParam String firstName,
                                 @RequestParam(required = false) String secondName,
                                 @RequestParam(required = false) String middleName,
                                 @RequestParam String position,
                                 @RequestParam(required = false) String phone,
                                 @RequestParam(required = false) Integer docCode,
                                 @RequestParam(required = false) String docName,
                                 @RequestParam(required = false) String docNumber,
                                 @RequestParam(required = false) String docDate,
                                 @RequestParam(required = false) Integer citizenshipCode,
                                 @RequestParam(required = false) Boolean isIdentified) {
        Date documentDate = null;
        if (docDate!=null) {
            documentDate = Date.valueOf(docDate);
        }
        return userDataService.save(officeId, firstName, secondName, middleName, position, phone, docCode, docName, docNumber, documentDate, citizenshipCode, isIdentified);
    }

    @PostMapping("/update")
    public ResponseView updateUser(@RequestParam Long id,
                                   @RequestParam(required = false) Long officeId,
                                   @RequestParam String firstName,
                                   @RequestParam(required = false) String secondName,
                                   @RequestParam(required = false) String middleName,
                                   @RequestParam String position,
                                   @RequestParam(required = false) String phone,
                                   @RequestParam(required = false) String docName,
                                   @RequestParam(required = false) String docNumber,
                                   @RequestParam(required = false) Date docDate,
                                   @RequestParam(required = false) Integer citizenshipCode,
                                   @RequestParam(required = false) Boolean isIdentified) {
        return userDataService.update(id, officeId, firstName, secondName, middleName, position, phone, docName, docNumber, docDate, citizenshipCode, isIdentified);
    }
}