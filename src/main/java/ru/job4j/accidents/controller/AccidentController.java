package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@AllArgsConstructor
@RequestMapping("/accidents") /* Работать с заявками будем по URI /accidents/** */
public class AccidentController {

    private final AccidentService accidentService;

    private final AccidentTypeService accidentTypeService;

    private final RuleService ruleService;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        List<AccidentType> types = new ArrayList<>();
        types.add(new AccidentType(1, "Две машины"));
        types.add(new AccidentType(2, "Машина и человек"));
        types.add(new AccidentType(3, "Машина и велосипед"));
        model.addAttribute("types", accidentTypeService.findAll());
        Set<Rule> rules = Set.of(
                new Rule(1, "Статья. 1"),
                new Rule(2, "Статья. 2"),
                new Rule(3, "Статья. 3")
        );
        model.addAttribute("accidents", accidentService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        return "accidents/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, Model model, HttpServletRequest httpServletRequest) {
        String[] ids = httpServletRequest.getParameterValues("rIds");
        Set<Rule> rules = new HashSet<>();
        for (String i : ids) {
            Optional<Rule> rule = ruleService
                    .findById(
                            Integer.parseInt(i)
                    );
            if (rule.isEmpty()) {
                return "404";
            }
            rules.add(rule.get());
        }
        accident.setRules(rules);
        accidentService.create(accident);
        model.addAttribute("message");
        return "redirect:/index";
    }

    @GetMapping("/formUpdateAccident")
    public String update(@RequestParam("id") int id, Model model) {
                var accidentOptional = accidentService.findById(id);
        if (accidentOptional.isEmpty()) {
            model.addAttribute("message", "Заявка с указанным идентификатором не найдена");
            return "errors/404";
        }
        model.addAttribute("accident", accidentService.findById(id).get());
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        return "accidents/updateAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident, Model model, HttpServletRequest httpServletRequest) {
        Optional<AccidentType> accidentType = accidentTypeService
                .findById(
                        accident.getType()
                                .getId()
                );
        if (accidentType.isEmpty()) {
            return "404";
        }
        accident.setType(accidentType.get());
        String[] ds = httpServletRequest.getParameterValues("rIds");
        Set<Rule> rules = new HashSet<>();
        for (String i : ds) {
            Optional<Rule> rule = ruleService
                    .findById(
                            Integer.parseInt(i)
                    );
            if (rule.isEmpty()) {
                return "404";
            }
            rules.add(rule.get());
        }
        accident.setRules(rules);
        accidentService.update(accident);
        model.addAttribute("message");
        return "redirect:/index";
    }
}

