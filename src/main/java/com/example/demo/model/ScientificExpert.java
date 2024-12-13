package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "Scientific_Experts")
public class ScientificExpert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String scientifiсDirection;

    @Column(nullable = false)
    private String specialization;

    @Transient
    @JsonProperty("fullName")  // Обеспечиваем правильное отображение при сериализации
    private String fullName;

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        updateFullName();
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
        updateFullName();
    }

    public String getScientifiсDirection() {
        return scientifiсDirection;
    }

    public void setScientifiсDirection(String scientifiсDirection) {
        this.scientifiсDirection = scientifiсDirection;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getFullName() {
        return fullName;
    }

    // Логика для установки fullName и обновления name и surname
    public void setFullName(String fullName) {
        this.fullName = fullName;
        if (fullName != null) {
            String[] parts = fullName.split(" ", 2); // Разделяем на имя и фамилию
            this.name = parts[0];
            this.surname = parts.length > 1 ? parts[1] : ""; // Если фамилия отсутствует, заполняем пустую строку
        }
    }

    // Метод для обновления `fullName`, если изменяются name или surname
    private void updateFullName() {
        this.fullName = name + " " + surname;
    }
}
