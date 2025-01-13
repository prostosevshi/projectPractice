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

    public byte[] generateExpertDocument(Long id) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            ScientificExpert expert = getExpertById(id);

            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            PdfFont font = PdfFontFactory.createFont("src/main/resources/fonts/ARIAL.ttf");

            document.add(new Paragraph("Имя, фамилия эксперта: " + expert.getFullName()).setFont(font));
            document.add(new Paragraph("Научное направление ОЭСР: " + expert.getScientifiсDirection()).setFont(font));
            document.add(new Paragraph("Специализация: " + expert.getSpecialization()).setFont(font));

            document.close();

            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании PDF документа", e);
        }
    }
}
