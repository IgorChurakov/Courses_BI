package bell.courses.controller;

import bell.courses.model.User;
import bell.courses.service.UserDataService;
import bell.courses.view.request.UserFilterView;
import bell.courses.view.request.UserSaveView;
import bell.courses.view.request.UserUpdateView;
import lombok.extern.slf4j.Slf4j;
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
    public User getUser(@PathVariable Long id) {
        return userDataService.get(id);
    }

    @PostMapping("/list")
    public List<User> listUsers(@RequestBody @Valid UserFilterView userFilterView) {
        return userDataService.list(userFilterView);
    }

    @PostMapping("/save")
    public Boolean saveUser(@RequestBody @Valid UserSaveView userSaveView) {
        return userDataService.save(userSaveView);
    }

    @PostMapping("/update")
    public Boolean updateUser(@RequestBody @Valid UserUpdateView userUpdateView) {
        return userDataService.update(userUpdateView);
    }
}