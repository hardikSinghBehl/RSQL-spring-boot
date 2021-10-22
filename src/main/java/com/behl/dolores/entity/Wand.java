package com.behl.dolores.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "wands")
@Data
public class Wand implements Serializable {

    private static final long serialVersionUID = -8195832520871706503L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "wood", nullable = true)
    private String wood;

    @Column(name = "core", nullable = true)
    private String core;

    @Column(name = "length_in_inches", nullable = true)
    private Double length;

}
