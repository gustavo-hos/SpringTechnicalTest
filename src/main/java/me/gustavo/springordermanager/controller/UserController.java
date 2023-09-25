package me.gustavo.springordermanager.controller;

import me.gustavo.springordermanager.exception.EntityAlreadyExistsException;
import me.gustavo.springordermanager.model.User;
import me.gustavo.springordermanager.model.dto.UserDto;
import me.gustavo.springordermanager.service.intf.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves a list of all users.
     *
     * @return A ResponseEntity containing the list of users (HTTP status 200) or
     *         NO_CONTENT if no users are found (HTTP status 204).
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = this.userService.findAll();

        if (users.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(users);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return A ResponseEntity containing the user if found (HTTP status 200) or
     *         NOT_FOUND if the user does not exist (HTTP status 404).
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return this.userService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new user.
     *
     * @param userDto DTO representing the user to create.
     * @return A ResponseEntity containing the created user (HTTP status 200).
     * @throws EntityAlreadyExistsException if a user with the same email already exists.
     */
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        Optional<User> existingUser = this.userService.findByEmail(userDto.getEmail());

        if (existingUser.isPresent()) {
            throw new EntityAlreadyExistsException("user", "email", userDto.getEmail());
        }

        User user = this.userService.create(userDto);

        return ResponseEntity.ok(user);
    }

    /**
     * Updates an existing user.
     *
     * @param userDto DTO representing the user to update.
     * @return A ResponseEntity containing the updated user if found (HTTP status 200)
     *         or NOT_FOUND if the user does not exist (HTTP status 404).
     */
    @PutMapping("/user")
    public ResponseEntity<User> updateUser(@RequestBody UserDto userDto) {
        return this.userService.update(userDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete.
     * @return A ResponseEntity with no content (HTTP status 204) upon successful
     *         deletion.
     */
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        this.userService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
