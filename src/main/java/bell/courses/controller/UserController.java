package bell.courses.controller;

import bell.courses.model.User;
import bell.courses.service.UserDataService;
import bell.courses.view.request.UserFilterView;
import bell.courses.view.request.UserSaveView;
import bell.courses.view.request.UserUpdateView;
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
 * Controller for operating with {@link User} entities
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@RestController
@RequestMapping(value = "api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserDataService userDataService;

    @Autowired
    UserController(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    /**
     * Method for getting a single User by it's ID
     * @param id user's ID
     * @return {@link User}
     */
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userDataService.get(id);
    }

    /**
     * Method for getting a list of users filtered by specified parameters
     * @param userFilterView filtering parameters
     * @return List of {@link User}
     */
    @PostMapping("/list")
    public List<User> listUsers(@RequestBody @Valid UserFilterView userFilterView) {
        return userDataService.list(userFilterView);
    }

    /**
     * Method for updating an {@link User} in the database
     * @param userUpdateView update parameters
     * @return true if successfully updated
     */
    @PostMapping("/update")
    public Boolean updateUser(@RequestBody @Valid UserUpdateView userUpdateView) {
        return userDataService.update(userUpdateView);
    }

    /**
     * Method for saving an {@link User} in the database
     * @param userSaveView save parameters
     * @return true if successfully saved
     */
    @PostMapping("/save")
    public Boolean saveUser(@RequestBody @Valid UserSaveView userSaveView) {
        return userDataService.save(userSaveView);
    }
}