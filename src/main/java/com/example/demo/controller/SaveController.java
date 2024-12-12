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

    /*@Autowired
    private PDFService pdfService;*/

    @PostMapping("/save")
    public String SaveExperts(@RequestParam String name, @RequestParam String surname, @RequestParam String scientifiсDirection, @RequestParam String specialization, Model model) {

        ScientificExpert scientificExpert = new ScientificExpert();
        scientificExpert.setName(name);
        scientificExpert.setSurname(surname);
        scientificExpert.setScientifiсDirection(scientifiсDirection);
        scientificExpert.setSpecialization(specialization);

        expertService.saveExpert(scientificExpert);

        /*pdfService.createPdf(name, surname, scientifiсDirection, specialization);*/

        model.addAttribute("successMessage", "Эксперт успешно добавлен!");
        return "createForm";
    }
}
