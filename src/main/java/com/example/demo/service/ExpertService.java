package com.example.demo.service;

import com.example.demo.model.ScientificExpert;
import com.example.demo.repository.ScientificExpertRepository;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.awt.*;
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
            document.add(new Paragraph("Имя, фамилия эксперта: " + expert.getFullName()).setFont(font));
            document.add(new Paragraph("Научное направление ОЭСР: " + expert.getScientifiсDirection()).setFont(font));
            document.add(new Paragraph("Специализация: " + expert.getSpecialization()).setFont(font));

            // Закрываем документ
            document.close();

            // Возвращаем байты PDF
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании PDF документа", e);
        }
    }

    /*public byte[] generateExpertDocument(Long id) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            // Получаем объект эксперта
            ScientificExpert expert = getExpertById(id);

            // Создаем PDF-документ
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Загружаем шрифт, поддерживающий кириллицу (например, Arial)
            PdfFont font = PdfFontFactory.createFont("src/main/resources/fonts/ARIAL.ttf");

            // Заголовок
            Paragraph title = new Paragraph("Данные эксперта")
                    .setFont(font)
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(title);

            // Создание таблицы с 2 столбцами
            Table table = new Table(2);

            // Настройка столбцов
            table.addCell(new Cell().add(new Paragraph("Имя, фамилия эксперта").setFont(font).setBold()));
            table.addCell(new Cell().add(new Paragraph(expert.getFullName()).setFont(font)));

            table.addCell(new Cell().add(new Paragraph("Научное направление ОЭСР").setFont(font).setBold()));
            table.addCell(new Cell().add(new Paragraph(expert.getScientifiсDirection()).setFont(font)));

            table.addCell(new Cell().add(new Paragraph("Специализация").setFont(font).setBold()));
            table.addCell(new Cell().add(new Paragraph(expert.getSpecialization()).setFont(font)));

            // Добавляем таблицу в документ
            document.add(table);

            // Закрываем документ
            document.close();

            // Возвращаем байты PDF
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании PDF документа", e);
        }
    }*/
}
