package ru.job4j.accidents.service.data;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.data.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    private static final Logger LOG = LoggerFactory.getLogger(UserService.class.getName());

    public Optional<User> save(User user) {
        Optional<User> rsl = Optional.empty();
        try {
            rsl = Optional.of(userRepository.save(user));
        } catch (Exception e) {
            LOG.error("Error!", e);
        }
        return rsl;
    }
//    public boolean save(User user) {
//        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
//            return false;
//        }
//        userRepository.save(user);
//        return true;
//    }
}
