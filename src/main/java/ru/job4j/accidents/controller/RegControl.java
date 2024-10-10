package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.model.Authority;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.AuthorRepository;
import ru.job4j.accidents.service.AuthorityService;
import ru.job4j.accidents.service.UserService;

@Controller
@AllArgsConstructor
public class RegControl {

    private final PasswordEncoder encoder;
    private final UserService userService;
    private final AuthorityService authorityService;
    private final AuthorRepository author;

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user, Authority authority, Model model) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(author.findByAuthority("ROLE_USER"));
        authority.setUsername(user.getUsername());
        authority.setAuthority("ROLE_USER");
        authorityService.save(authority);
        if (userService.save(user).isPresent()) {
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
