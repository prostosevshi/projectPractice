package com.example.demo.controller;

import com.example.demo.model.ScientificExpert;
import com.example.demo.service.ExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class ExpertController {

    @Autowired
    private ExpertService expertService;

    @GetMapping("/addExperts")
    public String showCreateForm() {
        return "createForm";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        ScientificExpert expert = expertService.getExpertById(id);

        // Генерация fullName на основе имени и фамилии
        String fullName = expert.getName() + " " + expert.getSurname();
        expert.setFullName(fullName);

        model.addAttribute("expert", expert);
        return "editExpert";  // Страница редактирования
    }

    @PostMapping("/update/{id}")
    public String updateExpert(@PathVariable Long id, @RequestParam String fullName,
                               @RequestParam String scientifiсDirection, @RequestParam String specialization) {
        ScientificExpert expert = expertService.getExpertById(id);
        expert.setFullName(fullName);
        expert.setScientifiсDirection(scientifiсDirection);
        expert.setSpecialization(specialization);

        expertService.saveExpert(expert);  // Сохраняем обновленные данные
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteExpert(@PathVariable Long id) {
        expertService.deleteExpert(id);
        return "redirect:/";
    }

    /*@GetMapping("/search")
    @ResponseBody
    public List<ScientificExpert> searchExperts(
            @RequestParam("query") String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return expertService.searchExperts(query, page, size); // Пагинация в поиске
    }*/


    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadExpertFile(@PathVariable Long id) {
        // Получаем объект эксперта по id
        ScientificExpert expert = expertService.getExpertById(id);

        if (expert == null) {
            // Если эксперт не найден, возвращаем ошибку 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Генерируем PDF-документ
        byte[] documentBytes = expertService.generateExpertDocument(id);

        // Формируем имя файла для скачивания (используем имя, фамилию и специализацию)
        String filename = expert.getName() + "_" + expert.getSurname() + "_" + expert.getSpecialization() + ".pdf";

        // Кодируем имя файла в UTF-8 для корректного отображения кириллицы
        try {
            filename = java.net.URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Заголовки для ответа
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

        // Возвращаем файл с соответствующими заголовками
        return new ResponseEntity<>(documentBytes, headers, HttpStatus.OK);
    }

}

