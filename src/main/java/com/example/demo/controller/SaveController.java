package com.example.demo.controller;

import com.example.demo.model.ScientificExpert;
import com.example.demo.service.ExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SaveController {

    @Autowired
    private ExpertService expertService;

    @PostMapping("/save")
    public String SaveExperts(@RequestParam String fullName, @RequestParam String scientifiсDirection, @RequestParam String specialization, Model model) {

        ScientificExpert scientificExpert = new ScientificExpert();
        scientificExpert.setFullName(fullName);
        scientificExpert.setScientifiсDirection(scientifiсDirection);
        scientificExpert.setSpecialization(specialization);

        expertService.saveExpert(scientificExpert);

        model.addAttribute("successMessage", "Эксперт успешно добавлен!");
        return "createForm";
    }
}
