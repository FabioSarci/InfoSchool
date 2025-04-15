package com.infoschool.infoschool.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course", uniqueConstraints = {
    @jakarta.persistence.UniqueConstraint(columnNames = {"codice", "anno"})
})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String codice;

    @Column(nullable = false)
    private int anno;

    private String nome;
    private String descrizione;

    @ManyToMany(mappedBy = "courses")
    private List<User> students;
}