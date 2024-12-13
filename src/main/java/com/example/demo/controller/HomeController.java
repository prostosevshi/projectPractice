package com.example.demo.controller;


import com.example.demo.model.ScientificExpert;
import com.example.demo.service.ExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    ExpertService expertService;

    @GetMapping("/")
    public String showExperts(Model model, @RequestParam(required = false) String keyword) {
        /*List<ScientificExpert> experts;
        if (keyword != null && !keyword.isEmpty()) {
            experts = expertService.searchExperts(keyword);
        } else {
            experts = expertService.getAllExperts();
        }

        model.addAttribute("experts", experts);
        model.addAttribute("keyword", keyword);*/
        return "tableOfExperts";
    }

    @GetMapping("/experts")
    @ResponseBody
    public List<ScientificExpert> getExperts() {
        List<ScientificExpert> experts = expertService.getAllExperts();

        // Обновляем fullName для каждого эксперта перед отправкой
        for (ScientificExpert expert : experts) {
            expert.setFullName(expert.getName() + " " + expert.getSurname());
        }

        return experts;
    }


    /*public String home() {
        return "Welcome to the application!";
    }*/
}
