package ru.job4j.accidents.service.data;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.data.UserRepository;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public boolean save(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return false;
        }
        userRepository.save(user);
        return true;
    }
}
