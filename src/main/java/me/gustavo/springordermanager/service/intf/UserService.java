package me.gustavo.springordermanager.service.intf;

import me.gustavo.springordermanager.model.User;
import me.gustavo.springordermanager.model.dto.UserDto;

import java.util.Optional;

public interface UserService extends CRUDService<User, Long> {

    User create(UserDto userDto);

    Optional<User> update(UserDto userDto);

    Optional<User> findByEmail(String email);
}
