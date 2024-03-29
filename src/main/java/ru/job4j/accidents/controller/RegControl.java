package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.data.AuthorityRepository;
import ru.job4j.accidents.service.data.UserService;

@Controller
@AllArgsConstructor
public class RegControl {

    private final PasswordEncoder encoder;
    private final UserService users;
    private final AuthorityRepository authorities;

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user, Model model) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorities.findByAuthority("ROLE_USER"));
        if (users.save(user).isPresent()) {
            return "redirect:/login";

        }
        model.addAttribute("error", "This login already exists");
        return "reg";

    }

    @GetMapping("/reg")
    public String regPage() {
        return "reg";
    }
}
