package me.gustavo.springordermanager.service.impl;

import me.gustavo.springordermanager.exception.EntityNotCreatedException;
import me.gustavo.springordermanager.model.User;
import me.gustavo.springordermanager.model.dto.UserDto;
import me.gustavo.springordermanager.repository.UserRepository;
import me.gustavo.springordermanager.service.intf.UserService;
import me.gustavo.springordermanager.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserServiceImpl extends BaseCRUDService<User, Long, UserRepository> implements UserService {

    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }

    @Override
    public User create(UserDto userDto) {
        User user = new User();

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());

        user = this.create(user);

        if (user.getId() == 0)
            throw new EntityNotCreatedException("User");


        return user;
    }

    @Override
    public Optional<User> update(UserDto userDto) {
        Optional<User> optUser = this.findById(userDto.getId());

        if (!optUser.isPresent())
            return Optional.empty();

        User user = optUser.get();

        if (!StringUtil.isEmpty(userDto.getName()))
            user.setName(userDto.getName());

        if (!StringUtil.isEmpty(userDto.getEmail()))
            user.setEmail(userDto.getEmail());

        return this.update(userDto.getId(), user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.repository.findUserByEmail(email);
    }

    @Override
    public Optional<User> update(User entity) {
        return this.update(entity.getId(), entity);
    }
}
