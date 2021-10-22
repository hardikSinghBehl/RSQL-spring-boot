package com.behl.dolores.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.behl.dolores.entity.Wand;

@Repository
public interface WandRepository extends JpaRepository<Wand, Integer> {

}
