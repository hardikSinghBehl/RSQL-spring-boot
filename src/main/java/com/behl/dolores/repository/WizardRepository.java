package com.behl.dolores.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.behl.dolores.entity.Wizard;

@Repository
public interface WizardRepository extends JpaRepository<Wizard, Integer> {

}
