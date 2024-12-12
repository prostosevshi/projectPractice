package com.example.demo.repository;

import com.example.demo.model.ScientificExpert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScientificExpertRepository extends JpaRepository<ScientificExpert, Long> {

    List<ScientificExpert> findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCaseOrScientifiсDirectionContainingIgnoreCaseOrSpecializationContainingIgnoreCase(
            String name, String surname, String scientifiсDirection, String specialization);

    @Query("SELECT e FROM ScientificExpert e " +
            "WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(e.surname) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(e.scientifiсDirection) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(e.specialization) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<ScientificExpert> searchByQuery(@Param("query") String query, Pageable pageable);
}

