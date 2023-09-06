package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.impl.AccidentService;
import ru.job4j.accidents.service.impl.AccidentTypeService;
import ru.job4j.accidents.service.impl.RuleService;

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
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("accidents", accidentService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        return "accidents/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, Model model, HttpServletRequest httpServletRequest) {
        String[] ids = httpServletRequest.getParameterValues("rIds");
        accident.setRules(ruleService.findByIds(ids));
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
            return "errors/404";
        }
        accident.setType(accidentType.get());
        String[] ds = httpServletRequest.getParameterValues("rIds");
        var isUpdatedRules = ruleService.findByIds(ds);
        accident.setRules(isUpdatedRules);
        var isUpdated = accidentService.update(accident);
        if (!isUpdated || isUpdatedRules.isEmpty()) {
        model.addAttribute("message", "Обновление не выполнено");
            return "errors/404";
        }
        return "redirect:/index";
    }
}

