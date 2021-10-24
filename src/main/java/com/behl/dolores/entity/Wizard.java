package com.behl.dolores.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.behl.dolores.entity.constant.Gender;
import com.behl.dolores.entity.constant.Species;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Table(name = "wizards")
@Data
public class Wizard implements Serializable {

    private static final long serialVersionUID = -5538436922346979932L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "species", nullable = true)
    private Species species;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "gender", nullable = true)
    private Gender gender;

    @Exclude
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "house_id", nullable = true)
    private MasterHouse house;

    @Column(name = "date_of_birth", nullable = true)
    private LocalDate dateOfBirth;

    @Column(name = "eye_color", nullable = true)
    private String eyeColor;

    @Column(name = "hair_color", nullable = true)
    private String hairColor;

    @Exclude
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "wand_id", nullable = true)
    private Wand wand;

    @Column(name = "patronus", nullable = true)
    private String patronus;

    @Column(name = "is_professor", nullable = true)
    private Boolean isProfessor;

    @Column(name = "alive", nullable = true)
    private Boolean alive;

    @Column(name = "image_url", nullable = true)
    private String imageUrl;

}
