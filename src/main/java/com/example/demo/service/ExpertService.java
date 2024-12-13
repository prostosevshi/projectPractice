package com.example.demo.service;

import com.example.demo.model.ScientificExpert;
import com.example.demo.repository.ScientificExpertRepository;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ExpertService {

    @Autowired
    private ScientificExpertRepository scientificExpertRepository;

    public List<ScientificExpert> getAllExperts() {
        return scientificExpertRepository.findAll();
    }

    public ScientificExpert getExpertById(Long id) {
        return scientificExpertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Эксперт не найден"));
    }

    public void deleteExpert(Long id) {
        scientificExpertRepository.deleteById(id);
    }

    public void saveExpert(ScientificExpert expert) {
        scientificExpertRepository.save(expert);
    }

    /*public List<ScientificExpert> searchExperts(String keyword) {
        return scientificExpertRepository.findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCaseOrScientifiсDirectionContainingIgnoreCaseOrSpecializationContainingIgnoreCase(
                keyword, keyword, keyword, keyword);
    }*/

    /*// Пагинированный вывод всех экспертов
    public List<ScientificExpert> getExperts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ScientificExpert> expertsPage = scientificExpertRepository.findAll(pageable);
        return expertsPage.getContent();
    }

    // Пагинированный поиск
    public List<ScientificExpert> searchExperts(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ScientificExpert> expertsPage = scientificExpertRepository.searchByQuery(query, pageable);
        return expertsPage.getContent();
    }*/

    // Метод для генерации PDF документа
    public byte[] generateExpertDocument(Long id) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            // Получаем объект эксперта
            ScientificExpert expert = getExpertById(id);

            // Создаем PDF-документ
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Загружаем шрифт, поддерживающий кириллицу (например, Arial)
            PdfFont font = PdfFontFactory.createFont("src/main/resources/fonts/ARIAL.ttf");

            // Добавляем содержимое
            document.add(new Paragraph("Эксперт ID: " + expert.getId()).setFont(font));
            document.add(new Paragraph("Имя: " + expert.getName()).setFont(font));
            document.add(new Paragraph("Фамилия: " + expert.getSurname()).setFont(font));
            document.add(new Paragraph("Научное направление: " + expert.getScientifiсDirection()).setFont(font));
            document.add(new Paragraph("Специализация: " + expert.getSpecialization()).setFont(font));

            // Закрываем документ
            document.close();

            // Возвращаем байты PDF
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании PDF документа", e);
        }
    }
}
